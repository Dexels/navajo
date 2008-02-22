package com.dexels.navajo.tipi.components.rich;

import java.net.URL;

import com.dexels.navajo.rich.components.DesktopButton;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.TipiButton;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;

public class TipiDesktopButton extends TipiButton {
	private DesktopButton myButton;
	private URL icon;
	
	public Object createContainer(){		
		myButton = new DesktopButton();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myButton;
	}
}
