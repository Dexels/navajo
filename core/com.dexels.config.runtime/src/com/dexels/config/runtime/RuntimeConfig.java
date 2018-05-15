package com.dexels.config.runtime;

public enum RuntimeConfig {
    NO_DBREPLICATION, DEVELOP_MODE, DEBUG_SCRIPTS, TENANT_MASTER, CLUSTER, INSTANCENAME, SIMULATION_MODE, NO_DBTRIGGER,STRICT_TYPECHECK;

    public String getValue() {
        return System.getenv(this.toString());
    }
}
