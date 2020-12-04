/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipi;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiStandaloneToplevelContainer;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;

import navajo.ExtensionDefinition;

public class SwingTopLevel implements TipiStandaloneToplevelContainer {

	private SwingTipiContext myContext = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SwingTopLevel.class);
	
	@Override
	public TipiContext getContext() {
		return myContext;
	}

	@Override
	public void loadClassPathLib(String location) {

		logger.debug("Adding location. Thought it was deprecated.");
	}

	@Override
	public void loadNavajo(Navajo nav, String method) throws TipiException,
			TipiBreakException {
		myContext.loadNavajo(nav, method);
	}

	@Override
	public void shutDownTipi() {
		myContext.exit();
		myContext = null;
	}

	@Override
	public void loadDefinition(String tipiPath, String definitionName,
			String resourceBaseDirectory, ExtensionDefinition ed)
			throws IOException, TipiException {

	}

}
