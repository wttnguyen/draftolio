package org.willwin.draftolioai.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.reactive.function.client.WebClient;
import org.willwin.draftolioai.config.RsoProperties;
import org.willwin.draftolioai.domain.User;
import org.willwin.draftolioai.repository.UserRepository;
import org.willwin.draftolioai.service.dto.UserInfoResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest
{

    @Mock
    private UserRepository userRepository;

    @Mock
    private RsoProperties rsoProperties;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private OAuth2User principal;

    @Mock
    private OAuth2AuthorizedClient authorizedClient;

    @Mock
    private OAuth2AccessToken accessToken;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp()
    {
        when(webClientBuilder.build()).thenReturn(webClient);
        userService = new UserService(userRepository, rsoProperties, webClientBuilder);
    }

    @Test
    void processAuthenticatedUser_NewUser_ShouldCreateUser()
    {
        // Given
        String subject = "test-subject-123";
        String cpid = "NA1";

        when(principal.getAttribute("sub")).thenReturn(subject);
        when(principal.getAttribute("cpid")).thenReturn(cpid);
        when(userRepository.findBySubject(subject)).thenReturn(Optional.empty());

        User newUser = User
                .builder()
                .id(1L)
                .subject(subject)
                .cpid(cpid)
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(authorizedClient.getAccessToken()).thenReturn(accessToken);
        when(accessToken.getExpiresAt()).thenReturn(Instant.now().plusSeconds(600));
        when(accessToken.getScopes()).thenReturn(Set.of("openid", "cpid", "offline_access"));

        // When
        UserInfoResponse result = userService.processAuthenticatedUser(principal, authorizedClient);

        // Then
        assertNotNull(result);
        assertEquals(subject, result.subject());
        assertEquals(cpid, result.cpid());
        assertTrue(result.active());
        assertTrue(result.authenticationInfo().authenticated());
        assertEquals("rso", result.authenticationInfo().provider());

        verify(userRepository).findBySubject(subject);
        verify(userRepository, times(2)).save(any(User.class)); // Once for creation, once for last login update
    }

    @Test
    void processAuthenticatedUser_ExistingUser_ShouldUpdateUser()
    {
        // Given
        String subject = "existing-subject-456";
        String cpid = "EUW1";

        User existingUser = User
                .builder()
                .id(2L)
                .subject(subject)
                .cpid("NA1") // Different CPID to test update
                .active(true)
                .createdAt(Instant.now().minus(Duration.ofDays(1)))
                .updatedAt(Instant.now().minus(Duration.ofDays(1)))
                .build();

        when(principal.getAttribute("sub")).thenReturn(subject);
        when(principal.getAttribute("cpid")).thenReturn(cpid);
        when(userRepository.findBySubject(subject)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(authorizedClient.getAccessToken()).thenReturn(accessToken);
        when(accessToken.getExpiresAt()).thenReturn(Instant.now().plusSeconds(600));
        when(accessToken.getScopes()).thenReturn(Set.of("openid", "cpid", "offline_access"));

        // When
        UserInfoResponse result = userService.processAuthenticatedUser(principal, authorizedClient);

        // Then
        assertNotNull(result);
        assertEquals(subject, result.subject());
        assertEquals(cpid, result.cpid()); // Should be updated to EUW1
        assertTrue(result.active());

        verify(userRepository).findBySubject(subject);
        verify(userRepository).save(any(User.class)); // Once for last login update
    }

    @Test
    void processAuthenticatedUser_MissingSubject_ShouldThrowException()
    {
        // Given
        when(principal.getAttribute("sub")).thenReturn(null);

        // When & Then
        assertThrows(
                RuntimeException.class, () ->
                {
                    userService.processAuthenticatedUser(principal, authorizedClient);
                }
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findBySubject_ExistingUser_ShouldReturnUser()
    {
        // Given
        String subject = "test-subject";
        User user = User.builder().subject(subject).cpid("NA1").active(true).build();

        when(userRepository.findBySubject(subject)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.findBySubject(subject);

        // Then
        assertTrue(result.isPresent());
        assertEquals(subject, result.get().getSubject());
        verify(userRepository).findBySubject(subject);
    }

    @Test
    void deactivateUser_ExistingUser_ShouldDeactivateUser()
    {
        // Given
        String subject = "test-subject";
        User user = User.builder().subject(subject).cpid("NA1").active(true).build();

        when(userRepository.findBySubject(subject)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        boolean result = userService.deactivateUser(subject);

        // Then
        assertTrue(result);
        assertFalse(user.getActive());
        verify(userRepository).findBySubject(subject);
        verify(userRepository).save(user);
    }

    @Test
    void deactivateUser_NonExistingUser_ShouldReturnFalse()
    {
        // Given
        String subject = "non-existing-subject";
        when(userRepository.findBySubject(subject)).thenReturn(Optional.empty());

        // When
        boolean result = userService.deactivateUser(subject);

        // Then
        assertFalse(result);
        verify(userRepository).findBySubject(subject);
        verify(userRepository, never()).save(any(User.class));
    }

}
