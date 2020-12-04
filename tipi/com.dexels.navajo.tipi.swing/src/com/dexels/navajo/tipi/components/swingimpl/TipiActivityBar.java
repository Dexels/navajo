/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.dexels.navajo.tipi.TipiActivityListener;

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
public class TipiActivityBar extends TipiLabel implements TipiActivityListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2210075056492345394L;
	private boolean amIActive = false;
	private ImageIcon busyIcon = null;
	private ImageIcon freeIcon = null;

	public TipiActivityBar() {
	}

	@Override
	public boolean isActive() {
		return amIActive;
	}

	@Override
	public void setActive(final boolean state) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				amIActive = state;
				// setComponentValue("indeterminate", Boolean.valueOf(amIActive));
				if (amIActive) {
					((JLabel) getSwingContainer()).setIcon(busyIcon);
				} else {
					((JLabel) getSwingContainer()).setIcon(freeIcon);
				}
			}
		});
	}

	@Override
	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("freeicon")) {
			freeIcon = getIcon(object);
		}
		if (name.equals("busyicon")) {
			busyIcon = getIcon(object);
		}
	}

	@Override
	public void setActiveThreads(int i) {
		setComponentValue("text", "Active operations: " + i);
	}

	/**
	 * createContainer
	 * 
	 * @return Object
	 * @todo Implement this
	 *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
	 */
	@Override
	public Object createContainer() {
		Object o = super.createContainer();
		myContext.addTipiActivityListener(this);
		return o;
	}
}
