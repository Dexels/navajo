package com.dexels.navajo.persistence;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

 /**
  * This interface is used to reconstruct Persistent objects that have expired or are not yet persisted.
  * The Dispatcher should implement this interface.
  */

public interface Constructor {

  public Persistable construct() throws Exception;

}