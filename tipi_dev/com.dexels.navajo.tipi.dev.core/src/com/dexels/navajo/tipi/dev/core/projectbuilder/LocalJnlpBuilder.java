package com.dexels.navajo.tipi.dev.core.projectbuilder;

import com.dexels.navajo.tipi.dev.core.util.XMLElement;

public class LocalJnlpBuilder extends BaseJnlpBuilder {

	@Override
	public String getJnlpName() {
		return "LocalJnlp.jnlp";
	}

	@Override
	protected void appendProxyResource(XMLElement resources, String repository,
			String mainExtension, boolean useVersioning) {
		// do nothing
		
	}





}
