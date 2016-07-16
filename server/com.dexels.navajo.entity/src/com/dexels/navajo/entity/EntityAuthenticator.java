package com.dexels.navajo.entity;

import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.script.api.Access;

public interface EntityAuthenticator {
    public String getIdentifier();
    /** 
     * Get a new instance of EntityAuthenticator
     * @return A new instance of EntityAuthenticator to be used to handle <code>request</code>
     */
    public EntityAuthenticator getInstance(HttpServletRequest request);


    public String getUsername();

    public String getPassword();
    
    public boolean isAuthenticated(Access a, Navajo in);
    
    public boolean checkAuthenticationFor(Operation op) throws EntityException;
    
}
