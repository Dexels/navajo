package com.dexels.navajo.client;

import com.dexels.navajo.document.*;

/**
 * The ServerAsyncListener interface is used by the ServerAsyncRunner. It provides the interface through which the Runner
* can pass on it's results to any implenting classes of this interface.
 */

public interface ServerAsyncListener {
  /**
   * At startup this function is called with the name of the started webservice
   * @param id String
   */
  public void serviceStarted(String id);

  /**
   * After each poll this method is called to inform the listener of the server's progress.
   * Progress d is a percentage (0-100).
   * @param id String
   * @param d int
   */
  public void setProgress(String id, int d);

  /**
   * Called when the server has finished the webservice, the result of webservice [method] is in Navajo [n]
   * @param n Navajo
   * @param method String
   * @param serverId String
   * @param clientId String
   */
  public void receiveServerAsync(Navajo n, String method, String serverId, String clientId);

  /**
   * If a serverside exception has occured, the listener is informed through this method, passing on the original Exception object
   * @param e Exception
   */
  public void handleException(Exception e);
}
