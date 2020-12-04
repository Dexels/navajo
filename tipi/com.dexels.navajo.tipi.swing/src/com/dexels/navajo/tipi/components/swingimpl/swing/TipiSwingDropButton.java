/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.swingimpl.TipiDropButton;

public class TipiSwingDropButton extends TipiSwingButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7360877972097624559L;
	private final TipiDropButton myDropButton;

	public TipiSwingDropButton(TipiDropButton component) {
		super(component);
		myDropButton = component;
	}

	public void setBinaryValue(final Binary b) {

		myDropButton.setBinaryValue(b);
	}
}
