package org.willwin.draftolio.core.feign;

import feign.Client;
import feign.MethodMetadata;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.willwin.draftolio.core.ratelimit.RateLimitExceededException;

import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateLimitedFeignClientTest
{

    @Mock
    private Client defaultFeignClient;

    @Mock
    private FeignResponseRateLimiterService rateLimiterService;

    @InjectMocks
    private RateLimitedFeignClient client;

    private Request mockRequest;

    private Request.Options mockOptions;

    @BeforeEach
    void setUp()
    {
        // Create mocks that will be reused across tests
        mockRequest = mockRequest("test-url");
        mockOptions = mock(Request.Options.class);
    }

    @Test
    @DisplayName("Should execute request with rate limiting")
    void shouldExecuteRequestWithRateLimiting() throws IOException
    {
        // Arrange
        Response expectedResponse = Response.builder()
                .request(mockRequest)
                .status(200)
                .headers(Collections.emptyMap())
                .build();

        when(defaultFeignClient.execute(mockRequest, mockOptions)).thenReturn(expectedResponse);
        when(rateLimiterService.executeWithRateLimit(eq("test-url"), any())).thenAnswer(
                invocation -> ((java.util.function.Supplier<Response>) invocation.getArgument(1)).get());

        // Act
        Response response = client.execute(mockRequest, mockOptions);

        // Assert
        assertThat(response, is(sameInstance(expectedResponse)));
        verify(rateLimiterService).executeWithRateLimit(eq("test-url"), any());
        verify(defaultFeignClient).execute(mockRequest, mockOptions);
    }

    @Test
    @DisplayName("Should propagate IOException from default client")
    void shouldPropagateIOExceptionFromDefaultClient() throws IOException
    {
        // Arrange
        IOException expectedException = new IOException("Test IO exception");

        when(defaultFeignClient.execute(mockRequest, mockOptions)).thenThrow(expectedException);
        when(rateLimiterService.executeWithRateLimit(eq("test-url"), any())).thenAnswer(invocation ->
        {
            try
            {
                return ((java.util.function.Supplier<Response>) invocation.getArgument(1)).get();
            }
            catch (RuntimeException e)
            {
                throw e;
            }
        });

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> client.execute(mockRequest, mockOptions));
        assertThat(exception.getMessage(), is(expectedException.getMessage()));
    }

    @Test
    @DisplayName("Should propagate RateLimitExceededException")
    void shouldPropagateRateLimitExceededException() throws IOException
    {
        // Arrange
        RateLimitExceededException expectedException = new RateLimitExceededException("Rate limit exceeded");

        when(rateLimiterService.executeWithRateLimit(eq("test-url"), any())).thenThrow(expectedException);

        // Act & Assert
        RateLimitExceededException exception = assertThrows(
                RateLimitExceededException.class,
                () -> client.execute(mockRequest, mockOptions)
        );
        assertThat(exception, is(sameInstance(expectedException)));
        verify(defaultFeignClient, never()).execute(any(), any());
    }

    @Test
    @DisplayName("Should handle null parameters correctly")
    void shouldHandleNullParametersCorrectly()
    {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> client.execute(null, mockOptions));
        assertThrows(NullPointerException.class, () -> client.execute(mockRequest, null));
    }

    private Request mockRequest(String url)
    {
        RequestTemplate requestTemplate = mock(RequestTemplate.class);
        MethodMetadata methodMetadata = mock(MethodMetadata.class);
        RequestTemplate template = mock(RequestTemplate.class);

        lenient().when(template.url()).thenReturn(url);
        lenient().when(methodMetadata.template()).thenReturn(template);
        lenient().when(requestTemplate.methodMetadata()).thenReturn(methodMetadata);

        Request request = mock(Request.class);
        lenient().when(request.requestTemplate()).thenReturn(requestTemplate);

        return request;
    }

}
