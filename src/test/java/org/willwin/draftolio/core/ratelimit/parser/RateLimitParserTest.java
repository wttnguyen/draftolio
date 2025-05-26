package org.willwin.draftolio.core.ratelimit.parser;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.willwin.draftolio.core.ratelimit.RateLimit;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RateLimitParserTest
{

    @Test
    @DisplayName("Should parse input with test implementation")
    void shouldParseInputWithTestImplementation()
    {
        // Arrange
        TestRateLimitParser parser = new TestRateLimitParser();
        String input = "test-input";

        // Act
        List<RateLimit> result = parser.parse(input);

        // Assert
        assertThat(result, hasSize(1));
        assertThat(result.get(0).getTokensAvailable(), is(100));
        assertThat(result.get(0).getTokensConsumed(), is(10));
        assertThat(result.get(0).getRefreshPeriod(), is(Duration.ofSeconds(60)));
    }

    @Test
    @DisplayName("Should throw NullPointerException when input is null")
    void shouldThrowNullPointerExceptionWhenInputIsNull()
    {
        // Arrange
        TestRateLimitParser parser = new TestRateLimitParser();

        // Act & Assert
        Executable executable = () -> parser.parse(null);
        assertThrows(NullPointerException.class, executable);
    }

    /**
     * Simple test implementation of RateLimitParser for testing the interface
     */
    private static class TestRateLimitParser implements RateLimitParser<String>
    {

        @Override
        public List<RateLimit> parse(
                @NonNull String input)
        {
            return List.of(RateLimit.builder()
                    .tokensAvailable(100)
                    .tokensConsumed(10)
                    .refreshPeriod(Duration.ofSeconds(60))
                    .build());
        }

    }

}
