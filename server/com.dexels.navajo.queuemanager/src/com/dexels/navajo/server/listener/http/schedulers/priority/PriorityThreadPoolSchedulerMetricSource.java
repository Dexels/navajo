/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.listener.http.schedulers.priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.prometheus.api.MetricSource;

import io.prometheus.client.Gauge;

public class PriorityThreadPoolSchedulerMetricSource implements MetricSource {

    private static final Logger logger = LoggerFactory
            .getLogger(PriorityThreadPoolSchedulerMetricSource.class);

    private Gauge normalThreadPoolSize;

    private Gauge activeNormalRequestCount;

    private Gauge queuedNormalRequestCount;

    private PriorityThreadPoolScheduler scheduler;

    public void activate() {

        logger.info("Prometheus priority threadpool scheduler data source started");

        normalThreadPoolSize = MetricSource.registerGauge("navajo_normal_threadpool_size",
                "Maximum number of simultaneously processed requests");

        activeNormalRequestCount = MetricSource.registerGauge("navajo_active_normal_request_count",
                "Number of requests currently processed");

        queuedNormalRequestCount = MetricSource.registerGauge("navajo_queued_normal_request_count",
                "Number of queued requests");
    }

    public void deactivate() {

        logger.info("Prometheus priority threadpool scheduler data source stopped");

        MetricSource.unregisterCollector(normalThreadPoolSize);
        MetricSource.unregisterCollector(activeNormalRequestCount);
        MetricSource.unregisterCollector(queuedNormalRequestCount);
    }

    @Override
    public void recordValues() {

        normalThreadPoolSize.set(scheduler.getNormalPoolSize());
        activeNormalRequestCount.set(scheduler.getNormalActive());
        queuedNormalRequestCount.set(scheduler.getNormalQueueSize());
    }

    public void setPriorityThreadPoolScheduler(PriorityThreadPoolScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void clearPriorityThreadPoolScheduler(PriorityThreadPoolScheduler scheduler) {
        this.scheduler = null;
    }

}
