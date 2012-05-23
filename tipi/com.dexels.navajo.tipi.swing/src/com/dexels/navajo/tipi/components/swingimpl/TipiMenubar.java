package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingMenuBar;

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
public class TipiMenubar extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6279031914998782201L;
	private TipiSwingMenuBar myMenuBar;

	public void removeFromContainer(final Object c) {
		super.removeFromContainer(c);
		myMenuBar.repaint();
	}

	public Object createContainer() {
		myMenuBar = new TipiSwingMenuBar();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myMenuBar;
	}
}
