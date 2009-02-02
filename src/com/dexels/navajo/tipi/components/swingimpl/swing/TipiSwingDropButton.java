package com.dexels.navajo.tipi.components.swingimpl.swing;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.components.swingimpl.TipiDropButton;

public class TipiSwingDropButton extends TipiSwingButton {

	private final TipiDropButton myDropButton;
	public TipiSwingDropButton(TipiDropButton component) {
		super(component);
		myDropButton = component;
	}
	
	public void setBinaryValue(final Binary b) {
		
		myDropButton.setBinaryValue(b);
	}
}
