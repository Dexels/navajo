/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.docker.swingimpl.swing;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.javadocking.dock.SplitDock;

public class TipiSwingSplitDock extends SplitDock {
	private static final long serialVersionUID = 115222378793765950L;
	private final TipiDataComponentImpl myComponent;
	
	public TipiSwingSplitDock(TipiDataComponentImpl dock){
		myComponent = dock;
	}
	
	@Override
	public void setDividerLocation(final int dividerLocation) {
		myComponent.runSyncInEventThread(new Runnable(){
			@Override
			public void run(){
				TipiSwingSplitDock.super.setDividerLocation(dividerLocation);
			}
		});		
	}
	
	
}
