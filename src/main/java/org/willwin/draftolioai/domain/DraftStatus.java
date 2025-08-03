package org.willwin.draftolioai.domain;

/**
 * Enumeration representing the status of a draft.
 */
public enum DraftStatus
{
    /**
     * Draft has been created but not yet started.
     */
    CREATED,

    /**
     * Draft is currently in progress.
     */
    IN_PROGRESS,

    /**
     * Draft has been completed successfully.
     */
    COMPLETED,

    /**
     * Draft has been cancelled or abandoned.
     */
    CANCELLED
}
