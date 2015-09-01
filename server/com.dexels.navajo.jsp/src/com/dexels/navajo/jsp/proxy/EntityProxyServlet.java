package com.dexels.navajo.jsp.proxy;

import java.net.URI;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.dexels.utils.Base64;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;

public class EntityProxyServlet extends org.eclipse.jetty.proxy.ProxyServlet {

	private static final long serialVersionUID = -2635217637957904173L;
	private String server;
	private String username;
	private String password;
	private String url;
	private String tenant;

//	org.eclipse.jetty.servlets.Pro

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		config.getServletContext().setAttribute("org.eclipse.jetty.server.Executor", Executors.newCachedThreadPool());
		super.init(config);
		this.server = getApplicationAttribute("NavajoServer",config);
		this.username = getApplicationAttribute("NavajoUser",config);
		this.password = getApplicationAttribute("NavajoPassword",config);
		this.url = getApplicationAttribute("Entity",config);
		this.tenant = getApplicationAttribute("Tenant",config);
		
	}
	
	@Override
	protected HttpClient createHttpClient() throws ServletException {
		return super.createHttpClient();
	}

	@Override
	protected void customizeProxyRequest(Request proxyRequest,
			HttpServletRequest request) {
//		proxyRequest.getHeaders().remove("Host");
		String auth = this.username+":"+this.password;
		String enc = Base64.encode(auth.getBytes());
		proxyRequest.getHeaders().add("Authentication", "Basic "+enc);
		if(this.tenant!=null) {
			proxyRequest.getHeaders().add("X-Navajo-Instance",this.tenant);
		}
	}

	@Override
	protected URI rewriteURI(HttpServletRequest request) {
		String query = request.getQueryString();
		if(this.url==null) {
			return getEntityFromServer(query);
		}
		return query!=null?URI.create(this.url + "?" + query ):URI.create(this.url);
	}

	private URI getEntityFromServer(String query) {
		final String POSTFIX = "/navajo";
		if(this.server.endsWith(POSTFIX)) {
			String url = server.substring(0,server.length()-POSTFIX.length())+"/entity";
			URI uri = query!=null? URI.create(url + "?" + query ): URI.create(url ) ;
			System.err.println("uri assembled: "+uri);
			return uri;
		}
		return null;
	}
	
	public static void main(String[] args) {
		EntityProxyServlet eps = new EntityProxyServlet();
		eps.server="https://knvb-test.sportlink.com/navajo";
		URI u = eps.getEntityFromServer("tralla");
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
