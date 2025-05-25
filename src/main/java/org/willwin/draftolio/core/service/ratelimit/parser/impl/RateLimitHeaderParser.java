package org.willwin.draftolio.core.service.ratelimit.parser.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.willwin.draftolio.core.service.ratelimit.RateLimit;
import org.willwin.draftolio.core.service.ratelimit.parser.RateLimitParser;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class RateLimitHeaderParser implements RateLimitParser<Map<String, Collection<String>>>
{

    @NonNull
    private final List<RateLimitHeader> rateLimitHeaders;

    @Override
    public List<RateLimit> parse(
            @NonNull Map<String, Collection<String>> input)
    {
        return rateLimitHeaders.stream()
                .filter(header -> containsHeader(input, header))
                .flatMap(header -> parseHeaderPair(input, header))
                .toList();
    }

    private Stream<RateLimit> parseHeaderPair(
            @NonNull Map<String, Collection<String>> input,
            @NonNull RateLimitHeader header)
    {
        Collection<String> availableHeaders = input.get(header.getTokensAvailableHeader());
        Collection<String> consumedHeaders = input.get(header.getTokensConsumedHeader());

        if (availableHeaders.isEmpty() || consumedHeaders.isEmpty())
        {
            return Stream.empty();
        }

        List<String> availableValues = parseHeaderValues(availableHeaders.iterator().next());
        List<String> consumedValues = parseHeaderValues(consumedHeaders.iterator().next());

        List<RateLimit> rateLimits = new ArrayList<>();
        int minLength = Math.min(availableValues.size(), consumedValues.size());

        for (int i = 0; i < minLength; i++)
        {
            createRateLimit(availableValues.get(i), consumedValues.get(i), header).ifPresent(rateLimits::add);
        }

        return rateLimits.stream();
    }

    private List<String> parseHeaderValues(
            @NonNull String headerValue)
    {
        if (headerValue.isEmpty())
        {
            return Collections.emptyList();
        }
        String[] values = headerValue.split(",");
        return List.of(values);
    }

    private Optional<RateLimit> createRateLimit(
            @NonNull String availableValue,
            @NonNull String consumedValue,
            @NonNull RateLimitHeader header)
    {
        Matcher availableMatcher = header.getHeaderPattern().matcher(availableValue.trim());
        Matcher consumedMatcher = header.getHeaderPattern().matcher(consumedValue.trim());

        if (!availableMatcher.matches() || !consumedMatcher.matches())
        {
            return Optional.empty();
        }

        try
        {
            int tokensAvailable = Integer.parseInt(availableMatcher.group(1));
            int tokensConsumed = Integer.parseInt(consumedMatcher.group(1));
            int refreshPeriodValue = Integer.parseInt(availableMatcher.group(2));

            return Optional.of(RateLimit.builder()
                    .tokensAvailable(tokensAvailable)
                    .tokensConsumed(tokensConsumed)
                    .refreshPeriod(Duration.of(refreshPeriodValue, header.getRefreshPeriodUnit()))
                    .build());
        }
        catch (NumberFormatException | IndexOutOfBoundsException e)
        {
            return Optional.empty();
        }
    }

    private boolean containsHeader(
            @NonNull Map<String, Collection<String>> input,
            @NonNull RateLimitHeader header)
    {
        return input.containsKey(header.getTokensAvailableHeader()) && input.containsKey(
                header.getTokensConsumedHeader());
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RateLimitHeader
    {

        @NonNull
        private final String tokensAvailableHeader;

        @NonNull
        private final String tokensConsumedHeader;

        @NonNull
        private final TemporalUnit refreshPeriodUnit;

        @NonNull
        private final Pattern headerPattern;

    }

}
