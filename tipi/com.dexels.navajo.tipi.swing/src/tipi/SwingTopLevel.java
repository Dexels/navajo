package tipi;

import java.io.IOException;


import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiStandaloneToplevelContainer;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.version.ExtensionDefinition;

public class SwingTopLevel implements TipiStandaloneToplevelContainer {

	private SwingTipiContext myContext = null;

	public TipiContext getContext() {
		return myContext;
	}

	public void loadClassPathLib(String location) {

		System.err.println("Adding location. Thought it was deprecated.");
	}

	public void loadNavajo(Navajo nav, String method) throws TipiException,
			TipiBreakException {
		myContext.loadNavajo(nav, method);
	}

	public void shutDownTipi() {
		myContext.exit();
		myContext = null;
	}

	public void loadDefinition(String tipiPath, String definitionName,
			String resourceBaseDirectory, ExtensionDefinition ed)
			throws IOException, TipiException {

	}

}
