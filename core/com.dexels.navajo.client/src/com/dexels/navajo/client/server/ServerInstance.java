package com.dexels.navajo.client.server;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.document.Navajo;


public interface ServerInstance {

	public int getPort();

	public int startServer(final String projectName);

	public void stopServer();

	public ClientContext getNavajoContext();
	
	public Navajo callService(Navajo n, String service) throws ClientException;
}