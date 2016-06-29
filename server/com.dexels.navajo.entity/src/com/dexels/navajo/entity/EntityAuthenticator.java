package com.dexels.navajo.entity;

import javax.servlet.http.HttpServletRequest;

public interface EntityAuthenticator {
    public String getIdentifier();
    
    public EntityAuthenticator getInstance(HttpServletRequest req);

    public String getUsername();

    public String getPassword();
}
