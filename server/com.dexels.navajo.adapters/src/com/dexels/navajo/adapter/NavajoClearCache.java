/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;


import java.io.File;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @deprecated
 * @version 1.0
 */

@Deprecated
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
