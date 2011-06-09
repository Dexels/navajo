package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.VerticalLayout;

public class TipiPanel extends TipiVaadinComponentImpl {

	@Override
	public Object createContainer() {
		return new VerticalLayout();
	}

}
