package com.dexels.navajo.tipi.vaadin.touch.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;

public class TipiHorizontalComponentGroup extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -5043895594246857632L;

	@Override
	public Object createContainer() {
		HorizontalComponentGroup nm = new HorizontalComponentGroup();
		return nm;
	}

	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	           getVaadinContainer().setCaption( (String) object);
	        }
	}
}
