package com.dexels.navajo.tipi.components.rich;

import com.dexels.navajo.rich.components.FlipPanel;
import com.dexels.navajo.rich.components.LushContainer;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiRichPanel extends TipiPanel {
	private LushContainer myPanel;
	
	public Object createContainer() {
	  myPanel = new LushContainer();
	  myPanel.setOpaque(false);
	  return myPanel;
  }
	
	
}
