package com.dexels.navajo.adapter;


import java.io.File;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.UserException;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class NavajoClearCache implements Mappable {

  private NavajoConfigInterface config;

  public NavajoClearCache() {
  }

  @Override
public void load(Access access) throws MappableException, UserException {
      this.config = DispatcherFactory.getInstance().getNavajoConfig();
  }

  @Override
public void store() throws MappableException, UserException {
      String cachePath = config.getRootPath()+"/cache";
      File path = new File(cachePath);
      File [] allFile = path.listFiles();
      for (int i = 0; i < allFile.length; i++) {
         allFile[i].delete();
      }
  }

  @Override
public void kill() {

  }
}
