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
  static final int default_currentRow = 0;
   static final int default_currentColumn = 0;
   static final int default_cellspacing = 0;
   static final int default_cellpadding = 0;
   static final int default_colspan = 1;
   static final int default_rowspan = 1;
   static final int default_height = 0;
   static final int default_width = 0;
   static final double default_weightx = 1.0;
   static final double default_weighty = 1.0;
   private int currentRow = 0;
   private int currentColumn = 0;
   private int cellspacing = 0;
   private int cellpadding = 0;
   private int colspan = 1;
   private int rowspan = 1;
   private int height = 0;
   private int width = 0;
   private int left = 0;
   private int right = 0;
   private int top = 0;
   private int bottom = 0;
   private int fill;
   private double weightx = 0;
   private double weighty = 0;

  public DefaultTipiTableLayout() {
  }

  public void createLayout() {
//    setLayout(new TipiTableLayout());
    setLayout(new GridBagLayout());
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


//    System.err.println("LOADING TABLE LAYOUT: \n\n"+table);
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
//      System.err.println("Adding row to tablelayout");
      XMLElement row = (XMLElement) rows.elementAt(r);
      rowAttributes = new HashMap(tableAttributes);

      Enumeration rowAttr = row.enumerateAttributeNames();
      while (rowAttr.hasMoreElements()) {
        String attrName = (String) rowAttr.nextElement();
        rowAttributes.put(attrName, row.getStringAttribute(attrName));
      }
      startRow();
      Vector columns = row.getChildren();
      for (int c = 0; c < columns.size(); c++) {
//        System.err.println("Adding column to row");
        XMLElement column = (XMLElement) columns.elementAt(c);
        Enumeration colAttr = column.enumerateAttributeNames();
        columnAttributes = new HashMap(rowAttributes);
        while (colAttr.hasMoreElements()) {
          String attrName = (String) colAttr.nextElement();
          columnAttributes.put(attrName, column.getStringAttribute(attrName));
        }
        startColumn();
        if (column.countChildren() > 1 || column.countChildren() == 0) {
          throw new TipiException(
              "More then one, or no children found inside <td>");
        }
        else {
          XMLElement component = (XMLElement) column.getChildren().elementAt(0);
//          String componentName = component.getName();
//          String cname = (String) component.getAttribute("name");
//          System.err.println("Adding child to tablelayout: "+columnAttributes);
          Map hMap = new HashMap(columnAttributes);
          Object constraint = createConstraint(hMap);
//          System.err.println("CONSTRAINT MAP>> "+hMap);
//          System.err.println("CONSTRAINT>> "+constraint.toString());
          current.addAnyInstance(context,component,constraint);
       }
        columnAttributes.clear();
        endColumn();
      }
     endRow();
    }
//    return layout;
  }
  public boolean needReCreate() {
    return false;
  }
  public boolean customParser() {
    return false;
  }

  // When saving, disguise as gridbag
  public XMLElement store() {
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("layout");
    xe.setAttribute("type","gridbag");
    return xe;
  }


//  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws TipiException {
//    t.clearProperties();
//    createLayout(context, t, myElement, n);
//  }
  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

    public Object createConstraint(Map myMap) {
      Object cons = null;
      String fillString;
      if (myMap != null) {
        cellspacing = 0;
        cellpadding = Integer.parseInt(getColumnAttribute(myMap,"cellpadding", String.valueOf(default_cellpadding)));
        left = Integer.parseInt(getColumnAttribute(myMap,"left", "" + cellpadding));
        right = Integer.parseInt(getColumnAttribute(myMap,"right", "" + cellpadding));
        top = Integer.parseInt(getColumnAttribute(myMap,"top", "" + cellpadding));
        bottom = Integer.parseInt(getColumnAttribute(myMap,"bottom", "" + cellpadding));
        colspan = Integer.parseInt(getColumnAttribute(myMap,"colspan", String.valueOf(default_colspan)));
        rowspan = Integer.parseInt(getColumnAttribute(myMap,"rowspan", String.valueOf(default_rowspan)));
        height = Integer.parseInt(getColumnAttribute(myMap,"height", String.valueOf(default_height)));
        width = Integer.parseInt(getColumnAttribute(myMap,"width", String.valueOf(default_width)));
        weightx = (new Double(getColumnAttribute(myMap,"weightx", String.valueOf(default_weightx)))).doubleValue();
        weighty = (new Double(getColumnAttribute(myMap,"weighty", String.valueOf(default_weighty)))).doubleValue();
        fillString = getColumnAttribute(myMap,"fill", "both");
//        System.err.println("FILLSTRING>>>> "+fillString);
        fill = GridBagConstraints.BOTH;
        if ("none".equals(fillString)) {
          fill = GridBagConstraints.NONE;
        }
        if ("horizontal".equals(fillString)) {
          fill = GridBagConstraints.HORIZONTAL;
        }
        if ("vertical".equals(fillString)) {
          fill = GridBagConstraints.VERTICAL;
        }
        if ("both".equals(fillString)) {
          fill = GridBagConstraints.BOTH;
        }
        int anchor = determineAnchor(myMap);
        cons = new DefaultTipiGridBagConstraints(currentColumn,
                                                 currentRow, colspan, rowspan, weightx,
                                                 weighty, anchor,
                                                 fill,
                                                 new Insets(top, left,
            bottom, right), width,
                                                 height);
      }
      else {
        cons = new DefaultTipiGridBagConstraints(currentColumn,
                                                 currentRow, default_colspan, default_rowspan, default_weightx,
                                                 default_weighty, GridBagConstraints.WEST,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(default_cellpadding, default_cellpadding,
            default_cellpadding, default_cellpadding), default_width,
                                                 default_height);
      }
      return cons;
    }

    private int determineAnchor(Map myMap) {
      String halign = getColumnAttribute(myMap,"align", "center");
      String valign = getColumnAttribute(myMap,"valign", "top");
      if (halign.equals("center") && valign.equals("top")) {
        return GridBagConstraints.NORTH;
      }
      if (halign.equals("center") && valign.equals("center")) {
        return GridBagConstraints.CENTER;
      }
      if (halign.equals("center") && valign.equals("bottom")) {
        return GridBagConstraints.SOUTH;
      }
      if (halign.equals("left") && valign.equals("top")) {
        return GridBagConstraints.NORTHWEST;
      }
      if (halign.equals("left") && valign.equals("center")) {
        return GridBagConstraints.WEST;
      }
      if (halign.equals("left") && valign.equals("bottom")) {
        return GridBagConstraints.SOUTHWEST;
      }
      if (halign.equals("right") && valign.equals("top")) {
        return GridBagConstraints.NORTHEAST;
      }
      if (halign.equals("right") && valign.equals("center")) {
        return GridBagConstraints.EAST;
      }
      if (halign.equals("right") && valign.equals("bottom")) {
        return GridBagConstraints.SOUTHEAST;
      }
//    System.err.println("Determined anchor: " + anchor);
      return GridBagConstraints.WEST;
    }

    private String getColumnAttribute(Map myMap, String name, String defaultValue) {
      if (myMap != null) {
        String value = (String) myMap.get(name);
        if (value != null) {
          return value;
        }
        else {
          return defaultValue;
        }
      }
      else {
//      System.err.println("WARNING!!!! columnAttribute map not loaded!");
        return defaultValue;
      }
    }

    public void startRow() {
    }

    public void endRow() {
      currentRow++;
      currentColumn = 0;
    }

    public void startColumn() {
    }

    public void endColumn() {
      currentColumn += colspan;
    }

}