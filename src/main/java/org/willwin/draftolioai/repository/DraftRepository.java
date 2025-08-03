package org.willwin.draftolioai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.willwin.draftolioai.domain.Draft;
import org.willwin.draftolioai.domain.DraftMode;
import org.willwin.draftolioai.domain.DraftStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Draft entity operations.
 */
@Repository
public interface DraftRepository extends JpaRepository<Draft, UUID>
{

    /**
     * Finds a draft by its spectate token.
     */
    Optional<Draft> findBySpectateToken(String spectateToken);

    /**
     * Finds a draft by its blue captain token.
     */
    Optional<Draft> findByBlueCaptainToken(String blueCaptainToken);

    /**
     * Finds a draft by its red captain token.
     */
    Optional<Draft> findByRedCaptainToken(String redCaptainToken);

    /**
     * Finds all drafts created by a specific user (as blue side captain).
     */
    List<Draft> findByBlueSideCaptainIdOrderByCreatedAtDesc(UUID blueSideCaptainId);

    /**
     * Finds all drafts where a user is either blue side or red side captain.
     */
    @Query("SELECT d FROM Draft d WHERE d.blueSideCaptainId = :userId OR d.redSideCaptainId = :userId ORDER BY d.createdAt DESC")
    List<Draft> findByUserInvolved(
            @Param("userId") UUID userId);

    /**
     * Finds drafts by status.
     */
    List<Draft> findByStatusOrderByCreatedAtDesc(DraftStatus status);

    /**
     * Finds drafts by mode with pagination.
     */
    Page<Draft> findByMode(DraftMode mode, Pageable pageable);

    /**
     * Finds drafts created within a date range.
     */
    @Query("SELECT d FROM Draft d WHERE d.createdAt BETWEEN :startDate AND :endDate ORDER BY d.createdAt DESC")
    Page<Draft> findByCreatedAtBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate, Pageable pageable);

    /**
     * Finds drafts by mode and date range.
     */
    @Query("SELECT d FROM Draft d WHERE d.mode = :mode AND d.createdAt BETWEEN :startDate AND :endDate ORDER BY d.createdAt DESC")
    Page<Draft> findByModeAndCreatedAtBetween(
            @Param("mode") DraftMode mode,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate, Pageable pageable);

    /**
     * Finds all completed drafts for a user with pagination.
     */
    @Query("SELECT d FROM Draft d WHERE (d.blueSideCaptainId = :userId OR d.redSideCaptainId = :userId) AND d.status = 'COMPLETED' ORDER BY d.completedAt DESC")
    Page<Draft> findCompletedDraftsByUser(
            @Param("userId") UUID userId, Pageable pageable);

    /**
     * Counts active drafts (created or in progress) for a user.
     */
    @Query("SELECT COUNT(d) FROM Draft d WHERE (d.blueSideCaptainId = :userId OR d.redSideCaptainId = :userId) AND d.status IN ('CREATED', 'IN_PROGRESS')")
    long countActiveDraftsByUser(
            @Param("userId") UUID userId);

}
