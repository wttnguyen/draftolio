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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Ban entity representing a champion ban during a draft.
 */
@Entity
@Table(name = "bans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ban
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

    @Column(
            name = "champion_id",
            nullable = false
    )
    private String championId;

    @Column(
            name = "champion_name",
            nullable = false
    )
    private String championName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DraftTurn team;

    @Column(
            name = "ban_order",
            nullable = false
    )
    private Integer banOrder;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

}
