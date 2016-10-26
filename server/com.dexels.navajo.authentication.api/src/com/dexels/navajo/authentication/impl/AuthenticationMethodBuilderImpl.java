package com.dexels.navajo.authentication.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.authentication.api.AuthenticationMethod;
import com.dexels.navajo.authentication.api.AuthenticationMethodBuilder;

public class AuthenticationMethodBuilderImpl implements AuthenticationMethodBuilder {
    private Map<String, AuthenticationMethod> instances = new HashMap<>();

    public void addMethod(AuthenticationMethod method) {
        instances.put(method.getIdentifier(), method);
    }
    
    public void removeMethod(AuthenticationMethod method) {
        instances.remove(method.getIdentifier());
    }
    
    @Override
    public AuthenticationMethod getInstanceForRequest(String header) {        
        if (header != null && header.startsWith("Basic")) {
            return instances.get(AuthenticationMethod.BASIC_IDENTIFIER).getInstanceForRequest(header);
        } else if (header != null && header.startsWith("Bearer")) {
            return instances.get(AuthenticationMethod.OAUTH_IDENTIFIER).getInstanceForRequest(header);
        } 
        return instances.get(AuthenticationMethod.DEFAULT_IDENTIFIER).getInstanceForRequest(header);
    }
}
