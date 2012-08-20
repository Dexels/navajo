package com.dexels.navajo.studio.script.plugin;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.document.Navajo;

/**
 * @author frank
 *
 */
public interface ServerInstance {

	public int getPort();

	public void startServer(final String projectName);

	public void stopServer();

	public NavajoContext getNavajoContext();
	
	public Navajo callService(Navajo n, String service) throws ClientException;
}