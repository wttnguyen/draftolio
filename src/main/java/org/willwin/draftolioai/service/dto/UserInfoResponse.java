package org.willwin.draftolioai.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;

/**
 * Response DTO for user information from Riot Sign-On (RSO).
 * <p>
 * This DTO contains the user information that is returned to clients
 * after successful authentication and user data processing.
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserInfoResponse(

        /**
         * Internal user ID from our database
         */
        Long id,

        /**
         * RSO subject identifier (unique user ID)
         */
        String subject,

        /**
         * Client Platform ID (game region for League of Legends)
         */
        String cpid,

        /**
         * User's display name or summoner name
         */
        String displayName,

        /**
         * User's email address (if available and consented)
         */
        String email,

        /**
         * Whether the user account is active
         */
        Boolean active,

        /**
         * Timestamp when the user was first created in our system
         */
        Instant createdAt,

        /**
         * Timestamp when the user information was last updated
         */
        Instant updatedAt,

        /**
         * Timestamp when the user last logged in
         */
        Instant lastLoginAt,

        /**
         * Authentication status information
         */
        AuthenticationInfo authenticationInfo
)
{

    /**
     * Nested record for authentication-related information
     */
    @Builder
    public record AuthenticationInfo(

            /**
             * Whether the user is currently authenticated
             */
            boolean authenticated,

            /**
             * Access token expiration time
             */
            Instant accessTokenExpiresAt,

            /**
             * OAuth 2.0 scopes granted to the user
             */
            String[] scopes,

            /**
             * Authentication provider (always "rso" for Riot Sign-On)
             */
            String provider
    ) { }

    /**
     * Create a UserInfoResponse for an authenticated user
     */
    public static UserInfoResponse authenticated(
            Long id,
            String subject,
            String cpid,
            String displayName,
            String email,
            Boolean active,
            Instant createdAt,
            Instant updatedAt,
            Instant lastLoginAt,
            Instant accessTokenExpiresAt,
            String[] scopes)
    {

        return UserInfoResponse
                .builder()
                .id(id)
                .subject(subject)
                .cpid(cpid)
                .displayName(displayName)
                .email(email)
                .active(active)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastLoginAt(lastLoginAt)
                .authenticationInfo(AuthenticationInfo
                        .builder()
                        .authenticated(true)
                        .accessTokenExpiresAt(accessTokenExpiresAt)
                        .scopes(scopes)
                        .provider("rso")
                        .build())
                .build();
    }

    /**
     * Create a UserInfoResponse for an unauthenticated user
     */
    public static UserInfoResponse unauthenticated()
    {
        return UserInfoResponse
                .builder()
                .authenticationInfo(AuthenticationInfo.builder().authenticated(false).provider("rso").build())
                .build();
    }

    /**
     * Check if the user is from a specific region
     */
    public boolean isFromRegion(String region)
    {
        return this.cpid != null && this.cpid.equalsIgnoreCase(region);
    }

    /**
     * Check if the user is currently authenticated
     */
    public boolean isAuthenticated()
    {
        return this.authenticationInfo != null && this.authenticationInfo.authenticated();
    }

    /**
     * Get the user's region in a human-readable format
     */
    public String getRegionDisplayName()
    {
        if (cpid == null)
        {
            return "Unknown";
        }

        return switch (cpid.toUpperCase())
        {
            case "NA1" -> "North America";
            case "EUW1" -> "Europe West";
            case "EUN1" -> "Europe Nordic & East";
            case "KR" -> "Korea";
            case "JP1" -> "Japan";
            case "BR1" -> "Brazil";
            case "LA1" -> "Latin America North";
            case "LA2" -> "Latin America South";
            case "OC1" -> "Oceania";
            case "TR1" -> "Turkey";
            case "RU" -> "Russia";
            case "PH2" -> "Philippines";
            case "SG2" -> "Singapore";
            case "TH2" -> "Thailand";
            case "TW2" -> "Taiwan";
            case "VN2" -> "Vietnam";
            default -> cpid;
        };
    }

}
