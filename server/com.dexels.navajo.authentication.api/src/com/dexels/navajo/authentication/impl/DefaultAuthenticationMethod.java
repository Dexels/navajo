package com.dexels.navajo.authentication.impl;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.authentication.api.AuthenticationMethod;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public class DefaultAuthenticationMethod implements AuthenticationMethod {
    private AAAQuerier authenticator;

    public void setAAAQuerier(AAAQuerier aa) {
        authenticator = aa;
    }

    public void clearAAAQuerier(AAAQuerier aa) {
        authenticator = null;
    }
    
    @Override
    public String getIdentifier() {
        return DEFAULT_IDENTIFIER;
    }

    @Override
    public void process(Access access) throws AuthorizationException {
        authenticator.process(access);        
    }

    @Override
    public AuthenticationMethod getInstanceForRequest(String header) {
        DefaultAuthenticationMethod newInstance = new DefaultAuthenticationMethod();
        newInstance.setAAAQuerier(this.authenticator);
        return newInstance;
    }

}