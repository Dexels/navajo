package com.dexels.navajo.server.enterprise.xmpp;

public interface JabberWorkerInterface {

	public String getAgentId(String nickName);
	public String getPostmanURL();
	public void configJabber(String jabberServer, String jabberPort, String jabberService, String bootstrapUrl);
	public String getJabberServer();
	public String getJabberPort();
	public String getJabberService();
	public void terminate();
}
