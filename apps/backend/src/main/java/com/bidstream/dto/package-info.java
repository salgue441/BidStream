/**
 * Data Transfer Object (DTO) package for the BidStream platform.
 *
 * <p>
 * This package contains all Data Transfer Objects used for communication
 * between different layers of the application, particularly between the
 * presentation layer (controllers) and client applications. DTOs provide
 * a clean separation between internal domain models and external API contracts.
 * </p>
 *
 * <p>
 * <strong>Package Structure:</strong>
 * </p>
 * <ul>
 * <li>{@link com.bidstream.dto.request} - Request DTOs for incoming data</li>
 * <li>{@link com.bidstream.dto.response} - Response DTOs for outgoing data</li>
 * </ul>
 *
 * <p>
 * <strong>Design Principles:</strong>
 * </p>
 * <ul>
 * <li><strong>Immutability:</strong> DTOs should be immutable when
 * possible</li>
 * <li><strong>Validation:</strong> Input validation using Bean Validation
 * annotations</li>
 * <li><strong>Serialization:</strong> JSON-friendly with proper Jackson
 * annotations</li>
 * <li><strong>Documentation:</strong> Comprehensive JavaDoc for all public
 * APIs</li>
 * <li><strong>Versioning:</strong> Stable contracts that evolve gracefully</li>
 * </ul>
 *
 * <p>
 * <strong>Key Benefits:</strong>
 * </p>
 * <ul>
 * <li><strong>API Stability:</strong> Changes to internal models don't break
 * client contracts</li>
 * <li><strong>Security:</strong> Control exactly what data is exposed to
 * clients</li>
 * <li><strong>Performance:</strong> Optimize data transfer for specific use
 * cases</li>
 * <li><strong>Validation:</strong> Centralized input validation and error
 * handling</li>
 * <li><strong>Documentation:</strong> Self-documenting API contracts</li>
 * </ul>
 *
 * <p>
 * <strong>Usage Patterns:</strong>
 * </p>
 *
 * <pre>
 * {@code
 * // Request DTO for creating an auction
 * &#64;PostMapping("/auctions")
 * public ResponseEntity<AuctionResponse> createAuction(
 *     @Valid @RequestBody CreateAuctionRequest request) {
 *   // Process request...
 *   return ResponseEntity.ok(response);
 * }
 *
 * // Response DTO for auction details
 * AuctionResponse response = AuctionResponse.builder()
 *     .id(auction.getId())
 *     .title(auction.getTitle())
 *     .currentPrice(auction.getCurrentPrice())
 *     .build();
 * }
 * </pre>
 *
 * <p>
 * <strong>Validation Strategy:</strong>
 * </p>
 * <ul>
 * <li>Use Bean Validation annotations ({@code @NotNull}, {@code @Size},
 * {@code @Email})</li>
 * <li>Custom validation annotations for business rules</li>
 * <li>Consistent error messages across all DTOs</li>
 * <li>Validation groups for different scenarios</li>
 * </ul>
 *
 * <p>
 * <strong>JSON Serialization:</strong>
 * </p>
 * <ul>
 * <li>Jackson annotations for custom serialization</li>
 * <li>Date/time formatting with ISO-8601 standards</li>
 * <li>Null value handling policies</li>
 * <li>Property naming strategies (camelCase)</li>
 * </ul>
 *
 * <p>
 * <strong>Best Practices:</strong>
 * </p>
 * <ul>
 * <li>Keep DTOs simple and focused on data transfer</li>
 * <li>Avoid business logic in DTOs</li>
 * <li>Use meaningful, descriptive field names</li>
 * <li>Provide comprehensive validation</li>
 * <li>Document all fields with clear descriptions</li>
 * <li>Consider backwards compatibility when evolving DTOs</li>
 * </ul>
 *
 * <p>
 * <strong>Security Considerations:</strong>
 * </p>
 * <ul>
 * <li>Never expose sensitive internal data</li>
 * <li>Validate all input data thoroughly</li>
 * <li>Use appropriate data types to prevent injection</li>
 * <li>Consider rate limiting and size restrictions</li>
 * </ul>
 *
 * @author Carlos Salguero
 * @since 1.0.0
 * @version 1.0.0
 */

package com.bidstream.dto;
