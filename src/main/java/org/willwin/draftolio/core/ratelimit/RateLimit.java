package org.willwin.draftolio.core.ratelimit;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Builder
@Getter
@RequiredArgsConstructor
public class RateLimit
{

    @NonNull
    private final Integer tokensAvailable;

    @NonNull
    private final Integer tokensConsumed;

    @NonNull
    private final Duration refreshPeriod;

}
