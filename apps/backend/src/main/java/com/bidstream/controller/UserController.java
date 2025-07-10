package com.bidstream.controller;

import com.bidstream.model.entity.User;
import com.bidstream.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * REST Controller for User management.
 * Provides basic CRUD operations for users.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "User Management",
    description = "API for managing users in BidStream platform")
@CrossOrigin(origins = "*")
public class UserController {

  /** Minimum username length constant. */
  private static final int MIN_USERNAME_LENGTH = 3;

  /** Maximum username length constant. */
  private static final int MAX_USERNAME_LENGTH = 50;

  /** Minimum password length constant. */
  private static final int MIN_PASSWORD_LENGTH = 6;

  /** Repository for user data operations. */
  @Autowired
  private UserRepository userRepository;

  /** Password encoder for securing user credentials. */
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Retrieves all registered users.
   *
   * @return ResponseEntity containing list of all users
   */
  @GetMapping
  @Operation(summary = "Get all users",
      description = "Retrieve a list of all registered users")
  @ApiResponse(responseCode = "200",
      description = "Successfully retrieved users")
  public final ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }

  /**
   * Retrieves a specific user by their unique identifier.
   *
   * @param id the UUID of the user to retrieve
   * @return ResponseEntity containing the user if found
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID",
      description = "Retrieve a specific user by their UUID")
  @ApiResponse(responseCode = "200",
      description = "User found")
  @ApiResponse(responseCode = "404",
      description = "User not found")
  public final ResponseEntity<User> getUserById(
      @Parameter(description = "User UUID") @PathVariable final UUID id) {
    Optional<User> user = userRepository.findById(id);
    return user.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Creates a new user in the platform.
   *
   * @param request the user creation request
   * @return ResponseEntity with created user or error message
   */
  @PostMapping
  @Operation(summary = "Create new user",
      description = "Register a new user in the platform")
  @ApiResponse(responseCode = "201",
      description = "User created successfully")
  @ApiResponse(responseCode = "400",
      description = "Invalid user data")
  @ApiResponse(responseCode = "409",
      description = "User already exists")
  public final ResponseEntity<?> createUser(
      @Valid @RequestBody final CreateUserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Map.of("error", "Email already exists"));
    }

    if (userRepository.existsByUsername(request.getUsername())) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Map.of("error", "Username already exists"));
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setUsername(request.getUsername());
    user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setPhone(request.getPhone());

    User savedUser = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
  }

  /**
   * Searches for a user by email or username.
   *
   * @param identifier the email or username to search for
   * @return ResponseEntity containing the user if found
   */
  @GetMapping("/search")
  @Operation(summary = "Search user",
      description = "Find user by email or username")
  @ApiResponse(responseCode = "200",
      description = "User found")
  @ApiResponse(responseCode = "404",
      description = "User not found")
  public final ResponseEntity<User> findUser(
      @Parameter(description = "Email or username")
      @RequestParam final String identifier) {
    Optional<User> user = userRepository.findByEmailOrUsername(identifier);
    return user.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves platform user statistics.
   *
   * @return ResponseEntity containing user statistics
   */
  @GetMapping("/stats")
  @Operation(summary = "Get user statistics",
      description = "Retrieve platform user statistics")
  @ApiResponse(responseCode = "200",
      description = "Statistics retrieved successfully")
  public final ResponseEntity<Map<String, Object>> getUserStats() {
    Map<String, Object> stats = new HashMap<>();
    stats.put("totalUsers", userRepository.countTotalUsers());
    stats.put("verifiedUsers", userRepository.countVerifiedUsers());
    stats.put("timestamp", LocalDateTime.now());

    return ResponseEntity.ok(stats);
  }

  /**
   * Inner class representing the user creation request.
   */
  public static final class CreateUserRequest {
    /**
     * User's email address. Must be a valid email format and
     * cannot be blank.
     *
     * @see jakarta.validation.constraints.Email
     * @see jakarta.validation.constraints.NotBlank
     */
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.NotBlank
    private String email;

    /**
     * Unique username for the user. Must be between 3 and 50 characters long
     * and cannot be blank.
     *
     * @see #MIN_USERNAME_LENGTH Minimum username length (3 characters)
     * @see #MAX_USERNAME_LENGTH Maximum username length (50 characters)
     * @see jakarta.validation.constraints.NotBlank
     * @see jakarta.validation.constraints.Size
     */
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = MIN_USERNAME_LENGTH,
        max = MAX_USERNAME_LENGTH)
    private String username;

    /**
     * User's password. Must be at least 6 characters long and cannot be blank.
     *
     * @see #MIN_PASSWORD_LENGTH Minimum password length (6 characters)
     * @see jakarta.validation.constraints.NotBlank
     * @see jakarta.validation.constraints.Size
     */
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = MIN_PASSWORD_LENGTH)
    private String password;

    /**
     * User's first name. Cannot be blank.
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @jakarta.validation.constraints.NotBlank
    private String firstName;

    /**
     * User's last name. Cannot be blank.
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @jakarta.validation.constraints.NotBlank
    private String lastName;

    /**
     * User's phone number. Optional field with no format restrictions.
     *
     * <p>
     * Example formats:
     * <ul>
     * <li>123-456-7890</li>
     * <li>(123) 456-7890</li>
     * <li>+1 123.456.7890</li>
     * </ul>
     */
    private String phone;

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmail() {
      return email;
    }

    /**
     * Sets the email address.
     *
     * @param emailParam the email address to set
     */
    public void setEmail(final String emailParam) {
      this.email = emailParam;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
      return username;
    }

    /**
     * Sets the username.
     *
     * @param usernameParam the username to set
     */
    public void setUsername(final String usernameParam) {
      this.username = usernameParam;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
      return password;
    }

    /**
     * Sets the password.
     *
     * @param passwordParam the password to set
     */
    public void setPassword(final String passwordParam) {
      this.password = passwordParam;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
      return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstNameParam the first name to set
     */
    public void setFirstName(final String firstNameParam) {
      this.firstName = firstNameParam;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
      return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastNameParam the last name to set
     */
    public void setLastName(final String lastNameParam) {
      this.lastName = lastNameParam;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
      return phone;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneParam the phone number to set
     */
    public void setPhone(final String phoneParam) {
      this.phone = phoneParam;
    }
  }
}
