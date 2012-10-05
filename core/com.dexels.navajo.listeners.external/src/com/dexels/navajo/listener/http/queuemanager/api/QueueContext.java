package com.dexels.navajo.listener.http.queuemanager.api;

import java.util.Set;

public interface QueueContext {

	public double getQueueHealth(String queueName);
	public int getQueueSize(String queueName);
	public Set<String> getQueueNames();

}