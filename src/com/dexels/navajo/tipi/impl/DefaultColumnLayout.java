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
  private XMLElement defaultElement = null;
  public DefaultColumnLayout() {
  }

  public void createLayout(TipiContext context, Tipi t, XMLElement instance, Navajo n) throws TipiException {
    myElement = instance;
    columnCount = Integer.parseInt( (String) instance.getAttribute("columns", "1"));
//    makeDefaultTipi(context,instance, t,n);
    int columns = 1;
    int childCount = instance.getChildren().size();
    if (childCount!=1) {
      throw new TipiException("No defailt property instance found.");
    }
    XMLElement def = (XMLElement)instance.getChildren().get(0);
    if ("default-instance".equals(def.getName())) {
     defaultElement = (XMLElement)def.getChildren().get(0);
    }

    columns = instance.getIntAttribute("columns", columns);
    System.err.println("Making new Tipi(??): " + instance);
//    Navajo n = t.getNavajo();
//    Thread.dumpStack();
    if (n == null) {
      return;
    }


    TipiTableLayout layout = new TipiTableLayout();

    t.setContainerLayout(layout);
//    System.err.println("Layoutclass: " + t.getContainer().getLayout().getClass());
    TipiTableLayout lo = (TipiTableLayout)t.getContainer().getLayout();
    int current_column = 0;
//    TipiTableLayout l = layout;
    ArrayList msgs = n.getAllMessages();
    //Thread.dumpStack();
    for (int i = 0; i < msgs.size(); i++) {
      Message current = (Message) msgs.get(i);
      ArrayList props = current.getAllProperties();
      lo.startRow();
      for (int j = 0; j < props.size(); j++) {
        lo.startColumn();
        Property p = (Property) props.get(j);
        defaultElement.setAttribute("name",p.getName());
//        BasePropertyComponent bpc = (BasePropertyComponent)context.instantiateComponent(defaultElement);

        BasePropertyComponent bpc = (BasePropertyComponent)t.addComponentInstance(context,  defaultElement, null);
//        BasePropertyComponent bpc = new BasePropertyComponent(p);
/** @todo Fix the PropertyInterface in tipi */
        bpc.setProperty(p);
        bpc.addTipiEventListener(t);
//System.err.println("ELEMENT: "+defaultElement.toString());
//        t.addProperty(p.getName(), bpc, context, null);
        lo.endColumn();
        current_column++;
        if (current_column > columns - 1) {
          current_column = 0;
          lo.endRow();
          lo.startRow();
        }
      }
    }
//      t.addTipiContainer(c, context, null);
  }

  public boolean needReCreate() {
    return true;
  }

  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws TipiException {
    t.clearProperties();
    createLayout(context, t, myElement, n);
    t.getContainer().doLayout();
  }

}