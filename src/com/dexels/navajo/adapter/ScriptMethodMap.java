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

public class ScriptMethodMap implements Mappable {

  public String name;

  public ScriptMethodMap() {
  }

  public String getName(){
    return name;
  }

  public void setName(String value){
    name = value;
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    //
  }
  public void store() throws MappableException, UserException {
    //
  }
  public void kill() {
    //
  }

}