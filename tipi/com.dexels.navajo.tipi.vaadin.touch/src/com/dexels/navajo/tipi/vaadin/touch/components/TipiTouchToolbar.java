/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.touch.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public class TipiTouchToolbar extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -5043895594246857632L;
	private Toolbar toolbar;

	@Override
	public Object createContainer() {
		toolbar = new Toolbar();
		return toolbar;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	}
	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer,
			Component component, Object constraints) {
		toolbar.addComponent(component);
		
	}
}
