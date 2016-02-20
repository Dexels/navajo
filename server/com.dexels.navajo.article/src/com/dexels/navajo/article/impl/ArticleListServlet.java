package com.dexels.navajo.article.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;

public class ArticleListServlet extends ArticleBaseServlet implements Servlet {
	private static final long serialVersionUID = -6895324256139435015L;
	
	private static String ARGUMENT_ARTICLE = "article";
	private static String ARGUMENT_PRETTY = "pretty";
	private static String ARGUMENT_EXTENDED = "extended";

	public ArticleListServlet() {

	}
	
	protected void doServiceImpl(HttpServletRequest request, HttpServletResponse response) throws APIException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		boolean extended = request.getParameter(ARGUMENT_EXTENDED) != null ? true : false;
		String requestedArticle = request.getParameter(ARGUMENT_ARTICLE);
		ObjectWriter writer = (request.getParameter(ARGUMENT_PRETTY) == null) 
				? mapper.writer() : mapper.writer().withDefaultPrettyPrinter();

		if (requestedArticle != null) {
			getContext().writeArticleMeta(requestedArticle, rootNode, mapper, extended);
		} else {
			List<String> articles = getContext().listArticles();
			for (String article : articles) {
				getContext().writeArticleMeta(article, rootNode, mapper, extended);
			}
		}
		
		try (PrintWriter pw = response.getWriter()) {
			writer.writeValue(pw, rootNode);
		}  catch (IOException e) {
			throw new APIException("Autoclose on printwriter failed", e, APIErrorCode.InternalError);
		}
	}
}
