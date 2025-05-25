package org.willwin.draftolio.core.configuration.ratelimit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.willwin.draftolio.core.service.ratelimit.RateLimit;
import org.willwin.draftolio.core.service.ratelimit.parser.RateLimitParser;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
class RateLimitParserConfigurationTest
{

    @Autowired
    private RateLimitParser<Map<String, Collection<String>>> rateLimitParser;

    @Test
    @DisplayName("Should create and configure RateLimitHeaderParser bean")
    void shouldCreateAndConfigureRateLimitHeaderParserBean()
    {
        // Verify the bean was created
        assertThat(rateLimitParser, is(notNullValue()));
    }

    @Test
    @DisplayName("Should parse app rate limit headers")
    void shouldParseAppRateLimitHeaders()
    {
        // Arrange
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("X-App-Rate-Limit", List.of("100:60,200:120"));
        headers.put("X-App-Rate-Limit-Count", List.of("10:60,20:120"));

        // Act
        List<RateLimit> rateLimits = rateLimitParser.parse(headers);

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
    @DisplayName("Should parse method rate limit headers")
    void shouldParseMethodRateLimitHeaders()
    {
        // Arrange
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("X-Method-Rate-Limit", List.of("50:30"));
        headers.put("X-Method-Rate-Limit-Count", List.of("5:30"));

        // Act
        List<RateLimit> rateLimits = rateLimitParser.parse(headers);

        // Assert
        assertThat(rateLimits, hasSize(1));

        // Rate limit
        assertThat(rateLimits.get(0).getTokensAvailable(), is(50));
        assertThat(rateLimits.get(0).getTokensConsumed(), is(5));
        assertThat(rateLimits.get(0).getRefreshPeriod(), is(Duration.ofSeconds(30)));
    }

    @Test
    @DisplayName("Should parse both app and method rate limit headers")
    void shouldParseBothAppAndMethodRateLimitHeaders()
    {
        // Arrange
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("X-App-Rate-Limit", List.of("100:60"));
        headers.put("X-App-Rate-Limit-Count", List.of("10:60"));
        headers.put("X-Method-Rate-Limit", List.of("50:30"));
        headers.put("X-Method-Rate-Limit-Count", List.of("5:30"));

        // Act
        List<RateLimit> rateLimits = rateLimitParser.parse(headers);

        // Assert
        assertThat(rateLimits, hasSize(2));

        // App rate limit
        boolean hasAppRateLimit = rateLimits.stream()
                .anyMatch(
                        limit -> limit.getTokensAvailable() == 100 && limit.getTokensConsumed() == 10 && limit.getRefreshPeriod()
                                .equals(Duration.ofSeconds(60)));

        // Method rate limit
        boolean hasMethodRateLimit = rateLimits.stream()
                .anyMatch(
                        limit -> limit.getTokensAvailable() == 50 && limit.getTokensConsumed() == 5 && limit.getRefreshPeriod()
                                .equals(Duration.ofSeconds(30)));

        assertThat("Should contain app rate limit", hasAppRateLimit, is(true));
        assertThat("Should contain method rate limit", hasMethodRateLimit, is(true));
    }

}
