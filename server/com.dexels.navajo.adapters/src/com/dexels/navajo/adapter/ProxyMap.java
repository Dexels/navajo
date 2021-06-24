/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

  @Override
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

  @Override
public void store() throws MappableException, UserException {
      if (server == null)
        throw new UserException(-1, "ProxyMap error: no server URI specified, e.g. localhost/servlet/Postman");
      try {
        ClientInterface nc = NavajoClientFactory.createClient();
        inMessage.removeHeader();
        nc.setUsername(username == null ? access.rpcUser : username);
        nc.setPassword(password == null ? access.rpcPwd : password);
        nc.setAllowCompression(true);
        nc.setServerUrl(server);
        Navajo out = nc.doSimpleSend(inMessage, (method == null ? access.rpcName : method));
        access.setOutputDoc(out);
      } catch (Exception e) {
        throw new UserException(-1, e.getMessage());
      }
  }

  @Override
public void kill() {
  }
}
