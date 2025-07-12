package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found.
 * Typically results in HTTP 404 response.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BidStreamException {

  /** Default error code for resource not found. */
  private static final String DEFAULT_ERROR_CODE = "RESOURCE_NOT_FOUND";

  /**
   * Constructor with message only.
   *
   * @param message with message only
   */
  public ResourceNotFoundException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and cause.
   *
   * @param message the error message
   * @param cause   the underlying cause
   */
  public ResourceNotFoundException(final String message,
      final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for specific resource type and ID.
   *
   * @param resourceType the type of resource (e.g., "User", "Auction")
   * @param resourceId   the ID of the resource
   */
  public ResourceNotFoundException(final String resourceType,
      final Object resourceId) {
    super(resourceType + " not found with ID: " + resourceId);
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
    return HttpStatus.NOT_FOUND;
  }
}
