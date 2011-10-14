package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.components.impl.TipiSwingTab;


public class TipiTab extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 8550459130365507667L;
	private TipiSwingTab myTab;

	public Object createContainer() {
		myTab = new TipiSwingTab();
		return myTab;
	}

	protected void setComponentValue(String name, Object object) {
		System.err.println("Setting: " + name + " to: " + object);
		if (name.equals("tabIcon")) {
			// Don't know why. The propertyChangeListener seems to miss it when
			// the icon is being set to null.
			myTab.setTabIcon(getResource(object));
		}
		super.setComponentValue(name, object);
	}

}
