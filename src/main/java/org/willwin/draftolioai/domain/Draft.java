package org.willwin.draftolioai.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Draft entity representing a League of Legends draft session.
 */
@Entity
@Table(name = "drafts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Draft
{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(
            name = "blue_team_name",
            nullable = false
    )
    private String blueTeamName;

    @Column(
            name = "red_team_name",
            nullable = false
    )
    private String redTeamName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DraftStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DraftMode mode;

    @Column(
            name = "blue_side_captain_id",
            nullable = false
    )
    private UUID blueSideCaptainId;

    @Column(name = "red_side_captain_id")
    private UUID redSideCaptainId;

    @Column(name = "blue_side_team_id")
    private UUID blueSideTeamId;

    @Column(name = "red_side_team_id")
    private UUID redSideTeamId;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_phase")
    private DraftPhase currentPhase;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_turn")
    private DraftTurn currentTurn;

    @Column(name = "game_number")
    @Builder.Default
    private Integer gameNumber = 1;

    @Column(name = "ban_phase_timer_duration")
    @Builder.Default
    private Integer banPhaseTimerDuration = 30;

    @Column(name = "pick_phase_timer_duration")
    @Builder.Default
    private Integer pickPhaseTimerDuration = 60;

    @Column(name = "timer_end_time")
    private LocalDateTime timerEndTime;

    @Column(name = "spectate_token")
    private String spectateToken;

    @Column(name = "blue_captain_token")
    private String blueCaptainToken;

    @Column(name = "red_captain_token")
    private String redCaptainToken;

    @OneToMany(
            mappedBy = "draft",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Pick> blueSidePicks = new ArrayList<>();

    @OneToMany(
            mappedBy = "draft",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Pick> redSidePicks = new ArrayList<>();

    @OneToMany(
            mappedBy = "draft",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Ban> blueSideBans = new ArrayList<>();

    @OneToMany(
            mappedBy = "draft",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Ban> redSideBans = new ArrayList<>();

    @OneToMany(
            mappedBy = "draft",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<DraftHistory> draftHistory = new ArrayList<>();

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Generates a unique spectate token for this draft.
     */
    public void generateSpectateToken()
    {
        this.spectateToken = UUID.randomUUID().toString();
    }

    /**
     * Generates unique captain tokens for this draft.
     */
    public void generateCaptainTokens()
    {
        this.blueCaptainToken = UUID.randomUUID().toString();
        this.redCaptainToken = UUID.randomUUID().toString();
    }

    /**
     * Generates all tokens (spectate and captain) for this draft.
     */
    public void generateAllTokens()
    {
        generateSpectateToken();
        generateCaptainTokens();
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
     * Gets the spectator URL for this draft.
     */
    public String getSpectateUrl()
    {
        if (spectateToken == null)
        {
            return null;
        }
        return "/draft/spectate/" + spectateToken;
    }

    /**
     * Gets the blue captain URL for this draft.
     */
    public String getBlueCaptainUrl()
    {
        if (blueCaptainToken == null)
        {
            return null;
        }
        return "/draft/captain/blue/" + blueCaptainToken;
    }

    /**
     * Gets the red captain URL for this draft.
     */
    public String getRedCaptainUrl()
    {
        if (redCaptainToken == null)
        {
            return null;
        }
        return "/draft/captain/red/" + redCaptainToken;
    }

}
