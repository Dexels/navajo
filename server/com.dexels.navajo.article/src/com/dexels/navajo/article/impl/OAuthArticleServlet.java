package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
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
		Token t = null;
		ClientRegistration cr = null;
		if(token==null) {
			// fallback:
			final String password = req.getParameter("clientId");
			final String username = req.getParameter("username");
			if(password== null || username == null ) {
				resp.sendError(400, "Missing token");
			}
			// TODO I think we should verify this one? Of is this enough?
			t = new Token() {
				
				@Override
				public Set<String> scopes() {
					return Collections.emptySet();
				}
				
				@Override
				public boolean isExpired() {
					return false;
				}
				
				@Override
				public String getUsername() {
					return username;
				}
				
				@Override
				public Map<String, Object> getUserAttributes() {
					return Collections.emptyMap();
				}
				
				@Override
				public int getExpirySeconds() {
					return 999;
				}
				
				@Override
				public String clientId() {
					return password;
				}

				@Override
				public Date getExpiryDate() {
					return new Date();
				}
			};
//			cr = clientStore.getClientByToken(password);
			cr = new ClientRegistration() {
				
				@Override
				public String getUsername() {
					return username;
				}
				
				@Override
				public String getRedirectUriPrefix() {
					return "";
				}
				
				@Override
				public String getClientId() {
					return "fake_id";
				}
				
				@Override
				public String getClientDescription() {
					return "";
				}
				
				@Override
				public Map<String, Object> getUserAttributes() {
					return new HashMap<String, Object>();
				}
				
				@Override
				public String getPassword() {
					return password;
				}
			};
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
		if(t==null) {
			resp.sendError(400, "Unauthorized or expired token");
			return;
		}
		
		String clientId = t.clientId();
		String username = t.getUsername();
		String pathInfo = req.getPathInfo();
		String instance = determineInstanceFromRequest(req);
		logger.info("Instance determined: "+instance); 
		if(pathInfo==null) {
			throw new ServletException("No article found, please specify");
		}
		File article = context.resolveArticle(determineArticleFromRequest(req));
		if(article.exists()) {
			ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, clientId,username, article,pathInfo,req.getParameterMap(),instance,t);
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
