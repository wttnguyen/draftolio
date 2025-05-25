package org.willwin.draftolio.core.service.ratelimit;

import lombok.NonNull;

import java.util.function.Supplier;

/**
 * Service for executing operations with rate limiting.
 *
 * @param <T> the type of result returned by the rate-limited operation
 */
public interface RateLimiterService<T>
{

    /**
     * Executes an operation with rate limiting based on the provided key.
     *
     * @param key      the key to identify the rate limit bucket
     * @param operator the operation to execute
     * @return the result of the operation
     * @throws org.willwin.draftolio.core.exception.ratelimit.RateLimitExceededException if the rate limit is exceeded
     */
    T executeWithRateLimit(
            @NonNull String key,
            @NonNull Supplier<T> operator);

}
