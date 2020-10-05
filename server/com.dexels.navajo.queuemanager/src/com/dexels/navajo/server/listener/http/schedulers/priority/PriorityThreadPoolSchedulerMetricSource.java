package com.dexels.navajo.server.listener.http.schedulers.priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.prometheus.api.MetricSource;

import io.prometheus.client.Gauge;

public class PriorityThreadPoolSchedulerMetricSource implements MetricSource {

    private static final Logger logger = LoggerFactory
            .getLogger(PriorityThreadPoolSchedulerMetricSource.class);

    private Gauge normalPoolSize;

    private Gauge normalActive;

    private Gauge normalQueueSize;

    private PriorityThreadPoolScheduler scheduler;

    public void activate() {

        logger.info("Prometheus priority threadpool scheduler data source started");

        normalPoolSize = MetricSource.registerGauge("normalPoolSize",
                "Maximum number of simultaneously processed requests");

        normalActive = MetricSource.registerGauge("normalActive",
                "Number of requests currently processed");

        normalQueueSize = MetricSource.registerGauge("normalQueueSize",
                "Number of queued requests");
    }

    public void deactivate() {

        logger.info("Prometheus priority threadpool scheduler data source stopped");

        MetricSource.unregisterCollector(normalPoolSize);
        MetricSource.unregisterCollector(normalActive);
        MetricSource.unregisterCollector(normalQueueSize);
    }

    @Override
    public void recordValues() {

        normalPoolSize.set(scheduler.getNormalPoolSize());
        normalActive.set(scheduler.getNormalActive());
        normalQueueSize.set(scheduler.getNormalQueueSize());
    }

    public void setPriorityThreadPoolScheduler(PriorityThreadPoolScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void clearPriorityThreadPoolScheduler(PriorityThreadPoolScheduler scheduler) {
        this.scheduler = null;
    }

}
