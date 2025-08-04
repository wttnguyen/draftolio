package org.willwin.draftolioai.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * Configuration properties for Riot Sign-On (RSO) integration.
 * <p>
 * This class provides type-safe configuration binding for RSO authentication
 * settings, supporting both mock and real RSO implementations.
 */
@ConfigurationProperties(prefix = "rso")
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsoProperties
{

    /**
     * Whether to use mock RSO service for development.
     * When true, authentication will always succeed without contacting RSO.
     */
    @NotNull
    private Boolean mockEnabled;

    /**
     * RSO client configuration
     */
    @NotNull
    private Client client;

    /**
     * RSO endpoints configuration
     */
    @NotNull
    private Endpoints endpoints;

    /**
     * Token management configuration
     */
    @NotNull
    private Tokens tokens;

    /**
     * RSO client registration details
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Client
    {

        /**
         * Client ID assigned during RSO registration
         */
        @NotBlank
        private String clientId;

        /**
         * Client secret for authentication
         */
        @NotBlank
        private String clientSecret;

        /**
         * Redirect URI for OAuth callback
         */
        @NotBlank
        private String redirectUri;

        /**
         * OAuth scopes to request
         */
        @NotNull
        private String scopes;

    }

    /**
     * RSO service endpoints
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Endpoints
    {

        /**
         * Base URL for RSO service
         */
        @NotBlank
        private String baseUrl;

        /**
         * Authorization endpoint path
         */
        @NotBlank
        private String authorizationPath;

        /**
         * Token endpoint path
         */
        @NotBlank
        private String tokenPath;

        /**
         * UserInfo endpoint path
         */
        @NotBlank
        private String userInfoPath;

        /**
         * JWKS endpoint path for token verification
         */
        @NotBlank
        private String jwksPath;

    }

    /**
     * Token management configuration
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tokens
    {

        /**
         * Access token expiration buffer time
         * Tokens will be refreshed this amount of time before actual expiration
         */
        @NotNull
        private Duration refreshBuffer;

        /**
         * Maximum number of token refresh retries
         */
        @NotNull
        private Integer maxRefreshRetries;

        /**
         * Timeout for token requests
         */
        @NotNull
        private Duration requestTimeout;

    }

}
