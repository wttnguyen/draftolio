package org.willwin.draftolioai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

/**
 * Security configuration for RSO authentication.
 * <p>
 * This configuration sets up security rules for RSO OAuth2 authentication,
 * allowing public access to authentication endpoints while protecting other resources.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{

    /**
     * Configure HTTP security for RSO authentication.
     *
     * @param http HttpSecurity configuration
     * @return SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
    {
        return http
                // Configure authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        // Allow public access to authentication endpoints
                        .requestMatchers("/api/auth/login", "/api/auth/logout")
                        .permitAll()
                        .requestMatchers("/auth/login", "/auth/status")
                        .permitAll() // Keep old paths for compatibility
                        .requestMatchers("/oauth2-callback")
                        .permitAll()
                        .requestMatchers("/login/**")
                        .permitAll()

                        // Allow access to auth status endpoints for authenticated users
                        .requestMatchers("/api/auth/me", "/api/auth/status", "/api/auth/refresh")
                        .authenticated()

                        // Allow public access to actuator health endpoint
                        .requestMatchers("/actuator/health", "/actuator/info")
                        .permitAll()

                        // Allow public access to static resources
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico")
                        .permitAll()

                        // Allow public access to error pages
                        .requestMatchers("/error")
                        .permitAll()

                        // Require authentication for all other requests
                        .anyRequest()
                        .authenticated())

                // Configure session management
                .sessionManagement(session -> session.maximumSessions(1).maxSessionsPreventsLogin(false))

                // Configure logout
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))

                // Configure exception handling
                .exceptionHandling(
                        exceptions -> exceptions.authenticationEntryPoint((request, response, authException) ->
                        {
                            // Redirect unauthenticated users to login
                            response.sendRedirect("/auth/login?redirect=" + request.getRequestURI());
                        }).accessDeniedHandler((request, response, accessDeniedException) ->
                        {
                            // Handle access denied
                            response.sendError(403, "Access Denied");
                        }))

                // Configure security headers
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.deny())
                        .contentTypeOptions(contentTypeOptions ->
                        { })
                        .httpStrictTransportSecurity(
                                hstsConfig -> hstsConfig.maxAgeInSeconds(31536000).includeSubDomains(true))
                        .referrerPolicy(referrerPolicy -> referrerPolicy.policy(
                                ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)))

                // Disable CSRF for API endpoints (can be enabled later with proper token handling)
                .csrf(AbstractHttpConfigurer::disable)

                .build();
    }

}
