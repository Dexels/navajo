package com.dexels.navajo.server.enterprise.queue;

public interface RequestResponseQueueInterface {

	public void send(Queable handler, int maxretries) throws Exception;
	public void setQueueOnly(boolean b);
}
