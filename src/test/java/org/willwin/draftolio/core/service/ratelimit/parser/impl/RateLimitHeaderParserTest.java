package org.willwin.draftolio.core.service.ratelimit.parser.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.willwin.draftolio.core.service.ratelimit.RateLimit;
import org.willwin.draftolio.core.service.ratelimit.parser.impl.RateLimitHeaderParser.RateLimitHeader;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class RateLimitHeaderParserTest
{

    private static final String AVAILABLE_HEADER = "X-Test-Rate-Limit";

    private static final String CONSUMED_HEADER = "X-Test-Rate-Limit-Count";

    private static final Pattern HEADER_PATTERN = Pattern.compile("(\\d+):(\\d+)");

    private RateLimitHeaderParser parser;

    private RateLimitHeader header;

    @BeforeEach
    void setUp()
    {
        header = RateLimitHeader.builder()
                .tokensAvailableHeader(AVAILABLE_HEADER)
                .tokensConsumedHeader(CONSUMED_HEADER)
                .refreshPeriodUnit(ChronoUnit.SECONDS)
                .headerPattern(HEADER_PATTERN)
                .build();

        parser = new RateLimitHeaderParser(List.of(header));
    }

    @Test
    @DisplayName("Should parse valid rate limit headers")
    void shouldParseValidRateLimitHeaders()
    {
        // Arrange
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put(AVAILABLE_HEADER, List.of("100:60,200:120"));
        headers.put(CONSUMED_HEADER, List.of("10:60,20:120"));

        // Act
        List<RateLimit> rateLimits = parser.parse(headers);

        // Assert
        assertThat(rateLimits, hasSize(2));

        // First rate limit
        assertThat(rateLimits.get(0).getTokensAvailable(), is(100));
        assertThat(rateLimits.get(0).getTokensConsumed(), is(10));
        assertThat(rateLimits.get(0).getRefreshPeriod(), is(Duration.ofSeconds(60)));

        // Second rate limit
        assertThat(rateLimits.get(1).getTokensAvailable(), is(200));
        assertThat(rateLimits.get(1).getTokensConsumed(), is(20));
        assertThat(rateLimits.get(1).getRefreshPeriod(), is(Duration.ofSeconds(120)));
    }

    @Test
    @DisplayName("Should return empty list when headers are missing")
    void shouldReturnEmptyListWhenHeadersAreMissing()
    {
        // Arrange
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put(AVAILABLE_HEADER, List.of("100:60"));
        // Consumed header is missing

        // Act
        List<RateLimit> rateLimits = parser.parse(headers);

        // Assert
        assertThat(rateLimits, is(empty()));
    }

    @Test
    @DisplayName("Should return empty list when header values are empty")
    void shouldReturnEmptyListWhenHeaderValuesAreEmpty()
    {
        // Arrange
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put(AVAILABLE_HEADER, Collections.emptyList());
        headers.put(CONSUMED_HEADER, List.of("10:60"));

        // Act
        List<RateLimit> rateLimits = parser.parse(headers);

        // Assert
        assertThat(rateLimits, is(empty()));
    }

    @Test
    @DisplayName("Should skip invalid header values")
    void shouldSkipInvalidHeaderValues()
    {
        // Arrange
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put(AVAILABLE_HEADER, List.of("100:60,invalid,300:180"));
        headers.put(CONSUMED_HEADER, List.of("10:60,20:120,30:180"));

        // Act
        List<RateLimit> rateLimits = parser.parse(headers);

        // Assert
        assertThat(rateLimits, hasSize(2));

        // First rate limit
        assertThat(rateLimits.get(0).getTokensAvailable(), is(100));
        assertThat(rateLimits.get(0).getTokensConsumed(), is(10));

        // Third rate limit (second was invalid and skipped)
        assertThat(rateLimits.get(1).getTokensAvailable(), is(300));
        assertThat(rateLimits.get(1).getTokensConsumed(), is(30));
    }

    @Test
    @DisplayName("Should handle multiple headers")
    void shouldHandleMultipleHeaders()
    {
        // Arrange
        String secondAvailableHeader = "X-Second-Rate-Limit";
        String secondConsumedHeader = "X-Second-Rate-Limit-Count";

        RateLimitHeader secondHeader = RateLimitHeader.builder()
                .tokensAvailableHeader(secondAvailableHeader)
                .tokensConsumedHeader(secondConsumedHeader)
                .refreshPeriodUnit(ChronoUnit.MINUTES)
                .headerPattern(HEADER_PATTERN)
                .build();

        parser = new RateLimitHeaderParser(List.of(header, secondHeader));

        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put(AVAILABLE_HEADER, List.of("100:60"));
        headers.put(CONSUMED_HEADER, List.of("10:60"));
        headers.put(secondAvailableHeader, List.of("500:5"));
        headers.put(secondConsumedHeader, List.of("50:5"));

        // Act
        List<RateLimit> rateLimits = parser.parse(headers);

        // Assert
        assertThat(rateLimits, hasSize(2));

        // First header's rate limit
        assertThat(rateLimits.get(0).getTokensAvailable(), is(100));
        assertThat(rateLimits.get(0).getRefreshPeriod(), is(Duration.ofSeconds(60)));

        // Second header's rate limit
        assertThat(rateLimits.get(1).getTokensAvailable(), is(500));
        assertThat(rateLimits.get(1).getRefreshPeriod(), is(Duration.ofMinutes(5)));
    }

}
