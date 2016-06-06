package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
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
	private TokenStore tokenStore;
	private ClientStore clientStore;
	
	@Override
	protected void doServiceImpl(HttpServletRequest req, HttpServletResponse resp) throws APIException {
		String token = req.getParameter("token");
				
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
				
		if(pathInfo==null) {
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
        
		try {
			ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, client.getPassword(), username, article, pathInfo, req.getParameterMap(), instance, oauthToken);
			runtime.setUsername(client.getUsername());
			runtime.setPassword(client.getPassword());
			runtime.setURL(url);
			runtime.execute(getContext());
			resp.setContentType("application/json; charset=utf-8");
			a.setExitCode(Access.EXIT_OK);
		}  catch (NoJSONOutputException e) {
		    a.setException(e);
		    a.setExitCode(Access.EXIT_EXCEPTION);
			resp.setContentType(e.getMimeType());
			try {
				IOUtils.copy(e.getStream(), resp.getOutputStream());
			} catch (IOException e1) {
				throw new APIException(e1.getMessage(), e1, APIErrorCode.InternalError);
			}
			return;
		} catch (Throwable e) {
		    a.setException(e);
            a.setExitCode(Access.EXIT_EXCEPTION);
			throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
		} finally {
		    a.setFinished();
		    NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(a));
		}
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
		//Because we want to be backwards compatible we support both client_id
		//and clientId as parameter input
		String clientId = request.getParameter("client_id");
		if (clientId == null) {
			clientId = request.getParameter("clientId");
		}
			
		//ClientId is null, no point in asking Mongo.
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
		if(pathinfo.startsWith("/")) {
			pathinfo = pathinfo.substring(1);
		}
		String article = null;
		final int indexOf = pathinfo.indexOf('/');
		if(indexOf!=-1) {
			article = pathinfo.substring(indexOf+1, pathinfo.length());
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
