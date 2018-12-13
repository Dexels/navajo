package com.dexels.navajo.authentication.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AuthenticationMethod;
import com.dexels.navajo.authentication.api.AuthenticationMethodBuilder;
import com.dexels.navajo.script.api.AuthorizationException;

public class AuthenticationMethodBuilderImpl implements AuthenticationMethodBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationMethodBuilderImpl.class);

    private Map<String, AuthenticationMethod> instances = new HashMap<>();

    public void addMethod(AuthenticationMethod method) {
        logger.info("Added authenticationmethod {}", method.getIdentifier());
        instances.put(method.getIdentifier(), method);
    }

    public void removeMethod(AuthenticationMethod method) {
        logger.info("Removed authenticationmethod {}", method.getIdentifier());
        instances.remove(method.getIdentifier());
    }

    @Override
    public AuthenticationMethod getInstanceForRequest(String header) throws AuthorizationException {
        final AuthenticationMethod method;
        if (header != null && header.indexOf(' ') > 0) {
            method = instances.get(header.substring(0,  header.indexOf(' ')));
            if (method == null) {
                return null;
            }
        } else {
        	
        	// If we are here the auth header should not contain bearer or basic
        	// Authorization header must be like "Authorization: <type> <credentials>" 
            if( header != null && (header.toLowerCase().contains("bearer") || header.toLowerCase().contains("basic") ) 
            		&& header.trim().split(" ").length != 2) {
            	throw new AuthorizationException(false, false, null, "Wrong authentication header provided");
            } 
            
            method = instances.get(AuthenticationMethod.DEFAULT_IDENTIFIER);
        }
        return method.getInstanceForRequest(header);
    }
}
