/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.actions.base;

import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.vaadin.Application;
import com.vaadin.terminal.Resource;

public abstract class TipiVaadinActionImpl extends TipiAction {

	
	private static final long serialVersionUID = 5997392321011697285L;

	protected Resource getResource(Object any) {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return c.getResource(any);
	}

//	public TipiVaadinApplication getVaadinApplication() {
//		VaadinTipiContext c = (VaadinTipiContext) getContext();
//		return c.getVaadinApplication();
//	}
	public Application getApplication() {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return (Application) c.getVaadinApplication();
//		return (Application) this.getApplicationInstance();
	}


}
