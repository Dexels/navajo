package com.dexels.navajo.entity;

import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.script.api.Access;

public interface EntityAuthenticator {
    public String getIdentifier();
    
    public EntityAuthenticator getInstance(HttpServletRequest req);

    public String getUsername();

    public String getPassword();
    
    public boolean isAuthenticated(Access a, Entity e);
}
