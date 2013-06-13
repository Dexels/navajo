package com.dexels.navajo.adapter;

import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;
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
 * @version $Id$
 */

public class ProxyMap implements Mappable {

  public String username = null;
  public String password = null;
  public String server = null;
  public String method = null;

  private Access access;
  private Navajo inMessage;

  public void load(Access access) throws MappableException, UserException {
    this.access = access;
    this.inMessage = access.getInDoc();
  }

  public void setMethod(String u) {
    this.method = u;
  }

  public void setUsername(String u) {
    this.username = u;
  }

  public void setPassword(String u) {
    this.password = u;
  }

  public void setServer(String u) {
    this.server = u;
  }

  public void store() throws MappableException, UserException {
      if (server == null)
        throw new UserException(-1, "ProxyMap error: no server URI specified, e.g. localhost/servlet/Postman");
      try {
        ClientInterface nc = NavajoClientFactory.createClient();
        inMessage.removeHeader();
        Navajo out = nc.doSimpleSend(inMessage, server,
                                     (method == null ? access.rpcName : method),
                                     (username == null ? access.rpcUser : username),
                                     (password == null ? access.rpcPwd : password), -1);
        access.setOutputDoc(out);
      } catch (Exception e) {
        throw new UserException(-1, e.getMessage());
      }
  }

  public void kill() {
  }
}
