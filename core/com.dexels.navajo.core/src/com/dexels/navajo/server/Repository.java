package com.dexels.navajo.server;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.SystemException;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 *
 *
 * @TODO: Extend functionality of repository interface:
 * addService(service_name, group)
 * addGroup(group_name, handler)
 * addUser(username, password)
 * addAuthorisation(username, group_name)
 */

/**
 * This interface describes the methods that are required for a Repository implementation.
 */

public interface Repository {

	/**
	 * Sets the NavajoConfig instance.
	 * @param config
	 */
    public void setNavajoConfig(NavajoConfigInterface config);

    /**
     * Authorize a Navajo user based on username, password and service name. An Access object is returned that can
     * be used in subsequent calls to the other Repository methods.
     * 
     * @param username the Navajo username
     * @param password the password
     * @param service the Navajo services
     * @param inMessage the Navajo inMessage
     * @param certificate optionally a security token
     * @return
     * @throws SystemException
     * @throws AuthorizationException
     */
    public Access authorizeUser(String username, String password, String service, Navajo inMessage, Object certificate) throws SystemException, AuthorizationException;


    /**
     * Get the dispatcher class from the repository using the Access object.
     * 
     */
    public String getServlet(Access access) throws SystemException;


    /**
     * Legacy date mode will truncate dates to day level (so time is discarded)
     * @return
     */
	public boolean useLegacyDateMode();

}
