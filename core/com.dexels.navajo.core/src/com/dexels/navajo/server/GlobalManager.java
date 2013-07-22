package com.dexels.navajo.server;

import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public interface GlobalManager {
    /**
     * Sets up the global parameters for this service
     * 
     * @param username
     * @param inMessage
     * @param region
     * @param userRoleString
     * @throws NavajoException
     */
    public void initGlobals(String method, String username, Navajo inMessage, Map<String,String> extraParams) throws NavajoException;

    /**
     * Sets up global parameters based on the inMessage. The username and the service name is assumed to be in the Navajo header
     * @param inMessage
     * @throws NavajoException
     */
    public void initGlobals(Navajo inMessage) throws NavajoException;

}
