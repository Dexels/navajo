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

	private int timeout = 7200000;
	private int throttleDelay = 2000;
	
	private static int WINDOW_SIZE                 = 500;
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
	private final Map<String,Integer> queueMaxBacklogMap = new HashMap<String,Integer>();
	
	
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
		
		queueManager = QueueManagerFactory.getInstance();
		queueManager.setQueueContext(this);
		queueManager.setScriptDir(new File(DispatcherFactory.getInstance().getNavajoConfig().getConfigPath()));
		
		createPools(initParams);
	}

    public void activate(Map<String, Object> settings) {
        logger.info("Activating prio threadpool scheduler...");

        try {
            createPools(settings);
        } catch (Throwable e) {
            logger.error("Error creating pools: ", e);
        }
    }
    
    public void modified(Map<String, Object> settings) {
        logger.warn("MODIFIED NOT SUPPORTED FOR PriorityThreadPoolScheduler! RESTART SERVER TO APPLY");
    }
    
    public void deactivate() {
        logger.debug("Deactivating priority queue");
        for (String key : queueMap.keySet()) {
            queueMap.get(key).shutDownQueue();
        }
        queueMap.clear();
        
    }

	private Integer extractPoolSize(Map<String, Object> params, String key) {
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
	private Integer extractPoolPrio(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if(value==null) {
            return Thread.NORM_PRIORITY;
        }
        if(value instanceof Integer) {
            return (Integer) params.get(key);
        }
        if(value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return Thread.NORM_PRIORITY;
    }
	private Integer extractMaxBacklogSize(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if(value==null) {
            if (key.equals(TASKS_POOL) || key.equals(NAVAJOMAP_LOWPRIO_POOL)) {
                return Integer.MAX_VALUE;
            }
            return maxbacklog;
        }
        if(value instanceof Integer) {
            return (Integer) params.get(key);
        }
        if(value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return maxbacklog;
    }
	

	private void createPools(Map<String, Object> settings) {
	    createPool(SLOW_POOL, settings);
	    createPool(NORMAL_POOL, settings);
	    createPool(EXT_LOWPRIO_POOL, settings);
	    createPool(NAVAJOMAP_PRIORITY_POOL, settings);
	    createPool(TESTER_POOL, settings);
	    createPool(TASKS_POOL, settings);
	    createPool(NAVAJOMAP_LOWPRIO_POOL, settings);
	    
    }
	
	private void createPool(String name, Map<String, Object> settings) {
	    int poolSize = extractPoolSize(settings, name + "Size");
	    int maxBacklogSize =extractMaxBacklogSize(settings, name + "MaxBacklogSize") ;
	    int prio = extractPoolPrio(settings, name + "Prio");
	    queueMap.put(name, ThreadPoolRequestQueue.create(this, name, prio, poolSize));
	    queueMaxBacklogMap.put(name, maxBacklogSize );
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
			return queueMap.get(NAVAJOMAP_PRIORITY_POOL);
		}
		if ( myRunner.getAttributeNames().contains("tester") && (boolean) myRunner.getAttribute("tester")) {
            return queueMap.get(TESTER_POOL);
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
	        
	        
	        if (LoginStatisticsProvider.reachedAbortThreshold(ic.getUserName())) {
	            logger.warn("Refusing request from {} for {} due to too many failed auth attempts",  ic.getUserName(), ic.getIpAddress());
	            return null;
	        } else if (LoginStatisticsProvider.reachedRateLimitThreshold(ic.getUserName())) {
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
	                logger.info("resolutionscript: Could not find queue resolution script, using default queue.",e);
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
	

    @Override
    public void submit(TmlRunnable myRunner, String queueid) {
        submitToPool(myRunner, getQueue(queueid) ) ;
        
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
		    logger.warn("Null pool - cannot schedule request!");
			run.abort("Scheduling refused");
			return;
		}
		
		if (pool.getActiveRequestCount() >= pool.getMaximumActiveRequestCount()) {
            logger.warn("All {} threads in {} busy. Entering queue with {} threads ahead of us",
                    pool.getMaximumActiveRequestCount(), pool.getId(), pool.getQueueSize());
		}
		if( pool.getQueueSize() >= queueMaxBacklogMap.get(pool.getId())) {
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
		return queueMap.get(NAVAJOMAP_PRIORITY_POOL).getExpectedQueueTime();
	}
	@Override
	public final double getExpectedSystemQueueTime() {
		return queueMap.get(TASKS_POOL).getExpectedQueueTime();
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
	    String status = "";
	    for (String key : queueMap.keySet()) {
	        RequestQueue requestQueue = queueMap.get(key);
	        status += key;
	        status += ": ";
	        status += requestQueue.getActiveRequestCount();
	        status += "/";
            status += requestQueue.getMaximumActiveRequestCount();
            status += "/";
            status += requestQueue.getQueueSize();
            status += ", ";
	    }
	    return status.substring(0, status.length()-2);
	}

	@Override
	public void shutdownScheduler() {
	    for (RequestQueue queue : queueMap.values()) {
	        queue.shutDownQueue();
	        logger.info("Shutdown complete");
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
		return 0;
	}

	@Override
	public int getFastPoolSize() {
		return 0;
	}

	@Override
	public int getFastQueueSize() {
		return 0;
	}

	@Override
	public int getNormalQueueSize() {
		return queueMap.get(NORMAL_POOL).getQueueSize();
	}

	@Override
	public int getPriorityQueueSize() {
		return queueMap.get(NAVAJOMAP_PRIORITY_POOL).getQueueSize();
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
		return queueMap.get(TASKS_POOL).getQueueSize();
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
		return queueMap.get(NORMAL_POOL).getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemPoolSize() {
		return queueMap.get(TASKS_POOL).getMaximumActiveRequestCount();
	}

	@Override
	public int getSystemActive() {
		return queueMap.get(TASKS_POOL).getActiveRequestCount();
	}

	@Override
	public int getNormalActive() {
		return queueMap.get(NORMAL_POOL).getActiveRequestCount();
	}

	@Override
	public int getPriorityActive() {
		return queueMap.get(NAVAJOMAP_PRIORITY_POOL).getActiveRequestCount();
	}

	@Override
	public int flushSystemQueue() {
		return queueMap.get(TASKS_POOL).flushQueue();
	}

	@Override
	public int flushNormalQueue() {
		return queueMap.get(NORMAL_POOL).flushQueue();
	}

	@Override
	public int flushPriorityQueue() {
		return  queueMap.get(NAVAJOMAP_PRIORITY_POOL).flushQueue();
	}

	@Override
	public int flushSlowQueue() {
		return queueMap.get(SLOW_POOL).flushQueue();
	}

	@Override
	public int flushFastQueue() {
	    return 0;

	}
	
	public List<TmlRunnable> getSlowRequests() {
		return queueMap.get(SLOW_POOL).getQueuedRequests();
	}
	
	public List<TmlRunnable> getNormalRequests() {
		return queueMap.get(NORMAL_POOL).getQueuedRequests();
	}
	
	public List<TmlRunnable> getFastRequests() {
		return null;
	}
	
	public List<TmlRunnable> getPriorityRequests() {
		return queueMap.get(NAVAJOMAP_PRIORITY_POOL).getQueuedRequests();
	}
	
	public List<TmlRunnable> getSystemRequests() {
		final RequestQueue pool = queueMap.get(TASKS_POOL);
		if(pool==null) {
			return null;
		}
		return pool.getQueuedRequests();
	}
	
	@Override
    public RequestQueue getQueue(String queueid) {
	    if (!queueMap.containsKey(queueid)) {
	        logger.info("Requested pool {} not available, returning default queue!", queueid);
	        return getDefaultQueue();
	    }
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
