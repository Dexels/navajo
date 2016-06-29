package com.dexels.navajo.entity.impl;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.entity.EntityAuthenticator;
import com.dexels.oauth.api.ClientStore;
import com.dexels.oauth.api.StoreException;
import com.dexels.oauth.api.TokenStore;

public class TokenAuthentication implements EntityAuthenticator {
    private final static Logger logger = LoggerFactory.getLogger(TokenAuthentication.class);

    private TokenStore tokenStore;
    private ClientStore clientStore;
    private String password;
    private String username;

    public void setTokenStore(TokenStore ts) {
        this.tokenStore = ts;
    }
    public void clearTokenStore(TokenStore ts) {
        this.tokenStore = null;
    }
    
    public void setClientStore(ClientStore cs) {
        this.clientStore = cs;
    }
    public void clearClientStore(ClientStore cs) {
        this.clientStore = null;
    }
    
    
    @Override
    public String getIdentifier() {
        return "Bearer";
    }

    @Override
    public EntityAuthenticator getInstance(HttpServletRequest req) {
        TokenAuthentication newInstance = new TokenAuthentication();
        newInstance.setTokenStore(this.tokenStore);
        newInstance.setClientStore(this.clientStore);
        try {
            newInstance.authenticate(req);
        } catch (StoreException e) {
            logger.error("StoreException on authentication!", e);
        }
        return newInstance;
    }
    
    private void authenticate(HttpServletRequest request) throws StoreException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.equals("")) {
            return;
        }

        StringTokenizer st = new StringTokenizer(authHeader);
        if (st.hasMoreTokens()) {
            if (st.nextToken().equalsIgnoreCase("Bearer")) {
                String token = st.nextToken();
                String clientid = tokenStore.getToken(token).getClientId();
                this.username = clientStore.getClient(clientid).getUsername();
                this.password = clientStore.getClient(clientid).getPassword();
            }
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
