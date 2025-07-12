package com.bidstream.exceptions;

/**
 * Exception thrown for user account related issues.
 */
public class UserException extends BusinessException {

  /** Error code for user not found. */
  public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

  /** Error code for user already exists. */
  public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";

  /** Error code for account locked. */
  public static final String ACCOUNT_LOCKED = "ACCOUNT_LOCKED";

  /** Error code for email not verified. */
  public static final String EMAIL_NOT_VERIFIED = "EMAIL_NOT_VERIFIED";

  /** Error code for invalid credentials. */
  public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";

  /**
   * Constructor with message only.
   *
   * @param message the error message
   */
  public UserException(final String message) {
    super(message);
  }

  /**
   * Constructor with message and specific error code.
   *
   * @param message   the error message
   * @param errorCode the specific user error code
   */
  public UserException(final String message, final String errorCode) {
    super(message, errorCode);
  }
}
