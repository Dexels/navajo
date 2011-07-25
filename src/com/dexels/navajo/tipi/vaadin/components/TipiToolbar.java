package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.HorizontalLayout;

public class TipiToolbar extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 4060634934285508125L;

	@Override
	public Object createContainer() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setWidth("100%");
//		horizontalLayout.setMargin(false);
		horizontalLayout.setMargin(true, false, true, false);
		return horizontalLayout;
	}

}
