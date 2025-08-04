package org.willwin.draftolioai.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.willwin.draftolioai.dto.AuthenticationState;
import org.willwin.draftolioai.dto.RsoTokenResponse;
import org.willwin.draftolioai.dto.RsoUserInfo;
import org.willwin.draftolioai.service.RsoService;

import java.util.Collections;

/**
 * Controller handling the OAuth2 callback endpoint.
 * <p>
 * This controller is separate from AuthController to match the proxy configuration
 * that maps /oauth2-callback to the backend.
 */
@Slf4j
@RestController
public class OAuth2CallbackController
{

    private final RsoService rsoService;

    // Session attribute keys
    private static final String ACCESS_TOKEN_KEY = "rso_access_token";

    private static final String REFRESH_TOKEN_KEY = "rso_refresh_token";

    private static final String USER_INFO_KEY = "rso_user_info";

    public OAuth2CallbackController(final RsoService rsoService)
    {
        this.rsoService = rsoService;
    }

    /**
     * Handle OAuth callback from RSO.
     * <p>
     * GET /oauth2-callback?code={code}&state={state}
     */
    @GetMapping("/oauth2-callback")
    public RedirectView handleCallback(
            @RequestParam("code")
            final String code,
            @RequestParam("state")
            final String state,
            @RequestParam(
                    value = "error",
                    required = false
            )
            final String error, final HttpServletRequest request, final HttpSession session)
    {
        log.info("[DEBUG_LOG] Handling OAuth2 callback - code: {}, state: {}, error: {}", code, state, error);

        // Handle authorization errors
        if (error != null)
        {
            log.warn("[DEBUG_LOG] OAuth authorization error: {}", error);
            return new RedirectView("/login?error=" + error);
        }

        try
        {
            // Exchange authorization code for tokens
            final RsoTokenResponse tokenResponse = rsoService.exchangeCodeForTokens(code, state);

            // Get user information
            final RsoUserInfo userInfo = rsoService.getUserInfo(tokenResponse.getAccessToken());

            // Store tokens and user info in session
            session.setAttribute(ACCESS_TOKEN_KEY, tokenResponse.getAccessToken());
            session.setAttribute(REFRESH_TOKEN_KEY, tokenResponse.getRefreshToken());
            session.setAttribute(USER_INFO_KEY, userInfo);

            // Authenticate user with Spring Security
            authenticateUser(userInfo, session);

            // Get original redirect URL from authentication state
            final AuthenticationState authState = rsoService.getAuthenticationState(state);
            final String redirectUrl = (authState != null) ? authState.getRedirectUrl() : "/";

            log.info(
                    "[DEBUG_LOG] Successfully authenticated user: {} - redirecting to: {}", userInfo.getSub(),
                    redirectUrl
            );

            return new RedirectView(redirectUrl);

        }
        catch (Exception e)
        {
            log.error("[DEBUG_LOG] Failed to process OAuth2 callback", e);
            return new RedirectView("/login?error=callback_failed");
        }
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

}
