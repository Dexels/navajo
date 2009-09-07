package com.dexels.navajo.client;

public class SystemInfo {

	int cpuCount;
	long maxMem;
	String os;
	String osVersion;
	String javaVersion;
	String osArch;
	
	String compactInfo;
	
	private volatile static SystemInfo instance = null;
	
	private void setCompactInfo() {
		compactInfo = javaVersion + "," + os + "," + osVersion + "," + osArch + "," + maxMem + "," + cpuCount;
	}
	
	private SystemInfo () {
		Runtime rt = java.lang.Runtime.getRuntime();
		cpuCount = rt.availableProcessors();
		maxMem = ( rt.maxMemory() / 1024 );
		os = System.getProperty("os.name");
		osVersion = System.getProperty("os.version");
		javaVersion = System.getProperty("java.version");
		osArch = System.getProperty("os.arch");
		setCompactInfo();
	}
	
	private SystemInfo (String s) {
		
		javaVersion = s.split(",")[0];
		os = s.split(",")[1];
		osVersion = s.split(",")[2];
		osArch = s.split(",")[3];
		maxMem = Integer.parseInt( s.split(",")[4] );
		cpuCount = Integer.parseInt( s.split(",")[5] );
		setCompactInfo();
		
	}
	
	private SystemInfo (int failed) {
		javaVersion = "unknown";
		os = "[Failed to get runtime]";
		osVersion = "unknown";
		osArch = "unknown";
		maxMem = -1;
		cpuCount = 1;
		setCompactInfo();
	}
	
	public static SystemInfo getSystemInfo() {

		if ( instance == null ) {
			try {
				instance = new SystemInfo();
			} catch (Throwable t) { // Could not get runtime
				instance = new SystemInfo(-1);
			}
		}

		return instance;
	}

	public int getCpuCount() {
		return cpuCount;
	}

	public long getMaxMem() {
		return maxMem;
	}

	public String getOs() {
		return os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getJavaVersion() {
		return javaVersion;
	}

	public String getOsArch() {
		return osArch;
	}
	
	/**
	 * Returns SystemInfo in compact string.
	 */
	public String toString() {
		return compactInfo;
	}
	
	public static SystemInfo parseSystemInfoString(String s) {
		SystemInfo info = new SystemInfo(s);
		return info;
	}
	
	public static void main(String [] args) {
		SystemInfo info = SystemInfo.getSystemInfo();
		System.err.println(info);
		SystemInfo info2 = SystemInfo.parseSystemInfoString(info.toString());
		System.err.println(info2);
	}
}
