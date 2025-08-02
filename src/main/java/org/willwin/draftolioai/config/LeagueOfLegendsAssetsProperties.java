package org.willwin.draftolioai.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration properties for League of Legends assets management.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "lol.assets")
public class LeagueOfLegendsAssetsProperties
{

    /**
     * URL to fetch the latest version information from Riot Games API.
     */
    private String versionsUrl = "https://ddragon.leagueoflegends.com/api/versions.json";

    /**
     * Base URL for downloading assets from Riot Games CDN.
     * The version will be substituted in the URL template.
     */
    private String assetsBaseUrl = "https://ddragon.leagueoflegends.com/cdn/dragontail-{version}.tgz";

    /**
     * Local directory path where assets will be cached.
     */
    private String cacheDirectory = "assets/lol";

    /**
     * Whether to enable automatic asset management on startup.
     */
    private Boolean enabled = true;

    /**
     * Timeout for HTTP requests to Riot Games API.
     */
    private Duration requestTimeout = Duration.ofSeconds(10);

    /**
     * Timeout for asset download operations.
     */
    private Duration downloadTimeout = Duration.ofMinutes(5);

}
