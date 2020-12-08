/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.docker.swingimpl;

import com.dexels.navajo.tipi.components.docker.swingimpl.swing.TipiSwingDockingPanel;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;


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
