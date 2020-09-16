package com.dexels.navajo.runtime.config;

public enum RuntimeConfig {

    DEBUG_SCRIPTS,
    CLUSTER,
    DEVELOP_MODE,
    INSTANCENAME,
    NO_DBREPLICATION,
    NO_DBTRIGGER,
    SIMULATION_MODE,
    STRICT_TYPECHECK,
    TENANT_MASTER;

    public String getValue() {
        return System.getenv(this.toString());
    }

}
