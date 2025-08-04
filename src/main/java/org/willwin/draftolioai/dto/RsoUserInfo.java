package org.willwin.draftolioai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the user information response from RSO userinfo endpoint.
 * <p>
 * Based on the RSO Integration Specification section 2.7.2.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsoUserInfo
{

    /**
     * Subject identifier (unique user ID)
     */
    @JsonProperty("sub")
    private String sub;

    /**
     * Game region for League of Legends (Client Platform ID)
     */
    @JsonProperty("cpid")
    private String cpid;

}
