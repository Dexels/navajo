package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.tipixml.*;
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

public class DefaultTipiBorderLayout extends DefaultTipiLayout {
  private XMLElement myDefinition = null;
  BorderLayout layout = new BorderLayout();

  public DefaultTipiBorderLayout() {
  }

  public void instantiateLayout(TipiContext context, Tipi t, XMLElement def) {
    BorderLayout layout = new BorderLayout();
    Container con = t.getContainer();
    t.setContainerLayout(layout);
    t.setLayout(this);
t.setContainerLayout(layout);
System.err.println("SETTING CONTAINER MANAGER!");
//con.doLayout();
    con.repaint();
  }

//  public void createLayout(TipiContext context, Tipi t, XMLElement def, Navajo n) throws com.dexels.navajo.tipi.TipiException {
//    myDefinition = def;
//    Container con = t.getContainer();
//    t.setContainerLayout(layout);
//    Vector v = def.getChildren();
//    for (int i = 0; i < v.size(); i++) {
//      System.err.println("Adding child to borderlayout");
//      XMLElement child = (XMLElement)v.get(i);
//      String constraint = child.getStringAttribute("constraint");
//      String str = BorderLayout.CENTER;
//      if (constraint==null) {
//        constraint="center";
//      }
//      if (constraint.equals("center")) {
//        str = BorderLayout.CENTER;
//      }
//      if (constraint.equals("north")) {
//        str = BorderLayout.NORTH;
//      }
//      if (constraint.equals("south")) {
//        str = BorderLayout.SOUTH;
//      }
//      if (constraint.equals("east")) {
//        str = BorderLayout.EAST;
//      }
//      if (constraint.equals("west")) {
//        str = BorderLayout.WEST;
//      }
//      t.addAnyInstance(context,child,str);
//    }
//  }

//  public boolean customParser() {
//    return false;
//  }

//  public boolean needReCreate() {
//    return false;
//  }
//  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
//    createLayout(context,t,myDefinition,n);
//  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public LayoutManager getLayout() {
    return layout;
  }

  protected Object parseConstraint(String text) {
    if (text==null) {
      return null;
    }

    if (text.equals("center")) {
       return BorderLayout.CENTER;
     }
     if (text.equals("north")) {
       return BorderLayout.NORTH;
     }
     if (text.equals("south")) {
       return BorderLayout.SOUTH;
     }
     if (text.equals("east")) {
       return BorderLayout.EAST;
     }
     if (text.equals("west")) {
       return BorderLayout.WEST;
     }
     return BorderLayout.CENTER;
  }

}