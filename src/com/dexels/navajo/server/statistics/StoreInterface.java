package com.dexels.navajo.server.statistics;

import com.dexels.navajo.server.Access;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface StoreInterface {

  /**
   * Method to store an access object in the (persistent) Navajo store.
   *
   * @param a
   */
  public void storeAccess(Access a);

}