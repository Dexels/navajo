package com.dexels.navajo.server.enterprise.xmpp;

public class DummyJabberWorker implements JabberWorkerInterface {

	public String getAgentId(String nickName) {
		return "unknownapp";
	}

}
