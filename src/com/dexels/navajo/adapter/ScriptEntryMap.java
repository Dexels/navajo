package com.dexels.navajo.adapter;

import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.Mappable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ScriptEntryMap implements Mappable {
  public String name;
  public ScriptEntryMap() {
  }

  public String getName(){
    return name;
  }
  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    //nip
  }
  public void store() throws MappableException, UserException {
    //nip
  }
  public void kill() {
    //nip
  }

}