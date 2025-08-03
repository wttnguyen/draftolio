package org.willwin.draftolioai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Security configuration for Riot Sign-On (RSO) integration.
 * <p>
 * This configuration sets up OAuth 2.0 authentication with RSO,
 * CSRF protection, CORS settings, and security headers.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    private final RsoProperties rsoProperties;

    public SecurityConfig(RsoProperties rsoProperties)
    {
        this.rsoProperties = rsoProperties;
    }

    /**
     * Configure the security filter chain with OAuth 2.0 and security settings.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                // Configure CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Configure CSRF protection
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/oauth2/**", "/login/oauth2/**"))

                // Configure session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))

                // Configure authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints
                        .requestMatchers(
                                "/", "/login", "/oauth2/**", "/login/oauth2/**", "/error", "/actuator/health",
                                "/actuator/info", "/static/**", "/public/**", "/favicon.ico"
                        ).permitAll()

                        // Protected endpoints
                        .requestMatchers("/api/**").authenticated().requestMatchers("/admin/**").hasRole("ADMIN")

                        // All other requests require authentication
                        .anyRequest().authenticated())

                // Configure OAuth 2.0 login
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(authorization -> authorization.baseUri("/oauth2/authorization"))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/oauth2-callback"))
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler()))

                // Configure logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))

                // Configure security headers
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.deny())
                        .contentTypeOptions(contentTypeOptions ->
                        { })
                        .httpStrictTransportSecurity(
                                hstsConfig -> hstsConfig.maxAgeInSeconds(31536000).includeSubDomains(true))
                        .referrerPolicy(referrerPolicy -> referrerPolicy.policy(
                                ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)))

                .build();
    }

    /**
     * Configure the OAuth 2.0 client registration for RSO.
     */
    @Bean
    ClientRegistrationRepository clientRegistrationRepository()
    {
        return new InMemoryClientRegistrationRepository(rsoClientRegistration());
    }

    /**
     * Create the RSO client registration configuration.
     */
    private ClientRegistration rsoClientRegistration()
    {
        return ClientRegistration
                .withRegistrationId("rso")
                .clientId(rsoProperties.clientId())
                .clientSecret(rsoProperties.clientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(rsoProperties.redirectUri())
                .scope(rsoProperties.scopes())
                .authorizationUri(rsoProperties.authorizationUri())
                .tokenUri(rsoProperties.tokenUri())
                .jwkSetUri(rsoProperties.jwksUri())
                .userInfoUri(rsoProperties.userInfoUri())
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .clientName("Riot Sign-On")
                .build();
    }

    /**
     * Configure CORS settings for the application.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "https://*.draftolio.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Handle successful authentication.
     */
    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler()
    {
        return (request, response, authentication) ->
        {
            // Redirect to the main application after successful authentication
            response.sendRedirect("/");
        };
    }

    /**
     * Handle authentication failures.
     */
    @Bean
    AuthenticationFailureHandler authenticationFailureHandler()
    {
        return (request, response, exception) ->
        {
            // Redirect to login page with error parameter
            response.sendRedirect("/login?error=true");
        };
    }

}
