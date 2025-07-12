package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when validation fails.
 * Typically results in HTTP 400 responses.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends BidStreamException {

  /** Default error code for validation failures. */
  private static final String DEFAULT_ERROR_CODE = "VALIDATION_FAILED";

  /**
   * Constructor with message only.
   *
   * @param message the validation error message
   */
  public ValidationException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and cause.
   *
   * @param message the validation error message
   * @param cause   the underlying cause
   */
  public ValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with field name and message.
   *
   * @param fieldName the field that failed validation
   * @param message   the validation error message
   */
  public ValidationException(final String fieldName, final String message) {
    super("Validation failed for field '" + fieldName + "': " + message);
  }

  /**
   * Returns the default error code associated with this exception.
   *
   * @return the default error code as a {@code String}
   */
  @Override
  protected String getDefaultErrorCode() {
    return DEFAULT_ERROR_CODE;
  }

  /**
   * Returns the default http status associated with this exception.
   *
   * @return the default http status as a {@code HttpStatus}
   */
  @Override
  protected HttpStatus getDefaultHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
