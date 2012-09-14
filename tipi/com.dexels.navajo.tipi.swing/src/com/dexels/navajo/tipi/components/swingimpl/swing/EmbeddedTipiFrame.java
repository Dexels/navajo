package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JInternalFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedTipiFrame extends JInternalFrame {

	private static final long serialVersionUID = -3251472052408327306L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EmbeddedTipiFrame.class);
	
	public void setVisible(boolean arg0) {
		logger.debug("setVisible: " + arg0);
		super.setVisible(true);
	}

}
