package com.dexels.navajo.listener.http.queuemanager.api;

import java.io.File;

import com.dexels.navajo.listener.http.queuemanager.api.InputContext;
import com.dexels.navajo.listener.http.queuemanager.api.QueueContext;

public interface QueueManager {

	public void setScriptDir(File scriptDir);

	public void setQueueContext(QueueContext queueContext);

	public void flushCache();

	public void flushCache(String service);

	public String resolve(InputContext in, String script)
			throws NavajoSchedulingException;

}