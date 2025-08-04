package org.willwin.draftolioai.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for Riot Sign-On (RSO) integration.
 * <p>
 * This class enables configuration properties and sets up the necessary
 * beans for RSO authentication.
 */
@Configuration
@EnableConfigurationProperties(RsoProperties.class)
public class RsoConfiguration
{

    /**
     * Provides a WebClient.Builder bean for RSO HTTP communication.
     *
     * @return Configured WebClient.Builder
     */
    @Bean
    WebClient.Builder webClientBuilder()
    {
        return WebClient.builder();
    }

}
