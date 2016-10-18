package com.dexels.navajo.server.api;


public interface NavajoServerContext {

	public String getInstallationPath();
	public String getOutputPath();
	public String getTempPath();
	public String getDeplyoment();
}