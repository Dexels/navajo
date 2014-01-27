package com.dexels.navajo.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

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

  @Override
public void load(Access access) throws MappableException, UserException {
    this.access = access;
  }

  @Override
public void store() throws MappableException, UserException {
    try {
      File f = new File(navajoObject);
      if ( type == null || type.equals("tml") || type.equals("tsl") ) {
        Navajo n = (type != null && type.equals("tsl") ?
                    NavajoFactory.getInstance().createNavaScript(new FileInputStream(f)) :
                    NavajoFactory.getInstance().createNavajo(new FileInputStream(f)) );
        Message current = access.getCurrentOutMessage();
        List<Message> msgList = n.getAllMessages();
        for (int i = 0; i < msgList.size(); i++) {
          Message tbc = msgList.get(i);
          Message copy = tbc.copy(access.getOutputDoc());
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
      throw new UserException( -1, e.getMessage(),e);
    }

  }

  @Override
public void kill() {
  }

  public void setNavajoObject(String navajoObject) {
    this.navajoObject = navajoObject;
  }
  public void setType(String type) {
    this.type = type;
  }

}