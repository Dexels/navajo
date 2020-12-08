/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

	@Override
	public void removeFromContainer(final Object c) {
		super.removeFromContainer(c);
		myMenuBar.repaint();
	}

	@Override
	public Object createContainer() {
		myMenuBar = new TipiSwingMenuBar();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myMenuBar;
	}
}
