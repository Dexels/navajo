package com.dexels.navajo.tipi.components.rich;

import java.net.*;

import com.dexels.navajo.rich.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiMacLink extends TipiButton {
	private MacLink myButton;
	private URL icon;

	public Object createContainer() {
		myButton = new MacLink();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myButton;
	}
}
