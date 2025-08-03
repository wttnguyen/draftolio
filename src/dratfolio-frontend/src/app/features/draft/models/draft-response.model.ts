import { DraftModeEnum } from './draft-mode.model';

/**
 * Interface representing a draft response from the backend.
 * Matches the backend DraftResponse DTO for US-1 implementation.
 */
export interface DraftResponse
{
	/**
	 * Unique identifier of the draft.
	 */
	id: string;

	/**
	 * Name of the draft (optional).
	 */
	name?: string;

	/**
	 * Optional description of the draft.
	 */
	description?: string;

	/**
	 * Name of the blue team.
	 */
	blueTeamName: string;

	/**
	 * Name of the red team.
	 */
	redTeamName: string;

	/**
	 * Current status of the draft.
	 */
	status: DraftStatus;

	/**
	 * Draft mode (Tournament, Fearless, Full Fearless).
	 */
	mode: DraftModeEnum;

	/**
	 * ID of the blue side captain.
	 * US-1 AC5: Creator is assigned as Blue Side captain by default.
	 */
	blueSideCaptainId: string;

	/**
	 * ID of the red side captain.
	 * US-1 AC4: User can invite another user to be the opposing captain.
	 */
	redSideCaptainId?: string;

	/**
	 * ID of the blue side team (for team-based drafts).
	 */
	blueSideTeamId?: string;

	/**
	 * ID of the red side team (for team-based drafts).
	 */
	redSideTeamId?: string;

	/**
	 * Current phase of the draft.
	 */
	currentPhase?: DraftPhase;

	/**
	 * Current turn (which team should act).
	 */
	currentTurn?: DraftTurn;

	/**
	 * Current game number (for series like Fearless drafts).
	 */
	gameNumber: number;

	/**
	 * Duration in seconds for ban phase timer.
	 */
	banPhaseTimerDuration: number;

	/**
	 * Duration in seconds for pick phase timer.
	 */
	pickPhaseTimerDuration: number;

	/**
	 * End time of the current timer.
	 */
	timerEndTime?: Date;

	/**
	 * Spectator URL for viewing the draft.
	 */
	spectateUrl?: string;

	/**
	 * Blue captain URL for controlling the blue side of the draft.
	 */
	blueCaptainUrl?: string;

	/**
	 * Red captain URL for controlling the red side of the draft.
	 */
	redCaptainUrl?: string;

	/**
	 * Timestamp when the draft was created.
	 */
	createdAt: Date;

	/**
	 * Timestamp when the draft was last updated.
	 */
	updatedAt: Date;

	/**
	 * Timestamp when the draft was completed.
	 */
	completedAt?: Date;
}

/**
 * Enum representing the status of a draft.
 */
export enum DraftStatus
{
	CREATED = 'CREATED',
	IN_PROGRESS = 'IN_PROGRESS',
	COMPLETED = 'COMPLETED',
	CANCELLED = 'CANCELLED'
}

/**
 * Enum representing the different phases of a draft.
 */
export enum DraftPhase
{
	BAN_PHASE_1 = 'BAN_PHASE_1',
	PICK_PHASE_1 = 'PICK_PHASE_1',
	BAN_PHASE_2 = 'BAN_PHASE_2',
	PICK_PHASE_2 = 'PICK_PHASE_2'
}

/**
 * Enum representing which team's turn it is during the draft.
 */
export enum DraftTurn
{
	BLUE = 'BLUE',
	RED = 'RED'
}
