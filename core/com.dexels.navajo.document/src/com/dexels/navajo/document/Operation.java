package com.dexels.navajo.document;

import java.util.Set;

/**
 * Operations for registering active REST based operations on Navajo Entity objects.
 * An operation supports (HTTP) methods for CRUD operations on Navajo Entity objects:
 * - GET: queries a specific entity
 * - PUT: inserts an entity
 * - POST: updates an entity
 * - DELETE: deletes an entity
 * - HEAD: describes an entity
 * 
 * @author arjenschoneveld
 *
 */
public interface Operation {

	/**
	 * Sets the 'method' for this operation.
	 * Method can be: GET, PUT, POST, HEAD or DELETE
	 * 
	 * @param method
	 */

	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String POST = "POST";
	public static final String HEAD = "HEAD";
	public static final String DELETE = "DELETE";
	
	public void setMethod(String method);
	
	public String getMethod();
	
	public void setValidationService(String service);
	
	public String getValidationService();
	
	public void setService(String service);
	
	public String getService();
	
	public void setEntityName(String entity);
	
	public String getEntityName();
	
	public void setExtraMessage(Message extra);
	
	public Message getExtraMessage();

	public Operation copy(Navajo n);
	
	public void setScopes(String scopes);
	public void setScopes(Set<String> scopes);
	public Set<String> getScopes();
	
	public void setClubModules(String clubModules);
	public String getClubModules();
	
	public void setUserModules(String userModules);
	public String getUserModules();

	public void setDescription(String description);
	public String getDescription();
	
	public void setDebug(String debugString);

    public boolean debugInput();

    public boolean debugOutput();
    
    public void setTenant(String tenant);
    
    public String getTenant();
    
}
