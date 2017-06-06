package com.dexels.navajo.mgmt.status;

import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;
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

    @Override
    public boolean isOk() {
        return navajoConfig != null && dispatcherInterface != null && javaCompiler != null && workflowManagerInterface != null 
                && tribeManagerInterface != null && tmlScheduler != null && entityManager != null && entityManager.isFinishedCompiling();
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
        if (tribeManagerInterface == null) {
            return "No tribe manager";
        }
        if (!tribeManagerInterface.isActive()) {
            return "No activate tribe manager";
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
        return "";
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
