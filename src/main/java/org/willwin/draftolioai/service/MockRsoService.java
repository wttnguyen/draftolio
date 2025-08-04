package org.willwin.draftolioai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.willwin.draftolioai.config.RsoProperties;
import org.willwin.draftolioai.dto.AuthenticationState;
import org.willwin.draftolioai.dto.RsoTokenResponse;
import org.willwin.draftolioai.dto.RsoUserInfo;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Mock implementation of RSO service for development purposes.
 * <p>
 * This service always authenticates successfully without contacting real RSO endpoints.
 * It generates mock tokens and user information for testing and development.
 */
@Slf4j
@Service
@ConditionalOnProperty(
        name = "rso.mock-enabled",
        havingValue = "true"
)
public class MockRsoService implements RsoService
{

    private final RsoProperties rsoProperties;

    private final SecureRandom secureRandom;

    private final ConcurrentMap<String, AuthenticationState> authenticationStates;

    // Mock data constants
    private static final String MOCK_ACCESS_TOKEN = "mock_access_token_";

    private static final String MOCK_REFRESH_TOKEN = "mock_refresh_token_";

    private static final String MOCK_ID_TOKEN = "mock_id_token_";

    private static final String MOCK_SUB_SID = "mock_sub_sid_";

    private static final String MOCK_USER_SUB = "mock_user_12345";

    private static final String MOCK_CPID = "NA1";

    private static final String MOCK_SCOPE = "openid cpid offline_access";

    private static final String MOCK_TOKEN_TYPE = "Bearer";

    private static final int MOCK_EXPIRES_IN = 600; // 10 minutes

    public MockRsoService(final RsoProperties rsoProperties)
    {
        this.rsoProperties = rsoProperties;
        this.secureRandom = new SecureRandom();
        this.authenticationStates = new ConcurrentHashMap<>();

        log.info("[DEBUG_LOG] MockRsoService initialized - authentication will always succeed");
    }

    @Override
    public String generateAuthorizationUrl(final String redirectUrl)
    {
        log.debug("[DEBUG_LOG] Generating mock authorization URL for redirect: {}", redirectUrl);

        final String state = generateSecureState();
        final AuthenticationState authState = new AuthenticationState(
                state, Instant.now(), redirectUrl,
                generateSessionId()
        );

        storeAuthenticationState(authState);

        // Return a mock authorization URL that would redirect back with a mock code
        final String mockAuthUrl = String.format(
                "%s%s?redirect_uri=%s&client_id=%s&response_type=code&scope=%s&state=%s",
                rsoProperties.getEndpoints().getBaseUrl(), rsoProperties.getEndpoints().getAuthorizationPath(),
                rsoProperties.getClient().getRedirectUri(), rsoProperties.getClient().getClientId(),
                rsoProperties.getClient().getScopes(), state
        );

        log.debug("[DEBUG_LOG] Generated mock authorization URL: {}", mockAuthUrl);
        return mockAuthUrl;
    }

    @Override
    public RsoTokenResponse exchangeCodeForTokens(final String code, final String state)
    {
        log.debug("[DEBUG_LOG] Exchanging mock authorization code for tokens - code: {}, state: {}", code, state);

        if (!validateState(state))
        {
            log.warn("[DEBUG_LOG] Invalid state parameter: {}", state);
            throw new IllegalArgumentException("Invalid state parameter");
        }

        // Generate mock tokens
        final String timestamp = String.valueOf(System.currentTimeMillis());
        final RsoTokenResponse tokenResponse = new RsoTokenResponse(
                MOCK_SCOPE, MOCK_EXPIRES_IN, MOCK_TOKEN_TYPE, MOCK_REFRESH_TOKEN + timestamp, MOCK_ID_TOKEN + timestamp,
                MOCK_SUB_SID + timestamp, MOCK_ACCESS_TOKEN + timestamp
        );

        // Clean up authentication state
        removeAuthenticationState(state);

        log.info("[DEBUG_LOG] Successfully generated mock tokens for state: {}", state);
        return tokenResponse;
    }

    @Override
    public RsoTokenResponse refreshAccessToken(final String refreshToken)
    {
        log.debug("[DEBUG_LOG] Refreshing mock access token - refresh token: {}", refreshToken);

        if (refreshToken == null || !refreshToken.startsWith(MOCK_REFRESH_TOKEN))
        {
            log.warn("[DEBUG_LOG] Invalid refresh token: {}", refreshToken);
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Generate new mock tokens
        final String timestamp = String.valueOf(System.currentTimeMillis());
        final RsoTokenResponse tokenResponse = new RsoTokenResponse(
                MOCK_SCOPE, MOCK_EXPIRES_IN, MOCK_TOKEN_TYPE, MOCK_REFRESH_TOKEN + timestamp, null,
                // ID token not included in refresh response
                null, // Sub SID not included in refresh response
                MOCK_ACCESS_TOKEN + timestamp
        );

        log.info("[DEBUG_LOG] Successfully refreshed mock access token");
        return tokenResponse;
    }

    @Override
    public RsoUserInfo getUserInfo(final String accessToken)
    {
        log.debug("[DEBUG_LOG] Getting mock user info - access token: {}", accessToken);

        if (accessToken == null || !accessToken.startsWith(MOCK_ACCESS_TOKEN))
        {
            log.warn("[DEBUG_LOG] Invalid access token: {}", accessToken);
            throw new IllegalArgumentException("Invalid access token");
        }

        final RsoUserInfo userInfo = new RsoUserInfo(MOCK_USER_SUB, MOCK_CPID);

        log.info(
                "[DEBUG_LOG] Successfully retrieved mock user info: sub={}, cpid={}", userInfo.getSub(),
                userInfo.getCpid()
        );
        return userInfo;
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
     * Generate a mock session ID.
     *
     * @return A mock session identifier
     */
    private String generateSessionId()
    {
        final byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return "mock_session_" + Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
