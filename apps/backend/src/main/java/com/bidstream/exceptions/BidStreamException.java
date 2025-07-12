package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Base exception class for all BidStream business exceptions.
 * Provides common functionality for error handling and logging.
 */
public abstract class BidStreamException extends RuntimeException {

  /** Error code for categorizing exceptions. */
  private final String errorCode;

  /** HTTP status code to return. */
  private final HttpStatus httpStatus;

  /** Additional context data for the exception. */
  private Object context;

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  protected BidStreamException(final String message) {
    super(message);

    this.errorCode = getDefaultErrorCode();
    this.httpStatus = getDefaultHttpStatus();
  }

  /**
   * Constructor with message and cause.
   *
   * @param message the error message
   * @param cause   the underlying cause
   */
  protected BidStreamException(final String message, final Throwable cause) {
    super(message, cause);
    this.errorCode = getDefaultErrorCode();
    this.httpStatus = getDefaultHttpStatus();
  }

  /**
   * Constructor with message and error code.
   *
   * @param message      the error message
   * @param newErrorCode the specific error code
   */
  protected BidStreamException(final String message,
      final String newErrorCode) {
    super(message);
    this.errorCode = newErrorCode;
    this.httpStatus = getDefaultHttpStatus();
  }

  /**
   * Constructor with message, error code, and HTTP status.
   *
   * @param message      the error message
   * @param newErrorCode the specific error code
   * @param newHttpStatus   the HTTP status to return
   */
  protected BidStreamException(final String message,
      final String newErrorCode, final HttpStatus newHttpStatus) {
    super(message);
    this.errorCode = newErrorCode;
    this.httpStatus = newHttpStatus;
  }

  /**
   * Gets the error code for this exception.
   *
   * @return the error code
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Gets the HTTP status for this exception.
   *
   * @return the HTTP status
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  /**
   * Gets additional context data.
   *
   * @return the context object
   */
  public Object getContext() {
    return context;
  }

  /**
   * Sets additional context data.
   *
   * @param contextData the context to set
   * @return this exception for method chaining
   */
  public BidStreamException withContext(final Object contextData) {
    this.context = contextData;
    return this;
  }

  /**
   * Gets the default error code for this exception type.
   * Subclasses should override this method.
   *
   * @return the default error code
   */
  protected abstract String getDefaultErrorCode();

  /**
   * Gets the default HTTP status for this exception type.
   * Subclasses should override this method.
   *
   * @return the default HTTP status
   */
  protected abstract HttpStatus getDefaultHttpStatus();
}
