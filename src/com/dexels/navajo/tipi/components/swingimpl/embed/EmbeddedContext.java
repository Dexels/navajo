package com.dexels.navajo.tipi.components.swingimpl.embed;

import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.XMLParseException;
import java.io.*;
import java.util.*;

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

public class EmbeddedContext extends SwingTipiContext {
	TipiStandaloneToplevel top = new TipiStandaloneToplevel();
	
	public EmbeddedContext() throws TipiException, IOException {
		if (SwingClient.getUserInterface() == null) {
			SwingTipiUserInterface stui = new SwingTipiUserInterface(this);
			SwingClient.setUserInterface(stui);
		}
		setDefaultTopLevel(top);
		getDefaultTopLevel().setContext(this);
		parseRequiredIncludes();
		processRequiredIncludes();

	
	}

	public EmbeddedContext(String tipiDefinition[], boolean debugMode, String[] definitionName, List libraries, String resourceBaseDirectory)
			throws TipiException, IOException {
		this();
		for (int i = 0; i < definitionName.length; i++) {
			loadDefinition(tipiDefinition[i], definitionName[i]);
		}
	}

	public void loadDefinition(String def, String name) throws XMLParseException, IOException, TipiException {
		// public void parseStream(InputStream in, String sourceName, boolean
		// studioMode) throws IOException, XMLParseException, TipiException {
		parseStream(getTipiResourceStream(def), name, false);
	}

	public void clearTopScreen() {
		top.removeAllChildren();
	}

	public void exit() {
		System.err.println("Exit in embedded");
		shutdown();
	}

}
