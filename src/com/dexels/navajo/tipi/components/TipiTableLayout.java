package com.dexels.navajo.tipi.components;

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

public class TipiTableLayout extends GridBagLayout {

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
  private double weightx = 0;
  private double weighty = 0;
  private Map myMap;
  private int anchor = 10;

  public TipiTableLayout() {
  }

  public void addLayoutComponent(Component comp, Object constraints) {
    if(Map.class.isInstance(constraints)){

      myMap = (Map)constraints;

      cellspacing = 0;
      cellpadding = Integer.parseInt(getColumnAttribute("cellpadding", String.valueOf(default_cellpadding)));
      colspan = Integer.parseInt(getColumnAttribute("colspan", String.valueOf(default_colspan)));
      rowspan = Integer.parseInt(getColumnAttribute("rowspan", String.valueOf(default_rowspan)));
      height = Integer.parseInt(getColumnAttribute("height", String.valueOf(default_height)));
      width = Integer.parseInt(getColumnAttribute("width", String.valueOf(default_width)));
      weightx = (new Double(getColumnAttribute("weightx", String.valueOf(default_weightx)))).doubleValue();
      weighty = (new Double(getColumnAttribute("weighty", String.valueOf(default_weighty)))).doubleValue();
      String foreground = getColumnAttribute("color", null);
      String background = getColumnAttribute("bgcolor", null);
      determineAnchor();
      if(foreground != null){
        comp.setForeground(Color.decode(foreground));
      }
      if(background != null){
        comp.setBackground(Color.decode(background));
      }
    }
    GridBagConstraints cons;
    cons = new GridBagConstraints(currentColumn,
          currentRow, colspan, rowspan, weightx, weighty, anchor,
          GridBagConstraints.BOTH,
          new Insets(cellpadding, cellpadding, cellpadding, cellpadding), width,
                              height);
    super.addLayoutComponent(comp, cons);
  }

  private void determineAnchor(){
    String halign = getColumnAttribute("align", "center");
    String valign = getColumnAttribute("valign", "center");
    if(halign.equals("center") && valign.equals("top")){
      anchor = GridBagConstraints.NORTH;
    }
    if(halign.equals("center") && valign.equals("center")){
      anchor = GridBagConstraints.CENTER;
    }
    if(halign.equals("center") && valign.equals("bottom")){
      anchor = GridBagConstraints.SOUTH;
    }
    if(halign.equals("left") && valign.equals("top")){
      anchor = GridBagConstraints.NORTHWEST;
    }
    if(halign.equals("left") && valign.equals("center")){
      anchor = GridBagConstraints.WEST;
    }
    if(halign.equals("left") && valign.equals("bottom")){
      anchor = GridBagConstraints.SOUTHWEST;
    }
    if(halign.equals("right") && valign.equals("top")){
      anchor = GridBagConstraints.NORTHEAST;
    }
    if(halign.equals("right") && valign.equals("center")){
      anchor = GridBagConstraints.EAST;
    }
    if(halign.equals("right") && valign.equals("bottom")){
      anchor = GridBagConstraints.SOUTHEAST;
    }
    System.err.println("Determined anchor: " + anchor);
  }

  private String getColumnAttribute(String name, String defaultValue){
    if(myMap != null){
      String value = (String)myMap.get(name);
      if(value != null){
        return value;
      }else{
        return defaultValue;
      }
    }else{
      System.err.println("WARNING!!!! columnAttribute map not loaded!");
      return defaultValue;
    }
  }

  public void startRow(){

  }

  public void endRow(){
    currentRow++;
    currentColumn = 0;
  }

  public void startColumn(){

  }

  public void endColumn(){
    currentColumn+=colspan;
  }


}