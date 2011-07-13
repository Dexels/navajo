package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Window;

public class TipiFrame extends TipiVaadinComponentImpl {

	private Window mainWindow;

	@Override
	public Object createContainer() {
		VaadinTipiContext context = (VaadinTipiContext) getContext();
		TipiVaadinApplication app =  (TipiVaadinApplication) context.getApplicationInstance();
		this.mainWindow = app.getMainWindow();
//		mainWindow.setSizeFull();
//		mainWindow.setWidth("640px");
//		mainWindow.setHeight("480px");
		return this.mainWindow;
	}

	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	          mainWindow.setCaption( (String) object);
	        }
	}
}
