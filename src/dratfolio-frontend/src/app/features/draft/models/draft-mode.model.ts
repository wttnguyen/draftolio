/**
 * Interface representing a draft mode option.
 * Used for US-1 AC3: User sees explanation of mode rules when selecting a draft mode.
 */
export interface DraftMode
{
	/**
	 * The value to be sent to the backend API.
	 */
	value: 'TOURNAMENT' | 'FEARLESS' | 'FULL_FEARLESS';

	/**
	 * The display label for the dropdown.
	 */
	label: string;

	/**
	 * The description explaining the mode rules.
	 */
	description: string;
}

/**
 * Enum representing the draft mode values that match the backend.
 */
export enum DraftModeEnum
{
	TOURNAMENT = 'TOURNAMENT',
	FEARLESS = 'FEARLESS',
	FULL_FEARLESS = 'FULL_FEARLESS'
}
