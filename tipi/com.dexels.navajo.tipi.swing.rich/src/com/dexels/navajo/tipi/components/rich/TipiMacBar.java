/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.rich;

import javax.swing.JComponent;

import com.dexels.navajo.rich.components.MacBar;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiMacBar extends TipiPanel {

	private static final long serialVersionUID = 4021914601392479783L;
	private MacBar myPanel;

	@Override
	public Object createContainer() {
		myPanel = new MacBar();
		return myPanel;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		myPanel.add((JComponent) c);
	}
}
