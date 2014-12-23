package com.dexels.navajo.dependency;

import org.codehaus.jackson.annotate.JsonIgnore;


public class Dependency {
    public static final int UNKNOWN_TYPE = 0;
    public static final int INCLUDE_DEPENDENCY = 1;
    public static final int NAVAJO_DEPENDENCY = 2;
    public static final int METHOD_DEPENDENCY = 3;
    public static final int ENTITY_DEPENDENCY = 4;
    public static final int TASK_DEPENDENCY = 5;
    public static final int WORKFLOW_DEPENDENCY = 6;
    public static final int BROKEN_DEPENDENCY = 7;

    private int type;
    private String scriptFile;
    private String dependeeFile;
    
    public Dependency() {
        // JSON serialisation likes to have a constructor...
    }

    public Dependency(String scriptFile, String dependeeFile, int type) {
        this.scriptFile = scriptFile;
        this.dependeeFile = dependeeFile;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getScriptFile() {
        return scriptFile;
    }

    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    public String getDependeeFile() {
        return dependeeFile;
    }

    public void setDependeeFile(String dependeeFile) {
        this.dependeeFile = dependeeFile;
    }

    @JsonIgnore
    public String getScript() {
        String scriptFileRel = null;
        if (scriptFile.indexOf("workflows") > 0) {
            scriptFileRel = scriptFile.split("workflows")[1];
            return scriptFileRel.substring(1, scriptFileRel.indexOf('.'));
        } else {
            scriptFileRel = scriptFile.split("scripts")[1];
        }
        return scriptFileRel.substring(1, scriptFileRel.indexOf('.'));

    }

    @JsonIgnore
    public String getDependee() {
        String scriptFileRel = null;
        if (dependeeFile.indexOf("workflows") > 0) {
            scriptFileRel = dependeeFile.split("workflows")[1];
            
        } else {
            scriptFileRel = dependeeFile.split("scripts")[1];
        }
        scriptFileRel = scriptFileRel.substring(1);
        
        return scriptFileRel.substring(0, scriptFileRel.indexOf('.'));
    }

    @Override
    public String toString() {
        return getScript() + " - " + getDependee();
    }

    public boolean isTentantSpecificDependee() {
        return tenantFromScriptPath(getDependee()) != null;
    }

    public String getTentantDependee() {
        return tenantFromScriptPath(getDependee());
    }
    
    private String tenantFromScriptPath(String scriptPath) {
        int scoreIndex = scriptPath.lastIndexOf("_");
        int slashIndex = scriptPath.lastIndexOf("/");
        if(scoreIndex>=0 && slashIndex < scoreIndex) {
            return scriptPath.substring(scoreIndex+1, scriptPath.length());
        } else {
            return null;
        }
    }

}
