package com.dexels.navajo.entity.listener;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpBasicAuthentication {
    private final static Logger logger = LoggerFactory.getLogger(HttpBasicAuthentication.class);
    Base64 base64 = new Base64();

    private String username;
    private String password;

    public HttpBasicAuthentication(HttpServletRequest req) {
        performBasicAuthentication(req);
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
