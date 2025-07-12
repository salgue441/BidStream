package com.bidstream.model.entity;

/**
 * Enumeration representing the various states an auction can be in.
 * Controls auction behavior and availability for biding.
 */
public enum AuctionStatus {
  /**
   * Auction is being created but not yet published.
   * Not visible to buyers and bidding is not allowed.
   */
  DRAFT,

  /**
   * Auction is scheduled but hasn't started yet.
   * Visible to buyers but bidding is not yet allowed.
   */
  SCHEDULED,

  /**
   * Auction is live and accepting bids.
   * Primary state for active auctions.
   */
  ACTIVE,

  /**
   * Auction has ended with successful sale.
   * Reserve price met and highest bidder wins.
   */
  COMPLETED,

  /**
   * Auction has ended without sale.
   * Either no bids or reserve price not met.
   */
  ENDED_NO_SALE,

  /**
   * Auction was cancelled by seller or admin.
   * All bids are voided and item not sold.
   */
  CANCELLED,

  /**
   * Auction is under review or suspended.
   * Temporarily not available for bidding.
   */
  SUSPENDED
}
