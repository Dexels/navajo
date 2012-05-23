package com.dexels.navajo.tipi.swingx;

import org.jdesktop.swingx.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXPanel extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 495952398319709388L;

	@Override
	public Object createContainer() {
		JXPanel p = new JXPanel();
		return p;
	}

}
