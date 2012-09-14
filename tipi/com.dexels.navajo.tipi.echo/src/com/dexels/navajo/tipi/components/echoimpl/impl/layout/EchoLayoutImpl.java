package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.Component;

import com.dexels.navajo.tipi.TipiComponent;

public abstract class EchoLayoutImpl {
	
	protected final ArrayList childComponents = new ArrayList();
	protected final Map constraints = new HashMap();
	protected TipiComponent myParent = null;
	protected Component myComponent = null;

	
	public void setParentComponent(TipiComponent c) {
		myParent = c;
	}

	public void setParentContainer(Component c) {
		myComponent = c;
	}

	
	public void addChildComponent(Component c, Object constraint) {
		childComponents.add(c);
//		logger.info("Adding component: "+c.hashCode()+ "constraint to echo layout: "+constraint);
		constraints.put(c, constraint);
//		logger.info("Constraint size: "+constraints.size());
	}
	
	public Component getLayoutComponent() {
		return myComponent;
	}
	
	public abstract void commitToParent();

}
