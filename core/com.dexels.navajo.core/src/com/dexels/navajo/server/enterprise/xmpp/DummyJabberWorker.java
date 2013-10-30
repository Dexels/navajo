package com.dexels.navajo.server.enterprise.xmpp;

public class DummyJabberWorker implements JabberWorkerInterface {

	@Override
	public String getAgentId(String nickName) {
		return "unknownapp";
	}

	@Override
	public String getPostmanURL() {
		return "";
	}

	@Override
	public void configJabber(String jabberServer, String jabberPort, String jabberService, String bootstrapUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJabberPort() {
		return "-1";
	}

	@Override
	public String getJabberServer() {
		return "Server not available";
	}

	@Override
	public String getJabberService() {
		return "Service not available";
	}

	@Override
	public void terminate() {
		
	}

}
