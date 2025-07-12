package com.bidstream.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown for payment processing errors.
 */
@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PaymentException extends BidStreamException {

  /** Default error code for payment failures. */
  private static final String DEFAULT_ERROR_CODE = "PAYMENT_FAILED";

  /** Error code for insufficient funds. */
  public static final String INSUFFICIENT_FUNDS = "INSUFFICIENT_FUNDS";

  /** Error code for payment method invalid. */
  public static final String INVALID_PAYMENT_METHOD = "INVALID_PAYMENT_METHOD";

  /** Error code for payment processing error. */
  public static final String PAYMENT_PROCESSING_ERROR =
    "PAYMENT_PROCESSING_ERROR";

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  public PaymentException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and specific error code.
   *
   * @param message   the error message
   * @param errorCode the specific payment error code
   */
  public PaymentException(final String message, final String errorCode) {
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
    return HttpStatus.PAYMENT_REQUIRED;
  }
}
