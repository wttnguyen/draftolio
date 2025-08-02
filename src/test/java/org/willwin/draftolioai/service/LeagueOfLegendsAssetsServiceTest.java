package org.willwin.draftolioai.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.willwin.draftolioai.config.LeagueOfLegendsAssetsProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LeagueOfLegendsAssetsService.
 */
class LeagueOfLegendsAssetsServiceTest {

    @TempDir
    Path tempDir;

    private LeagueOfLegendsAssetsService service;
    private LeagueOfLegendsAssetsProperties properties;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        properties = new LeagueOfLegendsAssetsProperties(
            "https://ddragon.leagueoflegends.com/api/versions.json",
            "https://ddragon.leagueoflegends.com/cdn/dragontail-{version}.tgz",
            tempDir.toString(),
            true,
            Duration.ofSeconds(10),
            Duration.ofMinutes(5)
        );

        // Create service with mocked RestTemplate
        service = new LeagueOfLegendsAssetsService(properties);
        
        // Replace the RestTemplate with a mock
        restTemplate = mock(RestTemplate.class);
        try {
            var restTemplateField = LeagueOfLegendsAssetsService.class.getDeclaredField("restTemplate");
            restTemplateField.setAccessible(true);
            restTemplateField.set(service, restTemplate);
        } catch (Exception e) {
            fail("Failed to inject mock RestTemplate: " + e.getMessage());
        }
    }

    @Test
    void testGetLatestVersion_Success() {
        // Arrange
        List<String> versions = Arrays.asList("14.15.1", "14.14.1", "14.13.1");
        ResponseEntity<List<String>> response = new ResponseEntity<>(versions, HttpStatus.OK);
        
        when(restTemplate.exchange(
            eq(properties.versionsUrl()),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenReturn(response);

        // Act
        String latestVersion = service.getLatestVersion();

        // Assert
        assertEquals("14.15.1", latestVersion);
        verify(restTemplate).exchange(
            eq(properties.versionsUrl()),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        );
    }

    @Test
    void testGetLatestVersion_EmptyResponse() {
        // Arrange
        ResponseEntity<List<String>> response = new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        
        when(restTemplate.exchange(
            eq(properties.versionsUrl()),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenReturn(response);

        // Act
        String latestVersion = service.getLatestVersion();

        // Assert
        assertNull(latestVersion);
    }

    @Test
    void testGetLatestVersion_Exception() {
        // Arrange
        when(restTemplate.exchange(
            eq(properties.versionsUrl()),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenThrow(new RuntimeException("Network error"));

        // Act
        String latestVersion = service.getLatestVersion();

        // Assert
        assertNull(latestVersion);
    }

    @Test
    void testIsVersionCached_NotCached() {
        // Act
        boolean cached = service.isVersionCached("14.15.1");

        // Assert
        assertFalse(cached);
    }

    @Test
    void testIsVersionCached_Cached() throws IOException {
        // Arrange
        String version = "14.15.1";
        Path versionDir = tempDir.resolve(version);
        Files.createDirectories(versionDir);

        // Act
        boolean cached = service.isVersionCached(version);

        // Assert
        assertTrue(cached);
    }

    @Test
    void testIsVersionCached_FileInsteadOfDirectory() throws IOException {
        // Arrange
        String version = "14.15.1";
        Path versionFile = tempDir.resolve(version);
        Files.createFile(versionFile);

        // Act
        boolean cached = service.isVersionCached(version);

        // Assert
        assertFalse(cached);
    }

    @Test
    void testGetCacheDirectory() {
        // Act
        Path cacheDir = service.getCacheDirectory();

        // Assert
        assertEquals(tempDir, cacheDir);
    }

    @Test
    void testGetVersionDirectory() {
        // Arrange
        String version = "14.15.1";

        // Act
        Path versionDir = service.getVersionDirectory(version);

        // Assert
        assertEquals(tempDir.resolve(version), versionDir);
    }

    @Test
    void testInitializeAssets_Disabled() {
        // Arrange
        LeagueOfLegendsAssetsProperties disabledProperties = new LeagueOfLegendsAssetsProperties(
            properties.versionsUrl(),
            properties.assetsBaseUrl(),
            properties.cacheDirectory(),
            false, // disabled
            properties.requestTimeout(),
            properties.downloadTimeout()
        );
        
        LeagueOfLegendsAssetsService disabledService = new LeagueOfLegendsAssetsService(disabledProperties);

        // Act & Assert - should not throw any exceptions
        assertDoesNotThrow(() -> disabledService.initializeAssets());
    }

    @Test
    void testInitializeAssets_VersionAlreadyCached() throws IOException {
        // Arrange
        String version = "14.15.1";
        List<String> versions = Arrays.asList(version, "14.14.1");
        ResponseEntity<List<String>> response = new ResponseEntity<>(versions, HttpStatus.OK);
        
        when(restTemplate.exchange(
            eq(properties.versionsUrl()),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenReturn(response);

        // Create cached version directory
        Files.createDirectories(tempDir.resolve(version));

        // Act
        service.initializeAssets();

        // Assert
        verify(restTemplate).exchange(
            eq(properties.versionsUrl()),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        );
        
        // Should not attempt to download since version is cached
        verify(restTemplate, never()).getForObject(anyString(), eq(byte[].class));
    }

    @Test
    void testInitializeAssets_FailedToGetVersion() {
        // Arrange
        when(restTemplate.exchange(
            eq(properties.versionsUrl()),
            eq(HttpMethod.GET),
            isNull(),
            any(ParameterizedTypeReference.class)
        )).thenThrow(new RuntimeException("Network error"));

        // Act & Assert - should not throw exceptions, just log the error
        assertDoesNotThrow(() -> service.initializeAssets());
    }

    @Test
    void testConstructor_CreatesDirectories() {
        // Arrange
        Path newTempDir = tempDir.resolve("new-cache");
        LeagueOfLegendsAssetsProperties newProperties = new LeagueOfLegendsAssetsProperties(
            properties.versionsUrl(),
            properties.assetsBaseUrl(),
            newTempDir.toString(),
            properties.enabled(),
            properties.requestTimeout(),
            properties.downloadTimeout()
        );

        // Act
        new LeagueOfLegendsAssetsService(newProperties);

        // Assert
        assertTrue(Files.exists(newTempDir));
        assertTrue(Files.isDirectory(newTempDir));
    }
}
