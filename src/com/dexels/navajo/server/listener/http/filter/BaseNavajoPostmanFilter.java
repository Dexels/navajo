package com.dexels.navajo.server.listener.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public abstract class BaseNavajoPostmanFilter implements Filter {

	
	protected Navajo createNavajoFromRequest(ServletRequest request) throws IOException {
		return NavajoFactory.getInstance().createNavajo(request.getInputStream());
	}
	protected void delegateToPostman(Navajo requestNavajo, ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new NavajoServletRequestWrapper(requestNavajo, (HttpServletRequest)request), response);
	}	
	
	public void init(FilterConfig config) throws ServletException {
		
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
