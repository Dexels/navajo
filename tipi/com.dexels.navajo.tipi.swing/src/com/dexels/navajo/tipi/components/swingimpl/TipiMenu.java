/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;

import javax.swing.JSeparator;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingMenu;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingMenuItem;

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
public class TipiMenu extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -918633606782923037L;
	private TipiSwingMenu myMenu = null;

	@Override
	public Object createContainer() {
		myMenu = new TipiSwingMenu();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myMenu;
	}

	@Override
	public void addToContainer(final Object menu, final Object item) {
		if (TipiSwingMenuItem.class.isInstance(menu)) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myMenu.add((TipiSwingMenuItem) menu);
				}
			});
		}
		if (TipiSwingMenu.class.isInstance(menu)) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myMenu.add((TipiSwingMenu) menu);
				}
			});
		}
		if (JSeparator.class.isInstance(menu)) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myMenu.add((JSeparator) menu);
				}
			});
		}

	}

	@Override
	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				myMenu.remove((Component) c);
			}
		});
	}
	//

}
