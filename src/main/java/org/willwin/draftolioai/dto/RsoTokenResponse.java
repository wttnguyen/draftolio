package org.willwin.draftolioai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the token response from RSO token endpoint.
 * <p>
 * Based on the RSO Integration Specification section 2.4.2.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsoTokenResponse
{

    /**
     * Level of access provided by the access token
     */
    @JsonProperty("scope")
    private String scope;

    /**
     * Token lifespan in seconds
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    /**
     * Authorization method (Bearer)
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * Token for obtaining new access tokens
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * JWT containing user identity information
     */
    @JsonProperty("id_token")
    private String idToken;

    /**
     * Session identifier for the subject
     */
    @JsonProperty("sub_sid")
    private String subSid;

    /**
     * Encrypted JWT for API access
     */
    @JsonProperty("access_token")
    private String accessToken;

}
