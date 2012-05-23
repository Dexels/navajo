package com.dexels.navajo.tipi.rcp.component;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.dexels.navajo.tipi.rcp.component.base.TipiSwtComponentImpl;

public class TipiButton extends TipiSwtComponentImpl {

	private static final long serialVersionUID = 6043715402795758785L;

	@Override
	public Widget createComponent(Composite parent) {
		Button l = new Button(parent,SWT.PUSH);
		l.setText("Traaalalala");
		return l;
	}
}
