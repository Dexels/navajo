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

  public DefaultTipiTableLayout() {
  }

  public void createLayout(TipiContext context, Tipi current, XMLElement table, Navajo n) throws TipiException {
    this.myElement = table;
//    parseTable(context, current, table);
    TipiTableLayout layout = new TipiTableLayout();
    Container con = current.getContainer();
    current.setContainerLayout(layout);
    TipiTableLayout l = layout;
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
      l.startRow();
      Vector columns = row.getChildren();
      for (int c = 0; c < columns.size(); c++) {
        XMLElement column = (XMLElement) columns.elementAt(c);
        Enumeration colAttr = column.enumerateAttributeNames();
        columnAttributes = new HashMap(rowAttributes);
        while (colAttr.hasMoreElements()) {
          String attrName = (String) colAttr.nextElement();
          columnAttributes.put(attrName, column.getStringAttribute(attrName));
        }
        l.startColumn();
        if (column.countChildren() > 1 || column.countChildren() == 0) {
          throw new TipiException(
              "More then one, or no children found inside <td>");
        }
        else {
          XMLElement component = (XMLElement) column.getChildren().elementAt(0);
          String componentName = component.getName();
          String cname = (String) component.getAttribute("name");
          current.addAnyInstance(context,component,new HashMap(columnAttributes));
       }
        columnAttributes.clear();
        l.endColumn();
      }
      l.endRow();
    }
  }
  public boolean needReCreate() {
    return false;
  }

  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws TipiException {
    t.clearProperties();
    createLayout(context, t, myElement, n);
  }

}