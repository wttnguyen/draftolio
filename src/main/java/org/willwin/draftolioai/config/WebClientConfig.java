package org.willwin.draftolioai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration for WebClient used in RSO integration.
 * <p>
 * This configuration provides WebClient beans for making HTTP requests
 * to external services like the RSO UserInfo endpoint.
 */
@Configuration
public class WebClientConfig
{

    /**
     * Provide a WebClient.Builder bean for dependency injection.
     *
     * @return configured WebClient.Builder
     */
    @Bean
    public WebClient.Builder webClientBuilder()
    {
        return WebClient
                .builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024) // 1MB buffer
                );
    }

    /**
     * Provide a default WebClient bean.
     *
     * @param webClientBuilder the WebClient.Builder
     * @return configured WebClient
     */
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder.build();
    }

}
