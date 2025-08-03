package org.willwin.draftolioai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.willwin.draftolioai.domain.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * <p>
 * Provides data access methods for managing users authenticated through
 * Riot Sign-On (RSO).
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

    /**
     * Find a user by their RSO subject identifier.
     *
     * @param subject the RSO subject identifier (sub claim)
     * @return Optional containing the user if found
     */
    Optional<User> findBySubject(String subject);

    /**
     * Find users by their game region (CPID).
     *
     * @param cpid the Client Platform ID (game region)
     * @return list of users in the specified region
     */
    List<User> findByCpid(String cpid);

    /**
     * Find active users by their game region.
     *
     * @param cpid   the Client Platform ID (game region)
     * @param active whether the user is active
     * @return list of active users in the specified region
     */
    List<User> findByCpidAndActive(String cpid, Boolean active);

    /**
     * Check if a user exists with the given subject identifier.
     *
     * @param subject the RSO subject identifier
     * @return true if user exists, false otherwise
     */
    boolean existsBySubject(String subject);

    /**
     * Find users who haven't logged in since a specific date.
     *
     * @param lastLoginBefore the cutoff date for last login
     * @return list of users who haven't logged in recently
     */
    @Query("SELECT u FROM User u WHERE u.lastLoginAt < :lastLoginBefore OR u.lastLoginAt IS NULL")
    List<User> findUsersWithLastLoginBefore(
            @Param("lastLoginBefore") Instant lastLoginBefore);

    /**
     * Update the last login timestamp for a user.
     *
     * @param subject     the RSO subject identifier
     * @param lastLoginAt the new last login timestamp
     * @return number of updated records
     */
    @Modifying
    @Query("UPDATE User u SET u.lastLoginAt = :lastLoginAt, u.updatedAt = :updatedAt WHERE u.subject = :subject")
    int updateLastLoginBySubject(
            @Param("subject") String subject,
            @Param("lastLoginAt") Instant lastLoginAt,
            @Param("updatedAt") Instant updatedAt);

    /**
     * Find users by display name (case-insensitive partial match).
     *
     * @param displayName the display name to search for
     * @return list of users with matching display names
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.displayName) LIKE LOWER(CONCAT('%', :displayName, '%'))")
    List<User> findByDisplayNameContainingIgnoreCase(
            @Param("displayName") String displayName);

    /**
     * Count active users by region.
     *
     * @param cpid the Client Platform ID (game region)
     * @return count of active users in the region
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.cpid = :cpid AND u.active = true")
    long countActiveUsersByCpid(
            @Param("cpid") String cpid);

    /**
     * Find users created within a date range.
     *
     * @param startDate the start of the date range
     * @param endDate   the end of the date range
     * @return list of users created within the date range
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findUsersCreatedBetween(
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);

}
