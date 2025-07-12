package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown for configuration errors.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConfigurationException extends BidStreamException {

  /** Default error code for contfiguration errores. */
  private static final String DEFAULT_ERROR_CODE = "CONFIGURATION_ERROR";

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  public ConfigurationException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and cause.
   *
   * @param message the error message
   * @param cause   the underlying cause
   */
  public ConfigurationException(final String message, final Throwable cause) {
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
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
