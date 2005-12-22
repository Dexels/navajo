package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.TipiComponent;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
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

	private Column myContainer;

	public TipiSwitchPanel() {
	}

	public Object createContainer() {
		myContainer = new Column();
		return myContainer;
	}

	 public void addToContainer(Object o, Object contraints){
		Component c = (Component)o;
		System.err.println("My childcount: "+getChildCount());
		if (getChildCount()>1) {
			c.setVisible(false);
		} else {
			System.err.println("\n\n\n ARRRR FOUNT AN OPJECT! "+o);
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
		// if (name.equals("placement")) {
		// final String sel = (String) object;
		// setTabPlacement(sel);
		// }
		/** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
	}
}
