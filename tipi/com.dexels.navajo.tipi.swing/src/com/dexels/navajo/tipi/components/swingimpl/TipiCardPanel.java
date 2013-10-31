package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;

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
 * @author not attributable
 * @version 1.0
 */

public class TipiCardPanel extends TipiSwingDataComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6348750707086562806L;
	private final CardLayout myCardLayout = new CardLayout();
	private final List<TipiComponent> myComponentList = new ArrayList<TipiComponent>();
	private final Map<Object, TipiComponent> myComponentMap = new HashMap<Object, TipiComponent>();

	private TipiComponent selectedComponent = null;

	public TipiCardPanel() {
	}

	@Override
	public Object createContainer() {
		JPanel jt = new JPanel();
		jt.setLayout(myCardLayout);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return jt;
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {

		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				TipiComponent tc = myComponentMap.get(c);
				if (tc != null) {
					((Container) getContainer()).add((Component) c, tc.getId());
					doLayout();
				}
			}
		});

	}

	@Override
	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("selected")) {
			String sel = (String) object;
			selectedComponent = getTipiComponent(sel);
			myCardLayout.show((Container) getContainer(),
					selectedComponent.getId());
			// ( (JTabbedPane) getContainer()).setSelectedComponent( (Component)
			// (tc.getContainer()));

		}
		if (name.equals("selectedindex")) {
			Integer sel = (Integer) object;
			selectedComponent = myComponentList.get(sel.intValue());
			myCardLayout.show((Container) getContainer(),
					selectedComponent.getId());
		}
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("selected")) {
			return selectedComponent;
		}
		if (name.equals("selectedindex")) {
			return new Integer(myComponentList.indexOf(selectedComponent));
		}
		return super.getComponentValue(name);
	}

	@Override
	public void addComponent(TipiComponent c, TipiContext context, Object td) {
		if (c.getContainer() != null) {
			myComponentList.add(c);
			myComponentMap.put(c.getContainer(), c);
			if (selectedComponent == null) {
				selectedComponent = c;
			}
		}
		super.addComponent(c, context, td);
	}

	@Override
	public void removeChild(TipiComponent child) {
		myComponentList.remove(child);
		myComponentMap.remove(child);
		if (selectedComponent == child) {
			selectedComponent = null;
		}
		super.removeChild(child);
	}

}
