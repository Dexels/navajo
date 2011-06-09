package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.HorizontalLayout;

public class TipiToolbar extends TipiVaadinComponentImpl {

	@Override
	public Object createContainer() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth("100%");
		return horizontalLayout;
	}

}
