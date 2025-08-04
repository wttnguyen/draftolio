package org.willwin.draftolioai.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.willwin.draftolioai.config.RsoProperties;
import org.willwin.draftolioai.dto.AuthenticationState;
import org.willwin.draftolioai.dto.RsoTokenResponse;
import org.willwin.draftolioai.dto.RsoUserInfo;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MockRsoService.
 * <p>
 * These tests verify that the mock RSO service always authenticates successfully
 * and provides consistent mock data for development purposes.
 */
class MockRsoServiceTest
{

    private MockRsoService mockRsoService;

    private RsoProperties rsoProperties;

    @BeforeEach
    void setUp()
    {
        // Create test RSO properties
        rsoProperties = new RsoProperties(
                true, // mock enabled
                new RsoProperties.Client(
                        "test_client_id", "test_client_secret", "http://localhost:8080/oauth2-callback",
                        "openid cpid offline_access"
                ), new RsoProperties.Endpoints(
                "https://auth.riotgames.com", "/authorize", "/token", "/userinfo",
                "/jwks.json"
        ), new RsoProperties.Tokens(Duration.ofMinutes(5), 3, Duration.ofSeconds(10))
        );

        mockRsoService = new MockRsoService(rsoProperties);
    }

    @Test
    void testGenerateAuthorizationUrl()
    {
        System.out.println("[DEBUG_LOG] Testing authorization URL generation");

        final String redirectUrl = "/dashboard";
        final String authUrl = mockRsoService.generateAuthorizationUrl(redirectUrl);

        assertNotNull(authUrl);
        assertTrue(authUrl.contains("https://auth.riotgames.com/authorize"));
        assertTrue(authUrl.contains("client_id=test_client_id"));
        assertTrue(authUrl.contains("response_type=code"));
        assertTrue(authUrl.contains("scope=openid cpid offline_access"));
        assertTrue(authUrl.contains("state="));

        System.out.println("[DEBUG_LOG] Generated authorization URL: " + authUrl);
    }

    @Test
    void testExchangeCodeForTokens()
    {
        System.out.println("[DEBUG_LOG] Testing token exchange");

        // First generate an authorization URL to create a valid state
        final String redirectUrl = "/dashboard";
        final String authUrl = mockRsoService.generateAuthorizationUrl(redirectUrl);

        // Extract state parameter from URL
        final String state = extractStateFromUrl(authUrl);
        assertNotNull(state);

        // Exchange code for tokens
        final String mockCode = "mock_authorization_code";
        final RsoTokenResponse tokenResponse = mockRsoService.exchangeCodeForTokens(mockCode, state);

        assertNotNull(tokenResponse);
        assertEquals("openid cpid offline_access", tokenResponse.getScope());
        assertEquals(600, tokenResponse.getExpiresIn());
        assertEquals("Bearer", tokenResponse.getTokenType());
        assertNotNull(tokenResponse.getAccessToken());
        assertNotNull(tokenResponse.getRefreshToken());
        assertNotNull(tokenResponse.getIdToken());
        assertNotNull(tokenResponse.getSubSid());

        assertTrue(tokenResponse.getAccessToken().startsWith("mock_access_token_"));
        assertTrue(tokenResponse.getRefreshToken().startsWith("mock_refresh_token_"));

        System.out.println("[DEBUG_LOG] Successfully exchanged code for tokens");
    }

    @Test
    void testRefreshAccessToken()
    {
        System.out.println("[DEBUG_LOG] Testing token refresh");

        final String mockRefreshToken = "mock_refresh_token_12345";
        final RsoTokenResponse tokenResponse = mockRsoService.refreshAccessToken(mockRefreshToken);

        assertNotNull(tokenResponse);
        assertEquals("openid cpid offline_access", tokenResponse.getScope());
        assertEquals(600, tokenResponse.getExpiresIn());
        assertEquals("Bearer", tokenResponse.getTokenType());
        assertNotNull(tokenResponse.getAccessToken());
        assertNotNull(tokenResponse.getRefreshToken());
        assertNull(tokenResponse.getIdToken()); // ID token not included in refresh response
        assertNull(tokenResponse.getSubSid()); // Sub SID not included in refresh response

        assertTrue(tokenResponse.getAccessToken().startsWith("mock_access_token_"));
        assertTrue(tokenResponse.getRefreshToken().startsWith("mock_refresh_token_"));

        System.out.println("[DEBUG_LOG] Successfully refreshed access token");
    }

    @Test
    void testGetUserInfo()
    {
        System.out.println("[DEBUG_LOG] Testing user info retrieval");

        final String mockAccessToken = "mock_access_token_12345";
        final RsoUserInfo userInfo = mockRsoService.getUserInfo(mockAccessToken);

        assertNotNull(userInfo);
        assertEquals("mock_user_12345", userInfo.getSub());
        assertEquals("NA1", userInfo.getCpid());

        System.out.println("[DEBUG_LOG] Successfully retrieved user info: " + userInfo);
    }

    @Test
    void testStateValidation()
    {
        System.out.println("[DEBUG_LOG] Testing state validation");

        // Generate authorization URL to create valid state
        final String authUrl = mockRsoService.generateAuthorizationUrl("/test");
        final String validState = extractStateFromUrl(authUrl);

        // Test valid state
        assertTrue(mockRsoService.validateState(validState));

        // Test invalid state
        assertFalse(mockRsoService.validateState("invalid_state"));
        assertFalse(mockRsoService.validateState(null));
        assertFalse(mockRsoService.validateState(""));

        System.out.println("[DEBUG_LOG] State validation tests passed");
    }

    @Test
    void testAuthenticationStateManagement()
    {
        System.out.println("[DEBUG_LOG] Testing authentication state management");

        final AuthenticationState authState = new AuthenticationState(
                "test_state_123", Instant.now(), "/dashboard",
                "test_session_123"
        );

        // Store authentication state
        mockRsoService.storeAuthenticationState(authState);

        // Retrieve authentication state
        final AuthenticationState retrievedState = mockRsoService.getAuthenticationState("test_state_123");
        assertNotNull(retrievedState);
        assertEquals(authState.getState(), retrievedState.getState());
        assertEquals(authState.getRedirectUrl(), retrievedState.getRedirectUrl());
        assertEquals(authState.getSessionId(), retrievedState.getSessionId());

        // Remove authentication state
        mockRsoService.removeAuthenticationState("test_state_123");
        assertNull(mockRsoService.getAuthenticationState("test_state_123"));

        System.out.println("[DEBUG_LOG] Authentication state management tests passed");
    }

    @Test
    void testInvalidTokenHandling()
    {
        System.out.println("[DEBUG_LOG] Testing invalid token handling");

        // Test invalid refresh token
        assertThrows(
                IllegalArgumentException.class, () ->
                {
                    mockRsoService.refreshAccessToken("invalid_refresh_token");
                }
        );

        assertThrows(
                IllegalArgumentException.class, () ->
                {
                    mockRsoService.refreshAccessToken(null);
                }
        );

        // Test invalid access token
        assertThrows(
                IllegalArgumentException.class, () ->
                {
                    mockRsoService.getUserInfo("invalid_access_token");
                }
        );

        assertThrows(
                IllegalArgumentException.class, () ->
                {
                    mockRsoService.getUserInfo(null);
                }
        );

        System.out.println("[DEBUG_LOG] Invalid token handling tests passed");
    }

    @Test
    void testInvalidStateHandling()
    {
        System.out.println("[DEBUG_LOG] Testing invalid state handling");

        // Test exchange with invalid state
        assertThrows(
                IllegalArgumentException.class, () ->
                {
                    mockRsoService.exchangeCodeForTokens("mock_code", "invalid_state");
                }
        );

        System.out.println("[DEBUG_LOG] Invalid state handling tests passed");
    }

    /**
     * Helper method to extract state parameter from authorization URL.
     */
    private String extractStateFromUrl(final String url)
    {
        final String stateParam = "state=";
        final int stateIndex = url.indexOf(stateParam);
        if (stateIndex == -1)
        {
            return null;
        }

        final int stateStart = stateIndex + stateParam.length();
        final int stateEnd = url.indexOf("&", stateStart);

        if (stateEnd == -1)
        {
            return url.substring(stateStart);
        }
        else
        {
            return url.substring(stateStart, stateEnd);
        }
    }

}
