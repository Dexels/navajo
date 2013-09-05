package com.dexels.navajo.tipi.dev.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.eclipse.jetty.servlets.GzipFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompressionFilter extends GzipFilter {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CompressionFilter.class);
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		logger.info("Filter called!");
		super.doFilter(req, resp, chain);
	}

}
