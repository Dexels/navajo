package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JLabel;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndCapable;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndManager;

public class TipiSwingPropertyDescriptionLabel extends JLabel implements
		TipiDndCapable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9012073326960746871L;
	private final TipiDndManager myDndManager;

	public TipiSwingPropertyDescriptionLabel(TipiSwingPropertyComponent tspc,
			TipiComponent tc) {
		myDndManager = new TipiDndManager(this, tc);
	}

	public TipiDndManager getDndManager() {
		// TODO Auto-generated method stub
		return myDndManager;
	}

}
