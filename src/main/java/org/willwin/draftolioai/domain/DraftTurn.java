package org.willwin.draftolioai.domain;

import lombok.Getter;

/**
 * Enumeration representing which team's turn it is during the draft.
 */
@Getter
public enum DraftTurn
{
    /**
     * Blue side team's turn to pick or ban.
     */
    BLUE("Blue Side"),

    /**
     * Red side team's turn to pick or ban.
     */
    RED("Red Side");

    /**
     * -- GETTER --
     * Gets the display name of the team turn.
     */
    private final String displayName;

    DraftTurn(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * Gets the opposite team turn.
     */
    public DraftTurn getOpposite()
    {
        return this == BLUE ? RED : BLUE;
    }
}
