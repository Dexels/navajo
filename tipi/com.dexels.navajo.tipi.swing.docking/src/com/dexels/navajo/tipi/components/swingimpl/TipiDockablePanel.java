package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingDockingPanel;


public class TipiDockablePanel extends TipiPanel{
	private static final long serialVersionUID = -8052043897710973102L;
	TipiSwingDockingPanel myContainer;	
//	@Override
	@Override
	public Object createContainer() {
		myContainer = new TipiSwingDockingPanel();
		return myContainer;
	}
	
}
