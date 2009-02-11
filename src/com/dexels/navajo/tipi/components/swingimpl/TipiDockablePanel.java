package com.dexels.navajo.tipi.components.swingimpl;

import java.net.URL;

import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingDockingPanel;


public class TipiDockablePanel extends TipiPanel{
	TipiSwingDockingPanel myContainer;	
//	@Override
	public Object createContainer() {
		myContainer = new TipiSwingDockingPanel();
		return myContainer;
	}
	
}
