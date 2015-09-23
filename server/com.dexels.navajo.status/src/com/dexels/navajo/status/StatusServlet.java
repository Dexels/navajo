package com.dexels.navajo.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.repository.api.ServerStatusChecker;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowManagerInterface;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class StatusServlet extends HttpServlet implements ServerStatusChecker, EventHandler {

    private static final String CACHE_NAVAJO_KEY = "Navajo";
    private static final String CACHE_EXCEPTIONS_KEY = "Exceptions";

    private static final long serialVersionUID = -1892139782799840837L;
    
    private DispatcherInterface dispatcherInterface;
    private JavaCompiler javaCompiler;
    private Repository repository;
    private NavajoConfigInterface navajoConfig;
    private TribeManagerInterface tribeManagerInterface;
    private WorkFlowManagerInterface workflowManagerInterface;
    private TmlScheduler tmlScheduler;
    
    private Object sync = new Object();
    private LoadingCache<String, Integer> cache = null;

    
    private final static Logger logger = LoggerFactory.getLogger(StatusServlet.class);


    public void activate() {
        cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).softValues()
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
        
        logger.info("Navajo Status servlet activated");
    }

    public void deactivate() {
        logger.info("Navajo Status servlet deactivated");
    }
    
   
 

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestTpe = req.getParameter("type");
        String res = null;
        if (requestTpe.equals("normalpoolstatus")) {
            if (tmlScheduler == null) {
                res = "error";
            } else {
                res = tmlScheduler.getDefaultQueue().getActiveRequestCount() + "/"
                        + tmlScheduler.getDefaultQueue().getMaximumActiveRequestCount() + "/"
                        + tmlScheduler.getDefaultQueue().getQueueSize();
            }

        } else if (requestTpe.equals("memory")) {
            // Check current memory usage.
            long max = Runtime.getRuntime().maxMemory();
            long total = Runtime.getRuntime().totalMemory();
            long free = Runtime.getRuntime().freeMemory();
            res = (total - free) + "/" + max;
        } else if (requestTpe.equals("users")) {
            // Check current memory usage.
            Set<Access> all = new HashSet<Access>(com.dexels.navajo.server.DispatcherFactory.getInstance().getAccessSet());
            Map<String, Map<String, String>> resultMap = new HashMap<>();
            
            for (Access a : all) {
                Map<String, String> fields = new HashMap<>();
                fields.put("startTime", a.getCreated().toString());
                fields.put("runTime",  new Long( System.currentTimeMillis() - a.created.getTime() ).toString());
                fields.put("rpcName", a.getRpcName());
                fields.put("rpcUser", a.getRpcUser());
                fields.put("async", a.getRpcUser());
                resultMap.put(a.getAccessID(), fields);
            }
            res = resultMap.toString();

        } else if (requestTpe.equals("requestcount")) {
            Integer navajo = 0;
            Integer exceptions = 0;
            synchronized (sync) {
                try {
                    navajo = cache.get(CACHE_NAVAJO_KEY);
                    exceptions = cache.get(CACHE_EXCEPTIONS_KEY);
                    res = exceptions.toString() + "/" + navajo.toString();
                } catch (ExecutionException e) {
                    logger.warn("ExecutionException exception while getting cached Navajo counters!", e);
                    res = "error";
                }
            }
        }
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.write(res);
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isOk()) {
            if (navajoConfig == null) {
                resp.sendError(500, "No configuration");
                logger.info("Status failed: 500, no configuration");
                return;
            }
            if (dispatcherInterface == null) {
                resp.sendError(501, "No dispatcher");
                logger.info("Status failed: 501, no dispatcher");
                return;
            }
            if (javaCompiler == null) {
                resp.sendError(502, "No java compiler");
                logger.info("Status failed: 502, no java compiler");
                return;
            }
            if (repository == null) {
                resp.sendError(503, "No repository");
                logger.info("Status failed: 503, no repository");
                return;
            }
            if (tribeManagerInterface == null) {
                resp.sendError(504, "No tribe manager");
                logger.info("Status failed: 504, no repository");
                return;
            }
            if (!tribeManagerInterface.isActive()) {
                resp.sendError(505, "No activate tribe manager");
                logger.info("Status failed: 505, no repository");
                return;
            }
            if (workflowManagerInterface == null) {
                resp.sendError(506, "No workflow manager");
                logger.info("Status failed: 506, no repository");
                return;
            }
            // Shouldn't happen?
            return;
        }

        logger.debug("Navajo status ok");
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.write("OK");
        writer.close();
    }

    public void setNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = nci;
    }

    public void clearNavajoConfig(NavajoConfigInterface nci) {
        this.navajoConfig = null;
    }

    public void setJavaCompiler(JavaCompiler javaCompiler) {
        this.javaCompiler = javaCompiler;
    }

    public void clearJavaCompiler(JavaCompiler javaCompiler) {
        this.javaCompiler = null;
    }

    public void setDispatcherInterface(DispatcherInterface dispatcherInterface) {
        this.dispatcherInterface = dispatcherInterface;
    }

    public void clearDispatcherInterface(DispatcherInterface dispatcherInterface) {
        this.dispatcherInterface = null;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void clearRepository(Repository repository) {
        this.repository = null;
    }
    

    @Override
    public Boolean isOk() {
        return navajoConfig != null && dispatcherInterface != null && javaCompiler != null 
        		&& repository != null && workflowManagerInterface!=null && tribeManagerInterface!=null;
    }

	public TribeManagerInterface getTribeManagerInterface() {
		return tribeManagerInterface;
	}

	public void setTribeManagerInterface(TribeManagerInterface tribeManagerInterface) {
		this.tribeManagerInterface = tribeManagerInterface;
	}

	public void clearTribeManagerInterface(TribeManagerInterface tribeManagerInterface) {
		this.tribeManagerInterface = null;
	}


	public void setWorkflowManagerInterface(WorkFlowManagerInterface workflowManagerInterface) {
		this.workflowManagerInterface = workflowManagerInterface;
	}

	public void clearWorkflowManagerInterface(WorkFlowManagerInterface workflowManagerInterface) {
		this.workflowManagerInterface = null;
	}
	
	public void setPriorityTmlScheduler(TmlScheduler sched) {
	    this.tmlScheduler = sched;
	}
	
	public void clearPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = null;
    }

    @Override
    public void handleEvent(Event event) {
        try {
            String type = (String) event.getProperty("type");
            String key = null;
            if (type.equals("navajoexception")) {
                key = CACHE_EXCEPTIONS_KEY;
            } else if (type.equals("navajo")) {
                key = CACHE_NAVAJO_KEY;
            }
            synchronized (sync) {
                Integer current = cache.get(key);
                cache.put(key, ++current);
            }
        } catch (Exception e) {
            logger.error("Someweird weird happened while trying to handle an event! ", e);

        }
    }

}
