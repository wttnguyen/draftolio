package org.willwin.draftolioai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.willwin.draftolioai.domain.Draft;
import org.willwin.draftolioai.domain.DraftMode;
import org.willwin.draftolioai.domain.DraftStatus;
import org.willwin.draftolioai.repository.DraftRepository;
import org.willwin.draftolioai.service.dto.CreateDraftRequest;
import org.willwin.draftolioai.service.dto.DraftResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing draft operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DraftService
{

    private final DraftRepository draftRepository;

    /**
     * Creates a new draft according to US-1 acceptance criteria.
     *
     * @param request   the draft creation request
     * @param creatorId the ID of the user creating the draft
     * @return the created draft response
     */
    public DraftResponse createDraft(CreateDraftRequest request, UUID creatorId)
    {
        log.info(
                "Creating draft with teams: {} vs {} for user: {}", request.getBlueTeamName(),
                request.getRedTeamName(), creatorId
        );

        // Validate the request
        validateCreateDraftRequest(request);

        // Create the draft entity
        Draft draft = Draft
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .blueTeamName(request.getBlueTeamName())
                .redTeamName(request.getRedTeamName())
                .mode(request.getMode())
                .status(DraftStatus.CREATED)
                .blueSideCaptainId(creatorId) // Keep for internal tracking
                .blueSideTeamId(request.getBlueSideTeamId())
                .redSideTeamId(request.getRedSideTeamId())
                .banPhaseTimerDuration(request.getBanPhaseTimerDuration())
                .pickPhaseTimerDuration(request.getPickPhaseTimerDuration())
                .build();

        // Generate all tokens (spectate and captain)
        draft.generateAllTokens();

        // Save the draft
        Draft savedDraft = draftRepository.save(draft);

        log.info(
                "Draft created successfully with ID: {}, teams: {} vs {}", savedDraft.getId(),
                savedDraft.getBlueTeamName(), savedDraft.getRedTeamName()
        );

        return mapToResponse(savedDraft);
    }

    /**
     * Retrieves a draft by its ID.
     */
    @Transactional(readOnly = true)
    public Optional<DraftResponse> getDraftById(UUID draftId)
    {
        return draftRepository.findById(draftId).map(this::mapToResponse);
    }

    /**
     * Retrieves a draft by its spectate token.
     */
    @Transactional(readOnly = true)
    public Optional<DraftResponse> getDraftBySpectateToken(String spectateToken)
    {
        return draftRepository.findBySpectateToken(spectateToken).map(this::mapToResponse);
    }

    /**
     * Retrieves a draft by its blue captain token.
     */
    @Transactional(readOnly = true)
    public Optional<DraftResponse> getDraftByBlueCaptainToken(String blueCaptainToken)
    {
        return draftRepository.findByBlueCaptainToken(blueCaptainToken).map(this::mapToResponse);
    }

    /**
     * Retrieves a draft by its red captain token.
     */
    @Transactional(readOnly = true)
    public Optional<DraftResponse> getDraftByRedCaptainToken(String redCaptainToken)
    {
        return draftRepository.findByRedCaptainToken(redCaptainToken).map(this::mapToResponse);
    }

    /**
     * Retrieves all drafts for a user (where they are either blue or red captain).
     */
    @Transactional(readOnly = true)
    public List<DraftResponse> getDraftsByUser(UUID userId)
    {
        return draftRepository.findByUserInvolved(userId).stream().map(this::mapToResponse).toList();
    }

    /**
     * Retrieves completed drafts for a user with pagination.
     */
    @Transactional(readOnly = true)
    public Page<DraftResponse> getCompletedDraftsByUser(UUID userId, Pageable pageable)
    {
        return draftRepository.findCompletedDraftsByUser(userId, pageable).map(this::mapToResponse);
    }

    /**
     * Retrieves drafts by mode with pagination.
     */
    @Transactional(readOnly = true)
    public Page<DraftResponse> getDraftsByMode(DraftMode mode, Pageable pageable)
    {
        return draftRepository.findByMode(mode, pageable).map(this::mapToResponse);
    }

    /**
     * Retrieves drafts created within a date range.
     */
    @Transactional(readOnly = true)
    public Page<DraftResponse> getDraftsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable)
    {
        return draftRepository.findByCreatedAtBetween(startDate, endDate, pageable).map(this::mapToResponse);
    }

    /**
     * Generates a new spectate URL for an existing draft.
     */
    public String generateSpectateUrl(UUID draftId, UUID userId)
    {
        Draft draft = draftRepository
                .findById(draftId)
                .orElseThrow(() -> new IllegalArgumentException("Draft not found"));

        // Check if user is authorized to generate spectate URL (must be a captain)
        if (!draft.getBlueSideCaptainId().equals(userId) && (draft.getRedSideCaptainId() == null || !draft
                .getRedSideCaptainId()
                .equals(userId)))
        {
            throw new IllegalArgumentException("User is not authorized to generate spectate URL for this draft");
        }

        // Generate new spectate token if it doesn't exist
        if (draft.getSpectateToken() == null)
        {
            draft.generateSpectateToken();
            draftRepository.save(draft);
        }

        return draft.getSpectateUrl();
    }

    /**
     * Counts active drafts for a user.
     */
    @Transactional(readOnly = true)
    public long countActiveDraftsByUser(UUID userId)
    {
        return draftRepository.countActiveDraftsByUser(userId);
    }

    /**
     * Validates the create draft request.
     */
    private void validateCreateDraftRequest(CreateDraftRequest request)
    {
        if (request.getBlueTeamName() == null || request.getBlueTeamName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Blue team name is required");
        }

        if (request.getRedTeamName() == null || request.getRedTeamName().trim().isEmpty())
        {
            throw new IllegalArgumentException("Red team name is required");
        }

        if (request.getMode() == null)
        {
            throw new IllegalArgumentException("Draft mode is required");
        }

        if (request.getBanPhaseTimerDuration() != null && request.getBanPhaseTimerDuration() < 10)
        {
            throw new IllegalArgumentException("Ban phase timer duration must be at least 10 seconds");
        }

        if (request.getPickPhaseTimerDuration() != null && request.getPickPhaseTimerDuration() < 10)
        {
            throw new IllegalArgumentException("Pick phase timer duration must be at least 10 seconds");
        }

        // For team-based drafts, both teams must be specified
        if (request.getBlueSideTeamId() != null || request.getRedSideTeamId() != null)
        {
            if (request.getBlueSideTeamId() == null || request.getRedSideTeamId() == null)
            {
                throw new IllegalArgumentException(
                        "Both blue side and red side teams must be specified for team-based drafts");
            }
        }
    }

    /**
     * Maps a Draft entity to a DraftResponse DTO.
     */
    private DraftResponse mapToResponse(Draft draft)
    {
        return DraftResponse
                .builder()
                .id(draft.getId().toString())
                .name(draft.getName())
                .description(draft.getDescription())
                .blueTeamName(draft.getBlueTeamName())
                .redTeamName(draft.getRedTeamName())
                .status(draft.getStatus())
                .mode(draft.getMode())
                .blueSideCaptainId(draft.getBlueSideCaptainId().toString())
                .redSideCaptainId(draft.getRedSideCaptainId() != null ? draft.getRedSideCaptainId().toString() : null)
                .blueSideTeamId(draft.getBlueSideTeamId() != null ? draft.getBlueSideTeamId().toString() : null)
                .redSideTeamId(draft.getRedSideTeamId() != null ? draft.getRedSideTeamId().toString() : null)
                .currentPhase(draft.getCurrentPhase())
                .currentTurn(draft.getCurrentTurn())
                .gameNumber(draft.getGameNumber())
                .banPhaseTimerDuration(draft.getBanPhaseTimerDuration())
                .pickPhaseTimerDuration(draft.getPickPhaseTimerDuration())
                .timerEndTime(draft.getTimerEndTime())
                .spectateUrl(draft.getSpectateUrl())
                .blueCaptainUrl(draft.getBlueCaptainUrl())
                .redCaptainUrl(draft.getRedCaptainUrl())
                .createdAt(draft.getCreatedAt())
                .updatedAt(draft.getUpdatedAt())
                .completedAt(draft.getCompletedAt())
                .build();
    }

}
