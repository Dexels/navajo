package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

public class TipiPanel extends TipiVaadinComponentImpl {
	private static final long serialVersionUID = 5587251071656604056L;

	@Override
	public Object createContainer() {
		Component panel = new VerticalLayout();
		return panel;
	}

	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		super.addToVaadinContainer(currentContainer, component, constraints);
		component.setWidth("100%");
	}


	  public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Component v = getVaadinContainer();

		        if ("logo".equals(name)) {
	                v.setIcon( getResource(object));
		        }

	  }

}
