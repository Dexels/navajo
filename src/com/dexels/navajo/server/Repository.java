package com.dexels.navajo.server;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

/**
 * This interface describes the methods that are required for a Repository implementation.
 */

public interface Repository {

  public void setResourceBundle(java.util.ResourceBundle b);

  /**
   * Authorize a Navajo user based on username, password and service name. An Access object is returned that can
   * be used in subsequent calls to the other Repository methods.
   */
  public Access authorizeUser(String username, String password, String service) throws SystemException;

  /**
   * Retrieve user/service specific condition rules from the repository. The conditions are returned in an array
   * of ConditionData.
   */
  public ConditionData [] getConditions(Access access) throws SystemException, UserException;

  /**
   * Retrieve user/service specific parameters from the repository. The parameters are returned in an array of
   * ParameterData.
   */
  public Parameter [] getParameters(Access access) throws SystemException;

  /**
   * This method is used to log timing information.
   */
  public void logTiming(Access access, int part, long timespent) throws SystemException;

  /**
   * This method is used to log certain actions.
   */
  public void logAction(Access access, int level, String comment)throws SystemException;

  /**
   * Get the dispatcher class from the repository using the Access object.
   */
  public String getServlet(Access access) throws SystemException;


  /**
   * Return all (registered) Navajo services.
   */
  public String [] getServices(Access access) throws SystemException;

}