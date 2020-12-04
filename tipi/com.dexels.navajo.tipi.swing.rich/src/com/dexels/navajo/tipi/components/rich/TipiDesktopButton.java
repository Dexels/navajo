/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.rich;

import com.dexels.navajo.rich.components.DesktopButton;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.TipiButton;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;

public class TipiDesktopButton extends TipiButton {
	private static final long serialVersionUID = 7153858395461227871L;
	private DesktopButton myButton;

	@Override
	public Object createContainer() {
		myButton = new DesktopButton();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myButton;
	}
}
