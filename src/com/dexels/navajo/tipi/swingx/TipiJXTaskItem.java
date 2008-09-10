package com.dexels.navajo.tipi.swingx;

import org.jdesktop.swingx.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXTaskItem extends TipiSwingDataComponentImpl {

	@Override
	public Object createContainer() {
		JXTaskPane p = new JXTaskPane();
		p.setTitle("Unspecified title");
		return p;
	}
}
