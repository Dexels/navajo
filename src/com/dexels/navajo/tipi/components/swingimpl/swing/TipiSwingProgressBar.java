package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;

public class TipiSwingProgressBar extends JProgressBar {
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
}
