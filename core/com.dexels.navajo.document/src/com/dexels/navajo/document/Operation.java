/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	
	public void setDescription(String description);
	public String getDescription();
	
	public void setDebug(String debugString);

    public boolean debugInput();

    public boolean debugOutput();
    
    public void setTenant(String tenant);
    
    public String getTenant();
    
	
	
}
