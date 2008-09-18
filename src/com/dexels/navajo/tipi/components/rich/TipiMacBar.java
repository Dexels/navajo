package com.dexels.navajo.tipi.components.rich;

import javax.swing.*;

import com.dexels.navajo.rich.components.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiMacBar extends TipiPanel {
	private MacBar myPanel;

	public Object createContainer() {
		myPanel = new MacBar();
		return myPanel;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		myPanel.add((JComponent) c);
	}
}
