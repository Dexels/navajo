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
//  static final int default_currentRow = 0;
//  static final int default_currentColumn = 0;
//  static final int default_cellspacing = 0;
//  static final int default_cellpadding = 0;
//  static final int default_colspan = 1;
//  static final int default_rowspan = 1;
//  static final int default_height = 0;
//  static final int default_width = 0;
//  static final double default_weightx = 1.0;
//  static final double default_weighty = 1.0;
//  private int currentRow = 0;
//  private int currentColumn = 0;
//  private int cellspacing = 0;
//  private int cellpadding = 0;
//  private int colspan = 1;
//  private int rowspan = 1;
//  private int height = 0;
//  private int width = 0;
//  private int left = 0;
//  private int right = 0;
//  private int top = 0;
//  private int bottom = 0;
//  private int fill;
//  private double weightx = 0;
//  private double weighty = 0;
//  private Map myMap;
  private int anchor = GridBagConstraints.WEST;
  public TipiTableLayout() {
  }

  public void addLayoutComponent(Component comp, Object constraints) {
    System.err.println("CONSTRAINTS: "+constraints+" class:"+comp.getClass());
    System.err.println("COmponent: "+comp.getClass());
//    if(Map.class.isInstance(constraints)){
//      myMap = (Map) constraints;
//      Object cons = createConstraint(myMap);
    super.addLayoutComponent(comp, constraints);
  }

}