package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;

public class AccessMap implements Mappable {

  public String userName;
  public String webService;
  public java.util.Date created;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

  }
  public void store() throws MappableException, UserException {

  }
  public void kill() {

  }
  public String getUserName() {
    return userName;
  }
  public String getWebService() {
    return webService;
  }
  public java.util.Date getCreated() {
    return created;
  }

}