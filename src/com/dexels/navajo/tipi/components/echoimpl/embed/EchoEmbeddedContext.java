package com.dexels.navajo.tipi.components.echoimpl.embed;

import java.io.*;
import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.echoimpl.*;
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

public class EchoEmbeddedContext extends EchoTipiContext {
	TipiEchoStandaloneToplevel top = new TipiEchoStandaloneToplevel();
	
	public EchoEmbeddedContext(TipiEchoInstance instance, EchoTipiContext parentContext) {
		super(instance,parentContext);

		setDefaultTopLevel(top);
		getDefaultTopLevel().setContext(this);
		parseRequiredIncludes();
		processRequiredIncludes();

	
	}

	public EchoEmbeddedContext(TipiEchoInstance instance, EchoTipiContext parentContext,String tipiDefinition[], boolean debugMode, String[] definitionName, List<String> libraries, String resourceBaseDirectory)
			throws TipiException, IOException {
		this(instance,parentContext);
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
