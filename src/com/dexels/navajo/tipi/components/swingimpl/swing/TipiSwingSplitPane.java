package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;

public class TipiSwingSplitPane extends JSplitPane {
	public static String STRING_ORIENTATION = "stringOrientation";
	
	public TipiSwingSplitPane(int orientation, JPanel left, JPanel right) {
		super(orientation,left,right);
	}

	public void setStringOrientation(String orientation) {
		String old = getStringOrientation();
		if("vertical".equals(orientation)) {
			setOrientation(VERTICAL_SPLIT);
		}
		if("horizontal".equals(orientation)) {
			setOrientation(HORIZONTAL_SPLIT);
		}
		firePropertyChange(STRING_ORIENTATION, old, orientation);
	}
	
	public String getStringOrientation() {
		if (getOrientation()==VERTICAL_SPLIT) {
			return "vertical";
		} else {
			return "horizontal";
		}
		
	}

}
