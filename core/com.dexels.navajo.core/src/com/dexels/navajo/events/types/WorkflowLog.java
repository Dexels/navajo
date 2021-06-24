/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
    private Navajo currentStateInDoc;
    private Navajo currentStateOutputDoc;
    private Navajo initiatingInDoc;
    private Navajo initiatingOutputDoc;

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

    public void setCurrentStateInDoc(Navajo inDoc) {
       this.currentStateInDoc = inDoc;
    }

    public void setCurrentStateOutputDoc(Navajo outputDoc) {
        this.currentStateOutputDoc = outputDoc;
    }
    
    public void setInitiatingInDoc(Navajo inDoc) {
        this.initiatingInDoc = inDoc;
     }

     public void setInitiatingOutputDoc(Navajo outputDoc) {
         this.initiatingOutputDoc = outputDoc;
     }
 

    public Throwable getException() {
        return myException;
    }

    public void setException(Throwable myException) {
        this.myException = myException;
    }

    public Navajo getCurrentStateInDoc() {
        return currentStateInDoc;
    }

    public Navajo getCurrentStateOutputDoc() {
        return currentStateOutputDoc;
    }
    
    

    public Navajo getInitiatingInDoc() {
        return initiatingInDoc;
    }

    public Navajo getInitiatingOutputDoc() {
        return initiatingOutputDoc;
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
