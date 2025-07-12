package com.bidstream.exceptions;

/**
 * Exception thrown for auction-related business rule violations.
 */
public class AuctionException extends BusinessException {

  /** Error code for auction not found. */
  public static final String AUCTION_NOT_FOUND = "AUCTION_NOT_FOUND";

  /** Error code for auction not active. */
  public static final String AUCTION_NOT_ACTIVE = "AUCTION_NOT_ACTIVE";

  /** Error code for auction already ended. */
  public static final String AUCTION_ENDED = "AUCTION_ENDED";

  /** Error code for invalid auction status. */
  public static final String INVALID_AUCTION_STATUS =
    "INVALID_AUCTION_STATUS";

  /** Error code for auction modification not allowed. */
  public static final String AUCTION_MODIFICATION_NOT_ALLOWED =
    "AUCTION_MODIFICATION_NOT_ALLOWED";

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  public AuctionException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and specific error code.
   *
   * @param message   the error message
   * @param errorCode the specific auction error code
   */
  public AuctionException(final String message, final String errorCode) {
    super(message, errorCode);
  }
}
