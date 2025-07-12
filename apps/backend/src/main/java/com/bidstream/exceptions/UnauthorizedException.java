package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user is not authenticated.
 * Typically results in HTTP 401 responses.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends BidStreamException {

  /** Default error code for authentication failures. */
  private static final String DEFAULT_ERROR_CODE = "AUTHENTICATION_REQUIRED";

  /**
   * Constructor with default message.
   */
  public UnauthorizedException() {
    super("Authentication is required to access this resource");
  }

  /**
   * Constructor with custom message.
   *
   * @param message the error message
   */
  public UnauthorizedException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and cause.
   *
   * @param message the error message
   * @param cause   the underlying cause
   */
  public UnauthorizedException(final String message, final Throwable cause) {
    super(message, cause);
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
    return HttpStatus.UNAUTHORIZED;
  }
}
