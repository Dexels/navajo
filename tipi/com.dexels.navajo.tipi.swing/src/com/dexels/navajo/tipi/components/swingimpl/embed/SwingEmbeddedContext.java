package com.dexels.navajo.tipi.components.swingimpl.embed;

import java.io.IOException;
import java.util.List;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipiswing.SwingTipiApplicationInstance;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiUserInterface;
import com.dexels.navajo.tipi.swingclient.SwingClient;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class SwingEmbeddedContext extends SwingTipiContext {
	private static final long serialVersionUID = -9108175308764124437L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(SwingEmbeddedContext.class);
	
	TipiSwingStandaloneToplevel top = new TipiSwingStandaloneToplevel();

	public SwingEmbeddedContext(SwingTipiApplicationInstance instance,
			SwingTipiContext parentContext) {
		super(instance,null, parentContext,parentContext.systemPropertyMap);
		if (SwingClient.getUserInterface() == null) {
			SwingTipiUserInterface stui = new SwingTipiUserInterface(this);
			SwingClient.setUserInterface(stui);
		}
		setDefaultTopLevel(top);
		getDefaultTopLevel().setContext(this);
		// parseRequiredIncludes();
		// processRequiredIncludes();
		// appendIncludes(coreExtensionList);
		// appendIncludes(mainExtensionList);
		// appendIncludes(optionalExtensionList);

	}

	public SwingEmbeddedContext(SwingTipiApplicationInstance instance,
			SwingTipiContext parentContext, String tipiDefinition[],
			boolean debugMode, String[] definitionName, List<String> libraries,
			String resourceBaseDirectory, ExtensionDefinition ed)
			throws TipiException, IOException {
		this(instance, parentContext);
		for (int i = 0; i < definitionName.length; i++) {
			loadDefinition(tipiDefinition[i], definitionName[i], ed);
		}
	}

	public void loadDefinition(String def, String name, ExtensionDefinition ed)
			throws XMLParseException, IOException, TipiException {
		// public void parseStream(InputStream in, String sourceName, boolean
		// studioMode) throws IOException, XMLParseException, TipiException {
		parseStream(getTipiResourceStream(def),  ed);
		switchToDefinition(name);
	}

	@Override
	public void clearTopScreen() {
		top.removeAllChildren();
	}

	@Override
	public void doExit() {
		logger.debug("Exit in embedded");
		shutdown();
	}

}
