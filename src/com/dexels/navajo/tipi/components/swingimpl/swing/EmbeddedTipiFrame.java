package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JInternalFrame;

public class EmbeddedTipiFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3251472052408327306L;

	public void setVisible(boolean arg0) {
		System.err.println("setVisible: " + arg0);
		super.setVisible(true);
	}

}
