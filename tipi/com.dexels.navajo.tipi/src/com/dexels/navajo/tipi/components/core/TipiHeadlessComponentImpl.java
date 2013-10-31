package com.dexels.navajo.tipi.components.core;

public abstract class TipiHeadlessComponentImpl extends TipiComponentImpl {

	private static final long serialVersionUID = -8955575091536997297L;

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
