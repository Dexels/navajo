/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JProgressBar;

public class TipiSwingProgressBar extends JProgressBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6423583567654250575L;
	public static String STRING_ORIENTATION = "stringOrientation";

	public void setStringOrientation(String orientation) {
		String old = getStringOrientation();
		if ("vertical".equals(orientation)) {
			setOrientation(VERTICAL);
		}
		if ("horizontal".equals(orientation)) {
			setOrientation(HORIZONTAL);
		}
		firePropertyChange(STRING_ORIENTATION, old, orientation);
	}

	public String getStringOrientation() {
		if (getOrientation() == VERTICAL) {
			return "vertical";
		} else {
			return "horizontal";
		}

	}
}
