package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiTableLayout
    extends TipiLayout {
  private XMLElement myElement = null;
  private int label_width = 50;
//  private TipiTableLayout layout =null;

  public DefaultTipiTableLayout() {
  }

  public void createLayout() {
    setLayout(new TipiTableLayout());
  }
//  public void instantiateLayout(TipiContext context, Tipi current, XMLElement def) {
//    layout = new TipiTableLayout();
//    current.setContainerLayout(layout);
//    current.getContainer().repaint();
//    current.setLayout(this);
//  }

//  public LayoutManager createLayout(TipiContext context, Tipi current, XMLElement table, Navajo n) throws TipiException {
  public void loadLayout(XMLElement table, Tipi current, Navajo n) throws TipiException {
    TipiContext context = TipiContext.getInstance();
    this.myElement = table;
//    parseTable(context, current, table);
//    instantiateLayout(context,current,table);
    Container con = current.getContainer();
//    TipiTableLayout l = layout;
    Map columnAttributes;
    Map tableAttributes = new HashMap();
    Map rowAttributes;
    Vector rows = table.getChildren();

    Enumeration attributes = table.enumerateAttributeNames();
    while (attributes.hasMoreElements()) {
      String attrName = (String) attributes.nextElement();
      tableAttributes.put(attrName, table.getStringAttribute(attrName));
    }
    for (int r = 0; r < rows.size(); r++) {
      XMLElement row = (XMLElement) rows.elementAt(r);
      rowAttributes = new HashMap(tableAttributes);

      Enumeration rowAttr = row.enumerateAttributeNames();
      while (rowAttr.hasMoreElements()) {
        String attrName = (String) rowAttr.nextElement();
        rowAttributes.put(attrName, row.getStringAttribute(attrName));
      }
      ((TipiTableLayout)getLayout()).startRow();
      Vector columns = row.getChildren();
      for (int c = 0; c < columns.size(); c++) {
        XMLElement column = (XMLElement) columns.elementAt(c);
        Enumeration colAttr = column.enumerateAttributeNames();
        columnAttributes = new HashMap(rowAttributes);
        while (colAttr.hasMoreElements()) {
          String attrName = (String) colAttr.nextElement();
          columnAttributes.put(attrName, column.getStringAttribute(attrName));
        }
        ((TipiTableLayout)getLayout()).startColumn();
        if (column.countChildren() > 1 || column.countChildren() == 0) {
          throw new TipiException(
              "More then one, or no children found inside <td>");
        }
        else {
          XMLElement component = (XMLElement) column.getChildren().elementAt(0);
//          String componentName = component.getName();
//          String cname = (String) component.getAttribute("name");
//          System.err.println("Adding child to tablelayout: "+columnAttributes);
          current.addAnyInstance(context,component,new HashMap(columnAttributes));
       }
        columnAttributes.clear();
        ((TipiTableLayout)getLayout()).endColumn();
      }
      ((TipiTableLayout)getLayout()).endRow();
    }
//    return layout;
  }
  public boolean needReCreate() {
    return false;
  }
  public boolean customParser() {
    return false;
  }

//  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws TipiException {
//    t.clearProperties();
//    createLayout(context, t, myElement, n);
//  }
  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

}