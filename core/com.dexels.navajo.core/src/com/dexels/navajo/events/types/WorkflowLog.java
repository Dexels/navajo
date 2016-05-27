package com.dexels.navajo.events.types;

import java.util.Date;

import com.dexels.navajo.document.Navajo;

public class WorkflowLog {
    private String workflowId;
    private String workflowName;
    private String workflowTenant;
    private Date startedAt = null;
    private String state;
    private String history;
    private Throwable myException;
    private Navajo inDoc;
    private Navajo outputDoc;
    private boolean debug = false;
    private String location;
    private int exitCode = -1;


    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowTenant() {
        return workflowTenant;
    }

    public void setWorkflowTenant(String workflowTenant) {
        this.workflowTenant = workflowTenant;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    } 

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;

    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;

    }

    public void setInDoc(Navajo inDoc) {
       this.inDoc = inDoc;
        
    }

    public void setOutputDoc(Navajo outputDoc) {
        this.outputDoc = outputDoc;
        
    }
 

    public Throwable getException() {
        return myException;
    }

    public void setException(Throwable myException) {
        this.myException = myException;
    }

    public Navajo getInDoc() {
        return inDoc;
    }

    public Navajo getOutputDoc() {
        return outputDoc;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
         
    }
    
    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitcode) {
       this.exitCode = exitcode;
        
    }
    
    
    

}
