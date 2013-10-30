package com.dexels.navajo.tipi.components.rich;

import javax.swing.JComponent;

import com.dexels.navajo.rich.components.MacBar;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiMacBar extends TipiPanel {

	private static final long serialVersionUID = 4021914601392479783L;
	private MacBar myPanel;

	@Override
	public Object createContainer() {
		myPanel = new MacBar();
		return myPanel;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		myPanel.add((JComponent) c);
	}
}
