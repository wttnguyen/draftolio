package org.willwin.draftolioai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.willwin.draftolioai.config.RsoProperties;
import org.willwin.draftolioai.domain.User;
import org.willwin.draftolioai.repository.UserRepository;
import org.willwin.draftolioai.service.dto.UserInfoResponse;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing users authenticated through Riot Sign-On (RSO).
 * <p>
 * This service handles user creation, updates, and authentication processing
 * for users who authenticate via RSO OAuth 2.0 flow.
 */
@Service
@Transactional
@Slf4j
public class UserService
{

    private final UserRepository userRepository;

    private final RsoProperties rsoProperties;

    private final WebClient webClient;

    public UserService(UserRepository userRepository, RsoProperties rsoProperties, WebClient.Builder webClientBuilder)
    {
        this.userRepository = userRepository;
        this.rsoProperties = rsoProperties;
        this.webClient = webClientBuilder.build();
    }

    /**
     * Process an authenticated user from RSO OAuth 2.0 flow.
     * <p>
     * This method creates or updates a user based on the OAuth2User information
     * and returns a UserInfoResponse with the user's details.
     *
     * @param principal        the OAuth2User from Spring Security
     * @param authorizedClient the OAuth2AuthorizedClient with token information
     * @return UserInfoResponse with user information and authentication details
     */
    public UserInfoResponse processAuthenticatedUser(OAuth2User principal, OAuth2AuthorizedClient authorizedClient)
    {
        try
        {
            // Extract user information from OAuth2User
            String subject = principal.getAttribute("sub");
            String cpid = principal.getAttribute("cpid");

            if (subject == null)
            {
                log.error("No subject (sub) found in OAuth2User attributes");
                throw new IllegalArgumentException("Invalid user principal: missing subject");
            }

            // Fetch additional user info from RSO UserInfo endpoint if needed
            Map<String, Object> userInfo = fetchUserInfoFromRso(authorizedClient);
            if (userInfo != null)
            {
                // Update principal attributes with fresh data from RSO
                subject = (String) userInfo.getOrDefault("sub", subject);
                cpid = (String) userInfo.getOrDefault("cpid", cpid);
            }

            // Find or create user in database
            User user = findOrCreateUser(subject, cpid, principal);

            // Update last login timestamp
            user.updateLastLogin();
            user = userRepository.save(user);

            // Extract token information
            Instant accessTokenExpiresAt = authorizedClient.getAccessToken().getExpiresAt();
            String[] scopes = authorizedClient.getAccessToken().getScopes().toArray(new String[0]);

            // Create and return UserInfoResponse
            return UserInfoResponse.authenticated(
                    user.getId(), user.getSubject(), user.getCpid(), user.getDisplayName(), user.getEmail(),
                    user.getActive(), user.getCreatedAt(), user.getUpdatedAt(), user.getLastLoginAt(),
                    accessTokenExpiresAt, scopes
            );

        }
        catch (Exception e)
        {
            log.error("Error processing authenticated user", e);
            throw new RuntimeException("Failed to process authenticated user", e);
        }
    }

    /**
     * Find an existing user or create a new one based on RSO subject identifier.
     *
     * @param subject   the RSO subject identifier
     * @param cpid      the Client Platform ID (game region)
     * @param principal the OAuth2User with additional attributes
     * @return the User entity (existing or newly created)
     */
    private User findOrCreateUser(String subject, String cpid, OAuth2User principal)
    {
        Optional<User> existingUser = userRepository.findBySubject(subject);

        if (existingUser.isPresent())
        {
            // Update existing user with latest information
            User user = existingUser.get();
            updateUserFromPrincipal(user, cpid, principal);
            return user;
        }
        else
        {
            // Create new user
            return createUserFromPrincipal(subject, cpid, principal);
        }
    }

    /**
     * Create a new user from OAuth2User principal.
     *
     * @param subject   the RSO subject identifier
     * @param cpid      the Client Platform ID
     * @param principal the OAuth2User with user attributes
     * @return the newly created User entity
     */
    private User createUserFromPrincipal(String subject, String cpid, OAuth2User principal)
    {
        log.info("Creating new user with subject: {}, cpid: {}", subject, cpid);

        User user = User
                .builder()
                .subject(subject)
                .cpid(cpid != null ? cpid : "UNKNOWN")
                .displayName(extractDisplayName(principal))
                .email(extractEmail(principal))
                .active(true)
                .build();

        return userRepository.save(user);
    }

    /**
     * Update an existing user with information from OAuth2User principal.
     *
     * @param user      the existing User entity
     * @param cpid      the Client Platform ID
     * @param principal the OAuth2User with updated attributes
     */
    private void updateUserFromPrincipal(User user, String cpid, OAuth2User principal)
    {
        boolean updated = false;

        // Update CPID if it has changed
        if (cpid != null && !cpid.equals(user.getCpid()))
        {
            user.setCpid(cpid);
            updated = true;
        }

        // Update display name if available and different
        String displayName = extractDisplayName(principal);
        if (displayName != null && !displayName.equals(user.getDisplayName()))
        {
            user.setDisplayName(displayName);
            updated = true;
        }

        // Update email if available and different
        String email = extractEmail(principal);
        if (email != null && !email.equals(user.getEmail()))
        {
            user.setEmail(email);
            updated = true;
        }

        if (updated)
        {
            log.debug("Updated user information for subject: {}", user.getSubject());
        }
    }

    /**
     * Extract display name from OAuth2User attributes.
     *
     * @param principal the OAuth2User
     * @return the display name or null if not available
     */
    private String extractDisplayName(OAuth2User principal)
    {
        // Try different attribute names that might contain display name
        String displayName = principal.getAttribute("name");
        if (displayName == null)
        {
            displayName = principal.getAttribute("preferred_username");
        }
        if (displayName == null)
        {
            displayName = principal.getAttribute("username");
        }
        return displayName;
    }

    /**
     * Extract email from OAuth2User attributes.
     *
     * @param principal the OAuth2User
     * @return the email or null if not available
     */
    private String extractEmail(OAuth2User principal)
    {
        return principal.getAttribute("email");
    }

    /**
     * Fetch fresh user information from RSO UserInfo endpoint.
     *
     * @param authorizedClient the OAuth2AuthorizedClient with access token
     * @return Map containing user information from RSO, or null if failed
     */
    private Map<String, Object> fetchUserInfoFromRso(OAuth2AuthorizedClient authorizedClient)
    {
        try
        {
            String accessToken = authorizedClient.getAccessToken().getTokenValue();

            return webClient
                    .get()
                    .uri(rsoProperties.userInfoUri())
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

        }
        catch (Exception e)
        {
            log.warn("Failed to fetch user info from RSO UserInfo endpoint", e);
            return null;
        }
    }

    /**
     * Find a user by their RSO subject identifier.
     *
     * @param subject the RSO subject identifier
     * @return Optional containing the user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> findBySubject(String subject)
    {
        return userRepository.findBySubject(subject);
    }

    /**
     * Find users by their game region (CPID).
     *
     * @param cpid the Client Platform ID
     * @return list of users in the specified region
     */
    @Transactional(readOnly = true)
    public java.util.List<User> findByCpid(String cpid)
    {
        return userRepository.findByCpid(cpid);
    }

    /**
     * Get user information by subject identifier.
     *
     * @param subject the RSO subject identifier
     * @return UserInfoResponse or null if user not found
     */
    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(String subject)
    {
        Optional<User> userOpt = userRepository.findBySubject(subject);

        if (userOpt.isEmpty())
        {
            return null;
        }

        User user = userOpt.get();
        return UserInfoResponse
                .builder()
                .id(user.getId())
                .subject(user.getSubject())
                .cpid(user.getCpid())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    /**
     * Deactivate a user account.
     *
     * @param subject the RSO subject identifier
     * @return true if user was deactivated, false if not found
     */
    public boolean deactivateUser(String subject)
    {
        Optional<User> userOpt = userRepository.findBySubject(subject);

        if (userOpt.isEmpty())
        {
            return false;
        }

        User user = userOpt.get();
        user.setActive(false);
        userRepository.save(user);

        log.info("Deactivated user with subject: {}", subject);
        return true;
    }

}
