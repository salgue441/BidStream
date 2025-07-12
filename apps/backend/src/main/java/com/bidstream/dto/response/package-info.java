/**
 * Response Data Transfer Objects for the BidStream platform.
 *
 * <p>
 * This package contains all DTOs used for outgoing responses to clients.
 * Response DTOs are responsible for structuring, formatting, and presenting
 * data from the application to external consumers via REST APIs.
 * </p>
 *
 * <p>
 * <strong>Response DTO Categories:</strong>
 * </p>
 * <ul>
 * <li><strong>Entity Responses:</strong> Full details of domain entities (e.g.,
 * {@code AuctionResponse})</li>
 * <li><strong>Summary Responses:</strong> Condensed views for listings (e.g.,
 * {@code AuctionSummaryResponse})</li>
 * <li><strong>Collection Responses:</strong> Paginated lists and search
 * results</li>
 * <li><strong>Status Responses:</strong> Operation results and
 * confirmations</li>
 * <li><strong>Analytics Responses:</strong> Statistical and reporting data</li>
 * </ul>
 *
 * <p>
 * <strong>Design Patterns:</strong>
 * </p>
 * <ul>
 * <li><strong>Full vs Summary:</strong> Different detail levels for different
 * use cases</li>
 * <li><strong>Nested Objects:</strong> Related entity data without circular
 * references</li>
 * <li><strong>Computed Fields:</strong> Derived values calculated for client
 * convenience</li>
 * <li><strong>Conditional Fields:</strong> Fields that appear based on context
 * or permissions</li>
 * </ul>
 *
 * <p>
 * <strong>JSON Serialization Best Practices:</strong>
 * </p>
 *
 * <pre>
 * {
 *   &#64;code
 *   &#64;JsonPropertyOrder({ "id", "title", "status", "timestamps" })
 *   public class AuctionResponse {
 *
 *     &#64;JsonProperty("id")
 *     private UUID id;
 *
 *     &#64;JsonProperty("created_at")
 *     &#64;JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
 *     private LocalDateTime createdAt;
 *
 *     &#64;JsonInclude(JsonInclude.Include.NON_NULL)
 *     private String optionalField;
 *
 *     @JsonIgnore
 *     private String internalField;
 *   }
 * }
 * </pre>
 *
 * <p>
 * <strong>Data Optimization Strategies:</strong>
 * </p>
 * <ul>
 * <li><strong>Field Selection:</strong> Only include necessary data for each
 * endpoint</li>
 * <li><strong>Lazy Loading:</strong> Load expensive data only when needed</li>
 * <li><strong>Caching Support:</strong> Include ETags and cache headers when
 * appropriate</li>
 * <li><strong>Compression:</strong> Structure data for efficient JSON
 * compression</li>
 * </ul>
 *
 * <p>
 * <strong>Security and Privacy:</strong>
 * </p>
 * <ul>
 * <li><strong>Data Filtering:</strong> Hide sensitive fields based on user
 * permissions</li>
 * <li><strong>PII Protection:</strong> Mask or exclude personally identifiable
 * information</li>
 * <li><strong>Role-based Views:</strong> Different response structures for
 * different user roles</li>
 * <li><strong>Audit Trail:</strong> Include only appropriate metadata for
 * transparency</li>
 * </ul>
 *
 * <p>
 * <strong>Common Response Patterns:</strong>
 * </p>
 *
 * <pre>{@code
 * // Full entity response
 * AuctionResponse fullAuction = AuctionResponse.builder()
 *     .id(auction.getId())
 *     .title(auction.getTitle())
 *     .description(auction.getDescription())
 *     .seller(SellerInfo.from(auction.getSeller()))
 *     .build();
 *
 * // Summary for listings
 * AuctionSummaryResponse summary = AuctionSummaryResponse.builder()
 *     .id(auction.getId())
 *     .title(auction.getTitle())
 *     .currentPrice(auction.getCurrentPrice())
 *     .endTime(auction.getEndTime())
 *     .build();
 *
 * // Paginated collection
 * PagedResponse<AuctionSummaryResponse> results =
 *   PagedResponse.<AuctionSummaryResponse>builder()
 *     .content(auctionSummaries)
 *     .page(0)
 *     .size(20)
 *     .totalElements(150)
 *     .build();
 * }</pre>
 *
 * <p>
 * <strong>Error Response Integration:</strong>
 * </p>
 * <ul>
 * <li>Consistent error format across all endpoints</li>
 * <li>Structured error details for validation failures</li>
 * <li>Appropriate HTTP status codes</li>
 * <li>Error categorization for client handling</li>
 * </ul>
 *
 * <p>
 * <strong>API Evolution Support:</strong>
 * </p>
 * <ul>
 * <li><strong>Backwards Compatibility:</strong> Add fields without breaking
 * existing clients</li>
 * <li><strong>Deprecation Handling:</strong> Mark deprecated fields with
 * annotations</li>
 * <li><strong>Version Support:</strong> Different response formats for
 * different API versions</li>
 * <li><strong>Progressive Enhancement:</strong> Optional fields for enhanced
 * client features</li>
 * </ul>
 *
 * <p>
 * <strong>Performance Monitoring:</strong>
 * </p>
 * <ul>
 * <li>Response size tracking for optimization</li>
 * <li>Serialization performance metrics</li>
 * <li>Client usage patterns analysis</li>
 * <li>Cache hit/miss ratios</li>
 * </ul>
 *
 * <p>
 * <strong>Documentation Standards:</strong>
 * </p>
 * <ul>
 * <li>Complete field descriptions with examples</li>
 * <li>Possible value ranges and constraints</li>
 * <li>Relationship explanations for nested objects</li>
 * <li>When fields appear or are omitted</li>
 * </ul>
 *
 * <p>
 * <strong>Common Response DTOs:</strong>
 * </p>
 * <ul>
 * <li>{@link com.bidstream.dto.response.AuctionResponse} - Complete auction
 * details</li>
 * <li>{@link com.bidstream.dto.response.AuctionSummaryResponse} - Auction
 * listing summary</li>
 * <li>{@link com.bidstream.dto.response.UserResponse} - User profile
 * information</li>
 * <li>{@link com.bidstream.dto.response.BidResponse} - Bid placement
 * confirmation</li>
 * <li>{@link com.bidstream.dto.response.ApiResponse} - Generic operation
 * results</li>
 * </ul>
 *
 * <p>
 * <strong>Builder Pattern Usage:</strong>
 * </p>
 *
 * <pre>{@code
 * // Recommended: Use builder pattern for complex responses
 * AuctionResponse response = AuctionResponse.builder()
 *     .id(auction.getId())
 *     .title(auction.getTitle())
 *     .currentPrice(auction.getCurrentPrice())
 *     .timeRemaining(calculateTimeRemaining(auction))
 *     .sellerInfo(UserSummary.from(auction.getSeller()))
 *     .build();
 *
 * // Factory methods for common conversions
 * public static AuctionResponse from(Auction auction) {
 *     return builder()
 *         .id(auction.getId())
 *         .title(auction.getTitle())
 *         // ... other mappings
 *         .build();
 * }
 * }</pre>
 *
 * @author Carlos Salguero
 * @since 1.0.0
 * @version 1.0.0
 * @see com.bidstream.dto.request
 * @see com.bidstream.exception.GlobalExceptionHandler.ErrorResponse
 */
package com.bidstream.dto.response;
