package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		if(pathInfo==null) {
			throw new ServletException("No article found, please specify after article");
		}
		File article = context.resolveArticle(pathInfo);
		if(article.exists()) {
			ArticleRuntime runtime = new ServletArticleRuntimeImpl(req, resp, article);
			try {
				runtime.execute(context);
			} catch (ArticleException e) {
				throw new ServletException("Problem executing article", e);
			}

		} else {
			throw new FileNotFoundException("Unknown article: "+article.getAbsolutePath());
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}


	// element, submit, setvalue, service, table?, table, setusername


}
