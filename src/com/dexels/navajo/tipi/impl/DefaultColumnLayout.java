package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import java.awt.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultColumnLayout
    extends TipiLayout {
  private int columnCount = 1;
  private XMLElement myElement = null;

  public DefaultColumnLayout() {
  }

  public void createLayout(TipiContext context, Tipi t, XMLElement def, Navajo n) throws TipiException {
    myElement = def;
    columnCount = Integer.parseInt( (String) def.getAttribute("columns", "1"));
    makeDefaultTipi(context, t, def,n);
  }

  private void makeDefaultTipi(TipiContext context, Tipi t, XMLElement elm,Navajo n) {
    int columns = 1;
    columns = elm.getIntAttribute("columns", columns);
//    Navajo n = t.getNavajo();
    if (n == null) {
      return;
    }

//      TipiContainer c = new DefaultTipiContainer();
//    TipiContainer c = currentTipi;
    try {
      t.load(elm, context);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    TipiTableLayout layout = new TipiTableLayout();
    Container con = t.getContainer();
    con.setLayout(layout);
    Container conTipi = t.getContainer();
    conTipi.setLayout(new TipiTableLayout());
    TipiTableLayout l = (TipiTableLayout) con.getLayout();
    int current_column = 0;

    ArrayList msgs = n.getAllMessages();
    for (int i = 0; i < msgs.size(); i++) {
      Message current = (Message) msgs.get(i);
      ArrayList props = current.getAllProperties();
      l.startRow();
      for (int j = 0; j < props.size(); j++) {
        l.startColumn();
        current_column++;
        Property p = (Property) props.get(j);
        BasePropertyComponent bpc = new BasePropertyComponent(p);
        t.addProperty(p.getName(), bpc, context, null);
        l.endColumn();
        if (current_column > columns - 1) {
          current_column = 0;
          l.endRow();
          l.startRow();
        }
      }
    }
//      t.addTipiContainer(c, context, null);
  }

  public boolean needReCreate() {
    return true;
  }

  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws TipiException {
    createLayout(context, t, myElement, n);
  }

}