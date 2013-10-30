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

public class EmptyMap implements Mappable {

  @Override
public void load(Access access) throws MappableException, UserException {

  }

  @Override
public void store() throws MappableException, UserException {

  }

  @Override
public void kill() {

  }
}
