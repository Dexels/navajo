package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;
import com.dexels.oauth.api.Token;
import com.dexels.oauth.api.TokenStore;

public class OAuthArticleServlet extends ArticleServlet {
	
	private static final long serialVersionUID = 1199676363102046960L;

	private TokenStore tokenStore;

	private final static Logger logger = LoggerFactory
			.getLogger(OAuthArticleServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String token = req.getParameter("token");
		if(token==null) {
			throw new ServletException("Please supply a token");
		}
		Token t = tokenStore.getTokenByString(token);
		String clientId = t.clientId();
		
		String username = t.getUsername();
		Map<String,String> scopes =  getScopes(t); // context.getScopes(getToken(req));
		String pathInfo = req.getPathInfo();
		String instance = determineInstanceFromRequest(req);
		logger.info("Instance determined: "+instance);
		if(pathInfo==null) {
			throw new ServletException("No article found, please specify after article");
		}
		File article = context.resolveArticle(determineArticleFromRequest(req));
		if(article.exists()) {
			ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, clientId,username, article,pathInfo,req.getParameterMap(),instance,scopes);
			try {
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
			throw new FileNotFoundException("Unknown article: "+article.getAbsolutePath());
		}
	}
	
	private Map<String,String> getScopes(Token t) {
		Map<String,String> result = new HashMap<String, String>();
		result.put("username", t.getUsername());
		result.put("clientId", t.clientId());
		Set<String> scopes = t.scopes();
		for (String s : scopes) {
			result.put(s, "true");
		}
		return result;
	}
	
	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	public void clearTokenStore(TokenStore tokenStore) {
		this.tokenStore = null;
	}

	
}
