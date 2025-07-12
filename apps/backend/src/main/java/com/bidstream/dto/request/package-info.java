/**
 * Request Data Transfer Objects for the BidStream platform.
 *
 * <p>
 * This package contains all DTOs used for incoming requests from clients.
 * Request DTOs are responsible for capturing, validating, and structuring
 * input data from HTTP requests before processing by service layers.
 * </p>
 *
 * <p>
 * <strong>Request DTO Categories:</strong>
 * </p>
 * <ul>
 * <li><strong>Creation Requests:</strong> For creating new entities (e.g.,
 * {@code CreateAuctionRequest})</li>
 * <li><strong>Update Requests:</strong> For modifying existing entities (e.g.,
 * {@code UpdateAuctionRequest})</li>
 * <li><strong>Search Requests:</strong> For complex search and filtering
 * operations</li>
 * <li><strong>Authentication Requests:</strong> For login, registration, and
 * auth operations</li>
 * <li><strong>Action Requests:</strong> For specific business operations (e.g.,
 * {@code PlaceBidRequest})</li>
 * </ul>
 *
 * <p>
 * <strong>Validation Framework:</strong>
 * </p>
 * <ul>
 * <li><strong>Bean Validation:</strong> Standard JSR-303/JSR-380
 * annotations</li>
 * <li><strong>Custom Validators:</strong> Business-specific validation
 * rules</li>
 * <li><strong>Cross-field Validation:</strong> Validation across multiple
 * fields</li>
 * <li><strong>Conditional Validation:</strong> Rules that depend on other field
 * values</li>
 * </ul>
 *
 * <p>
 * <strong>Standard Validation Annotations:</strong>
 * </p>
 *
 * <pre>
 * {@code
 * &#64;NotNull(message = "Field is required")
 * &#64;NotBlank(message = "Field cannot be empty")
 * &#64;Size(min = 3, max = 50, message = "Field must be 3-50 characters")
 * &#64;Email(message = "Must be a valid email address")
 * &#64;DecimalMin(value = "0.01", message = "Value must be positive")
 * &#64;Future(message = "Date must be in the future")
 * &#64;Pattern(regexp = "^[A-Z0-9]+$",
 *  message = "Only uppercase letters and numbers")
 * }
 * </pre>
 *
 * <p>
 * <strong>Custom Validation Examples:</strong>
 * </p>
 *
 * <pre>{@code
 * // Custom validator for auction dates
 * &#64;ValidAuctionDates
 * public class CreateAuctionRequest {
 *   &#64;Future
 *   private LocalDateTime startTime;
 *
 *   &#64;Future
 *   private LocalDateTime endTime;
 * }
 *
 * // Custom validator for bid amounts
 * &#64;ValidBidAmount
 * public class PlaceBidRequest {
 *   @NotNull
 *   &#64;DecimalMin("0.01")
 *   private BigDecimal amount;
 * }
 * }
 * </pre>
 *
 * <p>
 * <strong>Error Handling Integration:</strong>
 * </p>
 * <ul>
 * <li>Validation errors are automatically caught by
 * {@code GlobalExceptionHandler}</li>
 * <li>Field-level error messages are returned in structured format</li>
 * <li>Consistent error codes and messages across all request DTOs</li>
 * <li>Localization support for error messages</li>
 * </ul>
 *
 * <p>
 * <strong>Security Features:</strong>
 * </p>
 * <ul>
 * <li><strong>Input Sanitization:</strong> Automatic trimming and cleaning of
 * string inputs</li>
 * <li><strong>Size Limits:</strong> Maximum lengths to prevent DoS attacks</li>
 * <li><strong>Type Safety:</strong> Strong typing prevents injection
 * attacks</li>
 * <li><strong>Validation Bypass Prevention:</strong> Cannot skip validation
 * checks</li>
 * </ul>
 *
 * <p>
 * <strong>Performance Considerations:</strong>
 * </p>
 * <ul>
 * <li>Validation caching for repeated patterns</li>
 * <li>Lazy validation where appropriate</li>
 * <li>Minimal object creation during validation</li>
 * <li>Efficient serialization/deserialization</li>
 * </ul>
 *
 * <p>
 * <strong>Testing Guidelines:</strong>
 * </p>
 * <ul>
 * <li>Test all validation scenarios (valid/invalid inputs)</li>
 * <li>Test boundary conditions (min/max values)</li>
 * <li>Test cross-field validation rules</li>
 * <li>Test serialization/deserialization</li>
 * </ul>
 *
 * <p>
 * <strong>Naming Conventions:</strong>
 * </p>
 * <ul>
 * <li><strong>Creation:</strong> {@code Create[Entity]Request}</li>
 * <li><strong>Updates:</strong> {@code Update[Entity]Request}</li>
 * <li><strong>Actions:</strong> {@code [Action][Entity]Request}</li>
 * <li><strong>Authentication:</strong> {@code [Auth Action]Request}</li>
 * </ul>
 *
 * <p>
 * <strong>Common Request DTOs:</strong>
 * </p>
 * <ul>
 * <li>{@link com.bidstream.dto.request.CreateAuctionRequest} - Create new
 * auction</li>
 * <li>{@link com.bidstream.dto.request.UpdateAuctionRequest} - Update existing
 * auction</li>
 * <li>{@link com.bidstream.dto.request.PlaceBidRequest} - Place bid on
 * auction</li>
 * <li>{@link com.bidstream.dto.request.LoginRequest} - User authentication</li>
 * <li>{@link com.bidstream.dto.request.RegisterRequest} - User
 * registration</li>
 * </ul>
 *
 * @author Carlos Salguero
 * @since 1.0.0
 * @version 1.0.0
 *
 * @see com.bidstream.dto.response
 * @see com.bidstream.exception.GlobalExceptionHandler
 */
package com.bidstream.dto.request;
