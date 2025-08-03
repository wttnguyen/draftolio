package org.willwin.draftolioai.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.willwin.draftolioai.domain.DraftMode;

import java.util.UUID;

/**
 * Request DTO for creating a new draft.
 * Supports US-1 acceptance criteria for draft creation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDraftRequest
{

    /**
     * Name of the draft (optional).
     */
    @Size(
            max = 255,
            message = "Draft name must not exceed 255 characters"
    )
    private String name;

    /**
     * Optional description of the draft.
     */
    @Size(
            max = 1000,
            message = "Draft description must not exceed 1000 characters"
    )
    private String description;

    /**
     * Name of the blue team (required).
     */
    @NotBlank(message = "Blue team name is required")
    @Size(
            max = 255,
            message = "Blue team name must not exceed 255 characters"
    )
    private String blueTeamName;

    /**
     * Name of the red team (required).
     */
    @NotBlank(message = "Red team name is required")
    @Size(
            max = 255,
            message = "Red team name must not exceed 255 characters"
    )
    private String redTeamName;

    /**
     * Draft mode (required).
     */
    @NotNull(message = "Draft mode is required")
    private DraftMode mode;

    /**
     * ID of the blue side team (for team-based drafts).
     */
    private UUID blueSideTeamId;

    /**
     * ID of the red side team (for team-based drafts).
     */
    private UUID redSideTeamId;

    /**
     * Duration in seconds for ban phase timer.
     * Default is 30 seconds if not specified.
     */
    @Min(
            value = 10,
            message = "Ban phase timer duration must be at least 10 seconds"
    )
    @Builder.Default
    private Integer banPhaseTimerDuration = 30;

    /**
     * Duration in seconds for pick phase timer.
     * Default is 60 seconds if not specified.
     */
    @Min(
            value = 10,
            message = "Pick phase timer duration must be at least 10 seconds"
    )
    @Builder.Default
    private Integer pickPhaseTimerDuration = 60;

    /**
     * Checks if this is a team-based draft.
     */
    public boolean isTeamBasedDraft()
    {
        return blueSideTeamId != null && redSideTeamId != null;
    }

}
