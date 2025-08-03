import { DraftModeEnum } from './draft-mode.model';

/**
 * Interface representing a request to create a new draft.
 * Matches the backend CreateDraftRequest DTO for US-1 implementation.
 */
export interface CreateDraftRequest
{
	/**
	 * Name of the draft (optional).
	 */
	name?: string;

	/**
	 * Optional description of the draft.
	 */
	description?: string;

	/**
	 * Name of the blue team (required).
	 */
	blueTeamName: string;

	/**
	 * Name of the red team (required).
	 */
	redTeamName: string;

	/**
	 * Draft mode (required).
	 */
	mode: DraftModeEnum;

	/**
	 * ID of the blue side team (for team-based drafts).
	 */
	blueSideTeamId?: string;

	/**
	 * ID of the red side team (for team-based drafts).
	 */
	redSideTeamId?: string;

	/**
	 * Duration in seconds for ban phase timer.
	 * Default is 30 seconds if not specified.
	 */
	banPhaseTimerDuration?: number;

	/**
	 * Duration in seconds for pick phase timer.
	 * Default is 60 seconds if not specified.
	 */
	pickPhaseTimerDuration?: number;
}
