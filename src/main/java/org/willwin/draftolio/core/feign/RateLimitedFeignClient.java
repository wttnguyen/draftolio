package org.willwin.draftolio.core.feign;

import feign.Client;
import feign.Request;
import feign.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A Feign Client implementation that adds rate limiting functionality.
 * This client delegates actual HTTP requests to another Feign Client while
 * enforcing rate limits based on the request URL.
 */
@Component
@RequiredArgsConstructor
public class RateLimitedFeignClient implements Client
{

    @NonNull
    private final Client defaultFeignClient;

    @NonNull
    private final FeignResponseRateLimiterService rateLimiterService;

    /**
     * Executes a request with rate limiting applied.
     *
     * @param request the request to execute
     * @param options the options for the request
     * @return the response from the request
     * @throws IOException if an I/O error occurs during the request
     */
    @Override
    public Response execute(
            @NonNull Request request,
            @NonNull Request.Options options) throws IOException
    {
        String key = extractKeyFromRequest(request);

        try
        {
            return rateLimiterService.executeWithRateLimit(key, () -> executeRequest(request, options));
        }
        catch (RuntimeException e)
        {
            if (e.getCause() instanceof IOException)
            {
                throw (IOException) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Extracts a key from the request for rate limiting purposes.
     *
     * @param request the request to extract the key from
     * @return the key for rate limiting
     */
    private String extractKeyFromRequest(
            @NonNull Request request)
    {
        return request.requestTemplate().methodMetadata().template().url();
    }

    /**
     * Executes the request using the default Feign client.
     *
     * @param request the request to execute
     * @param options the options for the request
     * @return the response from the request
     */
    private Response executeRequest(
            @NonNull Request request,
            @NonNull Request.Options options)
    {
        try
        {
            return defaultFeignClient.execute(request, options);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error executing request", e);
        }
    }

}
