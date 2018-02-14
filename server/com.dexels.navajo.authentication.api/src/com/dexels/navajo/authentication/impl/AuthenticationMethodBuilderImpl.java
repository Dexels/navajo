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
        final AuthenticationMethod method;
        if (header != null && header.startsWith(AuthenticationMethod.BASIC_IDENTIFIER)) {
            method = instances.get(AuthenticationMethod.BASIC_IDENTIFIER);
        } else if (header != null && header.startsWith(AuthenticationMethod.OAUTH_IDENTIFIER)) {
            method = instances.get(AuthenticationMethod.OAUTH_IDENTIFIER);
        } else {
            method = instances.get(AuthenticationMethod.DEFAULT_IDENTIFIER);
        }
        if (method == null) {
            return null;
        }
        return method.getInstanceForRequest(header);
    }
}
