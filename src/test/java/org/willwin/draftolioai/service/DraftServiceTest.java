package org.willwin.draftolioai.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.willwin.draftolioai.domain.Draft;
import org.willwin.draftolioai.domain.DraftMode;
import org.willwin.draftolioai.domain.DraftStatus;
import org.willwin.draftolioai.repository.DraftRepository;
import org.willwin.draftolioai.service.dto.CreateDraftRequest;
import org.willwin.draftolioai.service.dto.DraftResponse;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DraftService.
 * Tests US-1 acceptance criteria implementation.
 */
@ExtendWith(MockitoExtension.class)
class DraftServiceTest
{

    @Mock
    private DraftRepository draftRepository;

    @InjectMocks
    private DraftService draftService;

    private UUID creatorId;

    private CreateDraftRequest validRequest;

    private Draft savedDraft;

    @BeforeEach
    void setUp()
    {
        creatorId = UUID.randomUUID();

        // Create a valid draft request for testing
        validRequest = CreateDraftRequest
                .builder()
                .name("Test Draft")
                .description("Test description")
                .blueTeamName("Blue Team")
                .redTeamName("Red Team")
                .mode(DraftMode.TOURNAMENT)
                .banPhaseTimerDuration(30)
                .pickPhaseTimerDuration(60)
                .build();

        // Create a mock saved draft
        savedDraft = Draft
                .builder()
                .id(UUID.randomUUID())
                .name("Test Draft")
                .description("Test description")
                .blueTeamName("Blue Team")
                .redTeamName("Red Team")
                .mode(DraftMode.TOURNAMENT)
                .status(DraftStatus.CREATED)
                .blueSideCaptainId(creatorId)
                .banPhaseTimerDuration(30)
                .pickPhaseTimerDuration(60)
                .spectateToken("test-token")
                .blueCaptainToken("blue-captain-token")
                .redCaptainToken("red-captain-token")
                .build();
    }

    @Test
    void createDraft_WithValidRequest_ShouldCreateDraftSuccessfully()
    {
        // Given
        when(draftRepository.save(any(Draft.class))).thenReturn(savedDraft);

        // When
        DraftResponse response = draftService.createDraft(validRequest, creatorId);

        // Then
        assertNotNull(response);
        assertEquals("Test Draft", response.getName());
        assertEquals("Test description", response.getDescription());
        assertEquals("Blue Team", response.getBlueTeamName());
        assertEquals("Red Team", response.getRedTeamName());
        assertEquals(DraftMode.TOURNAMENT, response.getMode());
        assertEquals(DraftStatus.CREATED, response.getStatus());
        assertEquals(creatorId.toString(), response.getBlueSideCaptainId());
        assertEquals(30, response.getBanPhaseTimerDuration());
        assertEquals(60, response.getPickPhaseTimerDuration());
        assertNotNull(response.getSpectateUrl());
        assertNotNull(response.getBlueCaptainUrl());
        assertNotNull(response.getRedCaptainUrl());

        // Verify that save was called
        verify(draftRepository, times(1)).save(any(Draft.class));
    }

    @Test
    void createDraft_WithNullBlueTeamName_ShouldThrowException()
    {
        // Given
        CreateDraftRequest invalidRequest = CreateDraftRequest
                .builder()
                .blueTeamName(null)
                .redTeamName("Red Team")
                .mode(DraftMode.TOURNAMENT)
                .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> draftService.createDraft(invalidRequest, creatorId)
        );

        assertEquals("Blue team name is required", exception.getMessage());
        verify(draftRepository, never()).save(any(Draft.class));
    }

    @Test
    void createDraft_WithEmptyBlueTeamName_ShouldThrowException()
    {
        // Given
        CreateDraftRequest invalidRequest = CreateDraftRequest
                .builder()
                .blueTeamName("   ")
                .redTeamName("Red Team")
                .mode(DraftMode.TOURNAMENT)
                .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> draftService.createDraft(invalidRequest, creatorId)
        );

        assertEquals("Blue team name is required", exception.getMessage());
        verify(draftRepository, never()).save(any(Draft.class));
    }

    @Test
    void createDraft_WithNullRedTeamName_ShouldThrowException()
    {
        // Given
        CreateDraftRequest invalidRequest = CreateDraftRequest
                .builder()
                .blueTeamName("Blue Team")
                .redTeamName(null)
                .mode(DraftMode.TOURNAMENT)
                .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> draftService.createDraft(invalidRequest, creatorId)
        );

        assertEquals("Red team name is required", exception.getMessage());
        verify(draftRepository, never()).save(any(Draft.class));
    }

    @Test
    void createDraft_WithEmptyRedTeamName_ShouldThrowException()
    {
        // Given
        CreateDraftRequest invalidRequest = CreateDraftRequest
                .builder()
                .blueTeamName("Blue Team")
                .redTeamName("   ")
                .mode(DraftMode.TOURNAMENT)
                .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> draftService.createDraft(invalidRequest, creatorId)
        );

        assertEquals("Red team name is required", exception.getMessage());
        verify(draftRepository, never()).save(any(Draft.class));
    }

    @Test
    void createDraft_WithNullMode_ShouldThrowException()
    {
        // Given
        CreateDraftRequest invalidRequest = CreateDraftRequest.builder().name("Test Draft").mode(null).build();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> draftService.createDraft(invalidRequest, creatorId)
        );

        assertEquals("Draft mode is required", exception.getMessage());
        verify(draftRepository, never()).save(any(Draft.class));
    }

    @Test
    void createDraft_WithInvalidTimerDuration_ShouldThrowException()
    {
        // Given
        CreateDraftRequest invalidRequest = CreateDraftRequest
                .builder()
                .name("Test Draft")
                .mode(DraftMode.TOURNAMENT)
                .banPhaseTimerDuration(5) // Less than minimum of 10
                .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> draftService.createDraft(invalidRequest, creatorId)
        );

        assertEquals("Ban phase timer duration must be at least 10 seconds", exception.getMessage());
        verify(draftRepository, never()).save(any(Draft.class));
    }

    @Test
    void createDraft_CreatorAssignedAsBlueSideCaptain()
    {
        // Given
        when(draftRepository.save(any(Draft.class))).thenReturn(savedDraft);

        // When
        DraftResponse response = draftService.createDraft(validRequest, creatorId);

        // Then - US-1 AC5: Creator is assigned as Blue Side captain by default
        assertEquals(creatorId.toString(), response.getBlueSideCaptainId());
    }

    @Test
    void createDraft_SpectateTokenGenerated()
    {
        // Given
        when(draftRepository.save(any(Draft.class))).thenReturn(savedDraft);

        // When
        DraftResponse response = draftService.createDraft(validRequest, creatorId);

        // Then - US-1 AC6: Spectator link is generated
        assertNotNull(response.getSpectateUrl());
        assertTrue(response.getSpectateUrl().contains("/draft/spectate/"));
    }

    @Test
    void getDraftById_WithValidId_ShouldReturnDraft()
    {
        // Given
        UUID draftId = savedDraft.getId();
        when(draftRepository.findById(draftId)).thenReturn(Optional.of(savedDraft));

        // When
        Optional<DraftResponse> response = draftService.getDraftById(draftId);

        // Then
        assertTrue(response.isPresent());
        assertEquals("Test Draft", response.get().getName());
        verify(draftRepository, times(1)).findById(draftId);
    }

    @Test
    void getDraftById_WithInvalidId_ShouldReturnEmpty()
    {
        // Given
        UUID draftId = UUID.randomUUID();
        when(draftRepository.findById(draftId)).thenReturn(Optional.empty());

        // When
        Optional<DraftResponse> response = draftService.getDraftById(draftId);

        // Then
        assertFalse(response.isPresent());
        verify(draftRepository, times(1)).findById(draftId);
    }

    @Test
    void getDraftBySpectateToken_WithValidToken_ShouldReturnDraft()
    {
        // Given
        String spectateToken = "test-token";
        when(draftRepository.findBySpectateToken(spectateToken)).thenReturn(Optional.of(savedDraft));

        // When
        Optional<DraftResponse> response = draftService.getDraftBySpectateToken(spectateToken);

        // Then
        assertTrue(response.isPresent());
        assertEquals("Test Draft", response.get().getName());
        verify(draftRepository, times(1)).findBySpectateToken(spectateToken);
    }

    @Test
    void generateSpectateUrl_WithAuthorizedUser_ShouldReturnUrl()
    {
        // Given
        UUID draftId = savedDraft.getId();
        when(draftRepository.findById(draftId)).thenReturn(Optional.of(savedDraft));

        // When
        String spectateUrl = draftService.generateSpectateUrl(draftId, creatorId);

        // Then
        assertNotNull(spectateUrl);
        assertTrue(spectateUrl.contains("/draft/spectate/"));
    }

    @Test
    void generateSpectateUrl_WithUnauthorizedUser_ShouldThrowException()
    {
        // Given
        UUID draftId = savedDraft.getId();
        UUID unauthorizedUserId = UUID.randomUUID();
        when(draftRepository.findById(draftId)).thenReturn(Optional.of(savedDraft));

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> draftService.generateSpectateUrl(draftId, unauthorizedUserId)
        );

        assertEquals("User is not authorized to generate spectate URL for this draft", exception.getMessage());
    }

}
