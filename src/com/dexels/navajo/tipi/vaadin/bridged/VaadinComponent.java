package com.dexels.navajo.tipi.vaadin.bridged;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.dexels.navajo.tipi.vaadin.application.InstallationPathResolver;
import com.dexels.navajo.tipi.vaadin.application.servlet.TipiVaadinServlet;

public class VaadinComponent {
	private static final String SERVLET_ALIAS = "/app";
	private HttpService httpService;

	public void setHttpService(HttpService httpService) {
		System.err.println("SETTING HTTPSERVICE");
		this.httpService = httpService;
	}

	protected void startup(ComponentContext ctxt) {
		try {
			System.out.println("Staring up sevlet at " + SERVLET_ALIAS);
			TipiVaadinServlet servlet = new TipiVaadinServlet();
			if(httpService==null) {
				System.err.println("WHOOMP!");
				return;
			}
			  HttpContext commonContext = httpService.createDefaultHttpContext();
//			httpService.registerResources("/jsp-examples", "/web", commonContext); 
//			sh.setInitParameter("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
			Hashtable<String,String> ht = new Hashtable<String,String>();
			ht.put("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
//			httpService.registerServlet(SERVLET_ALIAS, servlet, ht, null);
			
			String path = InstallationPathResolver.getInstallationFromPath("/oao");
			System.err.println("USING CONTEXT PATH: "+path);
			VaadinFileServlet vfs = new VaadinFileServlet(path);
			//httpService.registerResources("/VAADIN", path+"/VAADIN", commonContext);
			httpService.registerServlet("/VAADIN", vfs, null, commonContext);
			httpService.registerServlet(SERVLET_ALIAS, servlet, ht, commonContext);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (NamespaceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void shutdown(ComponentContext ctxt) {
		if(httpService!=null)
		System.err.println("SHUTTIN DOWN!");

	}
}
