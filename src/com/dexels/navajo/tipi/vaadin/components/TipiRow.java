package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class TipiRow extends TipiVaadinComponentImpl {

	@Override
	public Object createContainer() {
		return new HorizontalLayout();
	}

//	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
//		super.addToVaadinContainer(currentContainer, component, constraints);
//		component.setWidth("100%");
//	}

	
}
