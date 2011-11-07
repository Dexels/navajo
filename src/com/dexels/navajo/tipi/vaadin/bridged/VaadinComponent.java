package com.dexels.navajo.tipi.vaadin.bridged;

import java.util.Hashtable;

import javax.servlet.ServletException;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.dexels.navajo.tipi.vaadin.application.servlet.TipiVaadinServlet;

public class VaadinComponent {
	private static final String VAADIN_DIR_PREFIX = "/VAADIN";
	private static final String SERVLET_ALIAS = "/";
	private HttpService httpService;

	public void setHttpService(HttpService httpService) {
		try {
		System.err.println("VAADIN SETTING HTTPSERVICE");
		this.httpService = httpService;
		System.err.println("Vaadin Staring up sevlet at " + SERVLET_ALIAS);
		TipiVaadinServlet servlet = new TipiVaadinServlet();
		if(httpService==null) {
			System.err.println("WHOOMP!");
			return;
		}
		  HttpContext commonContext = httpService.createDefaultHttpContext();
		  //		httpService.registerResources("/jsp-examples", "/web", commonContext); 
//		sh.setInitParameter("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
		Hashtable<String,String> ht = new Hashtable<String,String>();
		ht.put("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
//		httpService.registerServlet(SERVLET_ALIAS, servlet, ht, null);
		VaadinFileServlet vfs = new VaadinFileServlet();
		//httpService.registerResources("/VAADIN", path+"/VAADIN", commonContext);
		httpService.registerServlet(VAADIN_DIR_PREFIX, vfs, null, commonContext);
		httpService.registerServlet(SERVLET_ALIAS, servlet, ht, commonContext);
	} catch (ServletException e) {
		e.printStackTrace();
	} catch (NamespaceException e) {
		e.printStackTrace();
	}
	}

	protected void startup(ComponentContext ctxt) {

	}

	protected void shutdown(ComponentContext ctxt) {
		if(httpService!=null)
		System.err.println("SHUTTIN DOWN!");

	}
}
