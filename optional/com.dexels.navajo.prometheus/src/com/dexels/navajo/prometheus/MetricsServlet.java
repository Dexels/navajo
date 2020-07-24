package com.dexels.navajo.prometheus;

//import com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolSchedulerMBean;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.common.TextFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolScheduler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * The MetricsServlet class exists to provide a simple way of exposing the metrics values.
 *
 */
public class MetricsServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final Gauge normalActive = Gauge.build()
    .name("normalActive").help("Normal Active").register();
	
	static final Gauge normalPoolSize = Gauge.build()
		    .name("normalPoolSize").help("Normal Pool Size").register();
			
	static final Gauge normalQueueSize = Gauge.build()
		    .name("normalQueueSize").help("Normal Pool Queue Size").register();
	
	static final Gauge normalRequestsSize = Gauge.build()
		    .name("normalRequests").help("Normal Pool Requests").register();
	
	static final Gauge availableCores = Gauge.build()
		    .name("availableCores").help("Available Cores JVM").register();
	
	static final Gauge freeMemory = Gauge.build()
		    .name("freeMemory").help("Free Memory JVM").register();
	
	static final Gauge totalMemoryUse = Gauge.build()
		    .name("totalMemoryUse").help("Total Memory in use JVM").register();
	
	PriorityThreadPoolScheduler m;
	
	

    private CollectorRegistry registry;

    /**
     * Construct a MetricsServlet for the default registry.
     */
    public MetricsServlet(PriorityThreadPoolScheduler m) {
        this(CollectorRegistry.defaultRegistry);
        this.m = m;
    }

    /**
     * Construct a MetricsServlet for the given registry.
     * @param registry collector registry
     */
    public MetricsServlet(CollectorRegistry registry) {
        this.registry = registry;
    }
    
    public void checkMem() {
    	/* Total number of processors or cores available to the JVM */
    	  System.out.println("Gem--- Available processors (cores): " + 
    	  Runtime.getRuntime().availableProcessors());
    	  availableCores.set( (double) Runtime.getRuntime().availableProcessors() );

    	  /* Total amount of free memory available to the JVM */
    	  System.out.println("Gem--- Free memory (bytes): " + 
    	  Runtime.getRuntime().freeMemory());
    	  freeMemory.set( (double) Runtime.getRuntime().freeMemory() );

    	  /* This will return Long.MAX_VALUE if there is no preset limit */
    	  long maxMemory = Runtime.getRuntime().maxMemory();
    	  /* Maximum amount of memory the JVM will attempt to use */
    	  System.out.println("Gem--- Maximum memory (bytes): " + 
    	  (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

    	  /* Total memory currently in use by the JVM */
    	  System.out.println("Gem--- Total memory (bytes): " + 
    	  Runtime.getRuntime().totalMemory());
    	  totalMemoryUse.set( (double) Runtime.getRuntime().totalMemory() );
    	  
    } 

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("aaa");
        
        checkMem();
        
        normalActive.set( (double) m.getNormalActive() );
        normalPoolSize.set( (double) m.getNormalPoolSize() );
        normalQueueSize.set( (double) m.getNormalQueueSize() );
        normalRequestsSize.set( (double) m.getNormalRequests().size() );
        
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(TextFormat.CONTENT_TYPE_004);

        Writer writer = new BufferedWriter(resp.getWriter());
        try {
            TextFormat.write004(writer, registry.filteredMetricFamilySamples(parse(req)));
            writer.flush();
        } finally {
            writer.close();
        }
    }

    private Set<String> parse(HttpServletRequest req) {
        String[] includedParam = req.getParameterValues("name[]");
        if (includedParam == null) {
            return Collections.emptySet();
        } else {
            return new HashSet<String>(Arrays.asList(includedParam));
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

}