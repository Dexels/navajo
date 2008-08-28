package com.dexels.navajo.tipi.connectors;

import java.util.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

public class HttpNavajoConnector extends TipiBaseConnector implements TipiConnector {


	
	public void doTransaction(Navajo input, String service) throws TipiBreakException, TipiException {
		if(input==null) {
			input = NavajoFactory.getInstance().createNavajo();
		}
		try {
			// Don't let NavajoClient touch your original navajo! It will mess things up.
			Navajo nn = input.copy();
			myContext.fireNavajoSent(input, service);
			Map<String,Object> s = new HashMap<String, Object>();
			s.put("service", service);
			performTipiEvent("onServiceSent", s, false);
			Navajo result = NavajoClientFactory.getClient().doSimpleSend(nn, service);
			performTipiEvent("onServiceReceived", s, false);
			if(result.getHeader()!=null) {
				result.getHeader().setHeaderAttribute("sourceScript", result.getHeader().getRPCName());
			}
			myContext.loadNavajo(result, service);

			
		} catch (ClientException e) {
			throw new TipiException("Error calling service: "+service,e);
		}
	}

	protected void setComponentValue(String name, Object object) {
		if(name.equals("server")) {
			NavajoClientFactory.getClient().setServerUrl((String)object);
		}
		if(name.equals("username")) {
			NavajoClientFactory.getClient().setUsername((String)object);
		}
		if(name.equals("password")) {
			NavajoClientFactory.getClient().setPassword((String)object);
		}

		super.setComponentValue(name, object);
	}

	public String getConnectorId() {
		return "http";
	}

	public void doTransaction(Navajo n, String service, String destination) throws TipiBreakException, TipiException {
		doTransaction(n, service);
	}

	public Set<String> getEntryPoints() {
		return null;
	}
	
	public String getDefaultEntryPoint() {
		return "InitClub";
	}

}
