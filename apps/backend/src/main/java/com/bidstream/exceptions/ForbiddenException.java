package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user lacks required permissions.
 * Typically results in HTTP 403 responses.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BidStreamException {

  /** Default error code for authorization failures. */
  private static final String DEFAULT_ERROR_CODE = "ACCESS_DENIED";

  /**
   * Constructor with default message.
   */
  public ForbiddenException() {
    super("Access denied: insufficient permissions");
  }

  /**
   * Constructor with custom message.
   *
   * @param message the error message
   */
  public ForbiddenException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and cause.
   *
   * @param message the error message
   * @param cause   the underlying cause
   */
  public ForbiddenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for resource-specific access denial.
   *
   * @param action   the action being attempted
   * @param resource the resource being accessed
   */
  public ForbiddenException(final String action, final String resource) {
    super("Access denied: cannot " + action + " " + resource);
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
    return HttpStatus.FORBIDDEN;
  }
}
