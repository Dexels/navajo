package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JToolBar;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1643352915642094872L;

	public TipiSwingToolBar() {
		setAlignmentX(0);
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}

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

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public void setOpaque(boolean isOpaque) {
		System.err.println("Setting opaque. Thread: "
				+ Thread.currentThread().getName());
		super.setOpaque(isOpaque);
	}

}
