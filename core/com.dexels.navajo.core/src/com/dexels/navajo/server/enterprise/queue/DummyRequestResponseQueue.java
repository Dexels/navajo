package com.dexels.navajo.server.enterprise.queue;

public class DummyRequestResponseQueue implements RequestResponseQueueInterface {

	@Override
	public void send(Queuable handler, int maxretries) throws Exception {
		// Do nothing.
	}

	@Override
	public void setQueueOnly(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
