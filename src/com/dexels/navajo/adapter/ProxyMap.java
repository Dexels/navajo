package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.client.NavajoClient;
import java.util.*;
import org.w3c.dom.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class ProxyMap implements Mappable {

  public String username;
  public String password;
  public String server;
  public String method;

  private NavajoClient nc;
  private Navajo outDoc;
  private Access access;
  private Navajo inMessage;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    this.access = access;
    this.inMessage = inMessage;
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
      try {
        nc = new NavajoClient();
        Document d = inMessage.getMessageBuffer();
        // REMOVE HEADER!
        Node n = com.dexels.navajo.xml.XMLutils.findNode(d, "header");
        Node body = com.dexels.navajo.xml.XMLutils.findNode(d, "tml");
        body.removeChild(n);
        Navajo out = nc.doSimpleSend(inMessage, server, method, username, password, -1);
        access.setOutputDoc(out);
      } catch (Exception e) {
        e.printStackTrace();
        throw new UserException(-1, e.getMessage());
      }
  }

  public void kill() {
  }
}