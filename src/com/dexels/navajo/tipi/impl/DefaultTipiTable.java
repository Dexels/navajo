package com.dexels.navajo.tipi.impl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.swingclient.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.document.*;

public class DefaultTipiTable extends DefaultTipi {
  private String messagePath = "/MemberData";

  public DefaultTipiTable() {
  }
  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    MessageTablePanel mm = new MessageTablePanel();
    setContainer(mm);
    messagePath = (String)elm.getAttribute("messagepath");
    super.load(elm,context);
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (!child.getName().equals("column")) {
        throw new TipiException("Unexpected tag encountered under table tipi. Only expecting column tags");
      }
      String label = (String)child.getAttribute("label");
      String name = (String)child.getAttribute("name");
//      boolean editable = (((String)child.getAttribute("editable")).equals("true"));
      boolean editable = false;
      mm.addColumn(name,label,editable);
    }
  }

  public void loadData(Navajo n, TipiContext tc) {
    System.err.println("LOADING DATA: "+n.toXml());
    MessageTablePanel mtp = (MessageTablePanel)getContainer();
    Message m = n.getByPath(messagePath);
    if (m!=null) {
      mtp.setMessage(m);
    }
  }

}