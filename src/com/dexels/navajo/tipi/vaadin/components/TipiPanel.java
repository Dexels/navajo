package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

public class TipiPanel extends TipiVaadinComponentImpl {

	@Override
	public Object createContainer() {
		return new VerticalLayout();
	}

	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		super.addToVaadinContainer(currentContainer, component, constraints);
		component.setWidth("100%");
	}

	
}
