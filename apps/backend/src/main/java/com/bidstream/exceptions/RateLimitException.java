package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown for rate limiting violations.
 */
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException extends BidStreamException {

  /** Default error code for rate limiting. */
  private static final String DEFAULT_ERROR_CODE = "RATE_LIMIT_EXCEEDED";

  /**
   * Constructor with default message.
   */
  public RateLimitException() {
    super("Rate limit exceeded. Please try again later.");
  }

  /**
   * Constructor with custom message.
   *
   * @param message the error message
   */
  public RateLimitException(final String message) {
    super(message);
  }

  /**
   * Constructor with rate limit details.
   *
   * @param limit      the rate limit
   * @param window     the time window
   * @param retryAfter seconds until retry is allowed
   */
  public RateLimitException(final int limit, final String window,
      final int retryAfter) {
    super("Rate limit of " + limit + " requests per "
        + window + " exceeded. "
        + "Try again in " + retryAfter + " seconds.");
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
    return HttpStatus.TOO_MANY_REQUESTS;
  }
}
