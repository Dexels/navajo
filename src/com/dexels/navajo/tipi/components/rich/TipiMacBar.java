package com.dexels.navajo.tipi.components.rich;

import javax.swing.JComponent;

import com.dexels.navajo.rich.components.LushContainer;
import com.dexels.navajo.rich.components.MacBar;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiMacBar extends TipiPanel {
private MacBar myPanel;
	
	public Object createContainer() {
	  myPanel = new MacBar();
//	  myPanel.setOpaque(false);
	  return myPanel;
  }
	
	@Override
	public void addToContainer(Object c, Object constraints) {
		myPanel.add((JComponent)c);
	}
}
