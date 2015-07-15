package com.dexels.navajo.status;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.repository.api.ServerStatusChecker;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowManagerInterface;

public class StatusServlet extends HttpServlet implements ServerStatusChecker {

    private static final long serialVersionUID = -1892139782799840837L;

    private DispatcherInterface dispatcherInterface;
    private JavaCompiler javaCompiler;
    private Repository repository;
    private NavajoConfigInterface navajoConfig;
    private TribeManagerInterface tribeManagerInterface;
    private WorkFlowManagerInterface workflowManagerInterface;
    
    
    private final static Logger logger = LoggerFactory.getLogger(StatusServlet.class);

    public StatusServlet() {
    }

    public void activate() {
        logger.info("Navajo Status servlet activated");
    }

    public void deactivate() {
        logger.info("Navajo Status servlet deactivated");
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
}
