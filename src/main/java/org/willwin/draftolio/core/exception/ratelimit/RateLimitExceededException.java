package org.willwin.draftolio.core.exception.ratelimit;

/**
 * Exception thrown when a rate limit is exceeded.
 */
public class RateLimitExceededException extends RuntimeException
{

    /**
     * Constructs a new rate limit exceeded exception with the specified detail message.
     *
     * @param message the detail message
     */
    public RateLimitExceededException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new rate limit exceeded exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public RateLimitExceededException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
