package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.HorizontalLayout;

public class TipiRow extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -8463738923426799145L;

	@Override
	public Object createContainer() {
		return new HorizontalLayout();
	}

//	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
//		super.addToVaadinContainer(currentContainer, component, constraints);
//		component.setWidth("100%");
//	}

	
}
