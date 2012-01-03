package com.dexels.navajo.authentication.api;

import com.dexels.navajo.document.Navajo;

/**
 * <p>Title: <h3>SportLink Services</h3><br></p>
 * <p>Description: Web Services for the SportLink Project<br><br></p>
 * <p>Copyright: Copyright (c) 2002<br></p>
 * <p>Company: Dexels.com<br></p>
 * <br>
 * This interface specifies the requirements of the AAA (Authorisation, Authentication and Audit)
 * document created by the "werkgroep Autorisatie/Authenticatie" d.d. 20-12-2002.
 *
 * @author Arjen Schoneveld
 * @version $Id$
 */

public interface AAAInterface {


    public static final String AUTH_PASSWORD = "password";
    public static final String AUTH_TOKEN = "token";
    public static final String AUTH_CERTIFICATE = "certificate";

    public static final String UNKNOWN_USER = "Unknown user";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String INVALID_CERTIFICATE = "Invalid certificate";
    public static final String EMPTY_CERTIFICATE = "Empty certificate";

    public static final String EMPTY_DISTRICTS = "No districts specified in database";

    /**
     * Return all valid districts for a given user.
     *
     * @param username
     * @param password
     * @return
     * @throws AAAException
     */
    public String [] checkUser(String username, String password) throws AAAException;

    /**
     * Determine whether a user is autenticated.
     *
     * @param username
     * @param password
     * @param token
     * @param authObject
     * @return userId and region
     * @throws AAAException
     */
    public int isAuthenticated(String username, String password, String token, Object authObject, StringBuffer region) throws AAAException;

    /**
     * Determine the number of days left before the password will expire.
     *
     * @param username
     * @return the number of days left before the password expires
     * @throws AAAException
     */
	public int checkExpiration(String username) throws AAAException;
	
    /**
     * Determine whether an account is blocked.
     *
     * @param username
     * @return true if account is blocked, and false otherwise
     * @throws AAAException
     */
	public boolean checkBlocked(String username) throws AAAException;
	
    /**
     * Determine whether an account is being used for the first time.
     *
     * @param username
     * @return true if account is being accessed for the first time, and false otherwise
     * @throws AAAException
     */
	public boolean checkFirstTimeUse(String username) throws AAAException;
	
    /**
     *
     * @param region
     * @param username
     * @param actionObject
     * @param inputData
     * @return
     * @throws AAAException
     */
    public boolean isAuthorizedAccess(String region, String username, String actionObject, Navajo inputData) throws AAAException;

    /**
     * Authenticate a user, using username and password.
     *
     * @param username
     * @param password
     * @return a String array containg all regions (organizations) in which the user is allowed to log in to.
     *
     * @throws AAAException
     */
    //public String [] getUserDistricts(String username) throws AAAException;

    /**
     * Get the roles of user
     *
     * @param username
     * @param password
     * @return the names of all the roles
     */
    public String [] getUserRoles(String username, String organizationId) throws AAAException;

    /**
     * Check the validity of a token
     *
     * @param token, the token to be checked.
     *
     * @return true if the token is valid, false otherwise
     */
    public boolean isValidToken(String token) throws AAAException;

    /**
     * Return the region identifier from the token
     *
     * @param token
     * @return the region (organization) name
     */
    public String getRegionFromToken(String token) throws AAAException;

    /**
     * Construct a token from the following parameters:
     *
     * @param region
     * @param username
     * @param expiry in millis.
     * @return a valid token
     */
    public String constructToken(String region, String username, long expiry);

    /**
     * Reset AAA module, i.e. re-load all configuration data.
     *
     */
    public void destroy();

    /**
     * Reset user credentials for specified username.
     *
     * @param username
     */
    public void resetUserCredential(String username);

    /**
     * Clear cache for valid action objects. Needs to be called in case of any user_role or role changes.
     *
     */
    public void clearActionObjects();
    
    
    /**
     * Get priority for OSGi resolution. If there are multiple instances active, the highest one will be chosen
     * TODO think this through: How do we want to resolve multiple instances?
     * - Fail completely
     * - Use all of them
     * - Use any of them (try until one succeeds)
     * - Use the one with the LOWEST prio
     * @return
     */
    public int getPriority();

}