package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.util.ArrayList;

public interface Method
    extends java.io.Serializable {
  /**
   * Add a required message to a method using a Message object.
   * @param message Message
   */
  public void addRequired(Message message);

  /**
   * Add a required message to a method using a message name.
   * @Param message String
   */
  public void addRequired(String message);

  /**
   * Add a required message to a method using a message name.
   * @param message String
   * @param filter 
   */
  public void addRequired(String message, String filter);
  
  /**
   * Return the name of the method.
   * @return String name
   */
  public String getName();

  /**
   * Set the name of the method.
   * @param name String
   */
  public void setName(String name);

  /**
   * Set the description of the method.
   * @param d String
   */
  public void setDescription(String d);

  /**
   * Get the description of the method.
   * @return String description
   */
  public String getDescription();

  /**
   * Return the server of the method (URI).
   * @return String serverURI
   */
  public String getServer();

  /**
   * Set the server (URI) of the method.
   * @param server String
   */
  public void setServer(String server);

  /**
   * Return a list of required message names.
   * @return ArrayList messagenames
   */
  public ArrayList getRequiredMessages();

  /**
   * Return the internal implementation specific representation of the Method.
   *
   * @return Object
   */
  public Object getRef();
  public Method copy(Navajo n);

}
