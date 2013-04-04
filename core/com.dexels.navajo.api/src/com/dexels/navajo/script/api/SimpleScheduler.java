package com.dexels.navajo.script.api;

import java.io.IOException;

import com.dexels.navajo.document.Navajo;

public class SimpleScheduler implements Scheduler {

	private final RequestQueue normalPool;
	
	public SimpleScheduler() {
		normalPool = ThreadPoolRequestQueue.create(this, "default", Thread.NORM_PRIORITY, 50);
	}
	
//	public void cancel(TmlRunnable myRunner) {
//		
//	}

	public boolean checkNavajo(Navajo input) {
		return true;
	}

	public String getSchedulingStatus() {
		return "SimpleScheduler: " + normalPool.getActiveRequestCount() + "/" + normalPool.getMaximumActiveRequestCount() + " (" + normalPool.getQueueSize() + ")";
	}

	public int getTimeout() {
		return 0;
	}

	public void run(TmlRunnable myRunner) {
		myRunner.run();
	}


//	public void runFinished(TmlRunnable tr) {
//	}

	public void shutdownScheduler() {
	}

	public void submit(TmlRunnable myRunner, boolean priority)
			throws IOException {
		normalPool.submit(myRunner);
	}

	public RequestQueue getDefaultQueue() {
		return normalPool;
	}

}
