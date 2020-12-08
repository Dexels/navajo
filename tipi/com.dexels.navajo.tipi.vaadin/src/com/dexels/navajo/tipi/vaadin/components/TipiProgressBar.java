/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.ProgressIndicator;


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
public class TipiProgressBar extends TipiVaadinComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2061730327911948158L;
	private ProgressIndicator myProgressBar = null;

	@Override
	public Object createContainer() {
		myProgressBar = new ProgressIndicator();

		myProgressBar.setValue(0);
		return myProgressBar;
	}

	@Override
	public Object getContainer() {
		return myProgressBar;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myProgressBar.setCaption((String) object);
				}
			});
			return;
		}
		if (name.equals("value")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					int value = (int) Float.parseFloat("" + object);
					myProgressBar.setValue(value/100);
					myProgressBar.setCaption("" + value + "%");
				}
			});
			return;
		}
		if (name.equals("orientation")) {
			return;
		}
		if (name.equals("indeterminate")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myProgressBar.setIndeterminate(((Boolean) object)
							.booleanValue());
					}
			});
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return myProgressBar.getCaption();
		}
		if (name.equals("value")) {
			Float f = (Float) myProgressBar.getValue();
			return f.intValue();
		}
		if (name.equals("orientation")) {
			
		}
		return super.getComponentValue(name);
	}
}
