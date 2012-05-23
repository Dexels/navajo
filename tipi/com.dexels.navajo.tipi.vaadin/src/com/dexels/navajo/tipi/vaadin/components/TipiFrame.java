package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TipiFrame extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -8797775838239790407L;
	private Window mainWindow;
	private VerticalLayout layout;

	@Override
	public Object createContainer() {
		VaadinTipiContext context = (VaadinTipiContext) getContext();
		Application app = context.getApplication();
		this.mainWindow = app.getMainWindow();
		this.layout = new VerticalLayout();
		this.layout.setMargin(false);
		this.layout.setSizeFull();
		this.mainWindow.setContent(layout);
		System.err.println("Size: "+mainWindow.getWidth()+" ; "+mainWindow.getHeight());
		return this.mainWindow;
	}

	
	@Override
	public Component getActualVaadinComponent() {
		return this.layout;
	}

	public void addToContainer(Object c, Object constraints) {
		System.err.println("c: "+c);
		super.addToContainer(c, constraints);
	}
	
	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	          mainWindow.setCaption( (String) object);
	        }
	}
}
