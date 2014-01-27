package com.dexels.navajo.adapter;


import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class MultipleEmptyMap implements Mappable {

  @Override
public void load(Access access) throws MappableException, UserException {
  }

  @Override
public void store() throws MappableException, UserException {
  }

  @Override
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
