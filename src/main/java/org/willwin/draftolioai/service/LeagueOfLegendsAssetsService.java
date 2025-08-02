package org.willwin.draftolioai.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.willwin.draftolioai.config.LeagueOfLegendsAssetsProperties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Service responsible for managing League of Legends assets.
 * This service automatically checks for the latest version on startup,
 * downloads and decompresses assets if needed, and manages local caching.
 */
@Service
@Slf4j
public class LeagueOfLegendsAssetsService {

    private final LeagueOfLegendsAssetsProperties properties;
    private final RestTemplate restTemplate;
    private final Path cacheDirectory;

    public LeagueOfLegendsAssetsService(LeagueOfLegendsAssetsProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
        this.cacheDirectory = Paths.get(properties.cacheDirectory());
        
        // Create cache directory if it doesn't exist
        try {
            Files.createDirectories(cacheDirectory);
        } catch (IOException e) {
            log.error("Failed to create cache directory: {}", cacheDirectory, e);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeAssets() {
        if (!properties.enabled()) {
            log.info("League of Legends assets management is disabled");
            return;
        }

        log.info("Initializing League of Legends assets management...");
        
        try {
            String latestVersion = getLatestVersion();
            if (latestVersion != null) {
                if (!isVersionCached(latestVersion)) {
                    log.info("New version {} detected, downloading assets...", latestVersion);
                    downloadAndExtractAssets(latestVersion);
                    log.info("Successfully downloaded and extracted assets for version {}", latestVersion);
                } else {
                    log.info("Version {} is already cached, skipping download", latestVersion);
                }
            } else {
                log.warn("Failed to retrieve latest version information");
            }
        } catch (Exception e) {
            log.error("Failed to initialize League of Legends assets", e);
        }
    }

    public String getLatestVersion() {
        try {
            log.debug("Fetching latest version from: {}", properties.versionsUrl());
            
            ResponseEntity<List<String>> response = restTemplate.exchange(
                properties.versionsUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
            );
            
            List<String> versions = response.getBody();
            if (versions != null && !versions.isEmpty()) {
                String latestVersion = versions.get(0); // First element is the latest version
                log.debug("Latest version: {}", latestVersion);
                return latestVersion;
            }
        } catch (Exception e) {
            log.error("Failed to fetch latest version", e);
        }
        
        return null;
    }

    public boolean isVersionCached(String version) {
        Path versionDirectory = cacheDirectory.resolve(version);
        boolean cached = Files.exists(versionDirectory) && Files.isDirectory(versionDirectory);
        log.debug("Version {} cached: {}", version, cached);
        return cached;
    }

    /**
     * Downloads and extracts assets for the specified version.
     *
     * @param version The version to download
     * @throws IOException if download or extraction fails
     */
    public void downloadAndExtractAssets(String version) throws IOException {
        String downloadUrl = properties.assetsBaseUrl().replace("{version}", version);
        log.info("Downloading assets from: {}", downloadUrl);
        
        // Create temporary file for download
        Path tempFile = Files.createTempFile("lol-assets-", ".tgz");

        try {
            // Download the file as byte array
            byte[] data = restTemplate.getForObject(downloadUrl, byte[].class);
            if (data == null || data.length == 0) {
                throw new IOException("Failed to download assets: empty response");
            }
            Files.write(tempFile, data);
            log.debug("Downloaded {} bytes to temporary file: {}", Files.size(tempFile), tempFile);

            // Extract the downloaded file
            extractTarGzFile(tempFile, version);

        } finally {
            // Clean up temporary file
            try {
                Files.deleteIfExists(tempFile);
            } catch (IOException e) {
                log.warn("Failed to delete temporary file: {}", tempFile, e);
            }
        }
    }

    private void extractTarGzFile(Path tarGzFile, String version) throws IOException {
        Path versionDirectory = cacheDirectory.resolve(version);
        Files.createDirectories(versionDirectory);
        
        log.info("Extracting assets to: {}", versionDirectory);
        
        try (FileInputStream fis = new FileInputStream(tarGzFile.toFile());
             GzipCompressorInputStream gzis = new GzipCompressorInputStream(fis);
             TarArchiveInputStream tais = new TarArchiveInputStream(gzis)) {
            
            TarArchiveEntry entry;
            int extractedFiles = 0;
            
            while ((entry = tais.getNextTarEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                
                // Create the file path relative to the version directory
                Path outputPath = versionDirectory.resolve(entry.getName());
                
                // Ensure parent directories exist
                Files.createDirectories(outputPath.getParent());
                
                // Extract the file
                try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = tais.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
                
                extractedFiles++;
                if (extractedFiles % 100 == 0) {
                    log.debug("Extracted {} files...", extractedFiles);
                }
            }
            
            log.info("Successfully extracted {} files for version {}", extractedFiles, version);
        }
    }

    public Path getCacheDirectory() {
        return cacheDirectory;
    }

    public Path getVersionDirectory(String version) {
        return cacheDirectory.resolve(version);
    }
}
