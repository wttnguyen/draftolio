package org.willwin.draftolio.core.ratelimit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RateLimitTest
{

    @Test
    @DisplayName("Should create rate limit with valid values")
    void shouldCreateRateLimitWithValidValues()
    {
        // Arrange & Act
        RateLimit rateLimit = RateLimit.builder()
                .tokensAvailable(100)
                .tokensConsumed(10)
                .refreshPeriod(Duration.ofSeconds(60))
                .build();

        // Assert
        assertThat(rateLimit.getTokensAvailable(), is(100));
        assertThat(rateLimit.getTokensConsumed(), is(10));
        assertThat(rateLimit.getRefreshPeriod(), is(Duration.ofSeconds(60)));
    }

    @Test
    @DisplayName("Should throw exception when tokensAvailable is null")
    void shouldThrowExceptionWhenTokensAvailableIsNull()
    {
        // Arrange
        Executable executable = () -> RateLimit.builder()
                .tokensAvailable(null)
                .tokensConsumed(10)
                .refreshPeriod(Duration.ofSeconds(60))
                .build();

        // Act & Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw exception when tokensConsumed is null")
    void shouldThrowExceptionWhenTokensConsumedIsNull()
    {
        // Arrange
        Executable executable = () -> RateLimit.builder()
                .tokensAvailable(100)
                .tokensConsumed(null)
                .refreshPeriod(Duration.ofSeconds(60))
                .build();

        // Act & Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw exception when refreshPeriod is null")
    void shouldThrowExceptionWhenRefreshPeriodIsNull()
    {
        // Arrange
        Executable executable = () -> RateLimit.builder()
                .tokensAvailable(100)
                .tokensConsumed(10)
                .refreshPeriod(null)
                .build();

        // Act & Assert
        assertThrows(NullPointerException.class, executable);
    }

}
