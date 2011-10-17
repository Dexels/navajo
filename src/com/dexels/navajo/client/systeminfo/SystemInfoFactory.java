package com.dexels.navajo.client.systeminfo;

public class SystemInfoFactory  {

	private volatile static SystemInfoProvider instance = null;

	private SystemInfoFactory() {
		
	}
	
	
	public synchronized static SystemInfoProvider getSystemInfo() {

		if ( instance == null ) {
			try {
				instance = new DefaultSystemInfo();
				instance.init();
			} catch (Throwable t) { // Could not get runtime
				instance = new DefaultSystemInfo(-1, t.getMessage());
			}
		}

		return instance;
	}

	public synchronized static void setSystemInfoProvider(SystemInfoProvider provider) {
		instance = provider;
	}
	
	public static void main(String [] args) {
		SystemInfoProvider info = SystemInfoFactory.getSystemInfo();
		System.err.println(info);
	}
	
	public static void clearInstance() {
		instance = null;
	}
}
