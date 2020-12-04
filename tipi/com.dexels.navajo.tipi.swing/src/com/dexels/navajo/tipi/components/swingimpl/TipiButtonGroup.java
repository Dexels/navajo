/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.ButtonGroup;

import com.dexels.navajo.tipi.components.core.TipiComponentImpl;

public class TipiButtonGroup extends TipiComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1270734252730614500L;
	private ButtonGroup myButtonGroup = new ButtonGroup();

	@Override
	public Object createContainer() {
		return null;
	}

	public ButtonGroup getButtonGroup() {
		return myButtonGroup;
	}

}
