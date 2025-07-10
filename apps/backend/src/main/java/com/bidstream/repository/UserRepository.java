package com.bidstream.repository;

import com.bidstream.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and custom queries for users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * Find user by email address.
   *
   * @param email the email address to search for
   * @return Optional containing the user if found
   */
  Optional<User> findByEmail(String email);

  /**
   * Find user by username.
   *
   * @param username the username to search for
   * @return Optional containing the user if found
   */
  Optional<User> findByUsername(String username);

  /**
   * Check if an email address exists in the database.
   *
   * @param email the email address to check
   * @return true if the email exists, false otherwise
   */
  boolean existsByEmail(String email);

  /**
   * Check if a username exists in the database.
   *
   * @param username the username to check
   * @return true if the username exists, false otherwise
   */
  boolean existsByUsername(String username);

  /**
   * Finds a user by either email or username (useful for login).
   *
   * @param identifier the email or username to search for
   * @return Optional containing the user if found
   */
  @Query("SELECT u FROM User u WHERE u.email = :identifier OR "
      + "u.username = :identifier")
  Optional<User> findByEmailOrUsername(@Param("identifier") String identifier);

  /**
   * Finds all verified users.
   *
   * @return list of all verified users
   */
  @Query("SELECT u FROM User u WHERE u.emailVerified = true")
  java.util.List<User> findAllVerifiedUsers();

  /**
   * Count the total number of users in the system.
   *
   * @return total user count
   */
  @Query("SELECT COUNT(u) FROM User u")
  long countTotalUsers();

  /**
   * Counts the number of verified users in the system.
   *
   * @return verified user count
   */
  @Query("SELECT COUNT(u) FROM User u WHERE u.emailVerified = true")
  long countVerifiedUsers();
}
