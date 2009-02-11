package com.dexels.navajo.tipi.components.swingimpl.swing;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.javadocking.dock.SplitDock;

public class TipiSwingSplitDock extends SplitDock {
	private final TipiDataComponentImpl myComponent;
	
	public TipiSwingSplitDock(TipiDataComponentImpl dock){
		myComponent = dock;
	}
	
	@Override
	public void setDividerLocation(final int dividerLocation) {
		myComponent.runSyncInEventThread(new Runnable(){
			public void run(){
				TipiSwingSplitDock.super.setDividerLocation(dividerLocation);
			}
		});		
	}
	
	
}
