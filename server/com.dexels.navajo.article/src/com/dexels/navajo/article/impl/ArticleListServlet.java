package com.dexels.navajo.article.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;

public class ArticleListServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = -6895324256139435015L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ArticleListServlet.class);
	

	private ArticleContext context;
	
	public ArticleListServlet() {
		
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
		// BZ2kTR4xD1Yqrkr0PlHP+3VOpTuzQzF3vzikqTjBLFioMmoofvpE0ykd1UT2tYPtayqzbWHrDdJA289Y1/IZGKa3h5/d9RMXzi65OsEP7W4=
		try {
			String token = req.getParameter("token");
			if (token == null) {
				throw new ServletException("Please supply a token");
			}
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode rootNode = mapper.createObjectNode(); 
			String requestedArticle = req.getParameter("article");
			if(requestedArticle!=null) {
				context.writeArticleMeta(requestedArticle, rootNode, mapper);
			} else {
				List<String> articles = context.listArticles();
				System.err.println("ARTicles: "+articles);
				for (String article : articles) {
					logger.info("Meta of article:"+article);
					context.writeArticleMeta(article, rootNode,mapper);
					// context.getArticleMeta(article);
				}
			}
			mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
			ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

			writer.writeValue(resp.getWriter(), rootNode);
		} catch (ArticleException e) {
			logger.error("Error: ", e);
		}

	}


}
