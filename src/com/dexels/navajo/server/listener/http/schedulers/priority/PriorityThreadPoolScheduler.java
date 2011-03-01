package com.dexels.navajo.server.listener.http.schedulers.priority;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.listener.http.TmlScheduler;
import com.dexels.navajo.server.resource.ResourceCheckerManager;
import com.dexels.navajo.server.resource.ServiceAvailability;

public final class PriorityThreadPoolScheduler implements TmlScheduler, PriorityThreadPoolSchedulerMBean {
	
	private static final int DEFAULT_POOL_SIZE = 15;
	private static final int DEFAULT_MAXBACKLOG = 500;
	private static final int MAX_TIMELOG_SIZE = 80;
	
	private final ThreadPoolExecutor normalPool;
	private final ThreadPoolExecutor priorityPool;
	private final ThreadPoolExecutor systemPool;
	
	// Special queue in which web service requests are put that require resources that are (temporarily) not available
	// or are that use resources which are in bad health.
	private final ThreadPoolExecutor slowPool;
	private final ThreadPoolExecutor fastPool;
	
	private final Map<TmlRunnable,ExecutorService> poolMapper = new HashMap<TmlRunnable, ExecutorService>();
	private final Map<HttpServletRequest, TmlRunnable> tmlRunnableMap = Collections.synchronizedMap( new HashMap<HttpServletRequest, TmlRunnable>());
	
	private int timeout = 7200000;
	private int throttleDelay = 2000;
	
	private static int WINDOW_SIZE                 = 500;
	private long   previousDepartureTime           = 0;
//	private double previousAvgDepartureTime        = 0.0;
	private int [] interDepartureTime              = new int[WINDOW_SIZE];
	private long   departures                      = 0;
	
	private long   previousArrivalTime = 0;
	private int [] interArrivalTime = new int[500];
	private long   arrivals = 0;
	
	public int getThrottleDelay() {
		return throttleDelay;
	}

	public void setThrottleDelay(int throttleDelay) {
		this.throttleDelay = throttleDelay;
	}

	private int maxbacklog = DEFAULT_MAXBACKLOG;
	
	private long processed = 0;
	private long errors = 0;
	private long resubmits = 0;
	
	// Keep track of last throttle timestamp.
//	private long throttleTimestamp = 0;
	
	private final Queue<Long> finishedServicesAt = new LinkedList<Long>();

	public PriorityThreadPoolScheduler() {
		
		normalPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		normalPool.setThreadFactory(new NamedThreadFactory("normalThread") {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread t = super.newThread(r);
				t.setPriority(3);
				return t;
			}
		});
		
		priorityPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		priorityPool.setThreadFactory(new NamedThreadFactory("priorityThread") {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread t = super.newThread(r);
				t.setPriority(8);
				return t;
			}
		});
		
		systemPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		systemPool.setThreadFactory(new NamedThreadFactory("systemThread") {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = super.newThread(r);
				t.setPriority(10);
				return t;
			}
		});
		
		slowPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		slowPool.setThreadFactory(new NamedThreadFactory("slowThread") {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = super.newThread(r);
				t.setPriority(3);
				return t;
			}
		});
		
		fastPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		fastPool.setThreadFactory(new NamedThreadFactory("fastThread") {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = super.newThread(r);
				t.setPriority(3);
				return t;
			}
		});
		
		JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, "PriorityScheduler");
		 
	}

	@Override
	public void initialize(HttpServlet servlet) {
		System.err.println("Priority thread queue available. Setting up pools.");
		String normalPoolSize = servlet.getInitParameter("normalPoolSize");
		if (normalPoolSize == null) {
			setNormalPoolSize(DEFAULT_POOL_SIZE);
		} else {
			int pool = Integer.parseInt(normalPoolSize);
			setNormalPoolSize(pool);
		}
		String priorityPoolSize = servlet.getInitParameter("priorityPoolSize");
		if (priorityPoolSize == null) {
			setPriorityPoolSize(DEFAULT_POOL_SIZE);
		} else {
			int pool = Integer.parseInt(priorityPoolSize);
			setPriorityPoolSize(pool);
		}

		String systemPoolSize = servlet.getInitParameter("systemPoolSize");
		if (systemPoolSize == null) {
			setSystemPoolSize(DEFAULT_POOL_SIZE);
		} else {
			int pool = Integer.parseInt(systemPoolSize);
			setSystemPoolSize(pool);
		}
			
		setPoolSize(1, slowPool);
		setPoolSize(Integer.parseInt(normalPoolSize) * 2, fastPool);
		
		System.err.println("Priority scheduler setup complete. Normal pool: "+normalPoolSize+" system: "+systemPoolSize+" priority: "+priorityPoolSize);
	}
	
	public void setSystemPoolSize(int pool) {
		setPoolSize(pool, systemPool);
	}
	
	public void setPriorityPoolSize(int pool) {
		setPoolSize(pool, priorityPool);
	}

	public void setNormalPoolSize(int pool) {
		System.err.println("Setting normal pool size to: " + pool);
		setPoolSize(pool, normalPool);
	}
	
	public void setPoolSize(int poolSize, ThreadPoolExecutor pool) {
		pool.setCorePoolSize(poolSize);
		pool.setMaximumPoolSize(poolSize);
	}
	
	public int getNormalPoolSize() {
		return normalPool.getCorePoolSize();
	}

	public int getPriorityPoolSize() {
		return priorityPool.getCorePoolSize();
	}
	
	public int getSystemPoolSize() {
		return systemPool.getCorePoolSize();
	}
	
	public int getSystemActive() {
		return systemPool.getActiveCount();
	}
	public int getNormalActive() {
		return normalPool.getActiveCount();
	}

	public int getPriorityActive() {
		return priorityPool.getActiveCount();
	}

	public final void submit(final TmlRunnable myRunner, boolean priority) throws IOException {
		submitToPool(myRunner, determineThreadPool(myRunner, priority) ) ;
	}

	private final ThreadPoolExecutor determineThreadPool(String user, String service) {
		boolean system;

		system = Dispatcher.isSpecialwebservice(service);

		String prio = null;
		if("admin".equals(user)) {
			prio = "zeer_belangrijk";
		}
		if (system) {
			return systemPool;
		} else if (prio != null) {
			if(priorityPool.getQueue().size()> normalPool.getQueue().size()) {
				System.err.println("Submitting request in normal queue, due to high load on the priority queue");
				return normalPool;
			} else {
				return priorityPool;
			}
		} else {
			return normalPool;
		}
	}
	
	private final ThreadPoolExecutor findPoolWithShortestQueue() {
		// Check system.
		int sys = getSystemQueueSize();
		int fast = getFastQueueSize();
		int normal = getNormalQueueSize();
		
		if ( sys < fast ) { // sys queue is smaller than fast.
			if ( sys < normal ) { // if also smaller than normal, it must be the smallest.
				return systemPool;
			} else {
				return normalPool; // normal must be the smallest.
			}
		} else { // fast queue is smaller than system.
			if ( fast < normal ) { // if also smaller than normal, it must be the smallest.
				return fastPool;
			} else {
				return normalPool; // normal must be the smallest.
			}
		}
	}
	
	private final ThreadPoolExecutor determineThreadPool(TmlRunnable myRunner, boolean priority) throws IOException {
		
		if ( priority ) {
			if ( getPriorityQueueSize() > 0 ) {
				return findPoolWithShortestQueue();
			} else {
				return priorityPool;
			}
		}
		
		if ( myRunner.getInputNavajo() == null || myRunner.getInputNavajo().getHeader() == null ) {
			throw new IOException("Missing Navajo header.");
		}
		
		final String service = myRunner.getInputNavajo().getHeader().getRPCName();

		boolean system;

		final ServiceAvailability sa = ResourceCheckerManager.getInstance().getResourceChecker(service, myRunner.getInputNavajo()).getServiceAvailability();
		
		//System.err.println("ServiceAvailability is: " + sa.getHealth());
		system = Dispatcher.isSpecialwebservice(service);

		if ( sa.getHealth() == ServiceAvailability.STATUS_OK ) {
			return fastPool;
		} else if ( sa.getHealth() == ServiceAvailability.STATUS_DEAD || !sa.isAvailable() ) {
			errors++;
			System.err.println("Service not available: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
			throw new IOException("Service not available: " + service);
		} 
		else if ( sa.getHealth() == ServiceAvailability.STATUS_VERYBUSY ) {
			System.err.println("Service to slow pool: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
			return slowPool;
		}
		
		if (system) {
			System.err.println("Service to system pool: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
			return systemPool;
		} else {
			System.err.println("Service to normal pool: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
			return normalPool;
		}

	}
	
	private final void submitToPool(TmlRunnable run, ThreadPoolExecutor pool) throws IOException {
		
		// Again, check back log size.
		if ( pool.getQueue().size() >= maxbacklog ) {
			throw new IOException("Service unavailable, queue size too large: " + run.getInputNavajo().getHeader().getRPCName());
		}
		
		// Calculate moving average inter-arrival time.
		synchronized (interArrivalTime) {
			long arrivalTime = System.currentTimeMillis();
			if ( previousArrivalTime != 0 ) { 
				int diff = (int) ( arrivalTime - previousArrivalTime );
				interArrivalTime[(int) ( ( arrivals - 1 ) % WINDOW_SIZE )  ] = diff;
			} 
			previousArrivalTime = arrivalTime;
			arrivals++;
		}
		
		long before = System.currentTimeMillis();
		run.setScheduledAt(before);
		poolMapper.put(run, pool);
		pool.submit(run);
		processed++;
		
	}
	
	@Override
	public final boolean checkNavajo(Navajo input) {
		return true;
	}

	@Override
	public final int getTimeout() {
		return timeout;
	}
	
	public final void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public final boolean preCheckRequest(final HttpServletRequest request) {
//		Enumeration<String> names = request.getHeaderNames();
//		while (names.hasMoreElements()) {
//			String headerName = (String) names.nextElement();
//			System.err.println("Header: "+headerName+" value: "+request.getHeader(headerName));
//		}
		
		if ( !request.getMethod().equals("POST") ) {
			// Only accept POST requests.
			System.err.println("Only accepting POSTs now!");
			return false;
		}
		
		String user = request.getHeader("rpcUser");
		String service = request.getHeader("rpcName");
		
		if(user==null || service ==null) {
			//System.err.println("No header fields found in HTTP header, skipping pre-check!");
			return true;
		}

		ThreadPoolExecutor pool = determineThreadPool(service, user);
		if(pool==null) {
			// hmm odd..
			System.err.println("Can't decide on a threadpool.");
			return false;
		}

		int backlog = pool.getQueue().size();
		if(backlog>maxbacklog) {
			System.err.println("Refusing service: Backlog too great ("+maxbacklog+")");
			return false;
		}

		double expectedQueuingTime = getExpectedQueueTime(pool);
		//System.err.println("Expecting queuetime: "+expectedQueuingTime+" timeout: "+timeout);
		if(expectedQueuingTime > timeout) {
			System.err.println("Refusing service: Don't expect to finish within timeout.");
			return false;
		}
		
		
		//System.err.println(request.getRemoteAddr()+" port: "+request.getRemotePort()+" host: "+request.getRemoteHost());
		return true;
	}

	

	private final double getExpectedQueueTime(ThreadPoolExecutor pool) {
		int backlog = pool.getQueue().size();
		return (backlog + 1) / getRequestRate();
	}	
	
	public final double getExpectedNormalQueueTime() {
		return getExpectedQueueTime(normalPool);
	}

	public final double getExpectedPriorityQueueTime() {
		return getExpectedQueueTime(priorityPool);
	}
	public final double getExpectedSystemQueueTime() {
		return getExpectedQueueTime(systemPool);
	}

	
	@Override
	public final void run(TmlRunnable myRunner) {
		System.err.println("Warning: Synchronously running script in ThreadPool based connector!");
		myRunner.setTmlScheduler(this);
		myRunner.setCommitted(true);
		myRunner.setScheduledAt(System.currentTimeMillis());
		myRunner.run();
	}

	@Override
	public final String getSchedulingStatus() {
		return 
		"  normal: "+normalPool.getActiveCount()+"/"+normalPool.getMaximumPoolSize()+" ("+normalPool.getQueue().size()+")" +
		", slow: "+slowPool.getActiveCount()+"/"+slowPool.getMaximumPoolSize()+" ("+slowPool.getQueue().size()+")" +
		", fast: "+fastPool.getActiveCount()+"/"+fastPool.getMaximumPoolSize()+" ("+fastPool.getQueue().size()+")" +
		", priority: "+priorityPool.getActiveCount()+"/"+priorityPool.getMaximumPoolSize()+" ("+priorityPool.getQueue().size()+")" +
		", system: "+systemPool.getActiveCount()+"/"+systemPool.getMaximumPoolSize()+" ("+systemPool.getQueue().size()+")" +
		", poolmap size: " + poolMapper.size() + ", runnablemap size: " + tmlRunnableMap.size();
		
	}

	@Override
	public final void runFinished(TmlRunnable tr) {
		synchronized (this) {
			finishedServicesAt.add(System.currentTimeMillis());
			while (finishedServicesAt.size() > MAX_TIMELOG_SIZE) {
				finishedServicesAt.remove();
			}
		}
		poolMapper.remove(tr);
	}

	public final double getRequestRate() {
		synchronized (this) {
			if(finishedServicesAt.isEmpty()) {
				// don't return 0
				return 1;
			}
			long first = finishedServicesAt.peek();
			long diff = System.currentTimeMillis() - first;
			return diff / finishedServicesAt.size();
		}
	}
	
	@Override
	public final void cancel(TmlRunnable myRunner) {
		//		System.err.println("Warning. Canceling from prioThreadpools not yet implemented. Ignoring.");
		ExecutorService pool = poolMapper.get(myRunner);
		poolMapper.remove(myRunner);
		if(pool==null) {
			System.err.println("Service not linked to a pool. Finished already?");
			try {
				myRunner.endTransaction();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		boolean success = false;
		if ( pool instanceof ThreadPoolExecutor ) {
			success = ((ThreadPoolExecutor) pool).getQueue().remove(myRunner);
		} else if ( pool instanceof ScheduledThreadPoolExecutor ) {
			success = ((ScheduledThreadPoolExecutor) pool).getQueue().remove(myRunner);
		}
		if(success) {
			System.err.println("queued request removed from queue");
			// service was not performed yet.
		}
		System.err.println("Cancelling running script");
		myRunner.abort();
	}

	private class NamedThreadFactory implements ThreadFactory {

		private final String name;
		private long counter = 0;
		public NamedThreadFactory(String name) {
			this.name = name;
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setName(name+"_"+getSuffix());
			return t;
		}

		private long getSuffix() {
			return ++counter;
		}

	}

	@Override
	public void shutdownScheduler() {
		shutdownPool(systemPool);
		shutdownPool(normalPool);
		shutdownPool(priorityPool);
		shutdownPool(fastPool);
		shutdownPool(slowPool);
		
	}

	private void shutdownPool(ThreadPoolExecutor pool) {
		List<Runnable> awaiting = pool.shutdownNow();
		if(awaiting==null) {
			System.err.println("Nothing left");
			return;
		}
		for (Runnable runnable : awaiting) {
			TmlRunnable tr = (TmlRunnable)runnable;
			tr.abort();
			System.err.println("Aborting task!");
		}
	}

	@Override
	public final int getMaxBackLog() {
		return maxbacklog;
	}

	@Override
	public final void setMaxBackLog(int backlog) {
		maxbacklog = backlog;
	}

	private int calculateAverageIntervalTime(int [] interval) {
		int sum = 0;
		int index = 0;
		for ( int i = 0; i < interval.length; i++ ) {
			if ( interval[i] > 0 ) {
				sum += interval[i];
				index++;
			}
		}
		if ( index > 0 ) {
			return ( sum / index );
		} else {
			return 1;
		}
	}
	
	@Override
	public void removeTmlRunnable(HttpServletRequest req) {
		// Calculate moving average inter-departure time.
		synchronized (interDepartureTime) {
			long departureTime = System.currentTimeMillis();
			if ( previousDepartureTime != 0 ) { 
				int diff = (int) ( departureTime - previousDepartureTime );
				interDepartureTime[(int) ( (departures - 1) % WINDOW_SIZE ) ] = diff;
			} 
			previousDepartureTime = departureTime;
			departures++;
		}
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
	public long getErrors() {
		return errors;
	}

	@Override
	public long getProcessed() {
		return processed;
	}

	@Override
	public long getResubmits() {
		return resubmits;
	}

	@Override
	public int getFastActive() {
		return fastPool.getActiveCount();
	}

	@Override
	public int getFastPoolSize() {
		return fastPool.getCorePoolSize();
	}

	@Override
	public int getFastQueueSize() {
		return fastPool.getQueue().size();
	}

	@Override
	public int getNormalQueueSize() {
		return normalPool.getQueue().size();
	}

	@Override
	public int getPriorityQueueSize() {
		return priorityPool.getQueue().size();
	}

	@Override
	public int getSlowActive() {
		return slowPool.getActiveCount();
	}

	@Override
	public int getSlowPoolSize() {
		return slowPool.getCorePoolSize();
	}

	@Override
	public int getSlowQueueSize() {
		return slowPool.getQueue().size();
	}

	@Override
	public int getSystemQueueSize() {
		return systemPool.getQueue().size();
	}

	@Override
	public void setFastPoolSize(int pool) {
		System.err.println("Setting fastpool threads to: " + pool);
		setPoolSize(pool, fastPool);
	}

	@Override
	public void setSlowPoolSize(int pool) {
		setPoolSize(pool, slowPool);
	}
	
	@Override
	public int getInterArrivalTime() {
		return calculateAverageIntervalTime(interArrivalTime);
	}

	@Override
	public int getInterDepartureTime() {
		return calculateAverageIntervalTime(interDepartureTime);
	}

	@Override
	public long getArrivals() {
		return arrivals;
	}

	@Override
	public long getDepartures() {
		return departures;
	}

	@Override
	public ThreadPoolExecutor getGenericPool() {
		return fastPool;
	}
	
}
