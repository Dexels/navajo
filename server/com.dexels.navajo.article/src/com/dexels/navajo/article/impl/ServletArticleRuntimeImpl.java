package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.article.ArticleRuntime;

public class ServletArticleRuntimeImpl extends BaseRuntimeImpl implements ArticleRuntime {

	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private String token = null;
	private String username;
	
	public ServletArticleRuntimeImpl(HttpServletRequest req, HttpServletResponse resp, File article,String articleName) throws IOException {
		super(articleName,article);
		this.request = req;
		this.response = resp;
		this.token = URLDecoder.decode(req.getParameter("token"),"UTF-8");
		System.err.println("token: "+this.token);
		token=token.replaceAll(" ", "+");
		System.err.println("t0ken: "+this.token);
		this.username = req.getParameter("username");
	}
	@Override
	public String resolveArgument(String name) {
		
		return request.getParameter(name.substring(1));
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
		return token;
	}
	
	@Override
	public String getUsername() {
		return username;
	}


}
