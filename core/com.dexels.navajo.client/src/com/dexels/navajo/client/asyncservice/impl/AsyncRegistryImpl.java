package com.dexels.navajo.client.asyncservice.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.asyncservice.AsyncRegistry;
import com.dexels.navajo.client.asyncservice.ServerAsyncListener;
import com.dexels.navajo.document.Navajo;

public class AsyncRegistryImpl implements AsyncRegistry {

	private Map<String,ServerAsyncRunner> asyncRunnerMap = Collections.synchronizedMap(new HashMap<String,ServerAsyncRunner>());
	  
	private ClientInterface clientInterface = null;
	
	
	public ClientInterface getClientInterface() {
		return clientInterface;
	}


	public void setClientInterface(ClientInterface clientInterface) {
		this.clientInterface = clientInterface;
	}

	public void clearClientInterface(ClientInterface clientInterface) {
		this.clientInterface = null;
	}

	public AsyncRegistryImpl() {
		// TODO Auto-generated constructor stub
	}


	  private final void registerAsyncRunner(String id, ServerAsyncRunner sar) {
	    asyncRunnerMap.put(id, sar);
	  }

	  /* (non-Javadoc)
	 * @see com.dexels.navajo.client.asyncservice.impl.AsyncRegistry#deRegisterAsyncRunner(java.lang.String)
	 */
	  @Override
	public final void deRegisterAsyncRunner(String id) {
	    asyncRunnerMap.remove(id);
	  }
	  
	  /* (non-Javadoc)
	 * @see com.dexels.navajo.client.asyncservice.impl.AsyncRegistry#doServerAsyncSend(com.dexels.navajo.document.Navajo, java.lang.String, com.dexels.navajo.client.asyncservice.ServerAsyncListener, java.lang.String, int)
	 */
	  @Override
	public final void doServerAsyncSend(Navajo in, String method, ServerAsyncListener listener, String clientId, int pollingInterval) throws ClientException {
	    ServerAsyncRunner sar = new ServerAsyncRunner(clientInterface,this, in, method, listener, clientId, pollingInterval);
	    String serverId = sar.startAsync();
	    registerAsyncRunner(serverId, sar);

	  }


}
