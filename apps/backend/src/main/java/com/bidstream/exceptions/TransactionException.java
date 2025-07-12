package com.bidstream.exceptions;

/**
 * Exception thrown for transaction-related errors.
 */
public class TransactionException extends BusinessException {

  /** Error code for transaction not found. */
  public static final String TRANSACTION_NOT_FOUND = "TRANSACTION_NOT_FOUND";

  /** Error code for invalid transaction state. */
  public static final String INVALID_TRANSACTION_STATE =
    "INVALID_TRANSACTION_STATE";

  /** Error code for transaction already processed. */
  public static final String TRANSACTION_ALREADY_PROCESSED =
    "TRANSACTION_ALREADY_PROCESSED";

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  public TransactionException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and specific error code.
   *
   * @param message   the error message
   * @param errorCode the specific transaction error code
   */
  public TransactionException(final String message, final String errorCode) {
    super(message, errorCode);
  }
}
