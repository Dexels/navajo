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

	public Object createContainer() {
		myMenu = new TipiSwingMenu();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myMenu;
	}

	public void addToContainer(final Object menu, final Object item) {
		if (TipiSwingMenuItem.class.isInstance(menu)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					myMenu.add((TipiSwingMenuItem) menu);
				}
			});
		}
		if (TipiSwingMenu.class.isInstance(menu)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					myMenu.add((TipiSwingMenu) menu);
				}
			});
		}
		if (JSeparator.class.isInstance(menu)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					myMenu.add((JSeparator) menu);
				}
			});
		}

	}

	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myMenu.remove((Component) c);
			}
		});
	}
	//

}
