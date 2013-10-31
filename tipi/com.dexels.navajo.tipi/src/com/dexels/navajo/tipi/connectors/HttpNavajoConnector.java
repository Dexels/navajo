package com.dexels.navajo.tipi.connectors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;

public class HttpNavajoConnector extends TipiBaseConnector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8531849428188689877L;

	@Override
	public void doTransaction(Navajo input, String service)
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
			Navajo result = myContext.getClient().doSimpleSend(nn, service);
			performTipiEvent("onServiceReceived", s, false);
			if (result.getHeader() != null) {
				result.getHeader().setHeaderAttribute("sourceScript",
						result.getHeader().getRPCName());
			}
			myContext.loadNavajo(result, service);

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
		}
		if (name.equals("password")) {
			myContext.getClient().setPassword((String) object);
		}
		if (name.equals("retryAttempt")) {
			myContext.getClient().setRetryAttempts((Integer) object);
		}

		super.setComponentValue(name, object);
	}

	@Override
	public String getConnectorId() {
		return "http";
	}

	@Override
	public void doTransaction(Navajo n, String service, String destination)
			throws TipiBreakException, TipiException {
		doTransaction(n, service);
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
