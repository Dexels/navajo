package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;

import java.io.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class NavajoClearCache implements Mappable {

  private NavajoConfig config;

  public NavajoClearCache() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
      this.config = config;
  }

  public void store() throws MappableException, UserException {
      String cachePath = config.getRootPath()+"/cache";
      File path = new File(cachePath);
      File [] allFile = path.listFiles();
      for (int i = 0; i < allFile.length; i++) {
         allFile[i].delete();
      }
  }

  public void kill() {

  }
}
