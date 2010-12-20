package com.dexels.navajo.client;

import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.util.thread.ThreadPool;

import com.dexels.navajo.listeners.Scheduler;
import com.dexels.navajo.listeners.SchedulerRegistry;

public class NavajoThreadPool implements ThreadPool {

	private static final int MINIMUM_THREADS = 1;
	final Scheduler rootPool;

	public NavajoThreadPool() {
		rootPool = SchedulerRegistry.getScheduler();
	}
	
	public NavajoThreadPool(Scheduler rootScheduler) {
		if(rootScheduler==null) {
			rootPool = SchedulerRegistry.getScheduler();
		} else {
			rootPool = rootScheduler;
		}
	}
	
	@Override
	public boolean dispatch(Runnable task) {
		rootPool.getGenericPool().submit(task);
		return true;
	}

	@Override
	public int getIdleThreads() {
		return rootPool.getGenericPool().getCorePoolSize() - rootPool.getGenericPool().getActiveCount();
	}

	@Override
	public int getThreads() {
		return rootPool.getGenericPool().getCorePoolSize();
	}

	@Override
	public boolean isLowOnThreads() {
		return rootPool.getGenericPool().getMaximumPoolSize() - rootPool.getGenericPool().getActiveCount() < MINIMUM_THREADS;
	}

	@Override
	public void join() throws InterruptedException {
		rootPool.getGenericPool().shutdown();
		rootPool.getGenericPool().awaitTermination(30, TimeUnit.SECONDS);
	}

}
