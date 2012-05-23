package com.dexels.navajo.tipi.components.core;

public abstract class TipiHeadlessComponentImpl extends TipiComponentImpl {

	private static final long serialVersionUID = -8955575091536997297L;

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
