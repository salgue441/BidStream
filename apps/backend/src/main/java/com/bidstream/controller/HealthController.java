package com.bidstream.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health check controller to verify the application is running correctly.
 * Provides endpoints for health status and version information.
 */
@RestController
@RequestMapping("/health")
public final class HealthController {
  /**
   * The name of the application from configuration.
   */
  @Value("${spring.application.name}")
  private String applicationName;

  /**
   * Provides health check information for the application.
   *
   * @return ResponseEntity containing health status, application
   *         name, timestamp, and status message
   */
  @GetMapping
  public ResponseEntity<Map<String, Object>> health() {
    Map<String, Object> health = new HashMap<>();

    health.put("status", "UP");
    health.put("application", applicationName);
    health.put("timestamp", LocalDateTime.now());
    health.put("message", "BidStream backend is running successfully");

    return ResponseEntity.ok(health);
  }

  /**
   * Provides version information for the application.
   * 
   * @return ResponseEntity containing application name, version number, and
   *         environment
   */
  @GetMapping("/version")
  public ResponseEntity<Map<String, String>> version() {
    Map<String, String> version = new HashMap<>();

    version.put("application", applicationName);
    version.put("version", "1.0.0");
    version.put("environment", "development");

    return ResponseEntity.ok(version);
  }
}
