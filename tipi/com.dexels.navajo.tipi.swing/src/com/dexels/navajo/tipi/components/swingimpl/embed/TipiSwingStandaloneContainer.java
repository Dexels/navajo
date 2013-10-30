package com.dexels.navajo.tipi.components.swingimpl.embed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import navajo.ExtensionDefinition;
import tipiswing.SwingTipiApplicationInstance;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiStandaloneToplevelContainer;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.swingclient.UserInterface;

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

public class TipiSwingStandaloneContainer implements
		TipiStandaloneToplevelContainer {

	private SwingEmbeddedContext embeddedContext = null;
	private final List<String> libraries = new ArrayList<String>();
	private UserInterface ui = null;
//	private SwingTipiContext parentContext = null;

	public TipiSwingStandaloneContainer(SwingTipiApplicationInstance instance,
			SwingTipiContext parentContext) {
		embeddedContext = new SwingEmbeddedContext(instance, parentContext);
//		this.parentContext = parentContext;
	}

	public void setUserInterface(UserInterface u) {
		ui = u;
		if (embeddedContext != null) {
			embeddedContext.setUserInterface(u);
		}
	}

	@Override
	public void loadDefinition(String tipiPath, String definitionName,
			String resourceBaseDirectory, ExtensionDefinition ed)
			throws IOException, TipiException {
		// logger.debug("Loading def: "+definitionName+" tipipath: "+tipiPath+" resbase: "+resourceBaseDirectory);
		embeddedContext = new SwingEmbeddedContext(
				(SwingTipiApplicationInstance) getContext()
						.getApplicationInstance(),
				(SwingTipiContext) getContext(), new String[] { tipiPath },
				false, new String[] { definitionName }, libraries,
				resourceBaseDirectory, ed);
		if (ui != null) {
			embeddedContext.setUserInterface(ui);
		}
	}

	public List<String> getListeningServices() {
		if (embeddedContext != null) {
			return embeddedContext.getListeningServices();
		}
		return null;
	}

	@Override
	public void loadClassPathLib(String location) {
		libraries.add(location);
	}

	@Override
	public void loadNavajo(Navajo nav, String method) throws TipiException,
			TipiBreakException {
		embeddedContext.loadNavajo(nav, method);
	}

	@Override
	public TipiContext getContext() {
		return embeddedContext;
	}

	@Override
	public void shutDownTipi() {
		embeddedContext.shutdown();
	}

}
