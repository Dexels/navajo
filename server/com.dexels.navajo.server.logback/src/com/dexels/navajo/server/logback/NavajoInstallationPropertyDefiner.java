package com.dexels.navajo.server.logback;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.PropertyDefiner;
import ch.qos.logback.core.status.Status;

public class NavajoInstallationPropertyDefiner implements PropertyDefiner {

	private Context context;
	private String navajoInstallationPath;
	
	public NavajoInstallationPropertyDefiner(String navajoInstallationPath) {
		this.navajoInstallationPath = navajoInstallationPath;
	}
	
	@Override
	public void addError(String message) {
		System.err.println("Error? "+message);
	}

	@Override
	public void addError(String message, Throwable t) {
		System.err.println("Errorwith t? "+message);

	}

	@Override
	public void addInfo(String message) {
		System.err.println("Info? "+message);
	}

	@Override
	public void addInfo(String message, Throwable t) {
		System.err.println("Info? t"+message);
	}

	@Override
	public void addStatus(Status status) {
		System.err.println("Status? "+status);
	}

	@Override
	public void addWarn(String message) {
		System.err.println("Warn? "+message);
	}

	@Override
	public void addWarn(String message, Throwable t) {
		System.err.println("Warn? t "+message);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public void setContext(Context cntxt) {
		this.context = cntxt;
	}

	@Override
	public String getPropertyValue() {
		return navajoInstallationPath;
	}

}
