package com.dexels.navajo.tipi.connectors;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;

public class ResourceNavajoConnector extends TipiBaseConnector {

	private static final long serialVersionUID = 8280248652467748667L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ResourceNavajoConnector.class);
	// assume a load:
	@Override
	public void doTransaction(Navajo input, String service)
			throws TipiBreakException, TipiException {
		throw new TipiException(
				"Please supply a service and a destination. Navajo input is ignored");
	}

	@Override
	protected void setComponentValue(String name, Object object) {

		super.setComponentValue(name, object);
	}

	@Override
	public String getConnectorId() {
		return "resource";
	}

	@Override
	public void doTransaction(Navajo n, String service, String destination)
			throws TipiBreakException, TipiException {
		try {
			InputStream is = myContext.getGenericResourceLoader()
					.getResourceStream(service);
			if (is == null) {
				throw new TipiException("Resource not found: " + service);
			}
			Navajo result = NavajoFactory.getInstance().createNavajo(is);
			is.close();
			myContext.loadNavajo(result, destination);

		} catch (IOException e) {
			logger.error("Error: ",e);
		}

	}

	@Override
	public Set<String> getEntryPoints() {
		return null;
	}

	@Override
	public String getDefaultEntryPoint() {
		return null;
	}

}
