package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class MultipleEmptyMap implements Mappable {

  public void load(Access access) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }
  
  public void setLoop(com.dexels.navajo.adapter.EmptyMap [] emptyMaps) {
	  this.emptyMaps = emptyMaps;
  }

  public void setEmptyMaps(com.dexels.navajo.adapter.EmptyMap [] emptyMaps) {
    this.emptyMaps = emptyMaps;
  }

  public com.dexels.navajo.adapter.EmptyMap [] emptyMaps;
}
