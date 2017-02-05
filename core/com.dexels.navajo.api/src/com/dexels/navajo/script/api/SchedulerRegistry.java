package com.dexels.navajo.script.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that is used to register the primary service scheduler.
 * 
 * @author cbrouwer
 *
 */
public class SchedulerRegistry {
    private final static Logger logger = LoggerFactory.getLogger(SchedulerRegistry.class);

    private volatile Scheduler tmlScheduler;
    private static SchedulerRegistry instance;
    
    public void activate() {
        instance = this;
    }
    
    public void deactivate() {
        instance = null;
    }

    public void setTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = scheduler;
    }

    public void clearTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = null;
    }


    public static void submit(TmlRunnable myRunner, boolean lowPrio) throws IOException {
        String queueid = Scheduler.INTERNAL_LOWPRIO_POOL;
        if (!lowPrio) {
            queueid = Scheduler.SYSTEM_POOL;
        }
        instance.tmlScheduler.submit(myRunner, queueid);
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
