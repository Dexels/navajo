package com.dexels.navajo.script.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;

public class SimpleScheduler implements Scheduler {
    private final static Logger logger = LoggerFactory.getLogger(SimpleScheduler.class);

	private final RequestQueue normalPool;
	
	public SimpleScheduler(boolean lowprio) {
        normalPool = ThreadPoolRequestQueue.create(this, "async", Thread.NORM_PRIORITY, 50);
        
	}

	// public void cancel(TmlRunnable myRunner) {
//  
//}

    public boolean checkNavajo(Navajo input) {
		return true;
	}

	@Override
	public String getSchedulingStatus() {
		return "SimpleScheduler: " + normalPool.getActiveRequestCount() + "/" + normalPool.getMaximumActiveRequestCount() + " (" + normalPool.getQueueSize() + ")";
	}

	@Override
	public int getTimeout() {
		return 0;
	}

	@Override
	public void run(TmlRunnable myRunner) {
		myRunner.run();
	}


//	public void runFinished(TmlRunnable tr) {
//	}

	@Override
	public void shutdownScheduler() {
	}

	@Override
	public void submit(TmlRunnable myRunner, boolean priority)
			throws IOException {
		normalPool.submit(myRunner);
	}

	@Override
	public RequestQueue getDefaultQueue() {
		return normalPool;
	}

    @Override
    public RequestQueue getQueue(String queueid) {
        if (queueid.equals("normalPool")) {
            return normalPool;
        }
        logger.info("Cannot find queue {} - returning normal", queueid);
        return normalPool;
    }

    @Override
    public void submit(TmlRunnable myRunner, String queueid) {
        normalPool.submit(myRunner);
        
    }

}
