package org.willwin.draftolioai.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DraftHistory entity representing the history of actions during a draft.
 */
@Entity
@Table(name = "draft_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DraftHistory
{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "draft_id",
            nullable = false
    )
    private Draft draft;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType action;

    @Column(name = "champion_id")
    private String championId;

    @Column(name = "champion_name")
    private String championName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DraftTurn team;

    @Column(
            name = "user_id",
            nullable = false
    )
    private UUID userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "player_name")
    private String playerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Pick.Position position;

    @Column(
            name = "action_order",
            nullable = false
    )
    private Integer actionOrder;

    @CreationTimestamp
    @Column(
            name = "timestamp",
            nullable = false,
            updatable = false
    )
    private LocalDateTime timestamp;

    /**
     * Enumeration representing the type of action in draft history.
     */
    @Getter
    public enum ActionType
    {
        PICK("Pick"),
        BAN("Ban"),
        DRAFT_STARTED("Draft Started"),
        DRAFT_COMPLETED("Draft Completed"),
        DRAFT_CANCELLED("Draft Cancelled");

        private final String displayName;

        ActionType(String displayName)
        {
            this.displayName = displayName;
        }

    }

}
