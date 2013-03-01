package com.dexels.navajo.article.impl;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.article.ArticleContext;

public class ArticleSessionServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = -6895324256139435015L;


	private ArticleContext context;
	
	public ArticleSessionServlet() {
		
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
//		BZ2kTR4xD1Yqrkr0PlHP+3VOpTuzQzF3vzikqTjBLFioMmoofvpE0ykd1UT2tYPtayqzbWHrDdJA289Y1/IZGKa3h5/d9RMXzi65OsEP7W4=
		String token = req.getParameter("token");
		if(token==null) {
			throw new ServletException("Please supply a token");
		}
		req.getSession().setAttribute("token", token);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}


	// element, submit, setvalue, service, table?, table, setusername


}
