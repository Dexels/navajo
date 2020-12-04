/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JLabel;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndCapable;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndManager;

public class TipiSwingPropertyDescriptionLabel extends JLabel implements
		TipiDndCapable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9012073326960746871L;
	private final TipiDndManager myDndManager;

	public TipiSwingPropertyDescriptionLabel(TipiSwingPropertyComponent tspc,
			TipiComponent tc) {
		myDndManager = new TipiDndManager(this, tc);
	}

	@Override
	public TipiDndManager getDndManager() {
		// TODO Auto-generated method stub
		return myDndManager;
	}

}
