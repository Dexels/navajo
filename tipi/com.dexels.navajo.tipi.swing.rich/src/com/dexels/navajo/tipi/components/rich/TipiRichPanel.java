/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.rich;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.dexels.navajo.rich.components.LushContainer;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiRichPanel extends TipiPanel {

	private static final long serialVersionUID = -4426671631853552634L;
	private LushContainer myPanel;
	private JPanel actualPanel = new JPanel();
	private GridBagLayout gridBagLayout;
	private Insets insets = new Insets(10, 10, 10, 10);

	@Override
	public Object createContainer() {
		myPanel = new LushContainer();
		myPanel.setOpaque(false);
		actualPanel.setOpaque(false);

		gridBagLayout = new GridBagLayout();
		myPanel.setLayout(gridBagLayout);
		myPanel.add(actualPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets,
				0, 0));
		return myPanel;
	}

	private void resetLayout() {
		gridBagLayout.setConstraints(actualPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, insets, 0, 0));

	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("indentx")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					int ii = ((Integer) object).intValue();
					insets = new Insets(ii, ii, ii, ii);
					resetLayout();
				}
			});
			return;
		}

	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		try {
			runSyncInEventThread(new Runnable() {

				@Override
				public void run() {
					actualPanel.add((Component) c, constraints);
				}
			});
		} catch (Throwable e) {
			throw new RuntimeException("Illegal constraint while adding object: " + c + " to component. "
					+ " with constraint: " + constraints); 
		}
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				actualPanel.setLayout((LayoutManager) layout);
			}
		});
	}

}
