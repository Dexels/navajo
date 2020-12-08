/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JTextField;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndCapable;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndManager;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingTextField extends JTextField implements TipiDndCapable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2457090775162843219L;
	private final TipiDndManager myDndManager;

	public TipiSwingTextField(TipiComponent component) {
		myDndManager = new TipiDndManager(this, component);
	}

	@Override
	public void setText(String t) {
		String old = getText();
		if (t == null) {
			if (old == null) {
				return;
			}
		} else {
			if (t.equals(old)) {
				return;
			}

		}
		super.setText(t);
	}

	@Override
	public TipiDndManager getDndManager() {
		return myDndManager;
	}

}
