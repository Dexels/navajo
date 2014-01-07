package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;

public class ArticleServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = -6895324256139435015L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ArticleServlet.class);

	private ArticleContext context;
	
	public ArticleServlet() {
		
	}
	
//	public void activate() {
//		logger.info("Activating acticle component");
//	}
//
//	public void deactivate() {
//		logger.info("Deactivating acticle component");
//	}
	
	public ArticleContext getContext() {
		return context;
	}

	public void setArticleContext(ArticleContext context) {
		this.context = context;
	}

	public void clearArticleContext(ArticleContext context) {
		this.context = null;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method = req.getMethod();

		String clientId = req.getParameter("token");
		if(clientId==null) {
			throw new ServletException("Please supply a token (a client id, actually)");
		}
		Map<String,String> scopes = context.getScopes(getToken(req));
		String pathInfo = req.getPathInfo();
		String instance = determineInstanceFromRequest(req);
		logger.info("Instance determined: "+instance);
		if(pathInfo==null) {
			throw new ServletException("No article found, please specify after article");
		}
		File article = context.resolveArticle(pathInfo);
		if(article.exists()) {
			ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, article,pathInfo,req.getParameterMap(),instance,scopes);
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
	
	private String determineInstanceFromRequest(final HttpServletRequest req) {
		String pathinfo = req.getPathInfo();
		if(pathinfo.startsWith("/")) {
			pathinfo = pathinfo.substring(1);
		}
		String instance = null;
		if(pathinfo.indexOf('/')!=-1) {
			instance = pathinfo.substring(0, pathinfo.indexOf('/'));
		} else {
			instance = pathinfo;
		}
		return instance;
	}

	private String getToken(HttpServletRequest req) {

		return null;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}


	// element, submit, setvalue, service, table?, table, setusername


}
