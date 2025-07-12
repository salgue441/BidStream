package com.bidstream.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating a new auction.
 * Contains all required and optional fields for auction creation with
 * validation.
 */
public class CreateAuctionRequest {

  /** Maximum title length matching entity constraint. */
  private static final int MAX_TITLE_LENGTH = 200;

  /** Maximum description length matching entity constraint. */
  private static final int MAX_DESCRIPTION_LENGTH = 2000;

  /** Maximum category length matching entity constraint. */
  private static final int MAX_CATEGORY_LENGTH = 100;

  /** Maximum condition length matching entity constraint. */
  private static final int MAX_CONDITION_LENGTH = 50;

  /** Maximum location length matching entity constraint. */
  private static final int MAX_LOCATION_LENGTH = 200;

  /** Minimum price value for validation. */
  private static final String MIN_PRICE_VALUE = "0.01";

  /**
   * Auction title. Required field with length validation.
   */
  @NotBlank(message = "Auction title is required")
  @Size(max = MAX_TITLE_LENGTH,
      message = "Title cannot exceed "
      + MAX_TITLE_LENGTH + " characters")
  private String title;

  /**
   * Detailed description of the auction item. Optional field.
   */
  @Size(max = MAX_DESCRIPTION_LENGTH,
      message = "Description cannot exceed "
      + MAX_DESCRIPTION_LENGTH + " characters")
  private String description;

  /**
   * Item category for classification. Required field.
   */
  @NotBlank(message = "Category is required")
  @Size(max = MAX_CATEGORY_LENGTH,
      message = "Category cannot exceed "
      + MAX_CATEGORY_LENGTH + " characters")
  private String category;

  /**
   * Physical condition of the item. Optional field.
   */
  @Size(max = MAX_CONDITION_LENGTH,
      message = "Condition cannot exceed "
      + MAX_CONDITION_LENGTH + " characters")
  private String condition;

  /**
   * Geographic location of the item. Optional field.
   */
  @Size(max = MAX_LOCATION_LENGTH,
      message = "Location cannot exceed "
      + MAX_LOCATION_LENGTH + " characters")
  private String location;

  /**
   * Starting bid amount. Required field with minimum value validation.
   */
  @NotNull(message = "Starting price is required")
  @DecimalMin(value = MIN_PRICE_VALUE,
      message = "Starting price must be at least $0.01")
  private BigDecimal startingPrice;

  /**
   * Reserve price - minimum amount for successful sale. Optional field.
   */
  @DecimalMin(value = MIN_PRICE_VALUE,
      message = "Reserve price must be at least $0.01")
  private BigDecimal reservePrice;

  /**
   * Buy Now price for immediate purchase. Optional field.
   */
  @DecimalMin(value = MIN_PRICE_VALUE,
      message = "Buy Now price must be at least $0.01")
  private BigDecimal buyNowPrice;

  /**
   * When the auction bidding starts. Must be in the future.
   */
  @NotNull(message = "Auction start time is required")
  @Future(message = "Auction start time must be in the future")
  private LocalDateTime startTime;

  /**
   * When the auction bidding ends. Must be after start time.
   */
  @NotNull(message = "Auction end time is required")
  private LocalDateTime endTime;

  /**
   * Whether the auction should be featured. Optional field.
   */
  private Boolean featured;

  /**
   * Default constructor.
   */
  public CreateAuctionRequest() {
  }

  // Getters and Setters

  /**
   * Gets the auction title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the auction title.
   *
   * @param newTitle the title to set
   */
  public void setTitle(final String newTitle) {
    this.title = newTitle;
  }

  /**
   * Gets the auction description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the auction description.
   *
   * @param newDescription the description to set
   */
  public void setDescription(final String newDescription) {
    this.description = newDescription;
  }

  /**
   * Gets the item category.
   *
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets the item category.
   *
   * @param newCategory the category to set
   */
  public void setCategory(final String newCategory) {
    this.category = newCategory;
  }

  /**
   * Gets the item condition.
   *
   * @return the condition
   */
  public String getCondition() {
    return condition;
  }

  /**
   * Sets the item condition.
   *
   * @param newCondition the condition to set
   */
  public void setCondition(final String newCondition) {
    this.condition = newCondition;
  }

  /**
   * Gets the item location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets the item location.
   *
   * @param newLocation the location to set
   */
  public void setLocation(final String newLocation) {
    this.location = newLocation;
  }

  /**
   * Gets the starting price.
   *
   * @return the starting price
   */
  public BigDecimal getStartingPrice() {
    return startingPrice;
  }

  /**
   * Sets the starting price.
   *
   * @param newStartingPrice the starting price to set
   */
  public void setStartingPrice(final BigDecimal newStartingPrice) {
    this.startingPrice = newStartingPrice;
  }

  /**
   * Gets the reserve price.
   *
   * @return the reserve price
   */
  public BigDecimal getReservePrice() {
    return reservePrice;
  }

  /**
   * Sets the reserve price.
   *
   * @param newReservePrice the reserve price to set
   */
  public void setReservePrice(final BigDecimal newReservePrice) {
    this.reservePrice = newReservePrice;
  }

  /**
   * Gets the Buy Now price.
   *
   * @return the Buy Now price
   */
  public BigDecimal getBuyNowPrice() {
    return buyNowPrice;
  }

  /**
   * Sets the Buy Now price.
   *
   * @param newBuyNowPrice the Buy Now price to set
   */
  public void setBuyNowPrice(final BigDecimal newBuyNowPrice) {
    this.buyNowPrice = newBuyNowPrice;
  }

  /**
   * Gets the auction start time.
   *
   * @return the start time
   */
  public LocalDateTime getStartTime() {
    return startTime;
  }

  /**
   * Sets the auction start time.
   *
   * @param newStartTime the start time to set
   */
  public void setStartTime(final LocalDateTime newStartTime) {
    this.startTime = newStartTime;
  }

  /**
   * Gets the auction end time.
   *
   * @return the end time
   */
  public LocalDateTime getEndTime() {
    return endTime;
  }

  /**
   * Sets the auction end time.
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

  /**
   * Returns string representation of the request.
   *
   * @return string with key request details
   */
  @Override
  public String toString() {
    return "CreateAuctionRequest{"
        + "title='" + title + '\''
        + ", category='" + category + '\''
        + ", startingPrice=" + startingPrice
        + ", startTime=" + startTime
        + ", endTime=" + endTime
        + '}';
  }
}
