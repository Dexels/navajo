package com.dexels.navajo.rhino;

import java.io.IOException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.TmlRunnable;

public abstract class BasicRunnable implements TmlRunnable {

	private boolean committed;
	// private long scheduledAt;
	// private Scheduler scheduler;
	private boolean aborted = false;

	@Override
	public boolean isCommitted() {
		return committed;
	}

	@Override
	public void setCommitted(boolean b) {
		committed = b;
	}

	@Override
	public void setScheduledAt(long currentTimeMillis) {
	}

	@Override
	public Navajo getInputNavajo() throws IOException {
		return null;
	}

	@Override
	public boolean isAborted() {
		return aborted;
	}

	@Override
	public void abort(String reason) {
		aborted = true;
	}

}
