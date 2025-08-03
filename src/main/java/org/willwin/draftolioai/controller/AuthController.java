package org.willwin.draftolioai.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.willwin.draftolioai.service.UserService;
import org.willwin.draftolioai.service.dto.UserInfoResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Authentication controller for Riot Sign-On (RSO) integration.
 * <p>
 * Handles authentication endpoints including login initiation, OAuth callback,
 * logout, token refresh, and user information retrieval.
 */
@RestController
@RequestMapping("/auth")
public class AuthController
{

    private final UserService userService;

    public AuthController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Initiate login with Riot Sign-On.
     * <p>
     * GET /auth/login
     * Response: Redirect to RSO authorization URL
     */
    @GetMapping("/login")
    public RedirectView login()
    {
        // Spring Security will handle the redirect to RSO authorization endpoint
        return new RedirectView("/oauth2/authorization/rso");
    }

    /**
     * Handle OAuth 2.0 callback from RSO.
     * <p>
     * This endpoint is automatically handled by Spring Security OAuth2 client,
     * but we can add custom logic here if needed.
     * <p>
     * GET /oauth2-callback?code={code}&state={state}
     */
    @GetMapping("/oauth2-callback")
    public RedirectView oauth2Callback(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String error, HttpServletRequest request)
    {

        if (error != null)
        {
            // Handle OAuth error (user denied authorization, etc.)
            return new RedirectView("/login?error=" + error);
        }

        // Successful authentication - redirect to main application
        return new RedirectView("/");
    }

    /**
     * Logout the current user.
     * <p>
     * POST /auth/logout
     * Response: Clear session and redirect to logout page
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
    {

        if (authentication != null)
        {
            // Clear the security context and invalidate session
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return ResponseEntity.ok(Map.of("message", "Logged out successfully", "redirectUrl", "/login?logout"));
    }

    /**
     * Refresh the current user's access token.
     * <p>
     * POST /auth/refresh
     * Response: New access token or error
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(
            @RegisteredOAuth2AuthorizedClient("rso") OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User principal)
    {

        try
        {
            if (authorizedClient == null || principal == null)
            {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "No valid authentication found"));
            }

            // The OAuth2AuthorizedClient automatically handles token refresh
            // when the access token is expired
            String accessToken = authorizedClient.getAccessToken().getTokenValue();

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Token refreshed successfully", "expiresAt",
                            authorizedClient.getAccessToken().getExpiresAt()
                    ));

        }
        catch (Exception e)
        {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to refresh token: " + e.getMessage()));
        }
    }

    /**
     * Get current authenticated user information.
     * <p>
     * GET /api/user/me
     * Headers: Authorization: Bearer {access_token}
     * Response: User profile information
     */
    @GetMapping("/user/me")
    public ResponseEntity<UserInfoResponse> getCurrentUser(
            @AuthenticationPrincipal OAuth2User principal,
            @RegisteredOAuth2AuthorizedClient("rso") OAuth2AuthorizedClient authorizedClient)
    {

        if (principal == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try
        {
            // Get user information from RSO and update/create user in database
            UserInfoResponse userInfo = userService.processAuthenticatedUser(principal, authorizedClient);
            return ResponseEntity.ok(userInfo);

        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Check authentication status.
     * <p>
     * GET /auth/status
     * Response: Authentication status and user information
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(
            @AuthenticationPrincipal OAuth2User principal, Authentication authentication)
    {

        if (principal == null || authentication == null || !authentication.isAuthenticated())
        {
            return ResponseEntity.ok(Map.of("authenticated", false, "message", "User is not authenticated"));
        }

        return ResponseEntity.ok(Map.of(
                "authenticated", true, "subject", principal.getAttribute("sub"), "cpid", principal.getAttribute("cpid"),
                "authorities", authentication.getAuthorities()
        ));
    }

    /**
     * Handle authentication errors.
     * <p>
     * GET /auth/error
     * Response: Error information and suggested actions
     */
    @GetMapping("/error")
    public ResponseEntity<Map<String, Object>> handleAuthError(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String error_description)
    {

        String message = "Authentication failed";
        String suggestion = "Please try logging in again";

        if ("access_denied".equals(error))
        {
            message = "Access was denied";
            suggestion = "You need to authorize the application to continue";
        }
        else if ("invalid_request".equals(error))
        {
            message = "Invalid authentication request";
            suggestion = "Please contact support if this problem persists";
        }
        else if ("server_error".equals(error))
        {
            message = "Authentication service is temporarily unavailable";
            suggestion = "Please try again in a few minutes";
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", error != null ? error : "unknown_error", "message", message, "description",
                error_description != null ? error_description : "No additional details available", "suggestion",
                suggestion, "retryUrl", "/auth/login"
        ));
    }

}
