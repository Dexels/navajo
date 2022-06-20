/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.global;

import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public interface GlobalManager {

    public static final String GLOBALSMSGNAME = "__globals__";

    /**
     * Sets up the global parameters for this service
     * 
     * @param username
     * @param inMessage
     * @param region
     * @param userRoleString
     * @throws NavajoException
     */
    public void initGlobals(String method, String username, Navajo inMessage, Map<String, String> extraParams)
            throws NavajoException;

    /**
     * Sets up global parameters based on the inMessage. The username and the
     * service name is assumed to be in the Navajo header
     * 
     * @param inMessage
     * @throws NavajoException
     */
    public void initGlobals(Navajo inMessage) throws NavajoException;

    

}  