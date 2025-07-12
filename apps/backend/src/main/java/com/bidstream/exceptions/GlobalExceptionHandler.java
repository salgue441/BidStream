package com.bidstream.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Global exception handler for the BidStream application.
 * Provides centralized error handling and consistent error response format
 * across all controllers and services.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /** Logger for this class. */
  private static final Logger LOGGER =
    LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /** Default error message for unexpected errors. */
  private static final String DEFAULT_ERROR_MESSAGE =
    "An unexpected error occurred";

  /** Default error code for unexpected errors. */
  private static final String DEFAULT_ERROR_CODE = "INTERNAL_ERROR";

  // Bidstream Custom Exceptions
  /**
   * Handles ResourceNotFoundException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(
      final ResourceNotFoundException ex, final WebRequest request) {
    LOGGER.warn("Resource not found: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles BusinessException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(
      final BusinessException ex, final WebRequest request) {

    LOGGER.warn("Business rule violation: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .context(ex.getContext())
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  /**
   * Handles UnauthorizedException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorizedException(
      final UnauthorizedException ex, final WebRequest request) {

    LOGGER.warn("Authentication error: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.UNAUTHORIZED.value())
        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles ForbiddenException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponse> handleForbiddenException(
      final ForbiddenException ex, final WebRequest request) {

    LOGGER.warn("Authorization error: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.FORBIDDEN.value())
        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  // Auction specific exceptions
  /**
   * Handles AuctionException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(AuctionException.class)
  public ResponseEntity<ErrorResponse> handleAuctionException(
      final AuctionException ex, final WebRequest request) {

    LOGGER.warn("Auction error: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .category("AUCTION")
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  /**
   * Handles BiddingException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(BiddingException.class)
  public ResponseEntity<ErrorResponse> handleBiddingException(
      final BiddingException ex, final WebRequest request) {

    LOGGER.warn("Bidding error: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .category("BIDDING")
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  /**
   * Handles UserException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(UserException.class)
  public ResponseEntity<ErrorResponse> handleUserException(
      final UserException ex, final WebRequest request) {

    LOGGER.warn("User error: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .category("USER")
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  /**
   * Handles PaymentException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(PaymentException.class)
  public ResponseEntity<ErrorResponse> handlePaymentException(
      final PaymentException ex, final WebRequest request) {

    LOGGER.warn("Payment error: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .category("PAYMENT")
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  /**
   * Handles TransactionException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(TransactionException.class)
  public ResponseEntity<ErrorResponse> handleTransactionException(
      final TransactionException ex, final WebRequest request) {

    LOGGER.warn("Transaction error: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .category("TRANSACTION")
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  // Spring validation exceptions
  /**
   * Handles MethodArgumentNotValidException (request body validation).
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response with field-specific validation errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final WebRequest request) {
    LOGGER.warn("Request body validation failed: {}", ex.getMessage());

    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();

      fieldErrors.put(fieldName, errorMessage);
    });

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message("Request validation failed")
        .errorCode("VALIDATION_FAILED")
        .path(extractPath(request))
        .details(fieldErrors)
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles ConstraintViolationException (path variable/query parameter
   * validation).
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(
      final ConstraintViolationException ex, final WebRequest request) {

    LOGGER.warn("Constraint validation failed: {}", ex.getMessage());

    List<String> violations = ex.getConstraintViolations()
        .stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toList());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message("Parameter validation failed")
        .errorCode("CONSTRAINT_VIOLATION")
        .path(extractPath(request))
        .details(Map.of("violations", violations))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles BindException (form data validation).
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ErrorResponse> handleBindException(
      final BindException ex, final WebRequest request) {

    LOGGER.warn("Form data validation failed: {}", ex.getMessage());

    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      fieldErrors.put(fieldName, errorMessage);
    });

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message("Form data validation failed")
        .errorCode("BIND_EXCEPTION")
        .path(extractPath(request))
        .details(fieldErrors)
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // HTTP Related Exceptions
  /**
   * Handles HttpRequestMethodNotSupportedException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(
    HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
      final HttpRequestMethodNotSupportedException ex,
      final WebRequest request) {
    LOGGER.warn("HTTP method not supported: {}", ex.getMessage());

    String supportedMethods = String.join(", ", ex.getSupportedMethods());
    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.METHOD_NOT_ALLOWED.value())
        .error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
        .message("HTTP method '" + ex.getMethod()
          + "' not supported for this endpoint")
        .errorCode("METHOD_NOT_ALLOWED")
        .path(extractPath(request))
        .details(Map.of("supportedMethods", supportedMethods))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * Handles HttpMediaTypeNotSupportedException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException ex, final WebRequest request) {

    LOGGER.warn("Media type not supported: {}", ex.getMessage());

    String supportedTypes = ex.getSupportedMediaTypes()
        .stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
        .error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
        .message("Media type '" + ex.getContentType()
          + "' not supported")
        .errorCode("UNSUPPORTED_MEDIA_TYPE")
        .path(extractPath(request))
        .details(Map.of("supportedTypes", supportedTypes))
        .build();

    return new ResponseEntity<>(errorResponse,
      HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  /**
   * Handles NoHandlerFoundException (404 errors).
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFound(
      final NoHandlerFoundException ex, final WebRequest request) {

    LOGGER.warn("No handler found: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message("Endpoint not found: " + ex.getHttpMethod()
          + " " + ex.getRequestURL())
        .errorCode("ENDPOINT_NOT_FOUND")
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles MissingServletRequestParameterException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(
    MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParameter(
      final MissingServletRequestParameterException ex,
      final WebRequest request) {

    LOGGER.warn("Missing request parameter: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message("Required parameter '"
          + ex.getParameterName() + "' is missing")
        .errorCode("MISSING_PARAMETER")
        .path(extractPath(request))
        .details(Map.of(
            "parameterName", ex.getParameterName(),
            "parameterType", ex.getParameterType()))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles MethodArgumentTypeMismatchException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(
      final MethodArgumentTypeMismatchException ex,
      final WebRequest request) {

    LOGGER.warn("Type mismatch for parameter: {}", ex.getMessage());

    String expectedType = ex.getRequiredType() != null
        ? ex.getRequiredType().getSimpleName()
        : "unknown";

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message("Invalid value '" + ex.getValue()
            + "' for parameter '" + ex.getName() + "'")
        .errorCode("TYPE_MISMATCH")
        .path(extractPath(request))
        .details(Map.of(
            "parameterName", ex.getName(),
            "providedValue", String.valueOf(ex.getValue()),
            "expectedType", expectedType))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles HttpMessageNotReadableException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleMessageNotReadable(
      final HttpMessageNotReadableException ex, final WebRequest request) {

    LOGGER.warn("Message not readable: {}", ex.getMessage());

    String message = "Invalid request body format";
    if (ex.getMessage().contains("JSON")) {
      message = "Invalid JSON format in request body";
    }

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(message)
        .errorCode("MALFORMED_REQUEST_BODY")
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // Technical Exceptions
  /**
   * Handles ExternalServiceException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(ExternalServiceException.class)
  public ResponseEntity<ErrorResponse> handleExternalServiceException(
      final ExternalServiceException ex, final WebRequest request) {

    LOGGER.error("External service error: {}", ex.getMessage(), ex);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message("External service temporarily unavailable")
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  /**
   * Handles RateLimitException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(RateLimitException.class)
  public ResponseEntity<ErrorResponse> handleRateLimitException(
      final RateLimitException ex, final WebRequest request) {

    LOGGER.warn("Rate limit exceeded: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.TOO_MANY_REQUESTS.value())
        .error(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase())
        .message(ex.getMessage())
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
  }

  /**
   * Handles ConfigurationException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(ConfigurationException.class)
  public ResponseEntity<ErrorResponse> handleConfigurationException(
      final ConfigurationException ex, final WebRequest request) {

    LOGGER.error("Configuration error: {}", ex.getMessage(), ex);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message("System configuration error")
        .errorCode(ex.getErrorCode())
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  // Generic Exception Handler
  /**
   * Handles all other uncaught exceptions.
   *
   * @param ex      the exception
   * @param request the web request
   * @return error response
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(
      final Exception ex, final WebRequest request) {

    LOGGER.error("Unexpected error occurred: {}", ex.getMessage(), ex);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .message(DEFAULT_ERROR_MESSAGE)
        .errorCode(DEFAULT_ERROR_CODE)
        .path(extractPath(request))
        .build();

    return new ResponseEntity<>(errorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Helper methods
  /**
   * Extracts the request path from WebRequest.
   *
   * @param request the web request
   * @return the request path
   */
  private String extractPath(final WebRequest request) {
    return request.getDescription(false).replace("uri=", "");
  }

  // Error Response Class
  /**
   * Standardized error response format for all API errors.
   * Provides consistent structure across all error responses.
   */
  public static class ErrorResponse {

    /** Minimum status code. */
    private final int minStatusCode = 100;

    /** Maximum status code. */
    private final int maxStatusCode = 599;

    /** Timestamp when the error occurred. */
    private LocalDateTime timestamp;

    /** HTTP status code. */
    private int status;

    /** HTTP status reason phrase. */
    private String error;

    /** Human-readable error message. */
    private String message;

    /** Application-specific error code. */
    private String errorCode;

    /** Request path where error occurred. */
    private String path;

    /** Error category for grouping. */
    private String category;

    /** Additional error details. */
    private Object details;

    /** Additional context information. */
    private Object context;

    /**
     * Default no-argument constructor.
     *
     * <p>
     * Creates an empty ErrorResponse instance. All fields will be null
     * until explicitly set. Typically used by JSON deserialization frameworks
     * or when building the response programmatically.
     * </p>
     *
     * @see #builder()
     */
    public ErrorResponse() {
    }

    /**
     * Creates a new ErrorResponseBuilder for constructing ErrorResponse
     * instances.
     *
     * <p>
     * The builder pattern provides a fluent, readable way to construct
     * ErrorResponse objects with method chaining. This is the preferred
     * way to create ErrorResponse instances.
     * </p>
     *
     * <p>
     * <strong>Usage Example:</strong>
     * </p>
     *
     * <pre>{@code
     * ErrorResponse error = ErrorResponse.builder()
     *     .timestamp(LocalDateTime.now())
     *     .status(400)
     *     .error("Bad Request")
     *     .message("Validation failed")
     *     .errorCode("VALIDATION_FAILED")
     *     .path("/auctions")
     *     .build();
     * }</pre>
     *
     * @return a new ErrorResponseBuilder instance for method chaining
     * @see ErrorResponseBuilder
     */
    public static ErrorResponseBuilder builder() {
      return new ErrorResponseBuilder();
    }

    // Getters and Setters

    /**
     * Gets the timestamp when the error occurred.
     *
     * <p>
     * Returns the exact moment when the error was caught and processed
     * by the exception handler. Useful for:
     * </p>
     * <ul>
     * <li>Debugging and troubleshooting</li>
     * <li>Log correlation and timeline analysis</li>
     * <li>Client-side error tracking and reporting</li>
     * <li>Performance monitoring and alerting</li>
     * </ul>
     *
     * @return the error timestamp, or null if not set
     * @see #setTimestamp(LocalDateTime)
     */
    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     *
     * <p>
     * Should be set to the current time when the error is first caught
     * and processed. Typically called automatically by the
     * GlobalExceptionHandler.
     * </p>
     *
     * <p>
     * <strong>Best Practice:</strong> Use {@code LocalDateTime.now()} for
     * consistency across the application.
     * </p>
     *
     * @param newTimestamp the error timestamp to set, should not be null
     * @see #getTimestamp()
     * @see LocalDateTime#now()
     */
    public void setTimestamp(final LocalDateTime newTimestamp) {
      this.timestamp = newTimestamp;
    }

    /**
     * Gets the HTTP status code for this error.
     *
     * <p>
     * Returns the HTTP status code that should be sent to the client.
     * This corresponds to standard HTTP status codes and helps clients
     * understand the type of error that occurred.
     * </p>
     *
     * <p>
     * <strong>Common Return Values:</strong>
     * </p>
     * <ul>
     * <li>400 - Client sent invalid data (validation errors)</li>
     * <li>401 - Authentication required or failed</li>
     * <li>403 - Access forbidden (authorization failed)</li>
     * <li>404 - Requested resource not found</li>
     * <li>409 - Request conflicts with current state</li>
     * <li>500 - Internal server error</li>
     * </ul>
     *
     * @return the HTTP status code, or 0 if not set
     * @see #setStatus(int)
     * @see org.springframework.http.HttpStatus
     */
    public int getStatus() {
      return status;
    }

    /**
     * Sets the HTTP status code for this error.
     *
     * <p>
     * Should correspond to the appropriate HTTP status code for the
     * error type. The status code helps clients determine how to handle
     * the error programmatically.
     * </p>
     *
     * <p>
     * <strong>Guidelines:</strong>
     * </p>
     * <ul>
     * <li>Use 4xx codes for client errors (bad input, auth issues)</li>
     * <li>Use 5xx codes for server errors (system failures)</li>
     * <li>Be consistent with HTTP standards</li>
     * </ul>
     *
     * @param newStatus the HTTP status code to set, should be a valid HTTP
     *                  status code
     * @throws IllegalArgumentException if status is not a valid HTTP
     *                                  status code
     * @see #getStatus()
     * @see org.springframework.http.HttpStatus
     */
    public void setStatus(final int newStatus) {
      if (newStatus < minStatusCode
        || newStatus > maxStatusCode) {
        throw new IllegalArgumentException("HTTP status code "
            + "must be between 100 and 599");
      }

      this.status = newStatus;
    }

    /**
     * Gets the HTTP status reason phrase.
     *
     * <p>
     * Returns the human-readable description that corresponds to the
     * HTTP status code. This provides additional context about the error
     * type without requiring clients to maintain status code mappings.
     * </p>
     *
     * @return the HTTP status reason phrase, or null if not set
     * @see #setError(String)
     * @see #getStatus()
     */
    public String getError() {
      return error;
    }

    /**
     * Sets the HTTP status reason phrase.
     *
     * <p>
     * Should correspond to the standard reason phrase for the HTTP status code.
     * Examples: "Bad Request", "Not Found", "Internal Server Error".
     * </p>
     *
     * <p>
     * <strong>Best Practice:</strong> Use the standard HTTP reason phrases
     * from {@code HttpStatus.getReasonPhrase()} for consistency.
     * </p>
     *
     * @param newError the HTTP status reason phrase to set
     * @see #getError()
     * @see org.springframework.http.HttpStatus#getReasonPhrase()
     */
    public void setError(final String newError) {
      this.error = newError;
    }

    /**
     * Gets the human-readable error message.
     *
     * <p>
     * Returns the primary error message that describes what went wrong.
     * This message should be suitable for display to end users and should
     * provide clear, actionable information about the error.
     * </p>
     *
     * <p>
     * <strong>Message Characteristics:</strong>
     * </p>
     * <ul>
     * <li>Clear and understandable by non-technical users</li>
     * <li>Specific enough to understand the problem</li>
     * <li>Actionable when possible (suggests next steps)</li>
     * <li>Free of sensitive information</li>
     * </ul>
     *
     * @return the error message, or null if not set
     * @see #setMessage(String)
     */
    public String getMessage() {
      return message;
    }

    /**
     * Sets the human-readable error message.
     *
     * <p>
     * The message should clearly describe what went wrong in language
     * that is appropriate for the intended audience (end users,
     * developers, etc.).
     * </p>
     *
     * <p>
     * <strong>Security Note:</strong> Ensure the message does not contain
     * sensitive information such as internal system details, database schemas,
     * or user data that should not be exposed.
     * </p>
     *
     * @param newMessage the error message to set, should be clear and helpful
     * @see #getMessage()
     */
    public void setMessage(final String newMessage) {
      this.message = newMessage;
    }

    /**
     * Gets the application-specific error code.
     *
     * <p>
     * Returns a unique identifier that allows clients to handle specific
     * error types programmatically. Error codes provide a stable way to
     * identify errors that won't change even if error messages are updated.
     * </p>
     *
     * <p>
     * <strong>Usage by Clients:</strong>
     * </p>
     *
     * <pre>{@code
     * switch (errorResponse.getErrorCode()) {
     *   case "USER_NOT_FOUND":
     *     handleUserNotFound();
     *     break;
     *   case "AUCTION_ENDED":
     *     showAuctionEndedMessage();
     *     break;
     *   default:
     *     showGenericError();
     * }
     * }</pre>
     *
     * @return the error code, or null if not set
     * @see #setErrorCode(String)
     */
    public String getErrorCode() {
      return errorCode;
    }

    /**
     * Sets the application-specific error code.
     *
     * <p>
     * Should be a unique, stable identifier for the error type.
     * Use UPPER_SNAKE_CASE convention for consistency across the application.
     * </p>
     *
     * <p>
     * <strong>Guidelines:</strong>
     * </p>
     * <ul>
     * <li>Use descriptive names (USER_NOT_FOUND vs ERROR_001)</li>
     * <li>Keep codes stable across API versions</li>
     * <li>Group related errors with common prefixes</li>
     * <li>Document all error codes for client developers</li>
     * </ul>
     *
     * @param newErrorCode the error code to set, should
     *                     follow naming conventions
     * @see #getErrorCode()
     */
    public void setErrorCode(final String newErrorCode) {
      this.errorCode = newErrorCode;
    }

    /**
     * Gets the request path where the error occurred.
     *
     * <p>
     * Returns the URL path (excluding query parameters) that was being
     * accessed when the error occurred. This is useful for debugging and
     * helps identify which endpoint or operation caused the error.
     * </p>
     *
     * <p>
     * <strong>Examples:</strong> "/users/123", "/auctions/search", "/bids"
     * </p>
     *
     * @return the request path, or null if not set
     * @see #setPath(String)
     */
    public String getPath() {
      return path;
    }

    /**
     * Sets the request path where the error occurred.
     *
     * <p>
     * Should be set to the URL path (without query parameters) of the
     * request that caused the error. Query parameters are excluded to
     * prevent sensitive information from being logged.
     * </p>
     *
     * <p>
     * <strong>Security Note:</strong> Only include the path portion of the URL.
     * Avoid including query parameters that might contain sensitive data.
     * </p>
     *
     * @param newPath the request path to set
     * @see #getPath()
     */
    public void setPath(final String newPath) {
      this.path = newPath;
    }

    /**
     * Gets the error category for grouping related errors.
     *
     * <p>
     * Returns a high-level category that groups related errors together.
     * This is useful for error monitoring, metrics aggregation, and
     * client-side error handling strategies.
     * </p>
     *
     * <p>
     * <strong>Standard Categories:</strong> USER, AUCTION, BIDDING, PAYMENT,
     * VALIDATION, AUTHENTICATION, AUTHORIZATION, SYSTEM
     * </p>
     *
     * @return the error category, or null if not set
     * @see #setCategory(String)
     */
    public String getCategory() {
      return category;
    }

    /**
     * Sets the error category for grouping related errors.
     *
     * <p>
     * Should be set to a high-level category that helps group related
     * errors together. Use consistent category names across the application.
     * </p>
     *
     * <p>
     * <strong>Best Practice:</strong> Use a predefined set of categories
     * rather than ad-hoc values to ensure consistency in monitoring and
     * reporting.
     * </p>
     *
     * @param newCategory the error category to set, should be
     *                    from a predefined set
     * @see #getCategory()
     */
    public void setCategory(final String newCategory) {
      this.category = newCategory;
    }

    /**
     * Gets additional structured error details.
     *
     * <p>
     * Returns detailed information about the error that goes beyond
     * the main message. This is typically used for validation errors,
     * constraint violations, or complex business rule failures.
     * </p>
     *
     * <p>
     * <strong>Common Return Types:</strong>
     * </p>
     * <ul>
     * <li>{@code Map<String, String>} - Field validation errors</li>
     * <li>{@code List<String>} - Multiple error messages</li>
     * <li>Custom objects - Complex error structures</li>
     * </ul>
     *
     * @return the error details object, or null if not set
     * @see #setDetails(Object)
     */
    public Object getDetails() {
      return details;
    }

    /**
     * Sets additional structured error details.
     *
     * <p>
     * Should contain specific, detailed information about the error.
     * The object should be JSON-serializable and provide meaningful
     * information to help clients understand and handle the error.
     * </p>
     *
     * <p>
     * <strong>Examples:</strong>
     * </p>
     * <ul>
     * <li>Validation errors: {@code Map<String, String>}
     *     of field names to error
     * messages</li>
     * <li>Business rule violations: Object with relevant business context</li>
     * <li>Constraint violations: List of violated constraints</li>
     * </ul>
     *
     * @param newDetails the error details to set, must be JSON-serializable
     * @see #getDetails()
     */
    public void setDetails(final Object newDetails) {
      this.details = newDetails;
    }

    /**
     * Gets additional context information for debugging.
     *
     * <p>
     * Returns supplementary information that helps with debugging,
     * monitoring, and understanding the circumstances under which the
     * error occurred. This information is typically used by developers
     * and support teams rather than end users.
     * </p>
     *
     * <p>
     * <strong>Common Context Types:</strong>
     * </p>
     * <ul>
     * <li>User and session identifiers</li>
     * <li>Request correlation IDs</li>
     * <li>Business entity IDs</li>
     * <li>Technical metadata (versions, timing, etc.)</li>
     * </ul>
     *
     * @return the context object, or null if not set
     * @see #setContext(Object)
     */
    public Object getContext() {
      return context;
    }

    /**
     * Sets additional context information for debugging.
     *
     * <p>
     * Should contain information that helps with debugging and error
     * tracking. Be mindful of privacy and security when setting context
     * information - avoid including sensitive data.
     * </p>
     *
     * <p>
     * <strong>Security Guidelines:</strong>
     * </p>
     * <ul>
     * <li>Never include passwords, tokens, or sensitive personal data</li>
     * <li>Use UUIDs or hashed values for user identification</li>
     * <li>Consider data protection regulations (GDPR, CCPA)</li>
     * <li>Include only information necessary for debugging</li>
     * </ul>
     *
     * @param newContext the context information to set, must be
     *                   JSON-serializable and non-sensitive
     * @see #getContext()
     */
    public void setContext(final Object newContext) {
      this.context = newContext;
    }

    /**
     * Builder class for constructing ErrorResponse instances using the builder
     * pattern.
     *
     * <p>
     * Provides a fluent, readable way to construct ErrorResponse objects
     * with method chaining. This approach offers several advantages:
     * </p>
     * <ul>
     * <li><strong>Readability:</strong> Method chaining creates
     * self-documenting code</li>
     * <li><strong>Flexibility:</strong> Set only the fields you need</li>
     * <li><strong>Immutability:</strong> Build complete objects in one step
     * </li>
     * <li><strong>Validation:</strong> Can include validation in build()
     * method</li>
     * </ul>
     *
     * <p>
     * <strong>Usage Example:</strong>context
     * </p>
     *
     * <pre>{@code
     * ErrorResponse error = ErrorResponse.builder()
     *     .timestamp(LocalDateTime.now())
     *     .status(404)
     *     .error("Not Found")
     *     .message("User not found with ID: 12345")
     *     .errorCode("USER_NOT_FOUND")
     *     .path("/users/12345")
     *     .category("USER")
     *     .build();
     * }</pre>
     *
     * <p>
     * <strong>Validation Example:</strong>
     * </p>
     *
     * <pre>{@code
     * ErrorResponse validationError = ErrorResponse.builder()
     *     .timestamp(LocalDateTime.now())
     *     .status(400)
     *     .error("Bad Request")
     *     .message("Validation failed")
     *     .errorCode("VALIDATION_FAILED")
     *     .path("/auctions")
     *     .category("VALIDATION")
     *     .details(Map.of(
     *         "title", "Title cannot be empty",
     *         "price", "Price must be greater than 0"))
     *     .build();
     * }</pre>
     *
     * @see ErrorResponse#builder()
     */
    public static class ErrorResponseBuilder {

      /** The ErrorResponse instance being built. */
      private final ErrorResponse errorResponse = new ErrorResponse();

      /**
       * Sets the timestamp when the error occurred.
       *
       * <p>
       * Typically set to {@code LocalDateTime.now()} when the error
       * is first caught and processed.
       * </p>
       *
       * @param timestamp the error timestamp to set
       * @return this builder for method chaining
       * @see ErrorResponse#setTimestamp(LocalDateTime)
       */
      public ErrorResponseBuilder timestamp(final LocalDateTime timestamp) {
        errorResponse.setTimestamp(timestamp);
        return this;
      }

      /**
       * Sets the HTTP status code for this error.
       *
       * <p>
       * Should correspond to the appropriate HTTP status code for
       * the error type (4xx for client errors, 5xx for server errors).
       * </p>
       *
       * @param status the HTTP status code to set
       * @return this builder for method chaining
       * @throws IllegalArgumentException if status is not a valid
       *                                  HTTP status code
       * @see ErrorResponse#setStatus(int)
       */
      public ErrorResponseBuilder status(final int status) {
        errorResponse.setStatus(status);
        return this;
      }

      /**
       * Sets the HTTP status reason phrase.
       *
       * <p>
       * Should be the standard reason phrase for the HTTP status code
       * (e.g., "Bad Request", "Not Found", "Internal Server Error").
       * </p>
       *
       * @param error the HTTP status reason phrase to set
       * @return this builder for method chaining
       * @see ErrorResponse#setError(String)
       */
      public ErrorResponseBuilder error(final String error) {
        errorResponse.setError(error);
        return this;
      }

      /**
       * Sets the human-readable error message.
       *
       * <p>
       * Should clearly describe what went wrong in language appropriate
       * for the intended audience. Avoid including sensitive information.
       * </p>
       *
       * @param message the error message to set
       * @return this builder for method chaining
       * @see ErrorResponse#setMessage(String)
       */
      public ErrorResponseBuilder message(final String message) {
        errorResponse.setMessage(message);
        return this;
      }

      /**
       * Sets the application-specific error code.
       *
       * <p>
       * Should be a unique, stable identifier for the error type
       * using UPPER_SNAKE_CASE convention.
       * </p>
       *
       * @param errorCode the error code to set
       * @return this builder for method chaining
       * @see ErrorResponse#setErrorCode(String)
       */
      public ErrorResponseBuilder errorCode(final String errorCode) {
        errorResponse.setErrorCode(errorCode);
        return this;
      }

      /**
       * Sets the request path where the error occurred.
       *
       * <p>
       * Should be the URL path (without query parameters) of the
       * request that caused the error.
       * </p>
       *
       * @param path the request path to set
       * @return this builder for method chaining
       * @see ErrorResponse#setPath(String)
       */
      public ErrorResponseBuilder path(final String path) {
        errorResponse.setPath(path);
        return this;
      }

      /**
       * Sets the error category for grouping related errors.
       *
       * <p>
       * Should be a high-level category from a predefined set
       * (e.g., USER, AUCTION, BIDDING, PAYMENT).
       * </p>
       *
       * @param category the error category to set
       * @return this builder for method chaining
       * @see ErrorResponse#setCategory(String)
       */
      public ErrorResponseBuilder category(final String category) {
        errorResponse.setCategory(category);
        return this;
      }

      /**
       * Sets additional structured error details.
       *
       * <p>
       * Should contain specific, detailed information about the error
       * such as validation failures or business rule violations.
       * </p>
       *
       * @param details the error details to set, must be JSON-serializable
       * @return this builder for method chaining
       * @see ErrorResponse#setDetails(Object)
       */
      public ErrorResponseBuilder details(final Object details) {
        errorResponse.setDetails(details);
        return this;
      }

      /**
       * Sets additional context information for debugging.
       *
       * <p>
       * Should contain non-sensitive information that helps with
       * debugging and error tracking.
       * </p>
       *
       * @param context the context information to set,
       *                must be JSON-serializable and non-sensitive
       * @return this builder for method chaining
       * @see ErrorResponse#setContext(Object)
       */
      public ErrorResponseBuilder context(final Object context) {
        errorResponse.setContext(context);
        return this;
      }

      /**
       * Builds and returns the configured ErrorResponse instance.
       *
       * <p>
       * This method completes the building process and returns the
       * final ErrorResponse object. After calling this method, the
       * builder can be discarded.
       * </p>
       *
       * <p>
       * <strong>Validation:</strong> This method could be enhanced
       * to include validation logic to ensure the ErrorResponse is
       * properly configured before returning it.
       * </p>
       *
       * @return the configured ErrorResponse instance
       * @throws IllegalStateException if required fields are missing (future
       *                               enhancement)
       */
      public ErrorResponse build() {
        validateRequiredFields();
        return errorResponse;
      }

      /**
       * Private method for validating required fields (future enhancement).
       *
       * <p>
       * This method could be implemented to ensure that essential
       * fields like status, message, and errorCode are set before
       * building the ErrorResponse.
       * </p>
       *
       * @throws IllegalStateException if required fields are missing
       */
      private void validateRequiredFields() {
        if (errorResponse.getStatus() == 0) {
          throw new IllegalStateException("HTTP status code is required");
        }
        if (errorResponse.getMessage() == null
            || errorResponse.getMessage().trim().isEmpty()) {
          throw new IllegalStateException("Error message is required");
        }

        if (errorResponse.getErrorCode() == null
            || errorResponse.getErrorCode().trim().isEmpty()) {
          throw new IllegalStateException("Error code is required");
        }
      }
    }
  }
}
