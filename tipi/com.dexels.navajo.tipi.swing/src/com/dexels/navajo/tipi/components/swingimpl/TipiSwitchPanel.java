package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiSwitchPanel extends TipiPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444665263726659451L;
	private CardLayout cardLayout;
	private final Map<Object, String> componentMap = new HashMap<Object, String>();
	private String selectedId;
	private Integer selectedIndex;

	public TipiSwitchPanel() {
	}

	@Override
	public Object createContainer() {
		Container c = (Container) super.createContainer();
		cardLayout = new CardLayout();
		c.setLayout(cardLayout);
		return c;
	}

	@Override
	public void addComponent(TipiComponent c, int index, TipiContext context,
			Object td) {
		if (c.getContainer() != null) {
			componentMap.put(c.getContainer(), c.getId());
		}
		super.addComponent(c, index, context, td);
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		final String name = componentMap.get(c);
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				if (name == null) {
					getSwingContainer().add((Component) c, name);
					if (getChildCount() <= 1) {
						selectedIndex = new Integer(0);
						// cardLayout.show(myPanel,name);
						// logger.debug("Showing component: "+name);
					}
				} else {
					getSwingContainer().add((Component) c, name);
				}
				updateSelected();
			}
		});

	}

	@Override
	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("selectedId")) {
			selectedId = (String) object;
			selectedIndex = null;
		}

		updateSelected();
		/** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
	}

	private void updateSelected() {

		TipiComponent tc = null;

		if (selectedIndex != null) {
			tc = getTipiComponent(selectedIndex.intValue());
		}
		if (selectedId != null) {
			tc = getTipiComponent(selectedId);

		}

		if (tc == null) {
			return;
		}
		final TipiComponent tc2 = tc;
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				cardLayout.show(getSwingContainer(), tc2.getId());

			}
		});

	}
}
