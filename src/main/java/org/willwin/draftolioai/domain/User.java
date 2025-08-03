package org.willwin.draftolioai.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * User entity representing an authenticated user from Riot Sign-On (RSO).
 * <p>
 * This entity stores the essential user information obtained from RSO
 * including the unique subject identifier and game region.
 */
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(
                        name = "idx_user_sub",
                        columnList = "sub",
                        unique = true
                ),
                @Index(
                        name = "idx_user_cpid",
                        columnList = "cpid"
                )
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User
{

    /**
     * Primary key for the user entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Subject identifier from RSO - unique user ID
     * This is the 'sub' claim from the RSO UserInfo endpoint
     */
    @Column(
            name = "sub",
            nullable = false,
            unique = true,
            length = 255
    )
    @NotBlank
    private String subject;

    /**
     * Client Platform ID (CPID) - game region for League of Legends
     * This is the 'cpid' claim from the RSO UserInfo endpoint
     */
    @Column(
            name = "cpid",
            nullable = false,
            length = 10
    )
    @NotBlank
    private String cpid;

    /**
     * User's display name or summoner name (if available)
     */
    @Column(
            name = "display_name",
            length = 100
    )
    private String displayName;

    /**
     * User's email address (if available and consented)
     */
    @Column(
            name = "email",
            length = 255
    )
    private String email;

    /**
     * Timestamp when the user was first created in our system
     */
    @Column(
            name = "created_at",
            nullable = false
    )
    @NotNull
    @Builder.Default
    private Instant createdAt = Instant.now();

    /**
     * Timestamp when the user information was last updated
     */
    @Column(
            name = "updated_at",
            nullable = false
    )
    @NotNull
    @Builder.Default
    private Instant updatedAt = Instant.now();

    /**
     * Timestamp when the user last logged in
     */
    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    /**
     * Whether the user account is active
     */
    @Column(
            name = "active",
            nullable = false
    )
    @NotNull
    @Builder.Default
    private Boolean active = true;

    /**
     * Update the updatedAt timestamp before persisting
     */
    @PreUpdate
    protected void onUpdate()
    {
        this.updatedAt = Instant.now();
    }

    /**
     * Update the last login timestamp
     */
    public void updateLastLogin()
    {
        this.lastLoginAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    /**
     * Check if the user is from a specific region
     *
     * @param region the region code to check
     * @return true if the user's CPID matches the region
     */
    public boolean isFromRegion(String region)
    {
        return this.cpid != null && this.cpid.equalsIgnoreCase(region);
    }

}
