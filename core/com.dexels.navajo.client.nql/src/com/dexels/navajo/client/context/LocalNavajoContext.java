package com.dexels.navajo.client.context;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class LocalNavajoContext extends NavajoContext implements ClientContext {

	
	private static final Logger logger = LoggerFactory.getLogger(LocalNavajoContext.class);
	
	private static final String DEFAULT_TENANT = "DEFAULT_TENANT";
	private final Map<String,LocalClient> localClients = new HashMap<>();
	
	public void addLocalClient(LocalClient c, Map<String,Object> settings) {
		if(settings==null) {
			logger.warn("Missing settings while adding localclient");
			return;
		}
		String tenant = (String) settings.get("instance");
		if(tenant==null) {
			logger.warn("Missing tenant ('instance') while adding localclient, using default");
			localClients.put(DEFAULT_TENANT, c);
			return;
		}
		localClients.put(tenant, c);
	}

	/**
	 * @param c  the LocalClient to remove
	 */
	public void removeLocalClient(LocalClient c, Map<String,Object> settings) {
		if(settings==null) {
			logger.warn("Missing settings while removing localclient");
			return;
		}
		String tenant = (String) settings.get("instance");
		if(tenant==null) {
			logger.warn("Missing tenant ('instance') while removing localclient, removing default");
			localClients.remove(DEFAULT_TENANT);
			return;
		}	
		
		localClients.remove(tenant);
	}

	
	
	@Override
	public void callService(String service,String tenant,String username, String password, Navajo input)
			throws ClientException {
		if(input ==null) {
			input = NavajoFactory.getInstance().createNavajo();
			input.addHeader(NavajoFactory.getInstance().createHeader(input,service, username,password, -1));
		} else {
			if(input.getHeader()==null) {
				input.addHeader(NavajoFactory.getInstance().createHeader(input,service,username,password, -1));
			} else {
				input.getHeader().setRPCName(service);
				input.getHeader().setRPCUser(getUsername());
				input.getHeader().setRPCPassword(getPassword());
			}
		}
		try {
			LocalClient lc = tenant==null?localClients.get(DEFAULT_TENANT):localClients.get(tenant);
			Navajo result = lc.call(input);
			result.getHeader().setRPCName(service);
			putNavajo(service, result);
			
		} catch (FatalException e) {
			throw( new ClientException(0, -1, "Error calling local client",e));
		}

	}

	@Override
	public void callService(String service,String tenant) throws ClientException {
		callService(service,tenant,null,null, null);
		
	}

}
