package com.dexels.navajo.server.internal;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class LocalNavajoContext extends NavajoContext implements ClientContext {

	private LocalClient localClient;
	
	public void setLocalClient(LocalClient c) {
		localClient = c;
	}

	public void clearLocalClient(LocalClient c) {
		localClient = null;
	}

	
	
	@Override
	public void callService(String service, Navajo input)
			throws ClientException {
		if(input ==null) {
			input = NavajoFactory.getInstance().createNavajo();
			input.addHeader(NavajoFactory.getInstance().createHeader(input,service, getUsername(),getPassword(), -1));
		} else {
			if(input.getHeader()==null) {
				input.addHeader(NavajoFactory.getInstance().createHeader(input,service,getUsername(),getPassword(), -1));
			} else {
				input.getHeader().setRPCName(service);
				input.getHeader().setRPCUser(getUsername());
				input.getHeader().setRPCPassword(getPassword());
			}
		}
		try {
			Navajo result = localClient.call(input);
			result.getHeader().setRPCName(service);
			putNavajo(service, result);
			
		} catch (FatalException e) {
			throw( new ClientException(0, -1, "Error calling local client",e));
		}

	}

	@Override
	public void callService(String service) throws ClientException {
		callService(service,null);
		
	}

}
