/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class TipiToolbar extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 4060634934285508125L;
	private HorizontalLayout inner;

	@Override
	public Object createContainer() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		inner = new HorizontalLayout();
//		horizontalLayout.setWidth("100%");
//		horizontalLayout.setMargin(false);
		horizontalLayout.setMargin(true, false, true, false);
		horizontalLayout.addComponent(inner);
		return horizontalLayout;
	}

	@Override
	public Component getActualVaadinComponent() {
		return inner;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
		if(!(c instanceof Component)) {
			throw new IllegalArgumentException("Can not add non-vaadin component to component: "+c);
		}
		Component cc = (Component)c;
		inner.addComponent(cc);
	}
	
}
