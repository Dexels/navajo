/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
