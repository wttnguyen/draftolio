package org.willwin.draftolio.core.ratelimit;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.willwin.draftolio.core.ratelimit.parser.RateLimitParser;
import org.willwin.draftolio.core.ratelimit.parser.impl.RateLimitHeaderParser;
import org.willwin.draftolio.core.ratelimit.parser.impl.RateLimitHeaderParser.RateLimitHeader;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
class RateLimitParserConfiguration
{

    // Pattern to match rate limit format: "number:number"
    private static final String RATE_LIMIT_PATTERN = "(\\d+):(\\d+)";

    // Header names
    private static final String APP_AVAILABLE_HEADER = "X-App-Rate-Limit";

    private static final String APP_CONSUMED_HEADER = "X-App-Rate-Limit-Count";

    private static final String METHOD_AVAILABLE_HEADER = "X-Method-Rate-Limit";

    private static final String METHOD_CONSUMED_HEADER = "X-Method-Rate-Limit-Count";

    // Time unit for refresh periods
    private static final TemporalUnit REFRESH_PERIOD_UNIT = ChronoUnit.SECONDS;

    @Bean
    public RateLimitParser<Map<String, Collection<String>>> rateLimitHeaderParser()
    {
        Pattern headerPattern = createHeaderPattern();

        List<RateLimitHeader> headers = List.of(
                createRateLimitHeader(APP_AVAILABLE_HEADER, APP_CONSUMED_HEADER, headerPattern),
                createRateLimitHeader(METHOD_AVAILABLE_HEADER, METHOD_CONSUMED_HEADER, headerPattern)
        );

        return new RateLimitHeaderParser(headers);
    }

    private Pattern createHeaderPattern()
    {
        return Pattern.compile(RATE_LIMIT_PATTERN);
    }

    private RateLimitHeader createRateLimitHeader(
            @NonNull String availableHeader,
            @NonNull String consumedHeader,
            @NonNull Pattern pattern)
    {
        return RateLimitHeader.builder()
                .tokensAvailableHeader(availableHeader)
                .tokensConsumedHeader(consumedHeader)
                .refreshPeriodUnit(REFRESH_PERIOD_UNIT)
                .headerPattern(pattern)
                .build();
    }

}
