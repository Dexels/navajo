package com.dexels.navajo.queuemanager.health;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.DispatcherFactory;

public class TmlSchedulerHealthMonitor {
    private static final int THREAD_SLEEP_TIME_NORMAL = 500;
    private static final int THREAD_SLEEP_TIME_LONG = 5000;

    private static final Logger logger = LoggerFactory.getLogger(TmlSchedulerHealthMonitor.class);
    private TmlScheduler tmlScheduler;
    private SchedulerHealthCheckThread healthThread;

    public void setPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = sched;
    }

    public void clearPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = null;
    }

    public void activate() {
        logger.info("Activating TmlSchedulerHealthMonitor...");
        healthThread = new SchedulerHealthCheckThread();
        healthThread.start();
    }

    public void deactivate() {
        logger.info("Deactivating TmlSchedulerHealthMonitor...");
        if (healthThread != null) {
            healthThread.setKeepRunning(false);
        }
    }

    /*
     * Inner class
     */

    private final class SchedulerHealthCheckThread extends Thread {

        private boolean keepRunning = true;

        public void setKeepRunning(boolean keepRunning) {
            this.keepRunning = keepRunning;
        }

        @Override
        public void run() {
            while (keepRunning) {
                int sleepTime = THREAD_SLEEP_TIME_NORMAL;
                try {
                    if (tmlScheduler.getDefaultQueue().getQueueSize() > 0) {
                        logPoolInfo();
                        
                        // The server is not very happy, and to prevent flooding logs we can wait a bit longer 
                        // before logging again
                        sleepTime = THREAD_SLEEP_TIME_LONG;
                    }
                } catch (Throwable t) {
                    logger.error("Exception in SchedulerHealthCheckThread", t);
                    sleepTime = THREAD_SLEEP_TIME_LONG;
                }
                
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    logger.warn("Thread interrupted!");
                    keepRunning = false;
                } catch (Throwable t) {
                    logger.error("Exception in SchedulerHealthCheckThread", t);
                    // Avoid tight loops in error state
                    try {
						Thread.sleep(THREAD_SLEEP_TIME_LONG);
					} catch (InterruptedException e) {
					}
                }
            }
        }

        private void logPoolInfo() {
            Set<Access> all = new HashSet<Access>(DispatcherFactory.getInstance().getAccessSet());
            long currenttime = System.currentTimeMillis();

            String logmsg = "Running requests: ";
            for (Access a : all) {
                StringBuilder sb = new StringBuilder();
                sb.append("\n");
                sb.append("Access ");
                sb.append(a.getAccessID());
                sb.append("; service ");
                sb.append(a.getRpcName());
                sb.append("; user ");
                sb.append(a.getRpcUser());
                sb.append("; runtime ");
                sb.append(currenttime - a.created.getTime());
                logmsg += sb.toString();
            }
            // Skipping async requests for now

            logger.info(logmsg);

        }

    }

}
