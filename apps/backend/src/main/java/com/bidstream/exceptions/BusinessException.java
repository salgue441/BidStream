package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when business rules are violated.
 * Typically results in HTTP 409 or 422 responses.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessException extends BidStreamException {

  /** Default error code for business rule violations. */
  private static final String DEFAULT_ERROR_CODE = "BUSINESS_RULE_VIOLATION";

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  public BusinessException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and cause.
   *
   * @param message the error message
   * @param cause   the underlying cause
   */
  public BusinessException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with message and specific error code.
   *
   * @param message   the error message
   * @param errorCode the specific business error code
   */
  public BusinessException(final String message, final String errorCode) {
    super(message, errorCode);
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
    return HttpStatus.CONFLICT;
  }
}
