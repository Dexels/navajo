/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.rich;

import javax.swing.JComponent;

import com.dexels.navajo.rich.components.PeelPanel;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiPeelPanel extends TipiPanel {

	private static final long serialVersionUID = 8431646615101260935L;
	private PeelPanel myPanel;
//	private int speed = 750;

	public TipiPeelPanel() {

	}

	@Override
	public Object createContainer() {
		myPanel = new PeelPanel();
		myPanel.setOpaque(false);
		return myPanel;
	}

	@Override
	public void addToContainer(final Object c, Object constraints) {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				myPanel.addComponent((JComponent) c);
			}
		});
	}

	@Override
	public void removeFromContainer(Object c) {
		myPanel.removeComponent((JComponent) c);
	}

	@Override
	public void setContainerLayout(Object layout) {
		// nop
	}

	

	public static void main(String[] args) {
		new TipiPeelPanel();
	}

}
