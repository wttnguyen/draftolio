package org.willwin.draftolioai.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.willwin.draftolioai.domain.DraftMode;
import org.willwin.draftolioai.domain.DraftPhase;
import org.willwin.draftolioai.domain.DraftStatus;
import org.willwin.draftolioai.domain.DraftTurn;

import java.time.LocalDateTime;

/**
 * Response DTO for draft information.
 * Supports US-1 acceptance criteria for draft creation and viewing.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DraftResponse
{

    /**
     * Unique identifier of the draft.
     */
    private String id;

    /**
     * Name of the draft.
     */
    private String name;

    /**
     * Optional description of the draft.
     */
    private String description;

    /**
     * Name of the blue team.
     */
    private String blueTeamName;

    /**
     * Name of the red team.
     */
    private String redTeamName;

    /**
     * Current status of the draft.
     */
    private DraftStatus status;

    /**
     * Draft mode (Tournament, Fearless, Full Fearless).
     */
    private DraftMode mode;

    /**
     * ID of the blue side captain.
     * US-1 AC5: Creator is assigned as Blue Side captain by default.
     */
    private String blueSideCaptainId;

    /**
     * ID of the red side captain.
     * US-1 AC4: User can invite another user to be the opposing captain.
     */
    private String redSideCaptainId;

    /**
     * ID of the blue side team (for team-based drafts).
     */
    private String blueSideTeamId;

    /**
     * ID of the red side team (for team-based drafts).
     */
    private String redSideTeamId;

    /**
     * Current phase of the draft.
     */
    private DraftPhase currentPhase;

    /**
     * Current turn (which team should act).
     */
    private DraftTurn currentTurn;

    /**
     * Current game number (for series like Fearless drafts).
     */
    private Integer gameNumber;

    /**
     * Duration in seconds for ban phase timer.
     */
    private Integer banPhaseTimerDuration;

    /**
     * Duration in seconds for pick phase timer.
     */
    private Integer pickPhaseTimerDuration;

    /**
     * End time of the current timer.
     */
    private LocalDateTime timerEndTime;

    /**
     * Spectator URL for viewing the draft.
     */
    private String spectateUrl;

    /**
     * Blue captain URL for controlling the blue side of the draft.
     */
    private String blueCaptainUrl;

    /**
     * Red captain URL for controlling the red side of the draft.
     */
    private String redCaptainUrl;

    /**
     * Timestamp when the draft was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the draft was last updated.
     */
    private LocalDateTime updatedAt;

    /**
     * Timestamp when the draft was completed.
     */
    private LocalDateTime completedAt;

    /**
     * Gets the display name of the draft mode.
     */
    public String getModeDisplayName()
    {
        return mode != null ? mode.getDisplayName() : null;
    }

    /**
     * Gets the description of the draft mode.
     */
    public String getModeDescription()
    {
        return mode != null ? mode.getDescription() : null;
    }

    /**
     * Gets the display name of the current phase.
     */
    public String getCurrentPhaseDisplayName()
    {
        return currentPhase != null ? currentPhase.getDisplayName() : null;
    }

    /**
     * Gets the display name of the current turn.
     */
    public String getCurrentTurnDisplayName()
    {
        return currentTurn != null ? currentTurn.getDisplayName() : null;
    }

    /**
     * Checks if the draft is in progress.
     */
    public boolean isInProgress()
    {
        return status == DraftStatus.IN_PROGRESS;
    }

    /**
     * Checks if the draft is completed.
     */
    public boolean isCompleted()
    {
        return status == DraftStatus.COMPLETED;
    }

    /**
     * Checks if this is a team-based draft.
     */
    public boolean isTeamBasedDraft()
    {
        return blueSideTeamId != null && redSideTeamId != null;
    }

}
