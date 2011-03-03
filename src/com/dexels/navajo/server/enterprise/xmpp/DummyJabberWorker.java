package com.dexels.navajo.server.enterprise.xmpp;

public class DummyJabberWorker implements JabberWorkerInterface {

	public String getAgentId(String nickName) {
		return "unknownapp";
	}

	public String getPostmanURL() {
		return "";
	}

	public void configJabber(String jabberServer, String jabberPort, String jabberService, String bootstrapUrl) {
		// TODO Auto-generated method stub
		
	}

	public String getJabberPort() {
		return "-1";
	}

	public String getJabberServer() {
		return "Server not available";
	}

	public String getJabberService() {
		return "Service not available";
	}

	@Override
	public void terminate() {
		
	}

}
