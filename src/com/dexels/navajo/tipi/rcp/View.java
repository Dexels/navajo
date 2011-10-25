package com.dexels.navajo.tipi.rcp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import com.dexels.navajo.tipi.rcp.application.RcpViewApplicationInstance;

public class View extends ViewPart {
	public static final String ID = "com.dexels.navajo.tipi.rcp.view";


	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		RcpViewApplicationInstance rvai = new RcpViewApplicationInstance(parent);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
}