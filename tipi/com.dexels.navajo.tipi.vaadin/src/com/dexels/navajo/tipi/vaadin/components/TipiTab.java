/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.components.impl.TipiSwingTab;


public class TipiTab extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 8550459130365507667L;
	private TipiSwingTab myTab;

	@Override
	public Object createContainer() {
		myTab = new TipiSwingTab();
		return myTab;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("tabIcon")) {
			// Don't know why. The propertyChangeListener seems to miss it when
			// the icon is being set to null.
			myTab.setTabIcon(getResource(object));
		}
		super.setComponentValue(name, object);
	}

}
