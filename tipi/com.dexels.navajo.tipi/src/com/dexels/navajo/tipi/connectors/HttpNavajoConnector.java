/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.connectors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;

public class HttpNavajoConnector extends TipiBaseConnector {
    private static final Logger logger = LoggerFactory.getLogger(HttpNavajoConnector.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -8531849428188689877L;

	@Override
	public Navajo doTransaction(Navajo input, String service, Integer retries)
			throws TipiBreakException, TipiException {
		if (input == null) {
			input = NavajoFactory.getInstance().createNavajo();
		}
		try {
			// Don't let NavajoClient touch your original navajo! It will mess
			// things up.
			Navajo nn = input.copy();
			myContext.fireNavajoSent(input, service);
			Map<String, Object> s = new HashMap<String, Object>();
			s.put("service", service);
			performTipiEvent("onServiceSent", s, false);
			Navajo result = myContext.getClient().doSimpleSend(nn, service, retries);
			performTipiEvent("onServiceReceived", s, false);
			if (result.getHeader() != null) {
				result.getHeader().setHeaderAttribute("sourceScript",
						result.getHeader().getRPCName());
			}
			return result;
		} catch (ClientException e) {
			throw new TipiException("Error calling service: " + service, e);
		}
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("server")) {
			myContext.getClient().setServerUrl((String) object);
		}
		if (name.equals("username")) {
			myContext.getClient().setUsername((String) object);
	        logger.info("Setting username {} for Client {}", (String) object, myContext.getClient().hashCode());

		}
		if (name.equals("password")) {
			myContext.getClient().setPassword((String) object);
		}

		super.setComponentValue(name, object);
	}

	@Override
	public String getConnectorId() {
		return "http";
	}

	@Override
	public Navajo doTransaction(Navajo n, String service, String destination)
			throws TipiBreakException, TipiException {
		return doTransaction(n, service, 0);
	}

	@Override
	public Set<String> getEntryPoints() {
		return null;
	}

	@Override
	public String getDefaultEntryPoint() {
		return "InitClub";
	}

}
