package com.dexels.navajo.entity.impl;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.entity.EntityAuthenticator;

public class HttpBasicAuthentication implements EntityAuthenticator {
    private final static Logger logger = LoggerFactory.getLogger(HttpBasicAuthentication.class);
    Base64 base64 = new Base64();

    private String username;
    private String password;
    
    public HttpBasicAuthentication getInstance(HttpServletRequest req) {
        HttpBasicAuthentication newInstance = new HttpBasicAuthentication();
        newInstance.performBasicAuthentication(req);
        return newInstance;
    }

    @Override
    public String getIdentifier() {
        return "Basic";
    }

    

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private void performBasicAuthentication(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || authHeader.equals("")) {
            return;
        }

        StringTokenizer st = new StringTokenizer(authHeader);
        if (st.hasMoreTokens()) {
            if (st.nextToken().equalsIgnoreCase("Basic")) {
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

}
