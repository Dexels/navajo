package com.dexels.navajo.tipi.components;

import java.awt.*;
import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTableLayout
    extends GridBagLayout {
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
//  private Map myMap;
  private int anchor = GridBagConstraints.WEST;
  public TipiTableLayout() {
  }

  public void addLayoutComponent(Component comp, Object constraints) {
//    System.err.println("CONSTRAINTS: "+constraints+" class:"+comp.getClass());
//    if(Map.class.isInstance(constraints)){
//      myMap = (Map) constraints;
//      Object cons = createConstraint(myMap);
    super.addLayoutComponent(comp, constraints);
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
      System.err.println("FILLSTRING>>>> "+fillString);
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
      determineAnchor(myMap);
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
                                               default_weighty, anchor,
                                               GridBagConstraints.BOTH,
                                               new Insets(default_cellpadding, default_cellpadding,
          default_cellpadding, default_cellpadding), default_width,
                                               default_height);
    }
    return cons;
  }

  private void determineAnchor(Map myMap) {
    String halign = getColumnAttribute(myMap,"align", "center");
    String valign = getColumnAttribute(myMap,"valign", "top");
    if (halign.equals("center") && valign.equals("top")) {
      anchor = GridBagConstraints.NORTH;
    }
    if (halign.equals("center") && valign.equals("center")) {
      anchor = GridBagConstraints.CENTER;
    }
    if (halign.equals("center") && valign.equals("bottom")) {
      anchor = GridBagConstraints.SOUTH;
    }
    if (halign.equals("left") && valign.equals("top")) {
      anchor = GridBagConstraints.NORTHWEST;
    }
    if (halign.equals("left") && valign.equals("center")) {
      anchor = GridBagConstraints.WEST;
    }
    if (halign.equals("left") && valign.equals("bottom")) {
      anchor = GridBagConstraints.SOUTHWEST;
    }
    if (halign.equals("right") && valign.equals("top")) {
      anchor = GridBagConstraints.NORTHEAST;
    }
    if (halign.equals("right") && valign.equals("center")) {
      anchor = GridBagConstraints.EAST;
    }
    if (halign.equals("right") && valign.equals("bottom")) {
      anchor = GridBagConstraints.SOUTHEAST;
    }
//    System.err.println("Determined anchor: " + anchor);
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