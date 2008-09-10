package com.dexels.navajo.tipi.components.core;

public abstract class TipiHeadlessDataComponentImpl extends TipiDataComponentImpl {
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
