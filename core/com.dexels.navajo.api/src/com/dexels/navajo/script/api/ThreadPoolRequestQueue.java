package com.dexels.navajo.script.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class ThreadPoolRequestQueue extends RequestQueue {

	private final ThreadPoolExecutor tpe;
	
	protected ThreadPoolRequestQueue(String id, ThreadPoolExecutor tpe, Scheduler ms) {
		super(ms, id);
		this.tpe = tpe;
	}

	public static ThreadPoolRequestQueue create(final Scheduler myScheduler, final String id, final int priority, final int nrThreads) {
		ThreadPoolExecutor t = (ThreadPoolExecutor) Executors.newFixedThreadPool(nrThreads);
		
		t.setCorePoolSize(nrThreads);
		
		t.setMaximumPoolSize(nrThreads);
        t.setThreadFactory(new NamedThreadFactory(id) {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread t = super.newThread(r);
				t.setPriority(priority);
				t.setDaemon(true);
				return t;
			}
		});
		return new ThreadPoolRequestQueue(id, t, myScheduler);
	}
	
	public ThreadPoolExecutor getThreadPoolExecutor() {
		return this.tpe;
	}
	
	@Override
	public int getQueueSize() {
		return tpe.getQueue().size();
	}

	@Override
	public int getActiveRequestCount() {
		return tpe.getActiveCount();
	}

	@Override
	public int getMaximumActiveRequestCount() {
		return tpe.getCorePoolSize();
	}

	@Override
	protected void sumbitToQueue(TmlRunnable tml) {
		tpe.submit(tml);
	}


	@Override
	public final int getExpectedQueueTime() {
		int backlog = getQueueSize();
		return  (int) ((backlog + 1) / getRequestRate());
	}
	
	@Override
	public final double getRequestRate() {
		return 1.0;
	}

	@Override
	public void finished() {

	}

	@Override
	public void shutDownQueue() {
		List<Runnable> awaiting = tpe.shutdownNow();
		if(awaiting==null) {
			System.err.println("Nothing left");
			return;
		}
		for (Runnable runnable : awaiting) {
			TmlRunnable tr = (TmlRunnable)runnable;
			tr.abort("Aborting: Queue shutting down");
			System.err.println("Aborting task!");
		}
	}

	@Override
	public int flushQueue() {
		List<Runnable> list = new ArrayList<Runnable>();
		int size = tpe.getQueue().drainTo(list);
		System.err.println("Drained " + size + " items. List size: " + list.size());
		return size;
	}

	// Should make a conversion or something?
	@Override
	public List<TmlRunnable> getQueuedRequests() {
		List<TmlRunnable> runnable = new ArrayList<TmlRunnable>();
		return runnable;
	}

}
