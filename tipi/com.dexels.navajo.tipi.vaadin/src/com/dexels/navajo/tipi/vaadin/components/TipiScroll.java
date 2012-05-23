package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Panel;

public class TipiScroll extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 8435237179020695689L;

	@Override
	public Object createContainer() {
		Panel panel = new Panel();
		panel.setScrollable(true);
		return panel;
	}

	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		super.addToVaadinContainer(currentContainer, component, constraints);
		component.setWidth("100%");
	}

	
}
