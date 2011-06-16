package com.dexels.navajo.rhino;

public abstract class ScriptFinishHandler implements Runnable {

	private ScriptEnvironment scriptEnvironment;

	public ScriptFinishHandler() {
	}

	public ScriptEnvironment getScriptEnvironment() {
		return scriptEnvironment;
	}

	public void setScriptEnvironment(ScriptEnvironment scriptEnvironment) {
		this.scriptEnvironment = scriptEnvironment;
	}

}
