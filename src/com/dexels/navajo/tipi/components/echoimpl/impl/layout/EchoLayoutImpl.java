package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.util.*;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.components.echoimpl.TipiEchoDataComponentImpl;

import nextapp.echo2.app.Component;

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
//		System.err.println("Adding component: "+c.hashCode()+ "constraint to echo layout: "+constraint);
		constraints.put(c, constraint);
//		System.err.println("Constraint size: "+constraints.size());
	}
	
	public Component getLayoutComponent() {
		return myComponent;
	}
	
	public abstract void commitToParent();

}
