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
    makeDefaultTipi(context,def, t, def,n);
  }

  private void makeDefaultTipi(TipiContext context, XMLElement instance, Tipi t, XMLElement elm,Navajo n) {
    int columns = 1;
    columns = elm.getIntAttribute("columns", columns);
//    Navajo n = t.getNavajo();
    if (n == null) {
      return;
    }

//      TipiContainer c = new DefaultTipiContainer();
//    TipiContainer c = currentTipi;
    try {
      t.load(elm,instance, context);   // ??
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    t.getContainer().setLayout(null);
    t.getContainer().setBackground(Color.blue);
    TipiTableLayout layout = new TipiTableLayout();
    Container con = t.getContainer();
    if(TipiPanel.class.isInstance(con)){
      ((TipiPanel)con).setTipiLayout(layout);
    }else{
      con.setLayout(layout);
    }
    //con.removeAll();
    //Container conTipi = t.getContainer();                //??
    //conTipi.setLayout(new TipiTableLayout());

    int current_column = 0;
    TipiTableLayout l = layout;
    ArrayList msgs = n.getAllMessages();
    //Thread.dumpStack();
    for (int i = 0; i < msgs.size(); i++) {
      Message current = (Message) msgs.get(i);
      ArrayList props = current.getAllProperties();
      l.startRow();
      for (int j = 0; j < props.size(); j++) {
        l.startColumn();
        Property p = (Property) props.get(j);
        BasePropertyComponent bpc = new BasePropertyComponent(p);
        t.addProperty(p.getName(), bpc, context, null);
        l.endColumn();
        current_column++;
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
    t.getContainer().doLayout();
  }

}