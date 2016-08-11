package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.script.api.Access;
import com.dexels.oauth.api.Client;
import com.dexels.oauth.api.ClientStore;
import com.dexels.oauth.api.ClientStoreException;
import com.dexels.oauth.api.OAuthToken;
import com.dexels.oauth.api.TokenStore;
import com.dexels.oauth.api.TokenStoreException;

public class OAuthArticleServlet extends ArticleBaseServlet {

    private static final long serialVersionUID = 1199676363102046960L;
    private static final String AUTHORIZATION_BEARER_PREFIX = "Bearer";
    private TokenStore tokenStore;
    private ClientStore clientStore;

    @Override
    protected void doServiceImpl(HttpServletRequest req, HttpServletResponse resp) throws APIException {
    	String token = getToken(req);

        OAuthToken oauthToken = null;
        Client client = null;

        if (token != null) {
            oauthToken = getOAuthToken(token);
            client = getClient(oauthToken);
        } else {
            client = getClient(req);
        }

        String username = client.getUsername();
        String pathInfo = req.getPathInfo();
        String instance = client.getInstance();
        String url = client.getRedirectURLPrefix();

        if (pathInfo == null) {
            throw new APIException("Pathinfo is null, we cannot find an article then", null, APIErrorCode.ArticleNotFound);
        }
        String articleName = determineArticleFromRequest(req);
        File article = getContext().resolveArticle(articleName);
        if (!article.exists()) {
            throw new APIException("Article does not exist", null, APIErrorCode.ArticleNotFound);
        }

        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null || ip.equals("")) {
            ip = req.getRemoteAddr();
        }

        Access a = new Access(-1, -1, username, "article/" + articleName, "", "", "", null, false, null);
        a.setTenant(instance);
        a.rpcPwd = token;
        a.created = new Date();
        a.ipAddress = ip;
        a.setClientToken("Client id: " + client.getId());

        try {
            ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, "", username, article, pathInfo, req.getParameterMap(), instance, oauthToken);
            runtime.setUsername(client.getUsername());
            runtime.setURL(url);
            runtime.execute(getContext());
            resp.setContentType("application/json; charset=utf-8");
            a.setExitCode(Access.EXIT_OK);
        } catch (NoJSONOutputException e) {
            resp.setContentType(e.getMimeType());
            try {
                IOUtils.copy(e.getStream(), resp.getOutputStream());
            } catch (IOException e1) {
                throw new APIException(e1.getMessage(), e1, APIErrorCode.InternalError);
            }
            return;
            
        } catch (APIException apiException) {
            if (apiException.getErrorCode() == APIErrorCode.InternalError) {
                logExceptionToAccess(a, apiException, createNavajoFromRequest(req));
            } else if (apiException.getErrorCode() == APIErrorCode.ConditionError) {
                a.setExitCode(Access.EXIT_VALIDATION_ERR);
            }
          
            throw apiException;
        } catch (Throwable  e) {
          
            logExceptionToAccess(a, e, createNavajoFromRequest(req));
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
            
        } finally {
            a.setFinished();
            NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(a));
        }
    }

    private void logExceptionToAccess(Access a, Throwable e, Navajo navajo) {
        a.setExitCode(Access.EXIT_EXCEPTION);
        // Create a navajo of the input
        a.setInDoc(navajo);
        a.setException(e);
    }
    
    private String getToken(HttpServletRequest request) {
    	String authorizationHeaderValue = request.getHeader("Authorization");
   
    	if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith(AUTHORIZATION_BEARER_PREFIX) &&  authorizationHeaderValue.length() > AUTHORIZATION_BEARER_PREFIX.length() + 1) {
    		return authorizationHeaderValue.substring(AUTHORIZATION_BEARER_PREFIX.length() + 1);
    	}
    	return request.getParameter("token");
    }

    private Navajo createNavajoFromRequest(HttpServletRequest req) {
        Navajo navajo = NavajoFactory.getInstance().createNavajo();
        Message properties = NavajoFactory.getInstance().createMessage(navajo, "Input");
        navajo.addMessage(properties);
        for (Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            Property property = NavajoFactory.getInstance().createProperty(navajo, entry.getKey(), null, null, null);
            property.setType("string");
         
            if (entry.getValue() != null && entry.getValue().length > 0) {
            	property.setValue(String.join("|", entry.getValue()));
            }
            properties.addProperty(property);
        }
        return navajo;
    }

    private OAuthToken getOAuthToken(String token) throws APIException {
        try {
            OAuthToken oauthToken = tokenStore.getToken(token);

            if (oauthToken == null) {
                throw new APIException("Invalid token " + token, null, APIErrorCode.TokenUnauthorized);
            }
            return oauthToken;
        } catch (TokenStoreException e) {
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
        }
    }

    private Client getClient(OAuthToken oauthToken) throws APIException {
        try {
            Client client = clientStore.getClient(oauthToken.getClientId());

            if (client == null) {
                throw new APIException("Invalid client id " + oauthToken.getClientId(), null, APIErrorCode.ClientIdUnauthorized);
            }
            return client;
        } catch (ClientStoreException e) {
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
        }
    }

    private Client getClient(HttpServletRequest request) throws APIException {
        // Because we want to be backwards compatible we support both client_id
        // and clientId as parameter input
        String clientId = request.getParameter("client_id");
        if (clientId == null) {
            clientId = request.getParameter("clientId");
        }

        // ClientId is null, no point in asking Mongo.
        if (clientId == null) {
            throw new APIException("Missing client id", null, APIErrorCode.ClientIdUnauthorized);
        }

        try {
            Client client = clientStore.getClient(clientId);

            if (client == null) {
                throw new APIException("Invalid client id " + clientId, null, APIErrorCode.ClientIdUnauthorized);
            }
            return client;
        } catch (ClientStoreException e) {
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
        }
    }

    protected String determineArticleFromRequest(final HttpServletRequest req) {
        String pathinfo = req.getPathInfo();
        if (pathinfo.startsWith("/")) {
            pathinfo = pathinfo.substring(1);
        }
        String article = null;
        final int indexOf = pathinfo.indexOf('/');
        if (indexOf != -1) {
            article = pathinfo.substring(indexOf + 1, pathinfo.length());
        } else {
            article = pathinfo;
        }
        return article;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public void clearTokenStore(TokenStore tokenStore) {
        this.tokenStore = null;
    }

    public void setClientStore(ClientStore clientStore) {
        this.clientStore = clientStore;
    }

    public void clearClientStore(ClientStore clientStore) {
        this.clientStore = null;
    }
}
