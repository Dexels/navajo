package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JProgressBar;

public class TipiSwingProgressBar extends JProgressBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6423583567654250575L;
	public static String STRING_ORIENTATION = "stringOrientation";

	public void setStringOrientation(String orientation) {
		String old = getStringOrientation();
		if ("vertical".equals(orientation)) {
			setOrientation(VERTICAL);
		}
		if ("horizontal".equals(orientation)) {
			setOrientation(HORIZONTAL);
		}
		firePropertyChange(STRING_ORIENTATION, old, orientation);
	}

	public String getStringOrientation() {
		if (getOrientation() == VERTICAL) {
			return "vertical";
		} else {
			return "horizontal";
		}

	}
}
