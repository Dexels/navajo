package com.dexels.navajo.tipi.dev.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.eclipse.jetty.servlets.GzipFilter;

public class CompressionFilter extends GzipFilter {

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		super.doFilter(req, resp, chain);
	}

}
