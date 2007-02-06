package com.dexels.navajo.mapping;

public interface AsyncMappableMXBean {
	public String getAccessId();
	public String getWebservice();
	public String getUser();
	public long getStartTime();
	public java.util.Date getStartDate();
	public String  getName();
	public boolean getRunning();
	public boolean getIsFinished();
	public boolean getInterrupt();
	public long getLastAccess();
	public void stop();
	public void interrupt();
	public void resume();
	public boolean isResumed();
	public boolean isStopped();
	public String getClassName();
	public String getVERSION();
}
