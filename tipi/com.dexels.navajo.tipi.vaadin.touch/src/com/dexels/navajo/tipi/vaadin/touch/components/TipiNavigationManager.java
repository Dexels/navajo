package com.dexels.navajo.tipi.vaadin.touch.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public class TipiNavigationManager extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -5043895594246857632L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiNavigationManager.class);
	
	@Override
	public Object createContainer() {
		NavigationManager nm = new NavigationManager();
//		nm.setHeight(getMainWindow().getHeight(), getMainWindow().getHeightUnits());
		
		return nm;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	           getVaadinContainer().setCaption( (String) object);
	        }
	}

	
	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer,
			Component component, Object constraints) {
		NavigationManager nm = (NavigationManager)currentContainer;
		if("noback".equals(constraints)) {
			NavigationView nv = (NavigationView)component;
			nv.getNavigationBar().getLeftComponent().setVisible(false);
			nm.removeAllComponents();
		}
		nm.navigateTo(component);
		}

	@Override
	public void removeFromContainer(Object c) {
		NavigationManager nm = (NavigationManager)getContainer();
		NavigationView nv = (NavigationView)c;
		Component top = nm.getCurrentComponent();
		if(nv==top) {
			nm.navigateBack();
		}
//		nm.removeComponent(nv);
	}

	@Override
	protected synchronized void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		NavigationManager nm = (NavigationManager)getContainer();
		if(name.equals("navigateBack")) {
			nm.navigateBack();
			return;
		}
		if(name.equals("navigateTo")) {
			TipiComponent tc = (TipiComponent) compMeth.getEvaluatedParameter("target", event);
			nm.navigateTo((Component) tc.getContainer());
			return;
		}
		super.performComponentMethod(name, compMeth, event);
	}
	
	
}
