package com.dexels.navajo.server.embedded;

import org.eclipse.jetty.server.Server;

import com.dexels.navajo.client.context.NavajoContext;

public class ServerInstance {
	private String projectName;
	private int port;
	
	private NavajoContext context;
	private Server jettyServer;

}
