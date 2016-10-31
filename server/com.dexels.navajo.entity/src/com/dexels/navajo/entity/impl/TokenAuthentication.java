package com.dexels.navajo.entity.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.EntityAuthenticator;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.script.api.Access;
import com.dexels.oauth.api.Client;
import com.dexels.oauth.api.ClientStore;
import com.dexels.oauth.api.OAuthToken;
import com.dexels.oauth.api.Scope;
import com.dexels.oauth.api.StoreException;
import com.dexels.oauth.api.TokenStore;

public class TokenAuthentication implements EntityAuthenticator {
    private final static Logger logger = LoggerFactory.getLogger(TokenAuthentication.class);

    private TokenStore tokenStore;
    private ClientStore clientStore;
    private OAuthToken token;
    private Client client;
    private HashSet<String> suppliedScopes;

    @SuppressWarnings("unused")
    private Map<String, Object> userAttributes;

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
    public boolean isAuthenticated(Access a, Navajo in) {
        // If the found the token in our store, the token is valid
        if (token == null) {
            return false;
        }
        if (in.getMessage("__parms__") == null) {
            Message paramMsg = NavajoFactory.getInstance().createMessage(in, "__parms__");
            in.addMessage(paramMsg);
        }
        Message paramMsg = in.getMessage("__parms__");
        
        
        // Add attributes
        for (String key: token.getAttributes().keySet()) {
            Object value = token.getAttributes().get(key);
            Property p2 = NavajoFactory.getInstance().createProperty(in, key, "", "", Property.DIR_OUT);
            p2.setAnyValue(value);
            paramMsg.addProperty(p2);
        }
        return true;
    }
    
    @Override
    public boolean checkAuthenticationFor(Operation op) throws EntityException {
        Set<String> missing = new HashSet<>();
        for (String scope : op.getScopes()) {
            if(!suppliedScopes.contains(scope)) {
                missing.add(scope);
            }
        }
        if(!missing.isEmpty()) {
            throw new EntityException(EntityException.UNAUTHORIZED, "Missin required scopes: " + missing);
        }
        return true;
    }

    @Override
    public EntityAuthenticator getInstance(HttpServletRequest req) {
        TokenAuthentication newInstance = new TokenAuthentication();
        newInstance.setTokenStore(this.tokenStore);
        newInstance.setClientStore(this.clientStore);
        try {
            newInstance.getAuthenticationFromHeader(req);
        } catch (StoreException e) {
            logger.error("StoreException on authentication!", e);
        }
        return newInstance;
    }
    
    
    
    private void getAuthenticationFromHeader(HttpServletRequest request) throws StoreException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.equals("")) {
            return;
        }

        String tokenString = null;
        StringTokenizer st = new StringTokenizer(authHeader);
        if (st.hasMoreTokens()) {
            if (st.nextToken().equalsIgnoreCase(getIdentifier())) {
                tokenString = st.nextToken();
                
            }
        }
        if (tokenString != null) {
            token = tokenStore.getToken(tokenString);
        } 
        if (token != null) {
            client = clientStore.getClient(token.getClientId());
            
            suppliedScopes = new HashSet<String>();
            for (Scope scope : this.token.getScopes()) {
                suppliedScopes.add(scope.getId());
            }
            
            userAttributes = token.getAttributes();
        }
    }

    @Override
    public String getUsername() {
        if (client == null || token == null) {
            return null;
        }
        if (token.getUsername() != null) {
            return token.getUsername();
        }
        return client.getUsername();
    }

    @Override
    public String getPassword() {
        return null;
    }


}
