package com.dexels.navajo.tipi.connectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiHeadlessComponentImpl;

public abstract class TipiBaseConnector extends TipiHeadlessComponentImpl
		implements TipiConnector {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBaseConnector.class);
	
	private static final long serialVersionUID = 6781839214790284978L;

	@Override
	public final Navajo doTransaction() throws TipiBreakException, TipiException {
		return doTransaction(null, null, null);
	}

	@Override
	public final Navajo doTransaction(String service) throws TipiBreakException,
			TipiException {
		return doTransaction(null, service, null);
	}

@Override
	public Navajo doTransaction(Navajo n, String service)
			throws TipiBreakException, TipiException {
		return doTransaction(n, service, null);
	}

	protected Navajo injectNavajo(String service, Navajo n)
			throws TipiBreakException {
		if (myContext == null) {
			
			logger.error("No context found while injecting: " + service);
			return null;
		} else {
			myContext.injectNavajo(service, n);
			return n;
		}
	}

	@Override
	public Object createContainer() {
		return null;
	}


}
