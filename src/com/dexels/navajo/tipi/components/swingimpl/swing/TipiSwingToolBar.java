package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingToolBar extends JToolBar {

  public TipiSwingToolBar() {
    setAlignmentX(0);
    setLayout(new FlowLayout(FlowLayout.LEFT));
  }
	public static String STRING_ORIENTATION = "stringOrientation";
	
	public void setStringOrientation(String orientation) {
		String old = getStringOrientation();
		if("vertical".equals(orientation)) {
			setOrientation(VERTICAL);
		}
		if("horizontal".equals(orientation)) {
			setOrientation(HORIZONTAL);
		}
		firePropertyChange(STRING_ORIENTATION, old, orientation);
	}
	
	public String getStringOrientation() {
		if (getOrientation()==VERTICAL) {
			return "vertical";
		} else {
			return "horizontal";
		}
		
	}

  public Dimension getMinimumSize() {
      return getPreferredSize();
  }

}
