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
