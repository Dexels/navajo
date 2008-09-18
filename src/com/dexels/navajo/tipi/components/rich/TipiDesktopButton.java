package com.dexels.navajo.tipi.components.rich;

import com.dexels.navajo.rich.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiDesktopButton extends TipiButton {
	private DesktopButton myButton;

	public Object createContainer() {
		myButton = new DesktopButton();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myButton;
	}
}
