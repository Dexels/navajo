package com.dexels.navajo.tipi.components.rich;

import java.net.URL;

import com.dexels.navajo.rich.components.DesktopButton;
import com.dexels.navajo.tipi.components.swingimpl.TipiButton;

public class TipiDesktopButton extends TipiButton {
	private DesktopButton myButton;
	private URL icon;
	
	public Object createContainer(){
		myButton = new DesktopButton();
		return myButton;
	}
}
