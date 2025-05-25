package org.willwin.draftolio.core.service.ratelimit.parser;

import lombok.NonNull;
import org.willwin.draftolio.core.service.ratelimit.RateLimit;

import java.util.List;

@FunctionalInterface
public interface RateLimitParser<T>
{

    List<RateLimit> parse(
            @NonNull T input);

}
