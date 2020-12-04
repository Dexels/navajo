/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.instance;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.connectors.TipiConnector;

public class LocalTipiConnector implements TipiConnector {

	private final LocalClient localClient;
	private TipiContext tipiContext;
	
	private final static Logger logger = LoggerFactory
			.getLogger(LocalTipiConnector.class);
	
	public LocalTipiConnector(LocalClient lc) {
		this.localClient = lc;
	}
	@Override
	public Set<String> getEntryPoints() {
		return null;
	}

	@Override
	public String getDefaultEntryPoint() {
		return null;
	}

	@Override
	public Navajo doTransaction(Navajo n, String service, String destination)
			throws TipiBreakException, TipiException {
		try {
			if(n==null) {
				n = NavajoFactory.getInstance().createNavajo();
			}
			Header h = n.getHeader();
			if(h==null) {
				h = NavajoFactory.getInstance().createHeader(n, service, "", "", -1);
				n.addHeader(h);
			} else {
				h.setRPCName(service);
			}
			String localeCode = tipiContext.getApplicationInstance().getLocaleCode();
			if (localeCode!=null) {
				  h.setHeaderAttribute("locale", localeCode);
			  }
			String subLocale = tipiContext.getApplicationInstance().getSubLocaleCode();
			  if (subLocale!=null) {
				 h.setHeaderAttribute("sublocale", subLocale);
			  }
			final Navajo result = localClient.call(n);
			return result;
		} catch (FatalException e) {
			logger.error("Error: ", e);
			return null;
		}
		
	}

	@Override
	public Navajo doTransaction(Navajo n, String service, Integer retries)
			throws TipiBreakException, TipiException {
			return doTransaction(n, service, (String) null);

	}

	@Override
	public Navajo doTransaction(String service) throws TipiBreakException,
			TipiException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		return doTransaction(n, service, 0);
	}

	@Override
	public Navajo doTransaction() throws TipiBreakException, TipiException {
		return null;
	}

	@Override
	public String getConnectorId() {
		return "local";
	}

	@Override
	public void setContext(TipiContext tipiContext) {
		this.tipiContext = tipiContext;
	}

}
