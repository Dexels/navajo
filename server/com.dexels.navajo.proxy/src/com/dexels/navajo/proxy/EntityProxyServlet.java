/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.proxy;


import java.nio.charset.Charset;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dexels.utils.Base64;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.http.HttpClientTransportOverHTTP;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityProxyServlet extends org.eclipse.jetty.proxy.ProxyServlet {

	
	private final static Logger logger = LoggerFactory
			.getLogger(EntityProxyServlet.class);
	
	private static final long serialVersionUID = -2635217637957904173L;
	private String server;
	private String username;
	private String password;
	private String url;

	private HttpClient httpClient;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.server = getApplicationAttribute("NavajoServer",config);
		this.username = getApplicationAttribute("NavajoUser",config);
		this.password = getApplicationAttribute("NavajoPassword",config);
		this.url = getApplicationAttribute("Entity",config);
//		this.tenant = getApplicationAttribute("Tenant",config);
		
	}
	
	@Override
	protected HttpClient createHttpClient() throws ServletException {
	    SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClientTransportOverHTTP transport = new HttpClientTransportOverHTTP();

	    httpClient = new HttpClient(transport,sslContextFactory);
        transport.setHttpClient(httpClient);
//	    httpClient.setExecutor(Executors.newFixedThreadPool(3));
	    try {
			httpClient.start();
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	    return httpClient;
	}
	

    @Override
	protected void sendProxyRequest(HttpServletRequest request, HttpServletResponse response, Request proxyRequest
			) {
		proxyRequest.getHeaders().remove("Host");
		proxyRequest.getHeaders().remove("Accept-Encoding");
		
		if (username != null && password != null) {
            // Use HTTP Basic auth - should only be used over HTTPS!
            String authString = username + ":" + password;
            byte[] bytes = authString.getBytes(Charset.forName("UTF-8"));
            String encoded = Base64.encode(bytes, 0, bytes.length, 0, "");
            proxyRequest.getHeaders().add("Authorization", "Basic " + encoded);
        }
		// If we don't call super, this request will hang!
		super.sendProxyRequest(request,response,proxyRequest);
		
	}

	@Override
	protected String rewriteTarget(HttpServletRequest request) {
//		String proto = request.getProtocol();
		String query = request.getQueryString();
		String pathInfo = request.getPathInfo();
		String construct = getEntityFromServer();
		if(pathInfo!=null) {
			construct = construct+pathInfo;
		}
		if(query!=null) {
			construct = construct+"?"+query;
		}

//		URI finalURI = URI.create(construct);
		return construct;
	}
	
	private synchronized String getEntityFromServer() {
		if(this.url!=null) {
			return this.url;
		}
		final String POSTFIX = "/navajo";
		if(this.server.endsWith(POSTFIX)) {
			url = server.substring(0,server.length()-POSTFIX.length())+"/entity";
			return url;
		}
		return null;
	}
	
	private String getApplicationAttribute(String key,ServletConfig config) {
		ServletContext servletContext = config.getServletContext();
		String value = null;
		if(servletContext!=null) {
			value = servletContext.getInitParameter(key);
		}
		if(value!=null) {
			return value;
		}
		value = System.getenv(key);
		if(value!=null) {
			return value;
		}
		return System.getProperty(key);
	}
}
