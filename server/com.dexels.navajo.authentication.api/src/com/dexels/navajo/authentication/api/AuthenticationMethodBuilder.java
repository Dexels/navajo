package com.dexels.navajo.authentication.api;

public interface AuthenticationMethodBuilder {
    AuthenticationMethod getInstanceForRequest(String header);
}