package com.dexels.navajo.queuemanager;

import java.io.File;

import com.dexels.navajo.queuemanager.api.InputContext;
import com.dexels.navajo.queuemanager.api.QueueContext;

public interface QueueManager {

	public void setScriptDir(File scriptDir);

	public void setQueueContext(QueueContext queueContext);

	public void flushCache();

	public void flushCache(String service);

	public String resolve(InputContext in, String script)
			throws NavajoSchedulingException;

}