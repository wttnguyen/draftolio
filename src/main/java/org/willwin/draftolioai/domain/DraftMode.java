package org.willwin.draftolioai.domain;

import lombok.Getter;

/**
 * Enumeration representing the different draft modes available.
 */
@Getter
public enum DraftMode
{
    /**
     * Standard tournament draft mode used in competitive League of Legends.
     * Each team bans and picks champions following the standard format.
     */
    TOURNAMENT("Tournament Draft", "Standard competitive draft format with alternating bans and picks."),

    /**
     * Fearless draft mode where champions picked in previous games are banned for subsequent games.
     * Only applies to picks, not bans.
     */
    FEARLESS("Fearless Draft", "Champions picked in previous games are automatically banned for subsequent games."),

    /**
     * Full Fearless draft mode where both champions picked AND banned in previous games
     * are banned for subsequent games.
     */
    FULL_FEARLESS(
            "Full Fearless Draft",
            "Both champions picked and banned in previous games are automatically banned for subsequent games."
    );

    /**
     * -- GETTER --
     * Gets the display name of the draft mode.
     */
    private final String displayName;

    /**
     * -- GETTER --
     * Gets the description of the draft mode.
     */
    private final String description;

    DraftMode(String displayName, String description)
    {
        this.displayName = displayName;
        this.description = description;
    }

}
