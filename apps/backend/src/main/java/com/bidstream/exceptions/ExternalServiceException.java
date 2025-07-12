package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown for external service failures.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ExternalServiceException extends BidStreamException {

  /** Default error code for external service failures. */
  private static final String DEFAULT_ERROR_CODE =
    "EXTERNAL_SERVICE_UNAVAILABLE";

  /**
   * Constructor with service name and message.
   *
   * @param serviceName the name of the external service
   * @param message     the error message
   */
  public ExternalServiceException(final String serviceName,
      final String message) {
    super("External service '" + serviceName + "' error: " + message);
  }

  /**
   * Constructor with service name, message, and cause.
   *
   * @param serviceName the name of the external service
   * @param message     the error message
   * @param cause       the underlying cause
   */
  public ExternalServiceException(final String serviceName,
      final String message, final Throwable cause) {
    super("External service '" + serviceName + "' error: " + message, cause);
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
    return HttpStatus.SERVICE_UNAVAILABLE;
  }
}
