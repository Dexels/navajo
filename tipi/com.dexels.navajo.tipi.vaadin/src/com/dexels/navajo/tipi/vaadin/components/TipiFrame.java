package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TipiFrame extends TipiVaadinComponentImpl{

	private static final long serialVersionUID = -8797775838239790407L;
	private Window mainWindow;
	private VerticalLayout layout;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiFrame.class);

	@Override
	public Object createContainer() {
		VaadinTipiContext context = (VaadinTipiContext) getContext();
		Application app = context.getApplication();
		this.mainWindow = app.getMainWindow();
		this.layout = new VerticalLayout();
		this.layout.setMargin(false);
		this.layout.setSizeFull();
		this.mainWindow.setContent(layout);
		logger.debug("Size: "+mainWindow.getWidth()+" ; "+mainWindow.getHeight());
		return this.mainWindow;
	}

	
	@Override
	public Component getActualVaadinComponent() {
		return this.layout;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		super.addToContainer(c, constraints);
	}
	
	@Override
	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	          mainWindow.setCaption( (String) object);
	        }
	}
}
