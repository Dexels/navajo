package com.dexels.navajo.server.listener.http.schedulers.priority;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.LoginStatisticsProvider;
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
	
	private static final String FAST_POOL = "fastPool";
    private static final String SLOW_POOL = "slowPool";
    private static final String SYSTEM_POOL = "systemPool";
    private static final String PRIORITY_POOL = "priorityPool";
    private static final String NORMAL_POOL = "normalPool";
    private static final int DEFAULT_POOL_SIZE = 15;
	private static final int DEFAULT_MAXBACKLOG = 500;
	private static final Logger logger = LoggerFactory.getLogger(PriorityThreadPoolScheduler.class);
	private static String RESOLUTION_SCRIPT_DOES_NOT_EXIST = "resolutionscript";

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
	

	private int maxbacklog = DEFAULT_MAXBACKLOG;
	
	private long processed = 0;
	private long errors = 0;
	private long resubmits = 0;
	private QueueManager queueManager;
	private final Map<String,RequestQueue> queueMap = new HashMap<String,RequestQueue>();
	
	
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
	

    @Override
    public int getThrottleDelay() {
        return throttleDelay;
    }

    @Override
    public void setThrottleDelay(int throttleDelay) {
        this.throttleDelay = throttleDelay;
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
		
		Map<String, Object> initParams = new HashMap<>();
		Enumeration<String> paramNames = context.getInitParameterNames();
		
		while (paramNames.hasMoreElements()) {
		    String key = paramNames.nextElement();
		    initParams.put(key, context.getInitParameter(key));
		}
		
		// Start normal pool.
		Integer normalPoolSize = extractInt(initParams, "normalPoolSize");
		Integer priorityPoolSize = extractInt(initParams, "priorityPoolSize");
		Integer systemPoolSize = extractInt(initParams, "systemPoolSize");
		Integer fastPoolSize = extractInt(initParams, "fastPoolSize");
		Integer slowPoolSize = extractInt(initParams, "slowPoolSize");

		queueManager = QueueManagerFactory.getInstance();
		queueManager.setQueueContext(this);
		queueManager.setScriptDir(new File(DispatcherFactory.getInstance().getNavajoConfig().getConfigPath()));
		
		createPools(normalPoolSize, priorityPoolSize, fastPoolSize, systemPoolSize, slowPoolSize);
	}

    public void activate(Map<String, Object> settings) {
        logger.info("Activating prio threadpool scheduler...");

        try {
            Integer normalPoolSize = extractInt(settings, "normalPoolSize");
            Integer priorityPoolSize = extractInt(settings, "priorityPoolSize");
            Integer systemPoolSize = extractInt(settings, "systemPoolSize");
            Integer fastPoolSize = extractInt(settings, "fastPoolSize");
            Integer slowPoolSize = extractInt(settings, "slowPoolSize");
            createPools(normalPoolSize, priorityPoolSize, fastPoolSize, systemPoolSize, slowPoolSize);
        } catch (Throwable e) {
            logger.error("Error: ", e);
        }
    }
    
    public void deactivate() {
        logger.debug("Deactivating priority queue");
        for (String key : queueMap.keySet()) {
            queueMap.get(key).shutDownQueue();
        }
        queueMap.clear();
        
    }

	private Integer extractInt(Map<String, Object> params, String key) {
		Object value = params.get(key);
		if(value==null) {
			return DEFAULT_POOL_SIZE;
		}
		if(value instanceof Integer) {
			return (Integer) params.get(key);
		}
		if(value instanceof String) {
			return Integer.parseInt((String) value);
		}
		return DEFAULT_POOL_SIZE;
	}
	


	private void createPools(int normalPoolSize, int priorityPoolSize, int fastPoolSize, int systemPoolSize, int slowPoolSize) {
		createThreadPool(this, NORMAL_POOL, 5, normalPoolSize);
		createThreadPool(this, PRIORITY_POOL, 8, priorityPoolSize);
		createThreadPool(this, SYSTEM_POOL, 10, systemPoolSize);
		createThreadPool(this, SLOW_POOL, 3, slowPoolSize);
		createThreadPool(this, FAST_POOL, 7, fastPoolSize);
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
	
	
	@Override
	public final void submit(final TmlRunnable myRunner, boolean priority)  {
			submitToPool(myRunner, determineThreadPool(myRunner, priority) ) ;
		
	}

	
	private final RequestQueue determineThreadPool(final TmlRunnable myRunner, final boolean priority) {
		
		/**
		 * Priority immediately returns priority pool (currently only used for scheduled Tasks).
		 */
		if ( priority ) {
			return queueMap.get(PRIORITY_POOL);
		}
		String queueName;
		if (myRunner.getAttributeNames().contains("queueName")) {
		    queueName = (String) myRunner.getAttribute("queueName");
		} else {
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
	                    logger.error("Error: ", e);
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
	                    logger.error("Error: ", e);
	                    return null;
	                }
	            }

	            @Override
	            public String getIpAddress() {
	                return myRunner.getRequest().getIpAddress();
	            }
	        };
	        
	        
	        if (LoginStatisticsProvider.reachedAbortThreshold(ic.getUserName(), ic.getIpAddress())) {
	            logger.warn("Refusing request from {} for {} due to too many failed auth attempts",  ic.getUserName(), ic.getIpAddress());
	            return null;
	        } else if (LoginStatisticsProvider.reachedRateLimitThreshold(ic.getUserName(), ic.getIpAddress())) {
	            logger.warn("Slow pool for request from {} for {} due to too many failed auth attempts",  ic.getUserName(), ic.getIpAddress());
	            return queueMap.get(SLOW_POOL);
	        }
	        
	        
	        try {
	            queueName = queueManager.resolve(ic, "resolvequeue.js");
//	          queueName = "normalThread";
//	          if(false) {
//	              throw new NavajoSchedulingException(1, "sure");
//	          }
	            if(queueName==null) {
	                return getDefaultQueue();
	            }
	        } catch (NavajoSchedulingException e) {
	            if(e.getReason()==NavajoSchedulingException.SCRIPT_PROBLEM || e.getReason() == NavajoSchedulingException.UNKNOWN) {
	                logger.info(RESOLUTION_SCRIPT_DOES_NOT_EXIST, "Could not find queue resolution script, using default queue.",e);
	                return getDefaultQueue();
	            }
	            logger.error("Error: ", e);
	            return null;
	        }
		}
		

		RequestQueue r = queueMap.get(queueName);
		if(r!=null) {
			return r;
		} else {
			logger.warn("Could not find thread pool: {}", queueName);
		}
		return getDefaultQueue();
	}
	
	private final void submitToPool(TmlRunnable run, RequestQueue pool) {
		
		// Check current memory usage.
		long max = Runtime.getRuntime().maxMemory();
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		
		run.setAttribute("maxmemory", max);
		run.setAttribute("usedmemory", (total - free));
		
		// Again, check back log size.
		if ( pool == null) {
				run.abort("Scheduling refused");
		
			return;
		}
		
		if (pool.getActiveRequestCount() >= pool.getMaximumActiveRequestCount()) {
            logger.warn("All {} threads in {} busy. Entering queue with {} threads ahead of us",
                    pool.getMaximumActiveRequestCount(), pool.getId(), pool.getQueueSize());
		}
		if( pool.getQueueSize() >= maxbacklog) {
			run.abort("Server too busy.");
			return;
		}
		// Calculate moving average inter-arrival time.
		long arrivalTime = System.currentTimeMillis();
		if ( previousArrivalTime != 0 ) { 
			int diff = (int) ( arrivalTime - previousArrivalTime );
			interArrivalTime[(int) ( ( arrivals - 1 ) % WINDOW_SIZE )  ] = diff;
		} 
		previousArrivalTime = arrivalTime;
		arrivals++;
		
		
		long before = System.currentTimeMillis();
		run.setScheduledAt(before);
		pool.submit(run);
		processed++;
		
	}

	@Override
	public final int getTimeout() {
		return timeout;
	}
	
	@Override
	public final void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	@Override
	public final double getExpectedNormalQueueTime() {
		return queueMap.get(NORMAL_POOL).getExpectedQueueTime();
	}

	@Override
	public final double getExpectedPriorityQueueTime() {
		return queueMap.get(PRIORITY_POOL).getExpectedQueueTime();
	}
	@Override
	public final double getExpectedSystemQueueTime() {
		return queueMap.get(SYSTEM_POOL).getExpectedQueueTime();
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
		
		RequestQueue normalPool = queueMap.get(NORMAL_POOL);
		RequestQueue slowPool = queueMap.get(SLOW_POOL);
		RequestQueue fastPool = queueMap.get(FAST_POOL);
		RequestQueue priorityPool = queueMap.get(PRIORITY_POOL);
		RequestQueue systemPool = queueMap.get(SYSTEM_POOL);
		return 
		"  normal: "+normalPool.getActiveRequestCount() +"/"+normalPool.getMaximumActiveRequestCount()+" ("+normalPool.getQueueSize()+")" +
		", slow: "+slowPool.getActiveRequestCount()+"/"+slowPool.getMaximumActiveRequestCount()+" ("+slowPool.getQueueSize()+")" +
		", fast: "+fastPool.getActiveRequestCount()+"/"+fastPool.getMaximumActiveRequestCount()+" ("+fastPool.getQueueSize()+")" +
		", priority: "+priorityPool.getActiveRequestCount()+"/"+priorityPool.getMaximumActiveRequestCount()+" ("+priorityPool.getQueueSize()+")" +
		", system: "+systemPool.getActiveRequestCount()+"/"+systemPool.getMaximumActiveRequestCount()+" ("+systemPool.getQueueSize()+")";
		
	}

	@Override
	public void shutdownScheduler() {
		queueMap.get(SYSTEM_POOL).shutDownQueue();
		queueMap.get(NORMAL_POOL).shutDownQueue();
		queueMap.get(SYSTEM_POOL).shutDownQueue();
		queueMap.get(FAST_POOL).shutDownQueue();
		queueMap.get(SLOW_POOL).shutDownQueue();
		
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
		return queueMap.get(FAST_POOL).getActiveRequestCount();
	}

	@Override
	public int getFastPoolSize() {
		return queueMap.get(FAST_POOL).getMaximumActiveRequestCount();
	}

	@Override
	public int getFastQueueSize() {
		return queueMap.get(FAST_POOL).getQueueSize();
	}

	@Override
	public int getNormalQueueSize() {
		return queueMap.get(NORMAL_POOL).getQueueSize();
	}

	@Override
	public int getPriorityQueueSize() {
		return queueMap.get(PRIORITY_POOL).getQueueSize();
	}

	@Override
	public int getSlowActive() {
		return queueMap.get(SLOW_POOL).getActiveRequestCount();
	}

	@Override
	public int getSlowPoolSize() {
		return queueMap.get(SLOW_POOL).getMaximumActiveRequestCount();
	}

	@Override
	public int getSlowQueueSize() {
		return queueMap.get(SLOW_POOL).getQueueSize();
	}

	@Override
	public int getSystemQueueSize() {
		return queueMap.get(SYSTEM_POOL).getQueueSize();
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
		return queueMap.get(NORMAL_POOL);
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
		return queueMap.get(NORMAL_POOL).getMaximumActiveRequestCount();
	}

	@Override
	public int getPriorityPoolSize() {
		return queueMap.get(PRIORITY_POOL).getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemPoolSize() {
		return queueMap.get(SYSTEM_POOL).getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemActive() {
		return queueMap.get(SYSTEM_POOL).getActiveRequestCount();
	}

	@Override
	public int getNormalActive() {
		return queueMap.get(NORMAL_POOL).getActiveRequestCount();
	}

	@Override
	public int getPriorityActive() {
		return queueMap.get(PRIORITY_POOL).getActiveRequestCount();
	}

	@Override
	public int flushSystemQueue() {
		return queueMap.get(SYSTEM_POOL).flushQueue();
	}

	@Override
	public int flushNormalQueue() {
		return queueMap.get(NORMAL_POOL).flushQueue();
	}

	@Override
	public int flushPriorityQueue() {
		return  queueMap.get(PRIORITY_POOL).flushQueue();
	}

	@Override
	public int flushSlowQueue() {
		return queueMap.get(SLOW_POOL).flushQueue();
	}

	@Override
	public int flushFastQueue() {
		return queueMap.get(FAST_POOL).flushQueue();
	}
	
	public List<TmlRunnable> getSlowRequests() {
		return queueMap.get(SLOW_POOL).getQueuedRequests();
	}
	
	public List<TmlRunnable> getNormalRequests() {
		return queueMap.get(NORMAL_POOL).getQueuedRequests();
	}
	
	public List<TmlRunnable> getFastRequests() {
		return queueMap.get(FAST_POOL).getQueuedRequests();
	}
	
	public List<TmlRunnable> getPriorityRequests() {
		return queueMap.get(PRIORITY_POOL).getQueuedRequests();
	}
	
	public List<TmlRunnable> getSystemRequests() {
		final RequestQueue pool = queueMap.get(SYSTEM_POOL);
		if(pool==null) {
			return null;
		}
		return pool.getQueuedRequests();
	}
	
	@Override
    public RequestQueue getQueue(String queueid) {
        return queueMap.get(queueid);
    }

	
	public static void main(String [] args) {
		
		long start = System.currentTimeMillis();
		double total = Runtime.getRuntime().maxMemory()/1024.0/1024.0;
		 double free = Runtime.getRuntime().freeMemory()/1024.0/1024.0;
		 System.err.println("Duration: " + (System.currentTimeMillis() - start));
		 
		 System.err.println("total: " + total);
		 System.err.println("free: " + free);
	}

    
}
