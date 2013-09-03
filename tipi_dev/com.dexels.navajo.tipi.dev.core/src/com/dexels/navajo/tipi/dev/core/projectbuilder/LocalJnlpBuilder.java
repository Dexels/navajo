package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.extensionmanager.ExtensionManager;
import com.dexels.navajo.tipi.dev.core.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLElement;

public class LocalJnlpBuilder extends BaseJnlpBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(LocalJnlpBuilder.class);
	

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
