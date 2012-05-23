package com.dexels.navajo.tipi.actions.adapters;

import java.io.Serializable;
import java.util.Map;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.actions.TipiActionFactory;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public abstract class BaseActions implements Serializable {

	private static final long serialVersionUID = -1962785060353421381L;

	protected TipiComponent myComponent;

	protected TipiAction invocation;
	protected TipiEvent event;

	public TipiComponent getComponent() {
		return myComponent;
	}

	public void setComponent(TipiComponent myComponent) {
		this.myComponent = myComponent;
	}

	public void performAction(String actionName, Map<String, Object> parameters)
			throws TipiException, TipiBreakException, TipiSuspendException {
		TipiActionFactory aa = getComponent().getContext().getActionManager()
				.getActionFactory(actionName);
		TipiAction rr = aa.createAction(getComponent());
		rr.loadParameters(parameters);
		rr.performAction(event, invocation, -1);
	}

	public void setInvocation(TipiAction invocation) {
		this.invocation = invocation;
	}

	public void setEvent(TipiEvent event) {
		this.event = event;
	}
}
