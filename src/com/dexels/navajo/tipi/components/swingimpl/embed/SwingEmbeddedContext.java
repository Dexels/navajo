package com.dexels.navajo.tipi.components.swingimpl.embed;

import java.io.*;
import java.util.*;

import navajo.ExtensionDefinition;

import tipi.SwingTipiApplicationInstance;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.tipixml.*;

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
	TipiSwingStandaloneToplevel top = new TipiSwingStandaloneToplevel();
	
	public SwingEmbeddedContext(SwingTipiApplicationInstance instance, SwingTipiContext parentContext) {
		super(instance, parentContext);
		if (SwingClient.getUserInterface() == null) {
			SwingTipiUserInterface stui = new SwingTipiUserInterface(this);
			SwingClient.setUserInterface(stui);
		}
		setDefaultTopLevel(top);
		getDefaultTopLevel().setContext(this);
//		parseRequiredIncludes();
//		processRequiredIncludes();
//		appendIncludes(coreExtensionList);
//		appendIncludes(mainExtensionList);
//		appendIncludes(optionalExtensionList);

	
	}

	public SwingEmbeddedContext(SwingTipiApplicationInstance instance,SwingTipiContext parentContext,String tipiDefinition[], boolean debugMode, String[] definitionName, List<String> libraries, String resourceBaseDirectory,ExtensionDefinition ed)
			throws TipiException, IOException {
		this(instance,parentContext);
		for (int i = 0; i < definitionName.length; i++) {
			loadDefinition(tipiDefinition[i], definitionName[i],ed);
		}
	}

	public void loadDefinition(String def, String name, ExtensionDefinition ed) throws XMLParseException, IOException, TipiException {
		// public void parseStream(InputStream in, String sourceName, boolean
		// studioMode) throws IOException, XMLParseException, TipiException {
		parseStream(getTipiResourceStream(def), name, false, ed);
		switchToDefinition(name);
	}

	public void clearTopScreen() {
		top.removeAllChildren();
	}

	public void exit() {
		System.err.println("Exit in embedded");
		shutdown();
	}

}
