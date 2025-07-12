package com.bidstream.exceptions;

/**
 * Exception thrown for bidding-related errors.
 */
public class BiddingException extends BusinessException {

  /** Error code for bid too low. */
  public static final String BID_TOO_LOW = "BID_TOO_LOW";

  /** Error code for bid increment invalid. */
  public static final String INVALID_BID_INCREMENT = "INVALID_BID_INCREMENT";

  /** Error code for self-bidding attempt. */
  public static final String SELF_BIDDING_NOT_ALLOWED =
   "SELF_BIDDING_NOT_ALLOWED";

  /** Error code for bidding on ended auction. */
  public static final String BIDDING_ENDED = "BIDDING_ENDED";

  /** Error code for duplicate bid. */
  public static final String DUPLICATE_BID = "DUPLICATE_BID";

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  public BiddingException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and specific error code.
   *
   * @param message   the error message
   * @param errorCode the specific bidding error code
   */
  public BiddingException(final String message, final String errorCode) {
    super(message, errorCode);
  }
}
