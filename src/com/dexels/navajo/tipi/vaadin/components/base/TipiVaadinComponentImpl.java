package com.dexels.navajo.tipi.vaadin.components.base;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public abstract class TipiVaadinComponentImpl extends TipiDataComponentImpl {

	
	private static final long serialVersionUID = -304628775000480212L;
	protected ComponentContainer layoutComponent;
	
	
	
	@Override
	public void setContainer(Object c) {
		super.setContainer(c);
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not remove non-vaadin component from component: "+c);
		}
		Component comp = getVaadinContainer();
		comp.setDebugId(getPath());
	}

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

	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("style")) {
			getActualVaadinComponent().addStyleName(""+object);
		}
		if(name.equals("visible")) {
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
	
	protected Resource getResource(Object any) {
		return getVaadinApplication().getResource(any);
	}
}
