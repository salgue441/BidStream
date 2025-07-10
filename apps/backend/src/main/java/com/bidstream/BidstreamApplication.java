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
 * Main Spring Boot application class that bootstraps the entire application.
 * 
 * Features enabled:
 * - JPA Auditing for automatic createdAt/updatedAt timestamps
 * - Caching for improved performance
 * - Async processing for non-blocking operations
 * - Scheduling for auction management tasks
 * - Transaction management for data consistency
 * 
 * @author Carlos Salguero
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class BidstreamApplication {
  public static void main(String[] args) {
    SpringApplication.run(BidstreamApplication.class, args);
  }
}