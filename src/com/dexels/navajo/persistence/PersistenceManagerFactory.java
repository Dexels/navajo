package com.dexels.navajo.persistence;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class PersistenceManagerFactory {

  public static PersistenceManager getInstance(String configurationFile) {
    return new com.dexels.navajo.persistence.impl.PersistenceManagerImpl(configurationFile);
  }
}