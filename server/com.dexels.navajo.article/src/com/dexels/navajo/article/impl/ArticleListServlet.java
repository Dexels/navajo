package com.dexels.navajo.article.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;

public class ArticleListServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = -6895324256139435015L;

	private final static Logger logger = LoggerFactory
			.getLogger(ArticleListServlet.class);
	
	private static String kARGUMENT_ARTICLE = "article";
	private static String kARGUMENT_PRETTY = "pretty";
	private static String kARGUMENT_EXTENDED = "extended";

	private ArticleContext context;

	public ArticleListServlet() {

	}

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
			throws ServletException {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.setContentType("application/json; charset=utf-8");
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		String requestedArticle = req.getParameter(kARGUMENT_ARTICLE);
		boolean extended = req.getParameter(kARGUMENT_EXTENDED) != null ? true : false;

		List<String> articles = context.listArticles();
		
		if (requestedArticle != null && articles.contains(requestedArticle)) {
			try {
				context.writeArticleMeta(requestedArticle, rootNode, mapper, extended);
			} catch (Throwable e) {
				logger.error("Error generating metadata for article: "
						+ requestedArticle, e);
			}
		} else {
			for (String article : articles) {
				try {
					context.writeArticleMeta(article, rootNode, mapper, extended);
				} catch (Throwable e) {
					logger.error("Error generating metadata for article: "
							+ article, e);
				}
			}
		}

		ObjectWriter writer = (req.getParameter(kARGUMENT_PRETTY) == null) 
				? mapper.writer() : mapper.writer().withDefaultPrettyPrinter();

		try {
			writer.writeValue(resp.getWriter(), rootNode);
			if (resp.getWriter() != null) {
				resp.getWriter().close();
			}
		} catch (JsonGenerationException e) {
			throw new ServletException("Error generating JSON", e);
		} catch (JsonMappingException e) {
			throw new ServletException("Error generating JSON", e);
		} catch (IOException e) {
			throw new ServletException("Error writing JSON", e);
		}
	}
}
