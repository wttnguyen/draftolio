package org.willwin.draftolioai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Configuration properties for Riot Sign-On (RSO) integration.
 * <p>
 * This class holds all the necessary configuration for OAuth 2.0 integration
 * with Riot Games authentication service.
 */
@ConfigurationProperties(prefix = "rso")
@Validated
public record RsoProperties(

        /**
         * RSO client ID obtained during client registration
         */
        @NotBlank String clientId,

        /**
         * RSO client secret obtained during client registration
         */
        @NotBlank String clientSecret,

        /**
         * Redirect URI for OAuth 2.0 callback (must be registered with RSO)
         */
        @NotBlank String redirectUri,

        /**
         * Base URL for RSO endpoints
         */
        @NotBlank String baseUrl,

        /**
         * Authorization endpoint URL
         */
        @NotBlank String authorizationUri,

        /**
         * Token endpoint URL
         */
        @NotBlank String tokenUri,

        /**
         * JWKS endpoint URL for token verification
         */
        @NotBlank String jwksUri,

        /**
         * UserInfo endpoint URL
         */
        @NotBlank String userInfoUri,

        /**
         * OAuth 2.0 scopes to request
         */
        @NotNull String[] scopes,

        /**
         * Post logout redirect URI
         */
        String postLogoutRedirectUri
)
{

    /**
     * Default constructor with sensible defaults for RSO endpoints
     */
    public RsoProperties
    {
        if (baseUrl == null)
        {
            baseUrl = "https://auth.riotgames.com";
        }
        if (authorizationUri == null)
        {
            authorizationUri = baseUrl + "/authorize";
        }
        if (tokenUri == null)
        {
            tokenUri = baseUrl + "/token";
        }
        if (jwksUri == null)
        {
            jwksUri = baseUrl + "/jwks.json";
        }
        if (userInfoUri == null)
        {
            userInfoUri = baseUrl + "/userinfo";
        }
        if (scopes == null)
        {
            scopes = new String[] {
                    "openid",
                    "cpid",
                    "offline_access"
            };
        }
    }

}
