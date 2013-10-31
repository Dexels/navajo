package com.dexels.navajo.tipi.components.core;

public abstract class TipiHeadlessDataComponentImpl extends
		TipiDataComponentImpl {

	private static final long serialVersionUID = 2009309562917964579L;

	@Override
	public void runSyncInEventThread(Runnable r) {
		r.run();
	}

	@Override
	public void runAsyncInEventThread(Runnable r) {
		r.run();
	}

	@Override
	public Object createContainer() {
		return null;
	}
}
