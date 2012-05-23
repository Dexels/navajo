package com.dexels.navajo.tipi.swingx;

import org.jdesktop.swingx.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXTaskItem extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = -8283604943121523660L;

	@Override
	public Object createContainer() {
		JXTaskPane p = new JXTaskPane();
		p.setTitle("Unspecified title");
		return p;
	}
}
