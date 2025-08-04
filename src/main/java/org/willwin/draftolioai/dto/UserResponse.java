package org.willwin.draftolioai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing user information for the frontend.
 * <p>
 * This matches the User interface structure expected by the Angular frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse
{

    /**
     * Unique user identifier
     */
    @JsonProperty("id")
    private String id;

    /**
     * Riot tag (gameName#tagLine format)
     */
    @JsonProperty("riotTag")
    private String riotTag;

    /**
     * Game name portion of the riot tag
     */
    @JsonProperty("gameName")
    private String gameName;

    /**
     * Tag line portion of the riot tag
     */
    @JsonProperty("tagLine")
    private String tagLine;

    /**
     * Profile icon ID (optional)
     */
    @JsonProperty("profileIconId")
    private Integer profileIconId;

    /**
     * Summoner level (optional)
     */
    @JsonProperty("summonerLevel")
    private Integer summonerLevel;

}
