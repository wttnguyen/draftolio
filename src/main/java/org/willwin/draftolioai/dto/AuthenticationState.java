package org.willwin.draftolioai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * DTO representing the authentication state during OAuth flow.
 * <p>
 * This class manages the state parameter for CSRF protection and
 * tracks authentication session information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationState
{

    /**
     * CSRF protection token
     */
    private String state;

    /**
     * Timestamp when the state was created
     */
    private Instant createdAt;

    /**
     * Original redirect URL after successful authentication
     */
    private String redirectUrl;

    /**
     * Session identifier
     */
    private String sessionId;

    /**
     * Check if the authentication state has expired.
     *
     * @param maxAgeMinutes Maximum age in minutes
     * @return true if expired, false otherwise
     */
    public boolean isExpired(long maxAgeMinutes)
    {
        return createdAt.plusSeconds(maxAgeMinutes * 60).isBefore(Instant.now());
    }

}
