package org.willwin.draftolioai.domain;

import lombok.Getter;

/**
 * Enumeration representing the different phases of a draft.
 * Based on the standard League of Legends tournament draft format.
 */
@Getter
public enum DraftPhase
{
    /**
     * First ban phase - each team bans 3 champions alternately.
     */
    BAN_PHASE_1("Ban Phase 1", 1),

    /**
     * First pick phase - each team picks 3 champions alternately.
     */
    PICK_PHASE_1("Pick Phase 1", 2),

    /**
     * Second ban phase - each team bans 2 more champions alternately.
     */
    BAN_PHASE_2("Ban Phase 2", 3),

    /**
     * Second pick phase - each team picks the remaining 2 champions alternately.
     */
    PICK_PHASE_2("Pick Phase 2", 4);

    /**
     * -- GETTER --
     * Gets the display name of the draft phase.
     */
    private final String displayName;

    /**
     * -- GETTER --
     * Gets the order of the draft phase.
     */
    private final int order;

    DraftPhase(String displayName, int order)
    {
        this.displayName = displayName;
        this.order = order;
    }

    /**
     * Checks if this is a ban phase.
     */
    public boolean isBanPhase()
    {
        return this == BAN_PHASE_1 || this == BAN_PHASE_2;
    }

    /**
     * Checks if this is a pick phase.
     */
    public boolean isPickPhase()
    {
        return this == PICK_PHASE_1 || this == PICK_PHASE_2;
    }

    /**
     * Gets the next phase in the draft sequence.
     */
    public DraftPhase getNextPhase()
    {
        return switch (this)
        {
            case BAN_PHASE_1 -> PICK_PHASE_1;
            case PICK_PHASE_1 -> BAN_PHASE_2;
            case BAN_PHASE_2 -> PICK_PHASE_2;
            case PICK_PHASE_2 -> null; // Draft is complete
        };
    }
}
