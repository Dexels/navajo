package com.dexels.navajo.authentication.api;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public interface AuthenticationMethod {
    public final static String OAUTH_IDENTIFIER = "Bearer";
    public final static String BASIC_IDENTIFIER = "Basic";
    public final static String DEFAULT_IDENTIFIER = "default";

    public AuthenticationMethod getInstanceForRequest(String header);

    public String getIdentifier();

    public void process(Access access) throws AuthorizationException;
}