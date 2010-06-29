package com.dexels.navajo.listeners;

import java.io.IOException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.FatalException;

public interface TmlRunnable extends Runnable {

	boolean isCommitted();
	void setCommitted(boolean b);
	void setScheduledAt(long currentTimeMillis);
	public void endTransaction() throws IOException;
	
	public Navajo getInputNavajo() throws IOException;
	
	public Scheduler getTmlScheduler();
	public void setTmlScheduler(Scheduler schedule);
	
	public boolean isAborted();
	public void abort();
	
}
