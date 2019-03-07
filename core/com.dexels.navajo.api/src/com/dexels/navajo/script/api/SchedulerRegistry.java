package com.dexels.navajo.script.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that is used to register the primary service scheduler.
 * 
 * @author cbrouwer
 *
 */
public class SchedulerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerRegistry.class);

    private Scheduler tmlScheduler;
    private static SchedulerRegistry instance;
    
    public void activate() {
        if (instance != null && instance.tmlScheduler != null) {
           logger.warn("Overwriting existing scheduler!");
           instance.tmlScheduler.shutdownScheduler();
           instance.tmlScheduler = null;
        }
        setInstance(this);
    }
    
    private static void setInstance(SchedulerRegistry sched) {
    	instance = sched;
    }
    public void deactivate() {
        logger.info("Deactivating SchedulerRegistry" );
        setInstance(null);
    }

    public void setTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = scheduler;
    }

    public void clearTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = null;
    }

  
    public static void submit(TmlRunnable myRunner, boolean lowPrio) {
        String queueid = Scheduler.NAVAJOMAP_LOWPRIO_POOL;
        if (!lowPrio) {
            queueid = Scheduler.NAVAJOMAP_PRIORITY_POOL;
        }
        getScheduler().submit(myRunner, queueid);
    }
    public static boolean submitTask(TmlRunnable task) {
    	if(getScheduler().getQueue(Scheduler.TASKS_POOL).getActiveRequestCount() >= getScheduler().getQueue(Scheduler.TASKS_POOL).getMaximumActiveRequestCount()) {
    		return false;
    	}
    	
    	getScheduler().submit(task, Scheduler.TASKS_POOL);        
    	return true;
    }

    public static void setScheduler(TmlScheduler scheduler) {
        logger.warn("Non-osgi mode!");
        if (instance == null) {
            instance = new SchedulerRegistry();
        }
        instance.tmlScheduler = scheduler;
    }

    public static Scheduler getScheduler() {
        if (instance == null) {
            logger.warn("Non-osgi mode!");
            instance = new SchedulerRegistry();
            instance.tmlScheduler = new SimpleScheduler(true);
        }
        return instance.tmlScheduler;
    }


}
