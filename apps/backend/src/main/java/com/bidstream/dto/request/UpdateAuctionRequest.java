package com.bidstream.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for updating an existing auction.
 * Contains optional fields that can be modified after auction creation.
 */
class UpdateAuctionRequest {

  /** Maximum title length matching entity constraint. */
  private static final int MAX_TITLE_LENGTH = 200;

  /** Maximum description length matching entity constraint. */
  private static final int MAX_DESCRIPTION_LENGTH = 2000;

  /** Maximum condition length matching entity constraint. */
  private static final int MAX_CONDITION_LENGTH = 50;

  /** Maximum location length matching entity constraint. */
  private static final int MAX_LOCATION_LENGTH = 200;

  /** Minimum price value for validation. */
  private static final String MIN_PRICE_VALUE = "0.01";

  /**
   * Updated auction title. Optional field.
   */
  @Size(max = MAX_TITLE_LENGTH,
      message = "Title cannot exceed "
      + MAX_TITLE_LENGTH + " characters")
  private String title;

  /**
   * Updated description. Optional field.
   */
  @Size(max = MAX_DESCRIPTION_LENGTH,
      message = "Description cannot exceed "
      + MAX_DESCRIPTION_LENGTH + " characters")
  private String description;

  /**
   * Updated item condition. Optional field.
   */
  @Size(max = MAX_CONDITION_LENGTH,
      message = "Condition cannot exceed "
      + MAX_CONDITION_LENGTH + " characters")
  private String condition;

  /**
   * Updated location. Optional field.
   */
  @Size(max = MAX_LOCATION_LENGTH,
      message = "Location cannot exceed "
      + MAX_LOCATION_LENGTH + " characters")
  private String location;

  /**
   * Updated starting price. Optional field with restrictions.
   * Note: Cannot be changed if auction has bids.
   */
  @DecimalMin(value = MIN_PRICE_VALUE,
      message = "Starting price must be at least $0.01")
  private BigDecimal startingPrice;

  /**
   * Updated reserve price. Optional field.
   */
  @DecimalMin(value = MIN_PRICE_VALUE,
      message = "Reserve price must be at least $0.01")
  private BigDecimal reservePrice;

  /**
   * Updated Buy Now price. Optional field.
   */
  @DecimalMin(value = MIN_PRICE_VALUE,
      message = "Buy Now price must be at least $0.01")
  private BigDecimal buyNowPrice;

  /**
   * Updated end time. Optional field with restrictions.
   * Note: Cannot be shortened if auction has bids.
   */
  private LocalDateTime endTime;

  /**
   * Updated featured status. Optional field.
   */
  private Boolean featured;

  // Getters and Setters

  /**
   * Gets the updated title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the updated title.
   *
   * @param newTitle the title to set
   */
  public void setTitle(final String newTitle) {
    this.title = newTitle;
  }

  /**
   * Gets the updated description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the updated description.
   *
   * @param newDescription the description to set
   */
  public void setDescription(final String newDescription) {
    this.description = newDescription;
  }

  /**
   * Gets the updated condition.
   *
   * @return the condition
   */
  public String getCondition() {
    return condition;
  }

  /**
   * Sets the updated condition.
   *
   * @param newCondition the condition to set
   */
  public void setCondition(final String newCondition) {
    this.condition = newCondition;
  }

  /**
   * Gets the updated location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets the updated location.
   *
   * @param newLocation the location to set
   */
  public void setLocation(final String newLocation) {
    this.location = newLocation;
  }

  /**
   * Gets the updated starting price.
   *
   * @return the starting price
   */
  public BigDecimal getStartingPrice() {
    return startingPrice;
  }

  /**
   * Sets the updated starting price.
   *
   * @param newStartingPrice the starting price to set
   */
  public void setStartingPrice(final BigDecimal newStartingPrice) {
    this.startingPrice = newStartingPrice;
  }

  /**
   * Gets the updated reserve price.
   *
   * @return the reserve price
   */
  public BigDecimal getReservePrice() {
    return reservePrice;
  }

  /**
   * Sets the updated reserve price.
   *
   * @param newReservePrice the reserve price to set
   */
  public void setReservePrice(final BigDecimal newReservePrice) {
    this.reservePrice = newReservePrice;
  }

  /**
   * Gets the updated Buy Now price.
   *
   * @return the Buy Now price
   */
  public BigDecimal getBuyNowPrice() {
    return buyNowPrice;
  }

  /**
   * Sets the updated Buy Now price.
   *
   * @param newBuyNowPrice the Buy Now price to set
   */
  public void setBuyNowPrice(final BigDecimal newBuyNowPrice) {
    this.buyNowPrice = newBuyNowPrice;
  }

  /**
   * Gets the updated end time.
   *
   * @return the end time
   */
  public LocalDateTime getEndTime() {
    return endTime;
  }

  /**
   * Sets the updated end time.
   *
   * @param newEndTime the end time to set
   */
  public void setEndTime(final LocalDateTime newEndTime) {
    this.endTime = newEndTime;
  }

  /**
   * Checks if the auction should be featured.
   *
   * @return true if featured
   */
  public Boolean isFeatured() {
    return featured;
  }

  /**
   * Sets the featured status.
   *
   * @param isFeatured true to feature the auction
   */
  public void setFeatured(final Boolean isFeatured) {
    this.featured = isFeatured;
  }
}
