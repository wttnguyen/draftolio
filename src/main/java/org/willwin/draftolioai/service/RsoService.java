package org.willwin.draftolioai.service;

import org.willwin.draftolioai.dto.AuthenticationState;
import org.willwin.draftolioai.dto.RsoTokenResponse;
import org.willwin.draftolioai.dto.RsoUserInfo;

/**
 * Service interface for Riot Sign-On (RSO) authentication operations.
 * <p>
 * This interface defines the contract for RSO authentication services,
 * supporting both mock and real implementations.
 */
public interface RsoService
{

    /**
     * Generate the authorization URL for RSO login.
     *
     * @param redirectUrl The URL to redirect to after successful authentication
     * @return The complete authorization URL with state parameter
     */
    String generateAuthorizationUrl(String redirectUrl);

    /**
     * Exchange authorization code for access tokens.
     *
     * @param code  The authorization code from RSO callback
     * @param state The state parameter for CSRF protection
     * @return Token response containing access, refresh, and ID tokens
     * @throws IllegalArgumentException if code or state is invalid
     */
    RsoTokenResponse exchangeCodeForTokens(String code, String state);

    /**
     * Refresh access token using refresh token.
     *
     * @param refreshToken The refresh token
     * @return New token response with refreshed access token
     * @throws IllegalArgumentException if refresh token is invalid
     */
    RsoTokenResponse refreshAccessToken(String refreshToken);

    /**
     * Get user information using access token.
     *
     * @param accessToken The access token
     * @return User information including subject ID and region
     * @throws IllegalArgumentException if access token is invalid
     */
    RsoUserInfo getUserInfo(String accessToken);

    /**
     * Validate the state parameter for CSRF protection.
     *
     * @param state The state parameter to validate
     * @return true if state is valid, false otherwise
     */
    boolean validateState(String state);

    /**
     * Store authentication state for CSRF protection.
     *
     * @param state The authentication state to store
     */
    void storeAuthenticationState(AuthenticationState state);

    /**
     * Retrieve authentication state by state parameter.
     *
     * @param stateParam The state parameter
     * @return The stored authentication state, or null if not found
     */
    AuthenticationState getAuthenticationState(String stateParam);

    /**
     * Remove authentication state after successful authentication.
     *
     * @param stateParam The state parameter
     */
    void removeAuthenticationState(String stateParam);

}
