package com.dexels.navajo.tipi.vaadin.touch.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

public class TipiMutableTable extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 7327677377846980006L;
	private CssLayout mainlayout;

	@Override
	public Object createContainer() {
		mainlayout = new CssLayout();
		Button edit = new Button();
		VerticalComponentGroup group = new VerticalComponentGroup();
		group.setCaption("tralala");
		mainlayout.addComponent(edit);
		mainlayout.addComponent(group);

		return mainlayout;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	           getVaadinContainer().setCaption( (String) object);
	        }
	}
}
