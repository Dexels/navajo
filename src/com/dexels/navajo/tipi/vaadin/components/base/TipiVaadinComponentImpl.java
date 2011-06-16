package com.dexels.navajo.tipi.vaadin.components.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public abstract class TipiVaadinComponentImpl extends TipiDataComponentImpl {

	
	protected ComponentContainer layoutComponent;
	
	// TODO Deal with layout at some point, ignore for now
	@Override
	public void setContainerLayout(Object layout) {
		this.layoutComponent = (ComponentContainer) layout;
		super.setContainerLayout(layout);
	}

	@Override
	public void removeFromContainer(Object c) {
		super.removeFromContainer(c);
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not remove non-vaadin component from component: "+c);
		}

		Component comp = getVaadinContainer();
		if(comp instanceof ComponentContainer) {
			ComponentContainer cc = (ComponentContainer)comp;
			cc.removeComponent((Component) c);
		} else {
			throw new IllegalArgumentException("Can not remove n component from non-vaadin container: "+comp);
		}	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not add non-vaadin component to component: "+c);
		}

		Component comp = getVaadinContainer();
		if(comp instanceof ComponentContainer) {
			addToVaadinContainer((ComponentContainer)comp, (Component)c, constraints);
		} else {
			throw new IllegalArgumentException("Can not add n component to non-vaadin container: "+comp);
		}
		
	}
	
	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		currentContainer.addComponent(component);
		
	}
	
	@SuppressWarnings("serial")
	public StreamResource getResource(Object u) {
		 if(u==null) {
			 return null;
		 }
		 InputStream is = null;
		 if(u instanceof URL) {
			 System.err.println("URL: "+u);
			 try {
				is = ((URL) u).openStream();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		 }
		 if(u instanceof Binary) {
			 is = ((Binary) u).getDataAsStream();
		 }
		 if(is==null) {
			 return null;
		 }
		 final InputStream stream = is; 
		 StreamResource.StreamSource s = new StreamResource.StreamSource() {
			
			@Override
			public InputStream getStream() {
				return stream;
			}
		};
		StreamResource sr = new StreamResource(s, "unknown", getVaadinApplication());
		return sr;
	}

	public Component getVaadinContainer() {
		return (Component) getContainer();
	}

	public TipiVaadinApplication getVaadinApplication() {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return c.getVaadinApplication();
	}
}
