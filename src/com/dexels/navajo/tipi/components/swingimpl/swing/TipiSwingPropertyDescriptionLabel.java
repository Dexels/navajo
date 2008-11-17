package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingimpl.dnd.*;

public class TipiSwingPropertyDescriptionLabel extends JLabel implements TipiDndCapable {
	private final TipiDndManager myDndManager;

	public TipiSwingPropertyDescriptionLabel(TipiSwingPropertyComponent tspc, TipiComponent tc) {
		myDndManager = new TipiDndManager(this,tc);
	}
	
	public TipiDndManager getDndManager() {
		// TODO Auto-generated method stub
		return myDndManager;
	}

}
