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
