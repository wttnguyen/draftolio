package org.willwin.draftolioai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Configuration properties for League of Legends assets management.
 */
@ConfigurationProperties(prefix = "lol.assets")
public record LeagueOfLegendsAssetsProperties(
    
    /**
     * URL to fetch the latest version information from Riot Games API.
     */
    String versionsUrl,
    
    /**
     * Base URL for downloading assets from Riot Games CDN.
     * The version will be substituted in the URL template.
     */
    String assetsBaseUrl,
    
    /**
     * Local directory path where assets will be cached.
     */
    String cacheDirectory,
    
    /**
     * Whether to enable automatic asset management on startup.
     */
    Boolean enabled,
    
    /**
     * Timeout for HTTP requests to Riot Games API.
     */
    Duration requestTimeout,
    
    /**
     * Timeout for asset download operations.
     */
    Duration downloadTimeout
) {
    
    /**
     * Default constructor with sensible defaults.
     */
    public LeagueOfLegendsAssetsProperties() {
        this(
            "https://ddragon.leagueoflegends.com/api/versions.json",
            "https://ddragon.leagueoflegends.com/cdn/dragontail-{version}.tgz",
            "assets/lol",
            true,
            Duration.ofSeconds(10),
            Duration.ofMinutes(5)
        );
    }
}
