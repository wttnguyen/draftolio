package org.willwin.draftolio.core.service.feign;

import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.willwin.draftolio.core.exception.ratelimit.RateLimitExceededException;
import org.willwin.draftolio.core.service.ratelimit.RateLimit;
import org.willwin.draftolio.core.service.ratelimit.parser.RateLimitParser;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeignResponseRateLimiterServiceTest
{

    @Mock
    private RateLimitParser<Map<String, Collection<String>>> rateLimitHeaderParser;

    @InjectMocks
    private FeignResponseRateLimiterService service;

    @Test
    @DisplayName("Should execute operation when rate limit is not exceeded")
    void shouldExecuteOperationWhenRateLimitNotExceeded()
    {
        // Arrange
        String key = "test-key";
        Response mockResponse = mock(Response.class);
        Map<String, Collection<String>> headers = new HashMap<>();

        when(mockResponse.headers()).thenReturn(headers);
        when(rateLimitHeaderParser.parse(headers)).thenReturn(Collections.emptyList());

        Supplier<Response> operation = () -> mockResponse;

        // Act
        Response result = service.executeWithRateLimit(key, operation);

        // Assert
        assertThat(result, is(sameInstance(mockResponse)));
        verify(rateLimitHeaderParser).parse(headers);
    }

    @Test
    @DisplayName("Should throw exception when rate limit is exceeded")
    void shouldThrowExceptionWhenRateLimitExceeded()
    {
        // Arrange
        String key = "test-key";

        // Exhaust the rate limit by calling multiple times
        for (int i = 0; i < 5; i++)
        {
            try
            {
                service.executeWithRateLimit(key, () -> mock(Response.class));
            }
            catch (RateLimitExceededException e)
            {
                // Expected after rate limit is exceeded
                break;
            }
        }

        Supplier<Response> operation = () -> mock(Response.class);

        // Act & Assert
        RateLimitExceededException exception = assertThrows(
                RateLimitExceededException.class,
                () -> service.executeWithRateLimit(key, operation)
        );

        assertThat(exception.getMessage(), containsString("Rate limit exceeded for key: " + key));
    }

    @Test
    @DisplayName("Should update bucket configuration based on response headers")
    void shouldUpdateBucketConfigurationBasedOnResponseHeaders()
    {
        // Arrange
        String key = "test-key";
        Response mockResponse = mock(Response.class);
        Map<String, Collection<String>> headers = new HashMap<>();

        List<RateLimit> rateLimits = List.of(RateLimit.builder()
                .tokensAvailable(100)
                .tokensConsumed(20)
                .refreshPeriod(Duration.ofMinutes(1))
                .build());

        when(mockResponse.headers()).thenReturn(headers);
        when(rateLimitHeaderParser.parse(headers)).thenReturn(rateLimits);

        Supplier<Response> operation = () -> mockResponse;

        // Act
        Response result = service.executeWithRateLimit(key, operation);

        // Make another call to verify the bucket was updated
        Response secondResult = service.executeWithRateLimit(key, operation);

        // Assert
        assertThat(result, is(sameInstance(mockResponse)));
        assertThat(secondResult, is(sameInstance(mockResponse)));
        verify(rateLimitHeaderParser, times(2)).parse(headers);
    }

    @Test
    @DisplayName("Should handle null parameters correctly")
    void shouldHandleNullParametersCorrectly()
    {
        // Arrange
        String key = "test-key";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> service.executeWithRateLimit(null, () -> mock(Response.class)));

        assertThrows(NullPointerException.class, () -> service.executeWithRateLimit(key, null));
    }

}
