package com.dexels.navajo.server;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class AuthorizationException extends Exception {

  public static String AUTHORIZATION_ERROR_MESSAGE = "AuthorizationError";
  public static String AUTHENTICATION_ERROR_MESSAGE = "AuthenticationError";

  private String message;
  private String user;
  private boolean authenticationError;
  private boolean authorizationError;

  public AuthorizationException(boolean authenticationError, boolean authorizationError, String user, String message) {
    this.message = message;
    this.user = user;
    this.authenticationError = authenticationError;
    this.authorizationError = authorizationError;
  }

  public String getMessage() {
    return this.message;
  }

  public String getUser() {
    return this.user;
  }

  public boolean isNotAuthenticated() {
    return this.authenticationError;
  }

  public boolean isNotAuthorized() {
    return this.authorizationError;
  }
}