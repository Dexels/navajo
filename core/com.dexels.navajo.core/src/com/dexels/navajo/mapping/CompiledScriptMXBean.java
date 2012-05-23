package com.dexels.navajo.mapping;

public interface CompiledScriptMXBean {

	public String getScriptName();
	public String getThreadName();
	public String getUser();
	public String getAccessId();
	public String getStackTrace();
	public boolean isWaiting();
	public String getLockName();
	public String getLockOwner();
	public String getLockClass();
	public long getRunningTime();
	public void kill();
	
}
