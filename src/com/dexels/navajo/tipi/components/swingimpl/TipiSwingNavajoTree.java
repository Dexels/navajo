package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.*;

public class TipiSwingNavajoTree extends JTree {
	private String selectedPath = null;

	public String getSelectedPath() {
		return selectedPath;
	}


	public void setSelectedPath(String selectedPath) {
		this.selectedPath = selectedPath;
	}

}
