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
public class DefaultTipiBorderLayout
    extends DefaultTipiLayout {
  private XMLElement myDefinition = null;
  BorderLayout layout = new BorderLayout();
  public DefaultTipiBorderLayout() {
  }

  public void createLayout() {
    setLayout(new BorderLayout());
  }
  public Object createDefaultConstraint(int index) {
    switch (index) {
      case 0:
        return BorderLayout.CENTER;
      case 1:
        return BorderLayout.NORTH;
      case 2:
        return BorderLayout.SOUTH;
      case 3:
        return BorderLayout.EAST;
      case 4:
        return BorderLayout.WEST;
      default:
        return null;
    }
  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }
  protected Object parseConstraint(String text) {
     if (text == null) {
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