package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.TipiComponent;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Row;
import echopointng.ContainerEx;
import echopointng.TabbedPane;

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

public class TipiSwitchPanel extends TipiEchoDataComponentImpl {

	private Grid myContainer;

	public TipiSwitchPanel() {
	}

	public Object createContainer() {
		myContainer = new Grid(1);
		return myContainer;
	}

	 public void addToContainer(Object o, Object contraints){
		Component c = (Component)o;
		if (getChildCount()>1) {
			c.setVisible(false);
		}
		myContainer.add(c);
	 }
	//
	// public void setContainerLayout(Object l){
	//
	// }

	private void hideAll() {
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent tc = getTipiComponent(i);
			Object o = tc.getContainer();
			if (o!=null && o instanceof Component) {
				((Component)o).setVisible(false);
			}
		}
	}
	 
	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("selected")) {
			hideAll();
			String sel = (String) object;
			final TipiComponent tc = getTipiComponent(sel);
			Object o = tc.getContainer();
			if (o!=null && o instanceof Component) {
				((Component)o).setVisible(true);
			}
		}
		if (name.equals("selectedindex")) {
			final Integer sel = (Integer) object;
			hideAll();
			final TipiComponent tc = getTipiComponent(sel.intValue());
			Object o = tc.getContainer();
			if (o!=null && o instanceof Component) {
				((Component)o).setVisible(true);
			}
		}
		
		if (name.equals("width")) {
			final Integer sel = (Integer) object;
			((Grid)myContainer).setWidth(new Extent(sel.intValue(),Extent.PX));
			((Grid)myContainer).setColumnWidth(0,new Extent(sel.intValue(),Extent.PX));
		}
		// if (name.equals("placement")) {
		// final String sel = (String) object;
		// setTabPlacement(sel);
		// }
		/** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
	}
}
