package com.dexels.navajo.server.listener.http.schedulers.priority;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.listeners.RequestQueue;
import com.dexels.navajo.listeners.ThreadPoolRequestQueue;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.queuemanager.NavajoSchedulingException;
import com.dexels.navajo.queuemanager.QueueManager;
import com.dexels.navajo.queuemanager.api.InputContext;
import com.dexels.navajo.queuemanager.api.QueueContext;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.listener.http.TmlScheduler;
import com.dexels.navajo.server.resource.ResourceCheckerManager;
import com.dexels.navajo.server.resource.ServiceAvailability;

public final class PriorityThreadPoolScheduler implements TmlScheduler, PriorityThreadPoolSchedulerMBean {
	
	private static final int DEFAULT_POOL_SIZE = 15;
	private static final int DEFAULT_MAXBACKLOG = 500;
	private static final Logger logger = LoggerFactory.getLogger(PriorityThreadPoolScheduler.class);
	private RequestQueue normalPool;
	private RequestQueue priorityPool;
	private RequestQueue systemPool;
	
	// Special queue in which web service requests are put that require resources that are (temporarily) not available
	// or are that use resources which are in bad health.
	private RequestQueue slowPool;
	private RequestQueue fastPool;
	
	private int timeout = 7200000;
	private int throttleDelay = 2000;
	
	private static int WINDOW_SIZE                 = 500;
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
	private QueueContext queueContext;
	private QueueManager queueManager;
	private Map<String,RequestQueue> queueMap;
	
	private Map<String,Long> warnings = new HashMap<String,Long>();
	private static String RESOLUTION_SCRIPT_DOES_NOT_EXIST = "resolutionscript";
	
	// Keep track of last throttle timestamp.
//	private long throttleTimestamp = 0;
	
	public PriorityThreadPoolScheduler() {
	}

	@Override
	public void initialize(ServletContext context) {
		// This must be done later
		JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, "PriorityScheduler");
		// =====
		System.err.println("Priority thread queue available. Setting up pools.");
		int poolSize;
		
		// Start normal pool.
		String stringPoolSize = context.getInitParameter("normalPoolSize");
		if (stringPoolSize == null) {
			poolSize = DEFAULT_POOL_SIZE;
		} else {
			poolSize = Integer.parseInt(stringPoolSize);
		}
		normalPool = ThreadPoolRequestQueue.create(this, "normalThead", 3, poolSize);
		
		// Start priority pool.
		stringPoolSize = context.getInitParameter("priorityPoolSize");
		if (stringPoolSize == null) {
			poolSize = DEFAULT_POOL_SIZE;
		} else {
			poolSize = Integer.parseInt(stringPoolSize);
		}
		priorityPool = ThreadPoolRequestQueue.create(this, "priorityThread", 8, poolSize);
		
		// Start system pool.
		stringPoolSize = context.getInitParameter("systemPoolSize");
		if (stringPoolSize == null) {
			poolSize = DEFAULT_POOL_SIZE;
		} else {
			poolSize = Integer.parseInt(stringPoolSize);
		}
		systemPool = ThreadPoolRequestQueue.create(this, "systemThread", 10, poolSize);
		
		// Start slow pool.
		slowPool = ThreadPoolRequestQueue.create(this, "slowThread", 3, 1);
			
		// Start fast pool.
		fastPool = ThreadPoolRequestQueue.create(this, "fastThread", 7, normalPool.getMaximumActiveRequestCount() * 2);
		
		queueMap = new HashMap<String,RequestQueue>();
		queueMap.put("fastThread",fastPool);
		queueMap.put("slowThread",slowPool);
		queueMap.put("normalThread",normalPool);
		queueMap.put("priorityThread",priorityPool);
		queueMap.put("systemThread", systemPool);

		this.queueContext = new QueueContext() {

			@Override
			public double getQueueHealth(String queueName) {
				return 0;
			}

			@Override
			public int getQueueSize(String queueName) {
				RequestQueue r = queueMap.get(queueName);
				if(r!=null) {
					return r.getQueueSize();
				}
				return -1;
			}

			
			@Override
			public Set<String> getQueueNames() {
				return queueMap.keySet();
			}};
			
		queueManager = new QueueManager();
		queueManager.setQueueContext(queueContext);
		queueManager.setScriptDir(new File(DispatcherFactory.getInstance().getNavajoConfig().getConfigPath()));
	}
	
	public final void submit(final TmlRunnable myRunner, boolean priority)  {
			submitToPool(myRunner, determineThreadPool(myRunner, priority) ) ;
		
	}

	private final void logWarning(String type, String message) {
		
		synchronized (warnings) {
			Long warning = warnings.get(type);
			if ( warning == null ) {
				warning = Long.valueOf(0);
				warnings.put(type, warning);
			} 
			if ( warning.longValue() < 100 ) {
				System.err.println(message);
				warning = warning.longValue() + 1;
			}	
		}
	}
	
	private final RequestQueue determineThreadPool(final TmlRunnable myRunner, final boolean priority) {
		
		/**
		 * Priority immediately returns priority pool (currently only used for scheduled Tasks).
		 */
		if ( priority ) {
			return priorityPool;
		}
		
		InputContext ic = new InputContext() {
			
			@Override
			public String getUserName() {
				return getInputNavajo().getHeader().getRPCUser();
			}
			
			@Override
			public String getServiceName() {
				return getInputNavajo().getHeader().getRPCName();
			}
			
			@Override
			public boolean isPriority() {
				return priority;
			}

			
			@Override
			public String getResourceAvailability() {
				ServiceAvailability sa;
				try {
					sa = ResourceCheckerManager.getInstance().getResourceChecker(getServiceName(), myRunner.getInputNavajo()).getServiceAvailability();
					return sa.getStatus();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return "ok";
			}
			
			@Override
			public HttpServletRequest getRequest() {

				return (HttpServletRequest) myRunner.getAttribute("httpRequest");
			}
			
			@Override
			public Navajo getInputNavajo() {
				try {
					return myRunner.getInputNavajo();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		String queueName;
		try {
			queueName = queueManager.resolve(ic, "resolvequeue.js", "javascript");
		} catch (NavajoSchedulingException e) {
			if(e.getReason()==NavajoSchedulingException.SCRIPT_PROBLEM || e.getReason() == NavajoSchedulingException.UNKNOWN) {
				logWarning(RESOLUTION_SCRIPT_DOES_NOT_EXIST, "Could not find queue resolution script, using default queue.");
				return getDefaultQueue();
			}
			e.printStackTrace(System.err);
			return null;
		}
		
		RequestQueue r = queueMap.get(queueName);
		if(r!=null) {
			return r;
		}
		return getDefaultQueue();
	}
	
//	private final RequestQueue findPoolWithShortestQueue() {
//		// Check system.
//		int sys = getSystemQueueSize();
//		int fast = getFastQueueSize();
//		int normal = getNormalQueueSize();
//		
//		if ( sys < fast ) { // sys queue is smaller than fast.
//			if ( sys < normal ) { // if also smaller than normal, it must be the smallest.
//				return systemPool;
//			} else {
//				return normalPool; // normal must be the smallest.
//			}
//		} else { // fast queue is smaller than system.
//			if ( fast < normal ) { // if also smaller than normal, it must be the smallest.
//				return fastPool;
//			} else {
//				return normalPool; // normal must be the smallest.
//			}
//		}
//	}
	
//	private final RequestQueue determineThreadPool(TmlRunnable myRunner, boolean priority) throws IOException {
//		
//		
//		if ( priority ) {
//			if ( getPriorityQueueSize() > 0 ) {
//				return findPoolWithShortestQueue();
//			} else {
//				return priorityPool;
//			}
//		}
//		
//		if ( myRunner.getInputNavajo() == null || myRunner.getInputNavajo().getHeader() == null ) {
//			throw new IOException("Missing Navajo header.");
//		}
//		
//		final String service = myRunner.getInputNavajo().getHeader().getRPCName();
//
//		boolean system;
//
//		final ServiceAvailability sa = ResourceCheckerManager.getInstance().getResourceChecker(service, myRunner.getInputNavajo()).getServiceAvailability();
//		
//		//System.err.println("ServiceAvailability is: " + sa.getHealth());
//		system = Dispatcher.isSpecialwebservice(service);
//
//		if ( sa.getHealth() == ServiceAvailability.STATUS_OK ) {
//			return fastPool;
//		} else if ( sa.getHealth() == ServiceAvailability.STATUS_DEAD || !sa.isAvailable() ) {
//			errors++;
//			System.err.println("Service not available: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
//			throw new IOException("Service not available: " + service);
//		} 
//		else if ( sa.getHealth() == ServiceAvailability.STATUS_VERYBUSY ) {
//			System.err.println("Service to slow pool: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
//			return slowPool;
//		}
//		
//		if (system) {
//			System.err.println("Service to system pool: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
//			return systemPool;
//		} else {
//			System.err.println("Service to normal pool: " + service + "(ServiceAvailability=" + sa.getHealth() + ")");
//			return normalPool;
//		}
//
//	}
	
	private final void submitToPool(TmlRunnable run, RequestQueue pool) {
		
		// Again, check back log size.
		if ( pool == null) {
				run.abort("Scheduling refused");
		
			return;
		}
		if( pool.getQueueSize() >= maxbacklog) {
				run.abort("Enormous backlog!");
				return;
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
		return true;
	}	
	
	public final double getExpectedNormalQueueTime() {
		return normalPool.getExpectedQueueTime();
	}

	public final double getExpectedPriorityQueueTime() {
		return priorityPool.getExpectedQueueTime();
	}
	public final double getExpectedSystemQueueTime() {
		return systemPool.getExpectedQueueTime();
	}

	
	@Override
	public final void run(TmlRunnable myRunner) {
		logger.info("Warning: Synchronously running script in ThreadPool based connector!");
		myRunner.setCommitted(true);
		myRunner.setScheduledAt(System.currentTimeMillis());
		myRunner.run();
	}

	@Override
	public final String getSchedulingStatus() {
		return 
		"  normal: "+normalPool.getActiveRequestCount() +"/"+normalPool.getMaximumActiveRequestCount()+" ("+normalPool.getQueueSize()+")" +
		", slow: "+slowPool.getActiveRequestCount()+"/"+slowPool.getMaximumActiveRequestCount()+" ("+slowPool.getQueueSize()+")" +
		", fast: "+fastPool.getActiveRequestCount()+"/"+fastPool.getMaximumActiveRequestCount()+" ("+fastPool.getQueueSize()+")" +
		", priority: "+priorityPool.getActiveRequestCount()+"/"+priorityPool.getMaximumActiveRequestCount()+" ("+priorityPool.getQueueSize()+")" +
		", system: "+systemPool.getActiveRequestCount()+"/"+systemPool.getMaximumActiveRequestCount()+" ("+systemPool.getQueueSize()+")";
		
	}

	@Override
	public void shutdownScheduler() {
		systemPool.shutDownQueue();
		normalPool.shutDownQueue();
		priorityPool.shutDownQueue();
		fastPool.shutDownQueue();
		slowPool.shutDownQueue();
		
		logger.info("Shutdown complete");
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
		return fastPool.getActiveRequestCount();
	}

	@Override
	public int getFastPoolSize() {
		return fastPool.getMaximumActiveRequestCount();
	}

	@Override
	public int getFastQueueSize() {
		return fastPool.getQueueSize();
	}

	@Override
	public int getNormalQueueSize() {
		return normalPool.getQueueSize();
	}

	@Override
	public int getPriorityQueueSize() {
		return priorityPool.getQueueSize();
	}

	@Override
	public int getSlowActive() {
		return slowPool.getActiveRequestCount();
	}

	@Override
	public int getSlowPoolSize() {
		return slowPool.getMaximumActiveRequestCount();
	}

	@Override
	public int getSlowQueueSize() {
		return slowPool.getQueueSize();
	}

	@Override
	public int getSystemQueueSize() {
		return systemPool.getQueueSize();
	}

	@Override
	public void setFastPoolSize(int pool) {
		System.err.println("Setting fastpool threads to: " + pool);
		//setPoolSize(pool, fastPool);
	}

	@Override
	public void setSlowPoolSize(int pool) {
		//setPoolSize(pool, slowPool);
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
	public RequestQueue getDefaultQueue() {
		return normalPool;
	}

	@Override
	public void setNormalPoolSize(int pool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPriorityPoolSize(int pool) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setSystemPoolSize(int pool) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getNormalPoolSize() {
		return normalPool.getMaximumActiveRequestCount();
	}

	@Override
	public int getPriorityPoolSize() {
		return priorityPool.getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemPoolSize() {
		return systemPool.getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemActive() {
		return systemPool.getActiveRequestCount();
	}

	@Override
	public int getNormalActive() {
		return normalPool.getActiveRequestCount();
	}

	@Override
	public int getPriorityActive() {
		return priorityPool.getActiveRequestCount();
	}

	@Override
	public int flushSystemQueue() {
		return systemPool.flushQueue();
	}

	@Override
	public int flushNormalQueue() {
		return normalPool.flushQueue();
	}

	@Override
	public int flushPriorityQueue() {
		return priorityPool.flushQueue();
	}

	@Override
	public int flushSlowQueue() {
		return slowPool.flushQueue();
	}

	@Override
	public int flushFastQueue() {
		return fastPool.flushQueue();
	}
	
	public List<TmlRunnable> getSlowRequests() {
		return slowPool.getQueuedRequests();
	}
	
	public List<TmlRunnable> getNormalRequests() {
		return normalPool.getQueuedRequests();
	}
	
	public List<TmlRunnable> getFastRequests() {
		return fastPool.getQueuedRequests();
	}
	
	public List<TmlRunnable> getPriorityRequests() {
		return priorityPool.getQueuedRequests();
	}
	
	public List<TmlRunnable> getSystemRequests() {
		return systemPool.getQueuedRequests();
	}
}
