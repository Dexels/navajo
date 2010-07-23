package com.dexels.navajo.listeners;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.dexels.navajo.document.Navajo;

public class SimpleScheduler implements Scheduler {

	private final ThreadPoolExecutor normalPool;
	
	public SimpleScheduler() {
		normalPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
	}
	
	public void cancel(TmlRunnable myRunner) {
		
	}

	public boolean checkNavajo(Navajo input) {
		return true;
	}

	public String getSchedulingStatus() {
		return "SimpleScheduler: " + normalPool.getActiveCount() + "/" + normalPool.getPoolSize() + " (" + normalPool.getQueue().size() + ")";
	}

	public int getTimeout() {
		return 0;
	}

	public void run(TmlRunnable myRunner) {
	}

	public void runFinished(TmlRunnable tr) {
	}

	public void shutdownScheduler() {
	}

	public void submit(TmlRunnable myRunner, boolean priority)
			throws IOException {
		normalPool.submit(myRunner);
	}

}
