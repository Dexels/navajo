package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import java.util.*;
import com.dexels.navajo.echoclient.components.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiTable extends TipiEchoDataComponentImpl {

  private String messagePath = "";

  public TipiTable() {
  }
  public Object createContainer() {
    MessageTable mt = new MessageTable();
    return mt;
  }

  public void loadData(Navajo n, TipiContext context) throws TipiException {
    super.loadData(n,context);
    System.err.println("Loading data: ");
    try {
      n.write(System.err);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    MessageTable mm = (MessageTable) getContainer();
    Message m = n.getMessage(messagePath);
    if (m!=null) {
      mm.setMessage(m);
    }
  }
  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    MessageTable mm = (MessageTable) getContainer();
//    TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
    messagePath = (String) elm.getAttribute("messagepath");
    super.load(elm, instance, context);
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String) child.getAttribute("label");
        String name = (String) child.getAttribute("name");
        String editableString = (String) child.getAttribute("editable");
        boolean editable = "true".equals(editableString);
        //System.err.println("Adding column " + name + ", editable: " + editable);
        mm.addColumn(name, label, editable);
//        mm.messageChanged();
      }
      if (child.getName().equals("column-attribute")) {
        String name = (String) child.getAttribute("name");
        String type = (String) child.getAttribute("type");
//        if (name != null && type != null && !name.equals("") && !type.equals("")) {
//          columnAttributes.put(name, cap.parseAttribute(child));
//        }
      }
    }
//    mm.setColumnAttributes(columnAttributes);
  }

}
