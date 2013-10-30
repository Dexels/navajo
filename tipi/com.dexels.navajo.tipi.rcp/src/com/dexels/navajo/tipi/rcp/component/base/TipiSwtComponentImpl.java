package com.dexels.navajo.tipi.rcp.component.base;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;

public abstract class TipiSwtComponentImpl extends TipiDataComponentImpl {

	private static final long serialVersionUID = -2552659907512739573L;

	@Override
	public Object createContainer() {
		return createComponent((Composite) getParentContainer());
	}

	public abstract Widget createComponent(Composite parent);
}
