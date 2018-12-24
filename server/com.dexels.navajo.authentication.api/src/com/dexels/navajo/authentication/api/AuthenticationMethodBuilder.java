package com.dexels.navajo.authentication.api;

import com.dexels.navajo.script.api.AuthorizationException;

public interface AuthenticationMethodBuilder {
    AuthenticationMethod getInstanceForRequest(String header) throws AuthorizationException;
}