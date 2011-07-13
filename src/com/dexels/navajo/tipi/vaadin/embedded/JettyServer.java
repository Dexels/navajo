package com.dexels.navajo.tipi.vaadin.embedded;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

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
import com.dexels.navajo.tipi.vaadin.application.servlet.TipiVaadinServlet;



public class JettyServer {
	
	public void init(int port,final String contextPath, final Bundle bundle) {
		Server jettyServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		jettyServer.addConnector(connector);
		HandlerList handlers = new HandlerList();
		
		StringTokenizer tokenizeContext = new StringTokenizer(contextPath,",");
		List<String> contexts = new LinkedList<String>();
		while (tokenizeContext.hasMoreTokens()) {
			String context = tokenizeContext.nextToken();
			contexts.add(context);
			addVaadinContext(context, bundle, jettyServer, handlers);
			
		}

		
		

		
		jettyServer.setHandler(handlers);


//		handlers.setHandlers(new Handler[] {webAppContext,resource_handler });

		//		VaadinFileServlet vaadinFile = new VaadinFileServlet();
//		ServletHolder s2 = new ServletHolder(vaadinFile);



		//		webAppContext.addServlet(s2,"/*");

		try {
			jettyServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Started: "+contexts.size()+" contexts.");
		int i = 1;
		for (String context : contexts) {
			
			System.err.println("Context #"+i+". Open with:\nhttp://localhost:"+port+context+"/app\n");
			i++;
		}

	}

	private void addVaadinContext(final String contextPath, final Bundle bundle, Server jettyServer,
			HandlerList handlers) {
		ServletContextHandler webAppContext = new ServletContextHandler(jettyServer,contextPath,true,false);
		webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		TipiVaadinServlet vaadin = new TipiVaadinServlet();
		ServletHolder sh = new ServletHolder(vaadin);
		sh.setInitParameter("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
		// Jetty style, OSGi style would be /app/
		webAppContext.addServlet(sh,"/app/*");

		
		ResourceHandler resource_handler = new ResourceHandler() {

			@Override
			public Resource getResource(String s) throws MalformedURLException {
//				System.err.println("Getting resource: "+s);
				if(s.startsWith(contextPath)) {
//					System.err.println("Cropping");
					s = s.substring(contextPath.length());
				}
				if(s==null || s.isEmpty()) {
					return null;
				}
				Resource r =  super.getResource(s);
				if(!r.exists()) {
					System.err.println("Not found. trying to resole class: "+s);
					Resource ur=null;
					try {
						ur = Resource.newResource(bundle.getResource(s));
					} catch (IOException e) {
						e.printStackTrace();
					}
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
			installationFolder = InstallationPathResolver.getInstallationFromPath(contextPath).get(0);
			System.err.println("Resolved install: "+installationFolder);
			resource_handler.setResourceBase(installationFolder);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
		handlers.addHandler(webAppContext);
		handlers.addHandler(resource_handler);
	}
}
