package org.willwin.draftolioai.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.willwin.draftolioai.dto.AuthenticationState;
import org.willwin.draftolioai.dto.RsoTokenResponse;
import org.willwin.draftolioai.dto.RsoUserInfo;
import org.willwin.draftolioai.dto.UserResponse;
import org.willwin.draftolioai.service.RsoService;
import org.willwin.draftolioai.service.MockRsoService;

import java.io.IOException;
import java.util.Collections;

/**
 * Controller handling RSO authentication endpoints.
 * <p>
 * This controller implements the OAuth 2.0 authorization code flow
 * for Riot Sign-On authentication as specified in the RSO integration specification.
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController
{

    private final RsoService rsoService;

    // Session attribute keys
    private static final String ACCESS_TOKEN_KEY = "rso_access_token";

    private static final String REFRESH_TOKEN_KEY = "rso_refresh_token";

    private static final String USER_INFO_KEY = "rso_user_info";

    public AuthController(final RsoService rsoService)
    {
        this.rsoService = rsoService;
    }

    /**
     * Initiate RSO login by returning authorization URL.
     * <p>
     * GET /auth/login
     */
    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestParam(
                    value = "redirect",
                    defaultValue = "/"
            )
            final String redirectUrl, final HttpServletRequest request, final HttpSession session)
    {
        log.info("[DEBUG_LOG] Initiating RSO login - redirect URL: {}", redirectUrl);

        try
        {
            // Check if using MockRsoService - if so, bypass OAuth2 flow and login directly
            if (rsoService instanceof MockRsoService)
            {
                log.info("[DEBUG_LOG] Using MockRsoService - performing direct mock login");

                // Generate mock tokens directly
                final String timestamp = String.valueOf(System.currentTimeMillis());
                final RsoTokenResponse mockTokens = new RsoTokenResponse(
                        "openid cpid offline_access", 600, // 10 minutes
                        "Bearer", "mock_refresh_token_" + timestamp, "mock_id_token_" + timestamp,
                        "mock_sub_sid_" + timestamp, "mock_access_token_" + timestamp
                );

                // Create mock user info
                final RsoUserInfo mockUserInfo = new RsoUserInfo("mock_user_12345", "NA1");

                // Store tokens and user info in session
                session.setAttribute(ACCESS_TOKEN_KEY, mockTokens.getAccessToken());
                session.setAttribute(REFRESH_TOKEN_KEY, mockTokens.getRefreshToken());
                session.setAttribute(USER_INFO_KEY, mockUserInfo);

                // Authenticate user with Spring Security
                authenticateUser(mockUserInfo, session);

                log.info("[DEBUG_LOG] Mock login successful - redirecting to home page");

                // Return redirect to home page instead of RSO authorization URL
                final LoginResponse response = new LoginResponse("http://localhost:4200/");
                return ResponseEntity.ok(response);
            }

            // For real RSO service, generate authorization URL with state parameter
            final String authorizationUrl = rsoService.generateAuthorizationUrl(redirectUrl);

            log.debug("[DEBUG_LOG] Generated RSO authorization URL: {}", authorizationUrl);

            final LoginResponse response = new LoginResponse(authorizationUrl);
            return ResponseEntity.ok(response);

        }
        catch (Exception e)
        {
            log.error("[DEBUG_LOG] Failed to initiate RSO login", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, "Failed to initiate login: " + e.getMessage()));
        }
    }

    /**
     * Logout user and clear session.
     * <p>
     * POST /auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(final HttpSession session)
    {
        log.info("[DEBUG_LOG] Logging out user");

        try
        {
            // Clear session attributes
            session.removeAttribute(ACCESS_TOKEN_KEY);
            session.removeAttribute(REFRESH_TOKEN_KEY);
            session.removeAttribute(USER_INFO_KEY);

            // Invalidate session
            session.invalidate();

            log.info("[DEBUG_LOG] Successfully logged out user");
            return ResponseEntity.ok().build();

        }
        catch (Exception e)
        {
            log.error("[DEBUG_LOG] Error during logout", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Refresh access token using refresh token.
     * <p>
     * POST /auth/refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(final HttpSession session)
    {
        log.debug("[DEBUG_LOG] Refreshing access token");

        final String refreshToken = (String) session.getAttribute(REFRESH_TOKEN_KEY);
        if (refreshToken == null)
        {
            log.warn("[DEBUG_LOG] No refresh token found in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try
        {
            // Refresh access token
            final RsoTokenResponse tokenResponse = rsoService.refreshAccessToken(refreshToken);

            // Update session with new tokens
            session.setAttribute(ACCESS_TOKEN_KEY, tokenResponse.getAccessToken());
            if (tokenResponse.getRefreshToken() != null)
            {
                session.setAttribute(REFRESH_TOKEN_KEY, tokenResponse.getRefreshToken());
            }

            log.info("[DEBUG_LOG] Successfully refreshed access token");
            return ResponseEntity.ok().build();

        }
        catch (Exception e)
        {
            log.error("[DEBUG_LOG] Failed to refresh access token", e);

            // Clear invalid tokens from session
            session.removeAttribute(ACCESS_TOKEN_KEY);
            session.removeAttribute(REFRESH_TOKEN_KEY);
            session.removeAttribute(USER_INFO_KEY);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Get current authentication status.
     * <p>
     * GET /auth/status
     */
    @GetMapping("/status")
    public ResponseEntity<AuthStatusResponse> getAuthStatus(final HttpSession session)
    {
        log.debug("[DEBUG_LOG] Checking authentication status");

        final String accessToken = (String) session.getAttribute(ACCESS_TOKEN_KEY);
        final RsoUserInfo userInfo = (RsoUserInfo) session.getAttribute(USER_INFO_KEY);

        final boolean isAuthenticated = accessToken != null && userInfo != null;

        final AuthStatusResponse response = new AuthStatusResponse(isAuthenticated, userInfo);

        log.debug("[DEBUG_LOG] Authentication status: authenticated={}", isAuthenticated);
        return ResponseEntity.ok(response);
    }

    /**
     * Get current user information for frontend.
     * <p>
     * GET /auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(final HttpSession session)
    {
        log.debug("[DEBUG_LOG] Getting current user information");

        final String accessToken = (String) session.getAttribute(ACCESS_TOKEN_KEY);
        final RsoUserInfo userInfo = (RsoUserInfo) session.getAttribute(USER_INFO_KEY);

        if (accessToken == null || userInfo == null)
        {
            log.debug("[DEBUG_LOG] User not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Map RSO user info to frontend User format
        final UserResponse user = new UserResponse(
                userInfo.getSub(), userInfo.getSub() + "#" + userInfo.getCpid(), // Create riot tag from sub and cpid
                userInfo.getSub(), // Use sub as game name for now
                userInfo.getCpid(), // Use cpid as tag line
                null, // Profile icon ID not available from RSO
                null  // Summoner level not available from RSO
        );

        log.debug("[DEBUG_LOG] Retrieved user info: id={}, riotTag={}", user.getId(), user.getRiotTag());
        return ResponseEntity.ok(user);
    }

    /**
     * Authenticate user with Spring Security by creating and setting Authentication object.
     *
     * @param userInfo The RSO user information
     * @param session  The HTTP session
     */
    private void authenticateUser(final RsoUserInfo userInfo, final HttpSession session)
    {
        log.debug("[DEBUG_LOG] Authenticating user with Spring Security: {}", userInfo.getSub());

        // Create authentication object with user details
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                userInfo.getSub(), // principal (user ID)
                null, // credentials (not needed for session-based auth)
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // authorities
        );

        // Set authentication in security context
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Store security context in session for persistence across requests
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        log.info("[DEBUG_LOG] Successfully authenticated user with Spring Security: {}", userInfo.getSub());
    }

    /**
     * Response DTO for login initiation.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse
    {

        private String authorizationUrl;

        private String error;

        public LoginResponse(String authorizationUrl)
        {
            this.authorizationUrl = authorizationUrl;
            this.error = null;
        }

    }

    /**
     * Response DTO for authentication status.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthStatusResponse
    {

        private boolean authenticated;

        private RsoUserInfo userInfo;

    }

}
