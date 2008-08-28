package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;

public class EmbeddedTipiFrame extends JInternalFrame {


	public void setVisible(boolean arg0) {
		System.err.println("setVisible: "+arg0);
		super.setVisible(true);
	}

}
