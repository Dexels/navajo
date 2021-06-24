/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
