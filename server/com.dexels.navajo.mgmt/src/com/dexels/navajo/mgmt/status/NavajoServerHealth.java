/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mgmt.status;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
import com.dexels.navajo.server.enterprise.workflow.WorkFlowManagerInterface;
import com.dexels.server.mgmt.api.ServerHealthCheck;

public class NavajoServerHealth implements ServerHealthCheck {
    private DispatcherInterface dispatcherInterface;
    private JavaCompiler javaCompiler;
    private NavajoConfigInterface navajoConfig;
    private TribeManagerInterface tribeManagerInterface;
    private WorkFlowManagerInterface workflowManagerInterface;
    private TmlScheduler tmlScheduler;
    private EntityManager entityManager;

    private static final int DEFAULT_MAX_QUEUED_REQUEST = 40;
    private int maxQueuedRequests = DEFAULT_MAX_QUEUED_REQUEST;
    
	private static final Logger logger = LoggerFactory.getLogger(NavajoServerHealth.class);

    public void activate(Map<String,Object> settings) {
    	Object max =  settings.get("maxQueue");
    	if(max!=null) {
    		if(max instanceof String) {
    			this.maxQueuedRequests = Integer.parseInt((String) max);
    		} else if(max instanceof Integer) {
    			this.maxQueuedRequests = (int) max;
    		} else {
    			logger.warn("Unexpected maxQueue setting: {}",max);
    		}
     	}
    }
    
    @Override
    public boolean isOk() {
        boolean threadsFull = tmlScheduler != null && (tmlScheduler.getDefaultQueue().getQueueSize() > maxQueuedRequests);
        return navajoConfig != null && dispatcherInterface != null && javaCompiler != null && workflowManagerInterface != null 
                && !getTribeManagerProblem().isPresent() && tmlScheduler != null && entityManager != null && entityManager.isFinishedCompiling() 
                && !threadsFull;
    }

    @Override
    public String getDescription() {
        if (isOk()) {
            return "Navajo Health";
        }

        if (navajoConfig == null) {
            return "No configuration";
        }
        if (dispatcherInterface == null) {
            return "No dispatcher";
        }
        if (javaCompiler == null) {
            return "No java compiler";
        }
        //
        Optional<String> tribeIssue = getTribeManagerProblem();
        if(tribeIssue.isPresent()) {
        	return tribeIssue.get();
        }
        
        if (workflowManagerInterface == null) {
            return "No workflow manager";
        }
        if (tmlScheduler == null) {
            return "No tmlScheduler";
        }
        if (entityManager == null) {
            return "No entityManager";
        }
        if (!entityManager.isFinishedCompiling()) {
            return "EntityManager compiling";
        }
        int queueSize = tmlScheduler.getDefaultQueue().getQueueSize();
		boolean threadsFull = (queueSize > maxQueuedRequests);
        if (threadsFull) {
            String message = "Queue size: "+queueSize+" is greater than the defined max of: "+maxQueuedRequests;
            logger.warn(message);
			return message;
        }
        return "";
    }
    
    private Optional<String> getTribeManagerProblem() {
        if (tribeManagerInterface == null) {
            return Optional.of("No tribe manager");
        }
        if (!tribeManagerInterface.isActive()) {
            return Optional.of("No active tribe manager");
        }
        if(TribeManagerFactory.getInstance()==null) {
            return Optional.of("No active tribe manager from factory");
        }
        return Optional.empty();
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

    public void setPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = sched;
    }

    public void clearPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = null;
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

    public void setEntityManager(EntityManager ent) {
        this.entityManager = ent;
    }

    public void clearEntityManager(EntityManager ent) {
        this.entityManager = null;
    }
}
