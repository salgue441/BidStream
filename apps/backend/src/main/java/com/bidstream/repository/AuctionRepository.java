package com.bidstream.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bidstream.model.entity.Auction;
import com.bidstream.model.entity.AuctionStatus;
import com.bidstream.model.entity.User;

/**
 * Repository interface for Auction entity data access operations.
 * Extends JpaRepository to provide standard CRUD operations and includes
 * custom query methods for auction-specific business logic.
 */
@Repository
public interface AuctionRepository extends JpaRepository<Auction, UUID> {
       /**
        * Finds all auctions by their current status.
        *
        * @param status the auction status to filter by
        * @return list of auctions with the specified status
        */
       List<Auction> findByStatus(AuctionStatus status);

       /**
        * Finds all active auctions that are currently accepting bids.
        * Filters by status and time window.
        *
        * @param now current timestamp for time comparison
        * @return list of active auctions withing biding window
        */
       @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' "
                     + "AND a.startTime <= :now AND a.endTime > :now "
                     + "ORDER BY a.endTime ASC")
       List<Auction> findActiveAuctions(@Param("now") LocalDateTime now);

       /**
        * Finds auctions ending soon (within specified time window).
        * Useful for urgent notifications and featured listings.
        *
        * @param now        current timestmap
        * @param cutoffTime time limit for "ending soon"
        * @return list of auctions ending within the time window
        */
       @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' "
                     + "AND a.endTime BETWEEN :now AND :cutoffTime "
                     + "ORDER BY a.endTime ASC")
       List<Auction> findAuctionsEndingSoon(
                     @Param("now") LocalDateTime now,
                     @Param("cutoffTime") LocalDateTime cutoffTime);

       /**
        * Finds all auctions created by a specific seller.
        *
        * @param seller the user who created the auction
        * @return list of auctions created by the seller, ordered by
        *         creation date.
        */
       @Query("SELECT a FROM Auction a WHERE a.seller = :seller "
                     + "ORDER BY a.createdAt DESC")
       List<Auction> findBySeller(@Param("seller") User seller);

       /**
        * Finds auctions by category with optional status filtering.
        *
        * @param category the auction category to filter by
        * @return list of auctions in the specified category
        */
       List<Auction> findByCategory(String category);

       /**
        * Finds auctions by category and status.
        *
        * @param category the auction category
        * @param status   the auction status
        * @return list of auctions matching both criteria
        */
       List<Auction> findByCategoryAndStatus(String category,
                     AuctionStatus status);

       /**
        * Finds featured auctions that are currently active. \
        * Used for homepage and promotional displays.
        *
        * @return list of featured active auctions ordered by end time
        */
       @Query("SELECT a FROM Auction a WHERE a.featured = true "
                     + "AND a.status = 'ACTIVE' "
                     + "ORDER BY a.endTime ASC")
       List<Auction> findFeaturedActiveAuctions();

       /**
        * Finds auctions where a specific user is the highest bidder.
        *
        * @param bidder the user who is currently winning
        * @return list of auctions where user has highest bid
        */
       @Query("SELECT a FROM Auction a WHERE a.highestBidder = :bidder "
                     + "AND a.status IN ('ACTIVE', 'COMPLETED') "
                     + "ORDER BY a.endTime ASC")
       List<Auction> findByHighestBidder(@Param("bidder") User bidder);

       /**
        * Searches auctions by title and description content.
        * Case-insensitive search across multiple fields.
        *
        * @param searchTerm the text to search for
        * @param status     optional status filter
        * @return list of matching auctions ordered by relevance
        */
       @Query("SELECT a FROM Auction a WHERE "
                     + "(LOWER(a.title) LIKE "
                     + "LOWER(CONCAT('%', :searchTerm, '%')) "
                     + "OR LOWER(a.description) LIKE "
                     + "LOWER(CONCAT('%', :searchTerm, '%'))) "
                     + "AND (:status IS NULL OR a.status = :status) "
                     + "ORDER BY a.createdAt DESC")
       List<Auction> searchAuctions(@Param("searchTerm") String searchTerm,
                     @Param("status") AuctionStatus status);

       /**
        * Finds auctions within a price range.
        *
        * @param minPrice minimum current price
        * @param maxPrice maximum current price
        * @param status   optional status filter
        * @return list of auctions within price range
        */
       @Query("SELECT a FROM Auction a WHERE "
                     + "a.currentPrice BETWEEN :minPrice AND :maxPrice "
                     + "AND (:status IS NULL OR a.status = :status) "
                     + "ORDER BY a.currentPrice ASC")
       List<Auction> findByPriceRange(@Param("minPrice") BigDecimal minPrice,
                     @Param("maxPrice") BigDecimal maxPrice,
                     @Param("status") AuctionStatus status);

       /**
        * Finds auctions ending within a specific time range.
        *
        * @param startTime earliest end time
        * @param endTime   latest end time
        * @return list of auctions ending within the time range
        */
       @Query("SELECT a FROM Auction a WHERE a.endTime "
                     + "BETWEEN :startTime AND :endTime "
                     + "ORDER BY a.endTime ASC")
       List<Auction> findByEndTimeBetween(
                     @Param("startTime") LocalDateTime startTime,
                     @Param("endTime") LocalDateTime endTime);

       /**
        * Counts total number of auctions by status.
        *
        * @param status the auction status to count
        * @return number of auctions with the specified status
        */
       @Query("SELECT COUNT(a) FROM Auction a WHERE a.status = :status")
       Long countByStatus(@Param("status") AuctionStatus status);

       /**
        * Counts total number of active auctions.
        *
        * @return number of currently active auctions
        */
       @Query("SELECT COUNT(a) FROM Auction a WHERE a.status = 'ACTIVE'")
       Long countActiveAuctions();

       /**
        * Counts auctions created by a specific seller.
        *
        * @param seller the seller to count auctions for
        * @return number of auctions created by the seller
        */
       @Query("SELECT COUNT(a) FROM Auction a WHERE a.seller = :seller")
       Long countBySeller(@Param("seller") User seller);

       /**
        * Counts auctions in a specific category.
        *
        * @param category the category to count
        * @return number of auctions in the category
        */
       @Query("SELECT COUNT(a) FROM Auction a WHERE a.category = :category")
       Long countByCategory(@Param("category") String category);

       /**
        * Gets the total value of all active auctions (sum of current prices).
        *
        * @return total value of active auctions
        */
       @Query("SELECT COALESCE(SUM(a.currentPrice), 0) "
                     + "FROM Auction a WHERE a.status = 'ACTIVE'")
       BigDecimal getTotalActiveAuctionValue();

       /**
        * Gets the average current price for active auctions.
        *
        * @return average price of active auctions
        */
       @Query("SELECT AVG(a.currentPrice) FROM Auction a "
                     + "WHERE a.status = 'ACTIVE'")
       BigDecimal getAverageActiveAuctionPrice();

       /**
        * Finds the most popular categories by auction count.
        *
        * @param limit maximum number of categories to return
        * @return list of category names ordered by popularity
        */
       @Query("SELECT a.category FROM Auction a "
                     + "GROUP BY a.category ORDER BY COUNT(a) DESC "
                     + "LIMIT :limit")
       List<String> findMostPopularCategories(@Param("limit") int limit);

       /**
        * Updates the view count for a specific auction.
        * Increments the current view count by 1.
        *
        * @param auctionId the ID of the auction to update
        * @return number of affected rows (should be 1 if successful)
        */
       @Modifying
       @Transactional
       @Query("UPDATE Auction a SET a.viewCount = a.viewCount + 1 "
                     + "WHERE a.id = :auctionId")
       int incrementViewCount(@Param("auctionId") UUID auctionId);

       /**
        * Updates the watch count for a specific auction.
        *
        * @param auctionId the ID of the auction to update
        * @param increment the amount to add to watch count (can be negative)
        * @return number of affected rows (should be 1 if successful)
        */
       @Modifying
       @Transactional
       @Query("UPDATE Auction a SET a.watchCount = a.watchCount + :increment "
                     + "WHERE a.id = :auctionId")
       int updateWatchCount(@Param("auctionId") UUID auctionId,
                     @Param("increment") int increment);

       /**
        * Updates the current price and highest bidder for an auction.
        * Used when a new highest bid is placed.
        *
        * @param auctionId the ID of the auction to update
        * @param newPrice  the new current price
        * @param newBidder the new highest bidder
        * @param bidCount  the new total bid count
        * @return number of affected rows (should be 1 if successful)
        */
       @Modifying
       @Transactional
       @Query("UPDATE Auction a SET "
                     + "a.currentPrice = :newPrice, "
                     + "a.highestBidder = :newBidder, "
                     + "a.bidCount = :bidCount, "
                     + "a.updatedAt = CURRENT_TIMESTAMP "
                     + "WHERE a.id = :auctionId")
       int updateBidInfo(@Param("auctionId") UUID auctionId,
                     @Param("newPrice") BigDecimal newPrice,
                     @Param("newBidder") User newBidder,
                     @Param("bidCount") Integer bidCount);

       /**
        * Updates the reserve met status for an auction.
        *
        * @param auctionId  the ID of the auction to update
        * @param reserveMet whether the reserve price has been met
        * @return number of affected rows (should be 1 if successful)
        */
       @Modifying
       @Transactional
       @Query("UPDATE Auction a SET a.reserveMet = :reserveMet, "
                     + "a.updatedAt = CURRENT_TIMESTAMP "
                     + "WHERE a.id = :auctionId")
       int updateReserveStatus(@Param("auctionId") UUID auctionId,
                     @Param("reserveMet") Boolean reserveMet);

       /**
        * Updates auction status.
        *
        * @param auctionId the ID of the auction to update
        * @param status    the new status
        * @return number of affected rows (should be 1 if successful)
        */
       @Modifying
       @Transactional
       @Query("UPDATE Auction a SET a.status = :status, "
                     + "a.updatedAt = CURRENT_TIMESTAMP "
                     + "WHERE a.id = :auctionId")
       int updateStatus(@Param("auctionId") UUID auctionId,
                     @Param("status") AuctionStatus status);

       /**
        * Finds auctions that should be automatically ended.
        * Used by scheduled tasks to close expired auctions.
        *
        * @param now current timestamp
        * @return list of auctions that need to be ended
        */
       @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' "
                     + "AND a.endTime <= :now")
       List<Auction> findAuctionsToEnd(@Param("now") LocalDateTime now);

       /**
        * Finds auctions that should be automatically started.
        * Used by scheduled tasks to activate scheduled auctions.
        *
        * @param now current timestamp
        * @return list of auctions that need to be started
        */
       @Query("SELECT a FROM Auction a WHERE a.status = 'SCHEDULED' "
                     + "AND a.startTime <= :now")
       List<Auction> findAuctionsToStart(@Param("now") LocalDateTime now);

       /**
        * Batch updates auction statuses for expired auctions.
        *
        * @param now current timestamp
        * @return number of auctions updated
        */
       @Modifying
       @Transactional
       @Query("UPDATE Auction a SET a.status = "
                     + "CASE "
                     + "  WHEN a.bidCount = 0 THEN 'ENDED_NO_SALE' "
                     + "  WHEN a.reservePrice IS NULL OR "
                     + "a.reserveMet = true THEN 'COMPLETED' "
                     + "  ELSE 'ENDED_NO_SALE' "
                     + "END, "
                     + "a.updatedAt = CURRENT_TIMESTAMP "
                     + "WHERE a.status = 'ACTIVE' AND a.endTime <= :now")
       int batchEndExpiredAuctions(@Param("now") LocalDateTime now);

       /**
        * Batch activates scheduled auctions whose start time has arrived.
        *
        * @param now current timestamp
        * @return number of auctions activated
        */
       @Modifying
       @Transactional
       @Query("UPDATE Auction a SET a.status = 'ACTIVE', "
                     + "a.updatedAt = CURRENT_TIMESTAMP "
                     + "WHERE a.status = 'SCHEDULED' AND a.startTime <= :now")
       int batchActivateScheduledAuctions(@Param("now") LocalDateTime now);

       /**
        * Checks if a user has any active auctions.
        *
        * @param seller the user to check
        * @return true if user has active auctions
        */
       @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END "
                     + "FROM Auction a WHERE a.seller = :seller "
                     + "AND a.status = 'ACTIVE'")
       boolean hasActiveAuctions(@Param("seller") User seller);

       /**
        * Checks if an auction title already exists for a specific seller.
        * Useful for preventing duplicate auction titles.
        *
        * @param title  the auction title to check
        * @param seller the seller to check for
        * @return true if title exists for this seller
        */
       @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END "
                     + "FROM Auction a WHERE a.title = :title "
                     + "AND a.seller = :seller "
                     + "AND a.status NOT IN "
                     + "('COMPLETED', 'CANCELLED', 'ENDED_NO_SALE')")
       boolean existsByTitleAndSeller(@Param("title") String title,
                     @Param("seller") User seller);

       /**
        * Finds auctions ordered by popularity (combination of bids, views, and
        * watches).
        *
        * @param status optional status filter
        * @return list of auctions ordered by popularity score
        */
       @Query("SELECT a FROM Auction a WHERE "
                     + "(:status IS NULL OR a.status = :status) "
                     + "ORDER BY (a.bidCount * 3 + a.viewCount "
                     + "+ a.watchCount * 2) DESC")
       List<Auction> findByPopularity(@Param("status") AuctionStatus status);

       /**
        * Finds recently created auctions.
        *
        * @param limit maximum number of auctions to return
        * @return list of newest auctions
        */
       @Query("SELECT a FROM Auction a WHERE a.status "
                     + "IN ('ACTIVE', 'SCHEDULED') "
                     + "ORDER BY a.createdAt DESC "
                     + "LIMIT :limit")
       List<Auction> findRecentAuctions(@Param("limit") int limit);

       /**
        * Finds auctions ending soonest (next to close).
        *
        * @param limit maximum number of auctions to return
        * @return list of auctions ending soonest
        */
       @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' "
                     + "ORDER BY a.endTime ASC "
                     + "LIMIT :limit")
       List<Auction> findEndingSoonest(@Param("limit") int limit);

       /**
        * Finds auctions with the highest current prices.
        *
        * @param limit maximum number of auctions to return
        * @return list of highest priced auctions
        */
       @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' "
                     + "ORDER BY a.currentPrice DESC "
                     + "LIMIT :limit")
       List<Auction> findHighestPriced(@Param("limit") int limit);

       /**
        * Finds auctions with no bids yet (potential bargains).
        *
        * @return list of active auctions with no bids
        */
       @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' "
                     + "AND a.bidCount = 0 "
                     + "ORDER BY a.endTime ASC")
       List<Auction> findNoBidAuctions();
}
