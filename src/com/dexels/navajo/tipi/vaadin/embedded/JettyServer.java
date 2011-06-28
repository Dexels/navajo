package com.dexels.navajo.tipi.vaadin.embedded;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletException;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.osgi.framework.Bundle;

import com.dexels.navajo.tipi.vaadin.application.InstallationPathResolver;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.dexels.navajo.tipi.vaadin.application.servlet.TipiVaadinServlet;
//<context-param>
//<description>
//Vaadin production mode</description>
//<param-name>productionMode</param-name>
//<param-value>false</param-value>
//</context-param>
//<servlet>
//<servlet-name>Vaadintest Application</servlet-name>
//<servlet-class>com.vaadin.terminal.gwt.server.ApplicationServlet</servlet-class>
//<init-param>
//	<description>
//	Vaadin application class to start</description>
//	<param-name>application</param-name>
//	<param-value>com.dexels.vaadintest.VaadinApplication</param-value>
//</init-param>
//<init-param>
//	<description>
//	Application widgetset</description>
//	<param-name>widgetset</param-name>
//	<param-value>com.dexels.vaadintest.widgetset.VaadintestWidgetset</param-value>
//</init-param>
//</servlet>



public class JettyServer {
	
	public void init(int port,final String contextPath, final Bundle bundle) {
		Server jettyServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		jettyServer.addConnector(connector);
		ServletContextHandler webAppContext = new ServletContextHandler(jettyServer,contextPath,true,false);
		webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		Handler[] a =  webAppContext.getHandlers();
		ResourceHandler resource_handler = new ResourceHandler() {

			@Override
			public Resource getResource(String s) throws MalformedURLException {
				System.err.println("Getting resource: "+s);
				System.err.println("Context path: "+contextPath);
				if(s.startsWith(contextPath)) {
					System.err.println("Cropping");
					s = s.substring(contextPath.length());
				}
				System.err.println("clipped to: "+s);
				if(s==null || s.isEmpty()) {
					return null;
				}
				Resource r =  super.getResource(s);
				if(!r.exists()) {
					System.err.println("Not found. trying to resole class: "+s);
					ClassLoader cl = TipiVaadinApplication.class.getClassLoader();
					Resource ur=null;
					try {
						ur = Resource.newResource(bundle.getResource(s));
					} catch (IOException e) {
						e.printStackTrace();
					}
//						Resource ur = Resource.newClassPathResource(s.substring(1),true,true);
						if(ur!=null) {
							System.err.println("Name: "+ur.getName()+" found: "+ur.exists());
							System.err.println(":: "+ur.length()+" mod: "+ur.lastModified());
							System.err.println("Resolved!!! returning...");
							return ur;
							
						} else {
							System.err.println("Not found");
						}
				}
				return r;
			}
			
		};

		resource_handler.setDirectoriesListed(false);
		// should be configurable, maybe?
		resource_handler.setAliases(false);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });

		;
		
		String installationFolder;
		try {
			installationFolder = InstallationPathResolver.getInstallationFromPath(contextPath);
			System.err.println("Resolved install: "+installationFolder);
			resource_handler.setResourceBase(installationFolder);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}

//		webAppContext.add
		
		HandlerList handlers = new HandlerList();
//		handlers.setHandlers(new Handler[] {webAppContext });
		handlers.setHandlers(new Handler[] {webAppContext,resource_handler });
		jettyServer.setHandler(handlers);
//		Resource r = webAppContext.getBaseResource();
//		System.err.println("R: "+r);
		//		

//		VaadinFileServlet vaadinFile = new VaadinFileServlet();
//		ServletHolder s2 = new ServletHolder(vaadinFile);


		TipiVaadinServlet vaadin = new TipiVaadinServlet();
		ServletHolder sh = new ServletHolder(vaadin);
		sh.setInitParameter("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
		webAppContext.addServlet(sh,"/app/");
//		webAppContext.addServlet(s2,"/*");

		try {
			jettyServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
