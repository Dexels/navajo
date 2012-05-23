package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JTree;

public class TipiSwingNavajoTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7879989320974319712L;
	private String selectedPath = null;

	public String getSelectedPath() {
		return selectedPath;
	}

	public void setSelectedPath(String selectedPath) {
		this.selectedPath = selectedPath;
	}

}
