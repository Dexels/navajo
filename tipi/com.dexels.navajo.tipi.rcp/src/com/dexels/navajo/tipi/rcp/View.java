package com.dexels.navajo.tipi.rcp;

import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.rcp.application.RcpViewApplicationInstance;

public class View extends ViewPart {
	public static final String ID = "com.dexels.navajo.tipi.rcp.view";
	
	private final static Logger logger = LoggerFactory.getLogger(View.class);

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		RcpViewApplicationInstance rvai = new RcpViewApplicationInstance(parent);
		File install = new File("/Users/frank/Documents/workspace-indigo/TipiLessonOne");
		rvai.getCurrentContext().setTipiInstallationFolder(install);
		try {
			rvai.getCurrentContext().switchToDefinition("init");
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
}