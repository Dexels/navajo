package com.dexels.navajo.tipi.vaadin.components.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.dexels.navajo.tipi.vaadin.components.io.InputStreamSource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public abstract class TipiVaadinComponentImpl extends TipiDataComponentImpl {

	
	private static final long serialVersionUID = -304628775000480212L;
	protected ComponentContainer layoutComponent;
	private InputStream stream;
	
	
	
	@Override
	public void setContainer(Object c) {
		super.setContainer(c);
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not remove non-vaadin component from component: "+c);
		}
		Component comp = getVaadinContainer();
		comp.setDebugId(getPath());
	}

	// TODO Deal with layout at some point, ignore for now
	@Override
	public void setContainerLayout(Object layout) {
		this.layoutComponent = (ComponentContainer) layout;
		super.setContainerLayout(layout);
		ComponentContainer oo = (ComponentContainer) getVaadinContainer();
		oo.addComponent(this.layoutComponent);
		this.layoutComponent.setSizeFull();
	}

	@Override
	public void removeFromContainer(Object c) {
//		super.removeFromContainer(c);
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not remove non-vaadin component from component: "+c);
		}
		if(this.layoutComponent!=null) {
			removeFromLayoutContainer((Component)c);
		}
		Component comp = getVaadinContainer();
		if(comp instanceof ComponentContainer) {
			ComponentContainer cc = (ComponentContainer)comp;
			cc.removeComponent((Component) c);
		} else {
			throw new IllegalArgumentException("Can not remove n component from non-vaadin container: "+comp);
		}	}

	
	private void removeFromLayoutContainer(Component c) {
		this.layoutComponent.removeComponent(c);
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not add non-vaadin component to component: "+c);
		}
		if(this.layoutComponent!=null) {
			System.err.println("Layout detected, adding to layout...");
			addToLayoutContainer((Component)c,constraints);
			return;
		}
		Component comp = getVaadinContainer();
		if(comp instanceof ComponentContainer) {
			addToVaadinContainer((ComponentContainer)comp, (Component)c, constraints);
		} else {
			throw new IllegalArgumentException("Can not add n component to non-vaadin container: "+comp);
		}
		
	}
	
	private void addToLayoutContainer(Component c, Object constraints) {
		getLayout().addToLayout(c, constraints);
//		this.layoutComponent.add
	}

	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		currentContainer.addComponent(component);
		
	}
	
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
		 InputStreamSource s = new InputStreamSource(is);
		 StreamResource sr = new StreamResource(s, u.toString(), getVaadinApplication());
		return sr;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("style")) {
			getActualVaadinComponent().addStyleName(""+object);
		}
		if(name.equals("visible")) {
			System.err.println("SETTTING VISIBLE TO: "+object);
			Boolean b = (Boolean) object;
			getActualVaadinComponent().setVisible(b);
		}
		if(name.equals("enabled")) {
			Boolean b = (Boolean) object;
			getActualVaadinComponent().setEnabled(b);
		}

		super.setComponentValue(name, object);
	}
	
	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("style")) {
			return getActualVaadinComponent().getStyleName();
		}
		return super.getComponentValue(name);
	}
	
	public Component getVaadinContainer() {
		return (Component) getContainer();
	}

	public Component getActualVaadinComponent() {
		return (Component) getActualComponent();
	}

	public TipiVaadinApplication getVaadinApplication() {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return c.getVaadinApplication();
	}
}
