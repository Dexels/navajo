/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JMenuBar;

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
public class TipiSwingMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3029022147452817283L;
	private boolean gridFlag = false;
	private boolean selected = false;

	public TipiSwingMenuBar() {
	}

	public void setHighlighted(boolean value) {
		selected = value;
	}

	public boolean isHighlighted() {
		return selected;
	}

	public void showGrid(boolean value) {
		gridFlag = value;
	}

	public boolean isGridShowing() {
		return gridFlag;
	}
}
