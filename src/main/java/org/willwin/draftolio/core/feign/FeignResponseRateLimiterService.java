package org.willwin.draftolio.core.feign;

import feign.Response;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConfigurationBuilder;
import io.github.bucket4j.TokensInheritanceStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.willwin.draftolio.core.ratelimit.RateLimit;
import org.willwin.draftolio.core.ratelimit.RateLimitExceededException;
import org.willwin.draftolio.core.ratelimit.RateLimiterService;
import org.willwin.draftolio.core.ratelimit.parser.RateLimitParser;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class FeignResponseRateLimiterService implements RateLimiterService<Response>
{

    @NonNull
    private final RateLimitParser<Map<String, Collection<String>>> rateLimitHeaderParser;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final Map<String, BucketConfiguration> bucketConfigurations = new ConcurrentHashMap<>();

    @Override
    public Response executeWithRateLimit(
            @NonNull String key,
            @NonNull Supplier<Response> operator)
    {
        Bucket bucket = buckets.computeIfAbsent(key, this::defaultBucket);
        if (bucket.tryConsume(1))
        {
            Response response = operator.get();
            updateBucket(key, rateLimitHeaderParser.parse(response.headers()));
            return response;
        }
        throw new RateLimitExceededException("Rate limit exceeded for key: " + key);
    }

    /**
     * Updates the bucket configuration based on rate limits from the response.
     *
     * @param key        the key identifying the bucket
     * @param rateLimits the list of rate limits from the response
     */
    private void updateBucket(
            @NonNull String key,
            @NonNull List<RateLimit> rateLimits)
    {
        if (rateLimits.isEmpty())
        {
            return;
        }

        ConfigurationBuilder builder = BucketConfiguration.builder();
        rateLimits.forEach(rateLimit -> addRateLimitToBucketConfiguration(builder, rateLimit));

        BucketConfiguration newConfiguration = builder.build();
        BucketConfiguration currentConfiguration = bucketConfigurations.get(key);

        if (!newConfiguration.equals(currentConfiguration))
        {
            Bucket bucket = buckets.computeIfAbsent(key, this::defaultBucket);
            bucket.replaceConfiguration(newConfiguration, TokensInheritanceStrategy.AS_IS);
            bucketConfigurations.put(key, newConfiguration);
        }
    }

    /**
     * Adds a rate limit to the bucket configuration builder.
     *
     * @param builder   the configuration builder
     * @param rateLimit the rate limit to add
     */
    private void addRateLimitToBucketConfiguration(
            @NonNull ConfigurationBuilder builder,
            @NonNull RateLimit rateLimit)
    {
        int safeLimit = calculateSafeLimit(rateLimit);
        builder.addLimit(Bandwidth.builder()
                .capacity(safeLimit)
                .refillIntervally(safeLimit, rateLimit.getRefreshPeriod())
                .build());
    }

    /**
     * Calculates a safe limit based on the rate limit and remaining tokens.
     *
     * @param rateLimit the rate limit
     * @return the safe limit
     */
    private int calculateSafeLimit(
            @NonNull RateLimit rateLimit)
    {
        int safeLimit = (int) Math.round(rateLimit.getTokensAvailable() * 0.8);
        int tokensRemaining = rateLimit.getTokensAvailable() - rateLimit.getTokensConsumed();
        double tokensRemainingPercent = (double) tokensRemaining / (double) rateLimit.getTokensAvailable();

        if (tokensRemainingPercent < 0.2)
        {
            safeLimit = (int) Math.round(safeLimit * 0.5);
        }

        return safeLimit;
    }

    /**
     * Creates a default bucket with conservative rate limits.
     *
     * @param key the key identifying the bucket
     * @return the default bucket
     */
    private Bucket defaultBucket(
            @NonNull String key)
    {
        return Bucket.builder()
                .addLimit(Bandwidth.builder().capacity(5).refillIntervally(5, Duration.ofMinutes(1)).build())
                .addLimit(Bandwidth.builder().capacity(5).refillIntervally(50, Duration.ofHours(1)).build())
                .build();
    }

}
