package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.article.ArticleRuntime;

public class ServletArticleRuntimeImpl extends BaseRuntimeImpl implements ArticleRuntime {

	
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	
	public ServletArticleRuntimeImpl(HttpServletRequest req, HttpServletResponse resp, File article) throws IOException {
		super(article);
		this.request = req;
		this.response = resp;
	}
	@Override
	public Object resolveArgument(String name) {
		return request.getParameter(name);
	}

	@Override
	public void setMimeType(String mime) {
		response.setContentType(mime);
	}
	@Override
	public Writer getOutputWriter() throws IOException {
		return response.getWriter();
	}

	
	@Override
	public String getPassword() {
		return "some_password";
	}
	@Override
	public String getUser() {
		return "some_username";
	}

}
