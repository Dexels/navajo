package com.dexels.navajo.rhino;

public abstract class ContinuationHandler implements Runnable {
	private Object continuation;
	private Object functionResult;
	private ScriptEnvironment env;

	public Object getContinuation() {
		return continuation;
	}

	public void setContinuation(Object continuation) {
		this.continuation = continuation;
	}

	public ScriptEnvironment getEnv() {
		return env;
	}

	public void setEnv(ScriptEnvironment env) {
		this.env = env;
	}

	public void setFunctionResult(Object functionResult) {
		this.functionResult = functionResult;
	}

	public Object getFunctionResult() {
		return functionResult;
	}

	public void resumeScript() {
		env.continueScript(continuation, functionResult);
	}

}
