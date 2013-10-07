package com.dexels.navajo.tipi.vaadin.components.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.peter.contextmenu.ContextMenu;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.vaadin.Application;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;

public abstract class TipiVaadinComponentImpl extends TipiDataComponentImpl {

	
	private static final long serialVersionUID = -304628775000480212L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinComponentImpl.class);
	
	protected ComponentContainer layoutComponent;

	protected String style;
	
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
	public void initBeforeBuildingChildren(XMLElement instance,
			XMLElement classdef, XMLElement definition) {
		super.initBeforeBuildingChildren(instance, classdef, definition);
		Component comp = getVaadinContainer();
		if(comp!=null) {
			comp.addStyleName("tipi-"+getComponentType());
		}
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
		if(c instanceof ContextMenu) {
			logger.debug("Register context. Not working yet.");
		}
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not add non-vaadin component to component: "+c);
		}
		if(this.layoutComponent!=null) {
			addToLayoutContainer((Component)c,constraints);
			return;
		}
		Component comp = getVaadinContainer();
		if(comp instanceof ComponentContainer) {
			if(comp instanceof AbstractOrderedLayout && c instanceof MenuBar) {
				
				AbstractOrderedLayout currentContainer = (AbstractOrderedLayout)comp;
				currentContainer.addComponentAsFirst((Component)c);

			} else {
				addToVaadinContainer((ComponentContainer)comp, (Component)c, constraints);
				
			}
		} else {
			throw new IllegalArgumentException("Can not add n component to non-vaadin container: "+comp);
		}
		
	}
	
	private void addToLayoutContainer(Component c, Object constraints) {
		getLayout().addToLayout(c, constraints);
//		this.layoutComponent.add
	}

/**
 * Default way of adding childcomponents to Vaadin based tipi components. Can be overridden.	
 * @param currentContainer
 * @param component
 * @param constraints
 */
	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		currentContainer.addComponent(component);
		
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("style")) {
			getActualVaadinComponent().addStyleName(""+object);
			this.style = ""+object;
		}
		if(name.equals("visible")) {
			Boolean b = (Boolean) object;
			getActualVaadinComponent().setVisible(b);
		}
		if(name.equals("enabled")) {
			Boolean b = (Boolean) object;
			getActualVaadinComponent().setEnabled(b);
		}
		if(name.equals("border")) {
			String b = (String) object;
			getActualVaadinComponent().setCaption(b);
			getActualVaadinComponent().addStyleName("titled");
		}
        if ("icon".equals(name)) {
        	getActualVaadinComponent().setIcon( getResource(object));
        }
        if ("text".equals(name)) {
        	getActualVaadinComponent().setCaption( ""+ object);
        }

		super.setComponentValue(name, object);
	}
	
	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("style")) {
			return getActualVaadinComponent().getStyleName();
		}
		if(name.equals("visible")) {
			return getActualVaadinComponent().isVisible();
		}
		if(name.equals("enabled")) {
			return getActualVaadinComponent().isVisible();
		}
		if(name.equals("border")) {
			return getActualVaadinComponent().getCaption();
		}
		if(name.equals("icon")) {
			// don't think this works TODO
			return getActualVaadinComponent().getIcon();
		}
		if(name.equals("text")) {
			return getActualVaadinComponent().getCaption();
		}
		
		return super.getComponentValue(name);
	}
	
	@Override
    protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
    	super.performComponentMethod(name, compMeth, event);
    	if (name.equals("show") || name.equals("hide"))
    	{
    		throw new UnsupportedOperationException("Vaadin does not support show or hide. Use instantiate and dispose instead.");
    	}
    	if (name.equals("removeStyle"))
    	{
    		Object o = compMeth.getEvaluatedParameterValue("style", event);
    		if (o != null && o.toString() != null){
    			getActualVaadinComponent().removeStyleName(o.toString());
    		}
    		else{
    			logger.error("At removeStyle, style is a required parameter.");
    			throw new TipiBreakException(TipiBreakException.BREAK_BLOCK);
    		}
    	}
    	if(name.equals("addStyle")){
    		Object o = compMeth.getEvaluatedParameterValue("style", event);
    		if(o != null && o.toString() != null){
    			getActualVaadinComponent().addStyleName(o.toString());
    		}else{
    			logger.error("At addStyle, style is a required parameter.");
    			throw new TipiBreakException(TipiBreakException.BREAK_BLOCK);
    		}
    	}
    	if(name.equals("setVisibility")){
    		Object o = compMeth.getEvaluatedParameterValue("visible", event);
    		if (o != null){
    			getActualVaadinComponent().setVisible((Boolean) o);
    			getActualVaadinComponent().setHeight("100%");
    		}
    		else{
    			logger.error("At setVisibility, style is a required parameter.");
    			throw new TipiBreakException(TipiBreakException.BREAK_BLOCK);
    		}
    	}
    }
	public Component getVaadinContainer() {
		Object o = getContainer();
		if(o instanceof Component) {
			return (Component) getContainer();
		} else {
			return null;
		}
	}

	public Component getActualVaadinComponent() {
		return (Component) getActualComponent();
	}

	public Application getVaadinApplication() {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return c.getApplication();
	}
	public VaadinTipiContext getVaadinContext() {
		if (myContext == null) {
			throw new RuntimeException(
					"TipiComponent without context. This is not allowed");
		}
		return (VaadinTipiContext) myContext;
	}

	public Window getMainWindow() {
		return getVaadinApplication().getMainWindow();
	}
	
	protected Resource getResource(Object any) {
		return getVaadinContext().getResource(any);
	}
}
