/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.article.impl;

import java.io.File;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.api.util.NavajoRequestConfig;
import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.runnable.ArticleTmlRunnable;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.oauth.api.Client;
import com.dexels.oauth.api.ClientStore;
import com.dexels.oauth.api.exception.ClientStoreException;
import com.dexels.oauth.api.OAuthToken;
import com.dexels.oauth.api.TokenStore;
import com.dexels.oauth.api.exception.TokenStoreException;

@Deprecated
public class OAuthArticleServlet extends ArticleBaseServlet implements Servlet {

    private static final long serialVersionUID = 1199676363102046960L;

    private static final Logger logger = LoggerFactory.getLogger(OAuthArticleServlet.class);

    private static final String ACCESS_PREFIX = "article/";

    private static final String AUTHORIZATION_BEARER_PREFIX = "Bearer";

    private TokenStore tokenStore;
    private ClientStore clientStore;
    private TmlScheduler tmlScheduler;

	private long requestTimeout;

    public void activate() {

        logger.info("Activating OAuthArticleServlet");
        requestTimeout = NavajoRequestConfig.getRequestTimeout(5 * 60 * 1000L); // 5 minutes
        logger.info("Using timeout in continuation: {}", requestTimeout);
    }

    public void deactivate() {
        logger.info("DeActivating OAuthArticleServlet");
    }

    @Override
    protected void doServiceImpl(HttpServletRequest req, HttpServletResponse resp)
            throws APIException {

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
        if (oauthToken != null && oauthToken.getUsername() != null
                && !oauthToken.getUsername().equals("")) {
            username = oauthToken.getUsername();
        }
        String pathInfo = req.getPathInfo();
        String instance = client.getInstance();

        if (pathInfo == null) {
            throw new APIException("Pathinfo is null, we cannot find an article then", null,
                    APIErrorCode.ArticleNotFound);
        }
        String articleName = determineArticleFromRequest(req);
        File article = getContext().resolveArticle(articleName);
        if (!article.exists()) {
            throw new APIException("Article does not exist", null, APIErrorCode.ArticleNotFound);
        }

        try {
            String ip = req.getHeader("X-Forwarded-For");
            if (ip == null || ip.equals("")) {
                ip = req.getRemoteAddr();
            }

            Access access = new Access(-1, -1, username, ACCESS_PREFIX + articleName, "", "", "",
                    null, false, null);
            access.setTenant(instance);
            access.rpcPwd = token;
            access.created = new Date();
            access.ipAddress = ip;
            access.setClientDescription("Article");
            access.setClientToken("Client id: " + client.getId());

            ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, "", username, article,
                    pathInfo, req.getParameterMap(), instance, oauthToken);
            runtime.setAccess(access);
            runtime.setUsername(username);

            ArticleTmlRunnable runner = new ArticleTmlRunnable(req, resp, client, runtime,
                    getContext(), requestTimeout);
            if (!runner.isAborted()) {
                tmlScheduler.submit(runner, false);
            }
        } catch (Throwable e) {
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
        }
    }

    public void setTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = scheduler;
    }

    public void clearTmlScheduler(TmlScheduler scheduler) {
        this.tmlScheduler = null;
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeaderValue = request.getHeader("Authorization");

        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith(AUTHORIZATION_BEARER_PREFIX)
                && authorizationHeaderValue.length() > AUTHORIZATION_BEARER_PREFIX.length() + 1) {
            return authorizationHeaderValue.substring(AUTHORIZATION_BEARER_PREFIX.length() + 1);
        }
        return request.getParameter("token");
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
