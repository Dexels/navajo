package com.dexels.navajo.tipi.components.rich;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.rich.components.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiRichPanel extends TipiPanel {
	private LushContainer myPanel;
	private JPanel actualPanel = new JPanel();
	private GridBagLayout gridBagLayout;
	private Insets insets = new Insets(10, 10, 10, 10);;

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

	public void setComponentValue(final String name, final Object object) {
		if (name.equals("indentx")) {
			runSyncInEventThread(new Runnable() {
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

				public void run() {
					actualPanel.add((Component) c, constraints);
				}
			});
		} catch (Throwable e) {
			throw new RuntimeException("Illegal constraint while adding object: " + c + " to component: " + getPath()
					+ " with constraint: " + constraints);
		}
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				actualPanel.setLayout((LayoutManager) layout);
			}
		});
	}

}
