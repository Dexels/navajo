package com.dexels.navajo.tipi.vaadin.actions.base;

import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.terminal.Resource;

public abstract class TipiVaadinActionImpl extends TipiAction {

	
	private static final long serialVersionUID = 5997392321011697285L;

	protected Resource getResource(Object any) {
		return getVaadinApplication().getResource(any);
	}

	public TipiVaadinApplication getVaadinApplication() {
		VaadinTipiContext c = (VaadinTipiContext) getContext();
		return c.getVaadinApplication();
	}
}
