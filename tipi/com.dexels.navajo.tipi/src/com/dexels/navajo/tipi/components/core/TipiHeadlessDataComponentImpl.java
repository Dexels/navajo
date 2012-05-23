package com.dexels.navajo.tipi.components.core;

public abstract class TipiHeadlessDataComponentImpl extends
		TipiDataComponentImpl {

	private static final long serialVersionUID = 2009309562917964579L;

	public void runSyncInEventThread(Runnable r) {
		r.run();
	}

	public void runAsyncInEventThread(Runnable r) {
		r.run();
	}

	public Object createContainer() {
		return null;
	}
}
