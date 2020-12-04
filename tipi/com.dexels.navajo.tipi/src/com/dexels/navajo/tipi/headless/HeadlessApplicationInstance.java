/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.headless;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiContextListener;

import tipi.BaseTipiApplicationInstance;

public class HeadlessApplicationInstance extends BaseTipiApplicationInstance {

	private URL contextUrl;

	@Override
	public TipiContext createContext() throws IOException {
		return null;
	}

	@Override
	public String getDefinition() {
		return null;
	}

	@Override
	public void setEvalUrl(URL context, String relativeUri) {
		//ignore
	}

	@Override
	public void setContextUrl(URL contextUrl) {
		this.contextUrl = contextUrl;

	}

	@Override
	public URL getContextUrl() {
		return contextUrl;
	}

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public void close() {

	}

	@Override
	public void addTipiContextListener(TipiContextListener t) {

	}

}
