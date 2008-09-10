package com.dexels.navajo.tipi.components.core;

public abstract class TipiHeadlessComponentImpl extends TipiComponentImpl {
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
