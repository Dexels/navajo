package com.dexels.navajo.tipi.rcp;

import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.rcp.application.RcpViewApplicationInstance;

public class View extends ViewPart {
	public static final String ID = "com.dexels.navajo.tipi.rcp.view";


	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		RcpViewApplicationInstance rvai = new RcpViewApplicationInstance(parent);
		File install = new File("/Users/frank/Documents/workspace-indigo/TipiLessonOne");
		rvai.getCurrentContext().setTipiInstallationFolder(install);
		try {
			rvai.getCurrentContext().switchToDefinition("init");
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
}