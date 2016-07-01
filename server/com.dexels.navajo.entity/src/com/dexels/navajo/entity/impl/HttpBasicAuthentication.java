package com.dexels.navajo.entity.impl;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.EntityAuthenticator;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.SystemException;

public class HttpBasicAuthentication implements EntityAuthenticator {
    private final static Logger logger = LoggerFactory.getLogger(HttpBasicAuthentication.class);
    Base64 base64 = new Base64();

    private String username;
    private String password;
    private AAAQuerier authenticator;
    
    public HttpBasicAuthentication getInstance(HttpServletRequest req) {
        HttpBasicAuthentication newInstance = new HttpBasicAuthentication();
        newInstance.getAuthenticationFromHeader(req);
        newInstance.setAAAQuerier(this.authenticator);
        return newInstance;
    }

    @Override
    public String getIdentifier() {
        return "Basic";
    }
    
    public void setAAAQuerier(AAAQuerier aa) {
        authenticator = aa;
    }

    public void clearAAAQuerier(AAAQuerier aa) {
        authenticator = null;
    }

    

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    

    private void getAuthenticationFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        StringTokenizer st = new StringTokenizer(authHeader);
        if (st.hasMoreTokens()) {
            if (st.nextToken().equalsIgnoreCase(getIdentifier())) {
                String credentials;

                credentials = new String(base64.decode(st.nextToken()));

                int p = credentials.indexOf(":");
                if (p != -1) {
                    this.username = credentials.substring(0, p).trim();
                    this.password = credentials.substring(p + 1).trim();
                    logger.debug("Successfully authenticated: {}", username);
                } else {
                    logger.warn("Invalid authentication token: {}", credentials);
                }

            }
        }

    }

    @Override
    public boolean isAuthenticated(Access a) {
        try {
            authenticator.process(a.getTenant(), a.getRpcUser(), a.rpcPwd, a.rpcName, null, a);
        } catch (SystemException | AuthorizationException e) {
            logger.warn("Auth exception", e);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkAuthenticationFor(Operation op) {
        // Not supported for Basic authentication
        return true;
    }
}
