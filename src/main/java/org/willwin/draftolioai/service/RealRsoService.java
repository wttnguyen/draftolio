package org.willwin.draftolioai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.willwin.draftolioai.config.RsoProperties;
import org.willwin.draftolioai.dto.AuthenticationState;
import org.willwin.draftolioai.dto.RsoTokenResponse;
import org.willwin.draftolioai.dto.RsoUserInfo;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Real implementation of RSO service for production use.
 * <p>
 * This service communicates with actual RSO endpoints to perform
 * OAuth 2.0 authentication flow with Riot Games.
 */
@Slf4j
@Service
@ConditionalOnProperty(
        name = "rso.mock-enabled",
        havingValue = "false",
        matchIfMissing = true
)
public class RealRsoService implements RsoService
{

    private final RsoProperties rsoProperties;

    private final WebClient webClient;

    private final SecureRandom secureRandom;

    private final ConcurrentMap<String, AuthenticationState> authenticationStates;

    private final String basicAuthHeader;

    public RealRsoService(final RsoProperties rsoProperties, final WebClient.Builder webClientBuilder)
    {
        this.rsoProperties = rsoProperties;
        this.secureRandom = new SecureRandom();
        this.authenticationStates = new ConcurrentHashMap<>();

        // Create WebClient with base configuration
        this.webClient = webClientBuilder
                .baseUrl(rsoProperties.getEndpoints().getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        // Pre-compute basic auth header
        this.basicAuthHeader = createBasicAuthHeader(
                rsoProperties.getClient().getClientId(),
                rsoProperties.getClient().getClientSecret()
        );

        log.info("[DEBUG_LOG] RealRsoService initialized - will use actual RSO endpoints");
    }

    @Override
    public String generateAuthorizationUrl(final String redirectUrl)
    {
        log.debug("[DEBUG_LOG] Generating authorization URL for redirect: {}", redirectUrl);

        final String state = generateSecureState();
        final AuthenticationState authState = new AuthenticationState(
                state, Instant.now(), redirectUrl,
                generateSessionId()
        );

        storeAuthenticationState(authState);

        // Build the authorization URL according to RSO specification
        final String authUrl = String.format(
                "%s%s?redirect_uri=%s&client_id=%s&response_type=code&scope=%s&state=%s",
                rsoProperties.getEndpoints().getBaseUrl(), rsoProperties.getEndpoints().getAuthorizationPath(),
                rsoProperties.getClient().getRedirectUri(), rsoProperties.getClient().getClientId(),
                rsoProperties.getClient().getScopes(), state
        );

        log.debug("[DEBUG_LOG] Generated authorization URL: {}", authUrl);
        return authUrl;
    }

    @Override
    public RsoTokenResponse exchangeCodeForTokens(final String code, final String state)
    {
        log.debug("[DEBUG_LOG] Exchanging authorization code for tokens - code: {}, state: {}", code, state);

        if (!validateState(state))
        {
            log.warn("[DEBUG_LOG] Invalid state parameter: {}", state);
            throw new IllegalArgumentException("Invalid state parameter");
        }

        // Prepare form data for token request
        final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", code);
        formData.add("redirect_uri", rsoProperties.getClient().getRedirectUri());

        try
        {
            final RsoTokenResponse tokenResponse = webClient
                    .post()
                    .uri(rsoProperties.getEndpoints().getTokenPath())
                    .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(RsoTokenResponse.class)
                    .timeout(rsoProperties.getTokens().getRequestTimeout())
                    .block();

            // Clean up authentication state
            removeAuthenticationState(state);

            log.info("[DEBUG_LOG] Successfully exchanged authorization code for tokens");
            return tokenResponse;

        }
        catch (WebClientResponseException e)
        {
            log.error(
                    "[DEBUG_LOG] Failed to exchange authorization code: {} - {}", e.getStatusCode(),
                    e.getResponseBodyAsString()
            );
            throw new IllegalArgumentException("Failed to exchange authorization code: " + e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.error("[DEBUG_LOG] Unexpected error during token exchange", e);
            throw new IllegalArgumentException("Token exchange failed: " + e.getMessage(), e);
        }
    }

    @Override
    public RsoTokenResponse refreshAccessToken(final String refreshToken)
    {
        log.debug("[DEBUG_LOG] Refreshing access token");

        if (refreshToken == null || refreshToken.trim().isEmpty())
        {
            log.warn("[DEBUG_LOG] Refresh token is null or empty");
            throw new IllegalArgumentException("Refresh token is required");
        }

        // Prepare form data for refresh request
        final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", refreshToken);
        formData.add("scope", rsoProperties.getClient().getScopes());

        try
        {
            final RsoTokenResponse tokenResponse = webClient
                    .post()
                    .uri(rsoProperties.getEndpoints().getTokenPath())
                    .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(RsoTokenResponse.class)
                    .timeout(rsoProperties.getTokens().getRequestTimeout())
                    .block();

            log.info("[DEBUG_LOG] Successfully refreshed access token");
            return tokenResponse;

        }
        catch (WebClientResponseException e)
        {
            log.error(
                    "[DEBUG_LOG] Failed to refresh access token: {} - {}", e.getStatusCode(),
                    e.getResponseBodyAsString()
            );
            throw new IllegalArgumentException("Failed to refresh access token: " + e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.error("[DEBUG_LOG] Unexpected error during token refresh", e);
            throw new IllegalArgumentException("Token refresh failed: " + e.getMessage(), e);
        }
    }

    @Override
    public RsoUserInfo getUserInfo(final String accessToken)
    {
        log.debug("[DEBUG_LOG] Getting user info with access token");

        if (accessToken == null || accessToken.trim().isEmpty())
        {
            log.warn("[DEBUG_LOG] Access token is null or empty");
            throw new IllegalArgumentException("Access token is required");
        }

        try
        {
            final RsoUserInfo userInfo = webClient
                    .get()
                    .uri(rsoProperties.getEndpoints().getUserInfoPath())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(RsoUserInfo.class)
                    .timeout(rsoProperties.getTokens().getRequestTimeout())
                    .block();

            log.info(
                    "[DEBUG_LOG] Successfully retrieved user info: sub={}, cpid={}", userInfo.getSub(),
                    userInfo.getCpid()
            );
            return userInfo;

        }
        catch (WebClientResponseException e)
        {
            log.error("[DEBUG_LOG] Failed to get user info: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new IllegalArgumentException("Failed to get user info: " + e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.error("[DEBUG_LOG] Unexpected error during user info retrieval", e);
            throw new IllegalArgumentException("User info retrieval failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean validateState(final String state)
    {
        if (state == null || state.trim().isEmpty())
        {
            log.debug("[DEBUG_LOG] State parameter is null or empty");
            return false;
        }

        final AuthenticationState authState = authenticationStates.get(state);
        if (authState == null)
        {
            log.debug("[DEBUG_LOG] No authentication state found for: {}", state);
            return false;
        }

        if (authState.isExpired(15))
        { // 15 minutes expiration
            log.debug("[DEBUG_LOG] Authentication state expired for: {}", state);
            authenticationStates.remove(state);
            return false;
        }

        log.debug("[DEBUG_LOG] State parameter is valid: {}", state);
        return true;
    }

    @Override
    public void storeAuthenticationState(final AuthenticationState state)
    {
        log.debug("[DEBUG_LOG] Storing authentication state: {}", state.getState());
        authenticationStates.put(state.getState(), state);
    }

    @Override
    public AuthenticationState getAuthenticationState(final String stateParam)
    {
        log.debug("[DEBUG_LOG] Retrieving authentication state for: {}", stateParam);
        return authenticationStates.get(stateParam);
    }

    @Override
    public void removeAuthenticationState(final String stateParam)
    {
        log.debug("[DEBUG_LOG] Removing authentication state for: {}", stateParam);
        authenticationStates.remove(stateParam);
    }

    /**
     * Generate a cryptographically secure state parameter.
     *
     * @return A secure random state string
     */
    private String generateSecureState()
    {
        final byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    /**
     * Generate a session ID.
     *
     * @return A session identifier
     */
    private String generateSessionId()
    {
        final byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return "session_" + Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    /**
     * Create Basic Authentication header value.
     *
     * @param clientId     The client ID
     * @param clientSecret The client secret
     * @return Base64 encoded basic auth header value
     */
    private String createBasicAuthHeader(final String clientId, final String clientSecret)
    {
        final String credentials = clientId + ":" + clientSecret;
        final String encodedCredentials = Base64
                .getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }

}
