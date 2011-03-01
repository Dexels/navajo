package com.dexels.navajo.server.listener.http.schedulers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.listener.http.TmlScheduler;

public class ThreadPoolTmlScheduler implements TmlScheduler {

	private static final int DEFAULT_POOL_SIZE = 15;
	private static final  ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	private final Map<HttpServletRequest, TmlRunnable> tmlRunnableMap = Collections.synchronizedMap( new HashMap<HttpServletRequest, TmlRunnable>());
	
	
	@Override
	public void initialize(HttpServlet servlet) {
		String poolSize = servlet.getInitParameter("poolSize");
		if(poolSize==null) {
			executor.setCorePoolSize(DEFAULT_POOL_SIZE);
			executor.setMaximumPoolSize(DEFAULT_POOL_SIZE);
		} else {
			int pool = Integer.parseInt(poolSize);
			executor.setCorePoolSize(pool);
			executor.setMaximumPoolSize(pool);
			System.err.println(">>>>> Poolsize set to: "+pool);
		}
	}
	
	public void submit(TmlRunnable myRunner, boolean retry) {
		if(myRunner.isCommitted()) {
			System.err.println("Already committed.");
		} else {
			myRunner.setCommitted(true);
			myRunner.setScheduledAt(System.currentTimeMillis());
			//long before = System.currentTimeMillis();
			executor.execute(myRunner);
			//long now = System.currentTimeMillis();
			//System.err.println("Execute schedule took:"+(now-before));
		}
	}

	
	@Override
	public void cancel(TmlRunnable myRunner) {
   	Queue<Runnable> q = executor.getQueue();
	  	 if(q.contains(myRunner)) {
	  		 q.remove(myRunner);
	  		 System.err.println("Unscheduling task!");
	  	 } else {
	  		 System.err.println("Cant cancel: Not found");
	  	 }		
	}
	@Override
	public boolean checkNavajo(Navajo input) {
		return true;
	}

	@Override
	public int getTimeout() {
		return 0;
	}

	@Override
	public boolean preCheckRequest(HttpServletRequest request) {
		return true;
	}


	@Override
	public void run(TmlRunnable myRunner) {
		System.err.println("Warning: Synchronously running script in ThreadPool based connector!");
		myRunner.setCommitted(true);
		myRunner.setScheduledAt(System.currentTimeMillis());
		myRunner.run();
	}


	@Override
	public String getSchedulingStatus() {
		return "pool: "+executor.getActiveCount()+"/"+executor.getMaximumPoolSize()+" ("+executor.getQueue().size()+")";
	}

	public void runFinished(TmlRunnable tr) {
		
	}

	@Override
	public void shutdownScheduler() {
		
	}

	@Override
	public void removeTmlRunnable(HttpServletRequest req) {
		tmlRunnableMap.remove(req);
	}
	
	public void addTmlRunnable(HttpServletRequest req, TmlRunnable run) {
		tmlRunnableMap.put(req, run);
	}

	@Override
	public TmlRunnable getTmlRunnable(HttpServletRequest req) {
		return tmlRunnableMap.get(req);
	}

	@Override
	public ThreadPoolExecutor getGenericPool() {
		// TODO Auto-generated method stub
		return executor;
	}

}
