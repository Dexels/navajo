package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
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
  private XMLElement defaultElement = null;
  public DefaultColumnLayout() {
  }

  public void createLayout(TipiContext context, Tipi t, XMLElement instance, Navajo n) throws TipiException {
    myElement = instance;
    columnCount = Integer.parseInt( (String) instance.getAttribute("columns", "1"));
    int columns = 1;
    int childCount = instance.getChildren().size();
    if (childCount!=1) {
      throw new TipiException("No default property instance found.");
    }
    XMLElement def = (XMLElement)instance.getChildren().get(0);
    if ("default-instance".equals(def.getName())) {
     defaultElement = (XMLElement)def.getChildren().get(0);
    }

    columns = instance.getIntAttribute("columns", columns);
//    System.err.println("Making new Layout: " + instance);
//    if (n == null) {
//      return;
//    }else{
//      System.err.println("T got: " + nl.toXml().toString());
//    }
//

    Navajo nl = t.getNavajo();

    TipiTableLayout lo = new TipiTableLayout();

    t.setContainerLayout(lo);
//    System.err.println("Layoutclass: " + t.getContainer().getLayout().getClass());
//    TipiTableLayout lo = (TipiTableLayout)t.getContainer().getLayout();
//    TipiTableLayout lo = (TipiTableLayout)t.getContainerLayout();
    int current_column = 0;
//    TipiTableLayout l = layout;
    if (n==null) {
      return;
    }

    ArrayList msgs = null;
    try {
      msgs = n.getAllMessages();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    //Thread.dumpStack();
    for (int i = 0; i < msgs.size(); i++) {
      Message current = (Message) msgs.get(i);
      ArrayList props = current.getAllProperties();
      lo.startRow();
      for (int j = 0; j < props.size(); j++) {
        lo.startColumn();
        Property p = (Property) props.get(j);
        defaultElement.setAttribute("name",p.getName());
        BasePropertyComponent bpc = (BasePropertyComponent)t.addAnyInstance(context,  defaultElement, null);
/** @todo Fix the PropertyInterface in tipi */
        bpc.setProperty(p);
        bpc.addTipiEventListener(t);
        lo.endColumn();
        current_column++;
        if (current_column > columns - 1) {
          current_column = 0;
          lo.endRow();
          lo.startRow();
        }
      }
    }
  }

  public boolean needReCreate() {
    return true;
  }
  public boolean customParser() {
    return true;
  }

  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws TipiException {
    t.clearProperties();
    createLayout(context, t, myElement, n);
    t.getContainer().doLayout();
  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

}