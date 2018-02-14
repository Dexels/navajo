package com.dexels.navajo.authentication.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AuthenticationMethod;
import com.dexels.navajo.authentication.api.AuthenticationMethodBuilder;

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
    public AuthenticationMethod getInstanceForRequest(String header) {
        if (header.indexOf(' ') > 0) {
            String identifier = header.substring(0, header.indexOf(' '));
            AuthenticationMethod authenticationMethod = instances.get(identifier);
            if (authenticationMethod != null) {
                return authenticationMethod.getInstanceForRequest(header);
            }
        }
        return null;

    }
}
