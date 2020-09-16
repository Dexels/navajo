package com.dexels.navajo.server.listener.http.schedulers.priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.prometheus.api.MetricSource;

import io.prometheus.client.Gauge;

public class PriorityThreadPoolSchedulerMetricSource implements MetricSource {

    private static final Logger logger = LoggerFactory.getLogger(PriorityThreadPoolSchedulerMetricSource.class);

    private static Gauge normalActive;

    private Gauge normalPoolSize;

    private Gauge normalQueueSize;

    private Gauge normalRequestsSize;

    private PriorityThreadPoolScheduler scheduler;
    
    public void activate() {

        logger.info("Prometheus priority threadpool scheduler data source started");
        normalActive = MetricSource.registerGauge("normalActive", "Normal Active");
        normalPoolSize = MetricSource.registerGauge("normalPoolSize", "Normal Pool Size");
        normalQueueSize = MetricSource.registerGauge("normalQueueSize", "Normal Pool Queue Size");
        normalRequestsSize = MetricSource.registerGauge("normalRequests", "Normal Pool Requests");
    }
    
    public void deactivate() {

        logger.info("Prometheus priority threadpool scheduler data source stopped");
        MetricSource.unregisterGauge(normalActive);
        MetricSource.unregisterGauge(normalPoolSize);
        MetricSource.unregisterGauge(normalQueueSize);
        MetricSource.unregisterGauge(normalRequestsSize);
    }
    
    @Override
    public void recordValues() {

        normalActive.set((double) scheduler.getNormalActive());
        normalPoolSize.set((double) scheduler.getNormalPoolSize());
        normalQueueSize.set((double) scheduler.getNormalQueueSize());
        normalRequestsSize.set((double) scheduler.getNormalRequests().size());
    }

    public void setPriorityThreadPoolScheduler(PriorityThreadPoolScheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    public void clearPriorityThreadPoolScheduler(PriorityThreadPoolScheduler scheduler) {
        this.scheduler = null;
    }
}
