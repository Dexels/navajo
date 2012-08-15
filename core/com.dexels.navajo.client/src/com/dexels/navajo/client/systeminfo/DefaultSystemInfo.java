package com.dexels.navajo.client.systeminfo;

public class DefaultSystemInfo implements SystemInfoProvider {

	int cpuCount;
	long maxMem;
	String os;
	String osVersion;
	String javaVersion;
	String osArch;
	
	String compactInfo = "[not initialized]";
	
	private void setCompactInfo() {
		compactInfo = javaVersion + "," + os + "," + osVersion + "," + osArch + "," + maxMem + "," + cpuCount;
	}
	
	DefaultSystemInfo() {
		
	}
	
	public final void init () {
		Runtime rt = java.lang.Runtime.getRuntime();
		cpuCount = rt.availableProcessors();
		maxMem = ( rt.maxMemory() / 1024 );
		os = System.getProperty("os.name");
		osVersion = System.getProperty("os.version");
		javaVersion = System.getProperty("java.version");
		osArch = System.getProperty("os.arch");
		setCompactInfo();
	}
	
	private DefaultSystemInfo (String s) {
		
		javaVersion = s.split(",")[0];
		os = s.split(",")[1];
		osVersion = s.split(",")[2];
		osArch = s.split(",")[3];
		maxMem = Integer.parseInt( s.split(",")[4] );
		cpuCount = Integer.parseInt( s.split(",")[5] );
		setCompactInfo();
		
	}
	
	/**
	 * @param failed - Flah to indicate this is the 'failed state' constructor 
	 */
	DefaultSystemInfo (int failed, String msg) {
		javaVersion = msg;
		os = "[Failed to get runtime]";
		osVersion = "unknown";
		osArch = "unknown";
		maxMem = -1;
		cpuCount = 1;
		setCompactInfo();
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#getCpuCount()
	 */
	@Override
	public int getCpuCount() {
		return cpuCount;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#getMaxMem()
	 */
	@Override
	public long getMaxMem() {
		return maxMem;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#getOs()
	 */
	@Override
	public String getOs() {
		return os;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#getOsVersion()
	 */
	@Override
	public String getOsVersion() {
		return osVersion;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#getJavaVersion()
	 */
	@Override
	public String getJavaVersion() {
		return javaVersion;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#getOsArch()
	 */
	@Override
	public String getOsArch() {
		return osArch;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#toString()
	 */
	@Override
	public String toString() {
		return compactInfo;
	}
	
	public SystemInfoProvider parseSystemInfoString(String s) {
		SystemInfoProvider info = new DefaultSystemInfo(s);
		return info;
	}

}
