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
import com.dexels.navajo.listener.http.queuemanager.api.InputContext;
import com.dexels.navajo.listener.http.queuemanager.api.NavajoSchedulingException;
import com.dexels.navajo.listener.http.queuemanager.api.QueueContext;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManager;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManagerFactory;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.Scheduler;
import com.dexels.navajo.script.api.ThreadPoolRequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.resource.ResourceCheckerManager;
import com.dexels.navajo.server.resource.ServiceAvailability;

public final class PriorityThreadPoolScheduler implements TmlScheduler, PriorityThreadPoolSchedulerMBean, QueueContext {
	
	private static final int DEFAULT_POOL_SIZE = 15;
	private static final int DEFAULT_MAXBACKLOG = 500;
	private static final Logger logger = LoggerFactory.getLogger(PriorityThreadPoolScheduler.class);
//	private RequestQueue normalPool;
//	private RequestQueue priorityPool;
//	private RequestQueue systemPool;
	
	// Special queue in which web service requests are put that require resources that are (temporarily) not available
	// or are that use resources which are in bad health.
//	private RequestQueue slowPool;
//	private RequestQueue fastPool;
	
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
	private QueueManager queueManager;
	private final Map<String,RequestQueue> queueMap = new HashMap<String,RequestQueue>();
	
	private static String RESOLUTION_SCRIPT_DOES_NOT_EXIST = "resolutionscript";
	
	// Keep track of last throttle timestamp.
//	private long throttleTimestamp = 0;
	
	public PriorityThreadPoolScheduler() {
	}

	public void setQueueManager(QueueManager queueManager) {
		this.queueManager = queueManager;
	}

	public void clearQueueManager(QueueManager queueManager) {
		this.queueManager = null;
	}

	
	/**
	 * Only called in non-osgi
	 */
	@Override
	public void initialize(ServletContext context) {
		// This must be done later
		JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, "PriorityScheduler");
		// =====
		logger.info("Non osgi (web.xml based) Priority thread queue available. Setting up pools.");
		
		// Start normal pool.
		int normalPoolSize;
		String stringPoolSize = context.getInitParameter("normalPoolSize");
		if (stringPoolSize == null) {
			normalPoolSize = DEFAULT_POOL_SIZE;
		} else {
			normalPoolSize = Integer.parseInt(stringPoolSize);
		}
		
		// Start priority pool.
		stringPoolSize = context.getInitParameter("priorityPoolSize");
		int priorityPoolSize;
		if (stringPoolSize == null) {
			priorityPoolSize = DEFAULT_POOL_SIZE;
		} else {
			priorityPoolSize = Integer.parseInt(stringPoolSize);
		}
		
		// Start system pool.
		stringPoolSize = context.getInitParameter("systemPoolSize");
		int systemPoolSize;
		if (stringPoolSize == null) {
			systemPoolSize = DEFAULT_POOL_SIZE;
		} else {
			systemPoolSize = Integer.parseInt(stringPoolSize);
		}

//		RequestQueue normalPool = ThreadPoolRequestQueue.create(this, "normalThead", 3, normalPoolSize);
//		RequestQueue priorityPool = ThreadPoolRequestQueue.create(this, "priorityThread", 8, priorityPoolSize);
//		RequestQueue systemPool = ThreadPoolRequestQueue.create(this, "systemThread", 10, systemPoolSize);
//		RequestQueue slowPool = ThreadPoolRequestQueue.create(this, "slowThread", 3, 1);
//		RequestQueue fastPool = ThreadPoolRequestQueue.create(this, "fastThread", 7, normalPool.getMaximumActiveRequestCount() * 2);
		
//		queueMap.put("fastThread",fastPool);
//		queueMap.put("slowThread",slowPool);
//		queueMap.put("normalThread",normalPool);
//		queueMap.put("priorityThread",priorityPool);
//		queueMap.put("systemThread", systemPool);
		queueManager = QueueManagerFactory.getInstance();
		queueManager.setQueueContext(this);
		queueManager.setScriptDir(new File(DispatcherFactory.getInstance().getNavajoConfig().getConfigPath()));
		
		createPools(normalPoolSize, priorityPoolSize, systemPoolSize);
	}

	public void activate(Map<String,Object> params ) {
		logger.info("Activating prio...");
		try {
			Integer normalPoolSize = (Integer) params.get("normalPoolSize");
			if(normalPoolSize==null) {
				normalPoolSize = 14;
			}
			Integer priorityPoolSize = (Integer) params.get("priorityPoolSize");
			if(priorityPoolSize==null) {
				priorityPoolSize = 14;
			}
			Integer systemPoolSize = (Integer) params.get("systemPoolSize");
			if(systemPoolSize==null) {
				systemPoolSize = 14;
			}
			createPools(normalPoolSize,priorityPoolSize,systemPoolSize);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void createPools(int normalPoolSize, int priorityPoolSize,
			int systemPoolSize) {
		RequestQueue normalPool = createThreadPool(this, "normalPool", 3, normalPoolSize);
		createThreadPool(this, "priorityPool", 8, priorityPoolSize);
		createThreadPool(this, "systemPool", 10, systemPoolSize);
		createThreadPool(this, "slowPool", 3, 1);
		createThreadPool(this, "fastPool", 7, normalPool.getMaximumActiveRequestCount() * 2);
	}
	
	private RequestQueue createThreadPool(Scheduler scheduler, String name, int priority, int size) {
		RequestQueue pool = ThreadPoolRequestQueue.create(this,name, priority, size);
		queueMap.put(name, pool);
		return pool;
	}
	
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
	}
	
	
	public final void submit(final TmlRunnable myRunner, boolean priority)  {
			submitToPool(myRunner, determineThreadPool(myRunner, priority) ) ;
		
	}

	
	private final RequestQueue determineThreadPool(final TmlRunnable myRunner, final boolean priority) {
		
		/**
		 * Priority immediately returns priority pool (currently only used for scheduled Tasks).
		 */
		if ( priority ) {
			return queueMap.get("priorityPool");
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
			queueName = queueManager.resolve(ic, "resolvequeue.js");
//			queueName = "normalThread";
//			if(false) {
//				throw new NavajoSchedulingException(1, "sure");
//			}
		} catch (NavajoSchedulingException e) {
			if(e.getReason()==NavajoSchedulingException.SCRIPT_PROBLEM || e.getReason() == NavajoSchedulingException.UNKNOWN) {
				logger.warn(RESOLUTION_SCRIPT_DOES_NOT_EXIST, "Could not find queue resolution script, using default queue.",e);
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
		return queueMap.get("normalPool").getExpectedQueueTime();
	}

	public final double getExpectedPriorityQueueTime() {
		return queueMap.get("priorityPool").getExpectedQueueTime();
	}
	public final double getExpectedSystemQueueTime() {
		return queueMap.get("systemPool").getExpectedQueueTime();
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
		RequestQueue normalPool = queueMap.get("normalPool");
		RequestQueue slowPool = queueMap.get("slowPool");
		RequestQueue fastPool = queueMap.get("fastPool");
		RequestQueue priorityPool = queueMap.get("priorityPool");
		RequestQueue systemPool = queueMap.get("systemPool");
		return 
		"  normal: "+normalPool.getActiveRequestCount() +"/"+normalPool.getMaximumActiveRequestCount()+" ("+normalPool.getQueueSize()+")" +
		", slow: "+slowPool.getActiveRequestCount()+"/"+slowPool.getMaximumActiveRequestCount()+" ("+slowPool.getQueueSize()+")" +
		", fast: "+fastPool.getActiveRequestCount()+"/"+fastPool.getMaximumActiveRequestCount()+" ("+fastPool.getQueueSize()+")" +
		", priority: "+priorityPool.getActiveRequestCount()+"/"+priorityPool.getMaximumActiveRequestCount()+" ("+priorityPool.getQueueSize()+")" +
		", system: "+systemPool.getActiveRequestCount()+"/"+systemPool.getMaximumActiveRequestCount()+" ("+systemPool.getQueueSize()+")";
		
	}

	@Override
	public void shutdownScheduler() {
		queueMap.get("systemPool").shutDownQueue();
		queueMap.get("normalPool").shutDownQueue();
		queueMap.get("systemPool").shutDownQueue();
		queueMap.get("fastPool").shutDownQueue();
		queueMap.get("slowPool").shutDownQueue();
		
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
		return queueMap.get("fastPool").getActiveRequestCount();
	}

	@Override
	public int getFastPoolSize() {
		return queueMap.get("fastPool").getMaximumActiveRequestCount();
	}

	@Override
	public int getFastQueueSize() {
		return queueMap.get("fastPool").getQueueSize();
	}

	@Override
	public int getNormalQueueSize() {
		return queueMap.get("normalPool").getQueueSize();
	}

	@Override
	public int getPriorityQueueSize() {
		return queueMap.get("priorityPool").getQueueSize();
	}

	@Override
	public int getSlowActive() {
		return queueMap.get("slowPool").getActiveRequestCount();
	}

	@Override
	public int getSlowPoolSize() {
		return queueMap.get("slowPool").getMaximumActiveRequestCount();
	}

	@Override
	public int getSlowQueueSize() {
		return queueMap.get("slowPool").getQueueSize();
	}

	@Override
	public int getSystemQueueSize() {
		return queueMap.get("systemPool").getQueueSize();
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
		return queueMap.get("normalPool");
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
		return queueMap.get("normalPool").getMaximumActiveRequestCount();
	}

	@Override
	public int getPriorityPoolSize() {
		return queueMap.get("priorityPool").getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemPoolSize() {
		return queueMap.get("systemPool").getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemActive() {
		return queueMap.get("systemPool").getActiveRequestCount();
	}

	@Override
	public int getNormalActive() {
		return queueMap.get("normalPool").getActiveRequestCount();
	}

	@Override
	public int getPriorityActive() {
		return queueMap.get("priorityPool").getActiveRequestCount();
	}

	@Override
	public int flushSystemQueue() {
		return queueMap.get("systemPool").flushQueue();
	}

	@Override
	public int flushNormalQueue() {
		return queueMap.get("normalPool").flushQueue();
	}

	@Override
	public int flushPriorityQueue() {
		return  queueMap.get("priorityPool").flushQueue();
	}

	@Override
	public int flushSlowQueue() {
		return queueMap.get("slowPool").flushQueue();
	}

	@Override
	public int flushFastQueue() {
		return queueMap.get("fastPool").flushQueue();
	}
	
	public List<TmlRunnable> getSlowRequests() {
		return queueMap.get("slowPool").getQueuedRequests();
	}
	
	public List<TmlRunnable> getNormalRequests() {
		return queueMap.get("normalPool").getQueuedRequests();
	}
	
	public List<TmlRunnable> getFastRequests() {
		return queueMap.get("fastPool").getQueuedRequests();
	}
	
	public List<TmlRunnable> getPriorityRequests() {
		return queueMap.get("priorityPool").getQueuedRequests();
	}
	
	public List<TmlRunnable> getSystemRequests() {
		final RequestQueue pool = queueMap.get("systemPool");
		if(pool==null) {
			return null;
		}
		return pool.getQueuedRequests();
	}
}
