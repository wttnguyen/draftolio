package org.willwin.draftolioai.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.willwin.draftolioai.domain.DraftMode;
import org.willwin.draftolioai.service.DraftService;
import org.willwin.draftolioai.service.dto.CreateDraftRequest;
import org.willwin.draftolioai.service.dto.DraftResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST controller for draft management operations.
 * Implements US-1 acceptance criteria for draft creation.
 */
@RestController
@RequestMapping("/api/v1/drafts")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*") // Allow frontend to access the API
public class DraftController
{

    private final DraftService draftService;

    /**
     * Creates a new draft.
     * US-1 AC1-6: Handles draft creation with all acceptance criteria.
     *
     * @param request the draft creation request
     * @return the created draft response
     */
    @PostMapping
    public ResponseEntity<DraftResponse> createDraft(
            @Valid
            @RequestBody
            CreateDraftRequest request,
            @RequestHeader(
                    value = "X-User-Id",
                    required = false
            ) String userIdHeader)
    {

        log.info("Creating draft with name: {}", request.getName());

        // For now, use a mock user ID since we don't have authentication yet
        // In a real implementation, this would come from the authentication context
        UUID creatorId = userIdHeader != null ? UUID.fromString(userIdHeader) : UUID.randomUUID();

        try
        {
            DraftResponse response = draftService.createDraft(request, creatorId);
            log.info("Draft created successfully with ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid draft creation request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e)
        {
            log.error("Error creating draft", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a draft by its ID.
     *
     * @param draftId the draft ID
     * @return the draft response
     */
    @GetMapping("/{draftId}")
    public ResponseEntity<DraftResponse> getDraftById(
            @PathVariable UUID draftId)
    {
        log.info("Retrieving draft with ID: {}", draftId);

        return draftService.getDraftById(draftId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all drafts for a user.
     *
     * @param userId the user ID
     * @return list of draft responses
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DraftResponse>> getDraftsByUser(
            @PathVariable UUID userId)
    {
        log.info("Retrieving drafts for user: {}", userId);

        List<DraftResponse> drafts = draftService.getDraftsByUser(userId);
        return ResponseEntity.ok(drafts);
    }

    /**
     * Retrieves draft history with optional filtering.
     *
     * @param mode      optional draft mode filter
     * @param startDate optional start date filter
     * @param endDate   optional end date filter
     * @param pageable  pagination parameters
     * @return page of draft responses
     */
    @GetMapping("/history")
    public ResponseEntity<Page<DraftResponse>> getDraftHistory(
            @RequestParam(required = false) DraftMode mode,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @PageableDefault(size = 20) Pageable pageable)
    {

        log.info("Retrieving draft history with mode: {}, startDate: {}, endDate: {}", mode, startDate, endDate);

        Page<DraftResponse> drafts;

        if (mode != null && startDate != null && endDate != null)
        {
            // Filter by mode and date range (not implemented in service yet)
            drafts = draftService.getDraftsByMode(mode, pageable);
        }
        else if (startDate != null && endDate != null)
        {
            drafts = draftService.getDraftsByDateRange(startDate, endDate, pageable);
        }
        else if (mode != null)
        {
            drafts = draftService.getDraftsByMode(mode, pageable);
        }
        else
        {
            // Return empty page for now - would need to implement getAllDrafts method
            drafts = Page.empty(pageable);
        }

        return ResponseEntity.ok(drafts);
    }

    /**
     * Generates a spectator URL for a draft.
     * US-1 AC6: User can generate a shareable spectator link.
     *
     * @param draftId the draft ID
     * @return the spectator URL
     */
    @PostMapping("/{draftId}/spectate")
    public ResponseEntity<Map<String, String>> generateSpectateUrl(
            @PathVariable UUID draftId,
            @RequestHeader(
                    value = "X-User-Id",
                    required = false
            ) String userIdHeader)
    {

        log.info("Generating spectate URL for draft: {}", draftId);

        // For now, use a mock user ID since we don't have authentication yet
        UUID userId = userIdHeader != null ? UUID.fromString(userIdHeader) : UUID.randomUUID();

        try
        {
            String spectateUrl = draftService.generateSpectateUrl(draftId, userId);
            Map<String, String> response = Map.of(
                    "spectateUrl", spectateUrl, "message",
                    "Spectator URL generated successfully"
            );
            return ResponseEntity.ok(response);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Error generating spectate URL: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e)
        {
            log.error("Error generating spectate URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a draft by its spectate token (for spectators).
     *
     * @param spectateToken the spectate token
     * @return the draft response
     */
    @GetMapping("/spectate/{spectateToken}")
    public ResponseEntity<DraftResponse> getDraftBySpectateToken(
            @PathVariable String spectateToken)
    {
        log.info("Retrieving draft by spectate token: {}", spectateToken);

        return draftService
                .getDraftBySpectateToken(spectateToken)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a draft by its blue captain token (for blue side captain).
     *
     * @param blueCaptainToken the blue captain token
     * @return the draft response
     */
    @GetMapping("/captain/blue/{blueCaptainToken}")
    public ResponseEntity<DraftResponse> getDraftByBlueCaptainToken(
            @PathVariable String blueCaptainToken)
    {
        log.info("Retrieving draft by blue captain token: {}", blueCaptainToken);

        return draftService
                .getDraftByBlueCaptainToken(blueCaptainToken)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a draft by its red captain token (for red side captain).
     *
     * @param redCaptainToken the red captain token
     * @return the draft response
     */
    @GetMapping("/captain/red/{redCaptainToken}")
    public ResponseEntity<DraftResponse> getDraftByRedCaptainToken(
            @PathVariable String redCaptainToken)
    {
        log.info("Retrieving draft by red captain token: {}", redCaptainToken);

        return draftService
                .getDraftByRedCaptainToken(redCaptainToken)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Gets available draft modes with their descriptions.
     * US-1 AC3: User sees explanation of mode rules when selecting a draft mode.
     *
     * @return map of draft modes and their descriptions
     */
    @GetMapping("/modes")
    public ResponseEntity<Map<String, Object>> getDraftModes()
    {
        log.info("Retrieving available draft modes");

        Map<String, Object> modes = Map.of(
                "TOURNAMENT",
                Map.of(
                        "name", DraftMode.TOURNAMENT.getDisplayName(), "description",
                        DraftMode.TOURNAMENT.getDescription()
                ), "FEARLESS",
                Map.of(
                        "name", DraftMode.FEARLESS.getDisplayName(), "description",
                        DraftMode.FEARLESS.getDescription()
                ), "FULL_FEARLESS",
                Map.of(
                        "name", DraftMode.FULL_FEARLESS.getDisplayName(), "description",
                        DraftMode.FULL_FEARLESS.getDescription()
                )
        );

        return ResponseEntity.ok(modes);
    }

    /**
     * Gets the count of active drafts for a user.
     *
     * @param userId the user ID
     * @return the count of active drafts
     */
    @GetMapping("/user/{userId}/active-count")
    public ResponseEntity<Map<String, Long>> getActiveDraftsCount(
            @PathVariable UUID userId)
    {
        log.info("Getting active drafts count for user: {}", userId);

        long count = draftService.countActiveDraftsByUser(userId);
        Map<String, Long> response = Map.of("activeCount", count);

        return ResponseEntity.ok(response);
    }

}
