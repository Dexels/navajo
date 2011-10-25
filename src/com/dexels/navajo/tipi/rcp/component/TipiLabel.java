package com.dexels.navajo.tipi.rcp.component;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

import com.dexels.navajo.tipi.rcp.component.base.TipiSwtComponentImpl;

public class TipiLabel extends TipiSwtComponentImpl {

	private static final long serialVersionUID = 6043715402795758785L;

	@Override
	public Widget createComponent(Composite parent) {
		Label l = new Label(parent,SWT.NORMAL);
		l.setText("Traaalalala");
		return l;
	}


}
