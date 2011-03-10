package com.dexels.navajo.listeners;

public abstract class RequestQueue {

	private final Scheduler myScheduler;
	private final String id;
	
	public RequestQueue(Scheduler myScheduler, String id) {
		this.myScheduler = myScheduler;
		this.id = id;
	}
	
	/**
	 * The unique id of this request queue.
	 * @return
	 */
	public final String getId() {
		return id;
	}
	
	/**
	 * Current size (number of requests) of this request queue.
	 * @return
	 */
	public abstract int getQueueSize();
	
	public abstract int getExpectedQueueTime();
	
	public abstract double getRequestRate();
	
	
	/**
	 * Number of requests that is currently being processed by this request queue.
	 * @return
	 */
	public abstract int getActiveRequestCount();
	
	/**
	 * Maximum number of requests that can be executed simultaneously.
	 *  
	 * @return
	 */
	public abstract int getMaximumActiveRequestCount();
	
	/**
	 * Submit a new TmlRunnable.
	 * 
	 * @param tml
	 */
	public final void submit(TmlRunnable tml) {
		tml.setRequestQueue(this);
		sumbitToQueue(tml);
	}
	
	public abstract void finished();
	
	public abstract void sumbitToQueue(TmlRunnable tml);
	
	public abstract void shutDownQueue();
	
	/**
	 * Returns the Scheduler associated with this RequestQueue.
	 * 
	 * @return
	 */
	public final Scheduler getTmlScheduler() {
		return myScheduler;
	}
	
	
}
