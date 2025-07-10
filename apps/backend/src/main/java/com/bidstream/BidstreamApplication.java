package com.bidstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * BidStream - Real-Time Trading & Auction Platform
 *
 * <p>
 * Main Spring Boot application class that bootstraps the entire application.
 * </p>
 *
 * <p>
 * Features enabled:
 * </p>
 *
 * <ul>
 * <li>JPA Auditing for automatic createdAt/updatedAt timestamps.</li>
 * <li>Caching for improved performance</li>
 * <li>Async processing for non-blocking operations</li>
 * <li>Scheduling for auction management tasks</li>
 * <li>Transaction management for data consistency</li>
 * </ul>
 *
 * @author Carlos Salguero
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public final class BidstreamApplication {

  /**
   * Private constructor to prevent instantiation.
   */
  private BidstreamApplication() {
    // Private constructor to hide implicit public one
  }

  /**
   * Main entry point for the Spring Boot application.
   *
   * @param args command line arguments passed to the application
   */
  public static void main(final String[] args) {
    SpringApplication.run(BidstreamApplication.class, args);
  }
}
