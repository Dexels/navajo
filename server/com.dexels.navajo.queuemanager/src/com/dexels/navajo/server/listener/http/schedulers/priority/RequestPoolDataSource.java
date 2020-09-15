package com.dexels.navajo.server.listener.http.schedulers.priority;

import com.dexels.prometheus.api.PrometheusDataSource;

import io.prometheus.client.Gauge;

public class RequestPoolDataSource implements PrometheusDataSource {

    static final Gauge normalActive = PrometheusDataSource.registerGauge("normalActive", "Normal Active");

    static final Gauge normalPoolSize = PrometheusDataSource.registerGauge("normalPoolSize", "Normal Pool Size");

    static final Gauge normalQueueSize = PrometheusDataSource.registerGauge("normalQueueSize", "Normal Pool Queue Size");

    static final Gauge normalRequestsSize = PrometheusDataSource.registerGauge("normalRequests", "Normal Pool Requests");

    PriorityThreadPoolScheduler scheduler;
    
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
