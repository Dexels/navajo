package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.ButtonGroup;

import com.dexels.navajo.tipi.components.core.TipiComponentImpl;

public class TipiButtonGroup extends TipiComponentImpl {
	private ButtonGroup myButtonGroup = new ButtonGroup();

	@Override
	public Object createContainer() {
		return null;
	}

	public ButtonGroup getButtonGroup() {
		return myButtonGroup;
	}

}
