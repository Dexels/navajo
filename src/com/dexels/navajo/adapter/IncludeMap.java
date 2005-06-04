package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import java.io.File;
import com.dexels.navajo.document.NavajoFactory;
import java.io.FileInputStream;
import com.dexels.navajo.document.Message;
import java.util.ArrayList;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class IncludeMap implements Mappable {

  public String navajoObject;
  public String type;

  private Access access;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    this.access = access;
  }

  public void store() throws MappableException, UserException {
    try {
      File f = new File(navajoObject);
      if ( type == null || type.equals("tml") || type.equals("tsl") ) {
        Navajo n = (type != null && type.equals("tsl") ?
                    NavajoFactory.getInstance().createNavaScript(new FileInputStream(f)) :
                    NavajoFactory.getInstance().createNavajo(new FileInputStream(f)) );
        Message current = access.getCurrentOutMessage();
        ArrayList msgList = n.getAllMessages();
        for (int i = 0; i < msgList.size(); i++) {
          Message tbc = (Message) msgList.get(i);
          Message copy = (Message) tbc.copy(access.getOutputDoc());
          if (current != null) {
            current.addMessage(copy);
          }
          else {
            access.getOutputDoc().addMessage(copy);
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      throw new UserException( -1, e.getMessage());
    }

  }

  public void kill() {
  }

  public void setNavajoObject(String navajoObject) {
    this.navajoObject = navajoObject;
  }
  public void setType(String type) {
    this.type = type;
  }

}