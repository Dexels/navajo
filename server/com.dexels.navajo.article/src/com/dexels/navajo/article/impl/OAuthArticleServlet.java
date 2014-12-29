package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;
import com.dexels.oauth.api.ClientRegistration;
import com.dexels.oauth.api.ClientStore;
import com.dexels.oauth.api.ClientStoreException;
import com.dexels.oauth.api.Token;
import com.dexels.oauth.api.TokenException;
import com.dexels.oauth.api.TokenStore;

public class OAuthArticleServlet extends ArticleServlet {
	
	private static final long serialVersionUID = 1199676363102046960L;
	private TokenStore tokenStore;
	private ClientStore clientStore;
	private final static Logger logger = LoggerFactory
			.getLogger(OAuthArticleServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String token = req.getParameter("token");
		resp.addHeader("Access-Control-Allow-Origin", "*");		

		Token t = null;
		ClientRegistration cr = null;
		if(token==null) {
			// fallback:
			final String clientId = req.getParameter("clientId");
			if(clientId==null) {
				resp.sendError(400, "No token and no clientId supplied");
				return;
			}
			try {
				cr = clientStore.getClient(clientId);
			} catch (ClientStoreException e) {
				throw new ServletException("Error acquiring client id:",e);
			}
			if(cr==null) {
				resp.sendError(400, "No token and no valid clientId supplied");
				return;
			}
			final String username = cr.getUsername();
			if(clientId== null || username == null ) {
				resp.sendError(400, "Missing token");
				return;
			}
		} else {
			try {
				t = tokenStore.getTokenByString(token);
			} catch (TokenException e) {
				throw new ServletException("Token problem",e);
			}
			if(t!=null) {
				try {
					cr = clientStore.getClient(t.clientId());
				} catch (ClientStoreException e) {
					throw new ServletException("Client Store problem",e);
				}
			}
			
		}
		String username = cr.getUsername();
		String pathInfo = req.getPathInfo();
		String instance = cr.getInstance();
		logger.info("clientInstance: "+instance);
		if(instance==null) {
			instance = determineInstanceFromRequest(req);
		}
		logger.info("Instance determined: "+instance); 
		if(pathInfo==null) {
			throw new ServletException("No article found, please specify");
		}
		File article = context.resolveArticle(determineArticleFromRequest(req));
		if(article.exists()) {
			ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, cr.getPassword(),username, article,pathInfo,req.getParameterMap(),instance,t);
			try {
				runtime.setUsername(cr.getUsername());
				runtime.setPassword(cr.getPassword());
				runtime.execute(context);
				resp.setContentType("application/json; charset=utf-8");
			} catch (ArticleException e) {
				throw new ServletException("Problem executing article", e);
			} catch (DirectOutputThrowable e) {
				resp.setContentType(e.getMimeType());
				IOUtils.copy(e.getStream(), resp.getOutputStream());
				return;
			}

		} else {
			resp.sendError(404, "No such article");
			throw new FileNotFoundException("Unknown article: "+article.getAbsolutePath());
		}
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
