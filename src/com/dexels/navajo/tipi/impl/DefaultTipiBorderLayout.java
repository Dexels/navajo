package com.dexels.navajo.tipi.impl;

import nanoxml.*;
import com.dexels.navajo.document.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiBorderLayout extends TipiLayout {
  private XMLElement myDefinition = null;

  public DefaultTipiBorderLayout() {
  }
  public void createLayout(TipiContext context, Tipi t, XMLElement def, Navajo n) throws com.dexels.navajo.tipi.TipiException {
    myDefinition = def;
    BorderLayout layout = new BorderLayout();
//    Map m = new HashMap
    Container con = t.getContainer();
    t.setContainerLayout(layout);
    Vector v = def.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement child = (XMLElement)v.get(i);
      String constraint = child.getStringAttribute("constraint");
      String str = BorderLayout.CENTER;
      if (constraint.equals("center")) {
        str = BorderLayout.CENTER;
      }
      if (constraint.equals("north")) {
        str = BorderLayout.NORTH;
      }
      if (constraint.equals("south")) {
        str = BorderLayout.SOUTH;
      }
      if (constraint.equals("east")) {
        str = BorderLayout.EAST;
      }
      if (constraint.equals("west")) {
        str = BorderLayout.WEST;
      }
      t.addAnyInstance(context,child,str);
    }


  }
  public boolean needReCreate() {
    return false;
  }
  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
    createLayout(context,t,myDefinition,n);
  }

}