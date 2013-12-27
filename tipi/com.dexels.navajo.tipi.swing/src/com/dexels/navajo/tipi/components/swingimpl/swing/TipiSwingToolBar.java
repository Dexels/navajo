package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TipiSwingToolBar extends JToolBar {

	private static final long serialVersionUID = 1643352915642094872L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingToolBar.class);
	
	
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

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public void setOpaque(boolean isOpaque) {
		logger.debug("Setting opaque. Thread: "
				+ Thread.currentThread().getName());
		super.setOpaque(isOpaque);
	}

}
