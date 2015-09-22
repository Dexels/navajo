package com.dexels.navajo.jsp.proxy;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
	protected void customizeProxyRequest(Request proxyRequest,
			HttpServletRequest request) {
		proxyRequest.getHeaders().remove("Host");
		
	}

	@Override
	protected URI rewriteURI(HttpServletRequest request) {
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
//		logger.info("Constructed: "+construct);
		if(this.username!=null) {
			construct = appendParam(construct,"username",username);
		}
		if(this.password!=null) {
			construct = appendParam(construct,"password",password);
		}
		URI finalURI = URI.create(construct);
//		try {
//			testURL(finalURI);
//		} catch (IOException e) {
//			logger.error("Error: ", e);
//		}
		
		return finalURI;
//		return URI.create("http://www.dexels.com/");
	}
	
	private String appendParam(String url, String key, String value) {
		return url + (url.contains("?")?"&":"?")+key+"="+value;
	}

	private String getEntityFromServer() {
		if(this.url!=null) {
			return this.url;
		}
		final String POSTFIX = "/navajo";
		if(this.server.endsWith(POSTFIX)) {
			String url = server.substring(0,server.length()-POSTFIX.length())+"/entity";
//			URI uri = query!=null? URI.create(url + "?" + query ): URI.create(url ) ;
//			System.err.println("uri assembled: "+uri);
			return url;
		}
		return null;
	}
	
	public static void main(String[] args) throws URISyntaxException {
		EntityProxyServlet eps = new EntityProxyServlet();
		eps.server="knvb-test.sportlink.com/navajo";
//		String construct = 
		URI u = new URI(eps.getEntityFromServer());
		System.err.println("u: "+u);
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
