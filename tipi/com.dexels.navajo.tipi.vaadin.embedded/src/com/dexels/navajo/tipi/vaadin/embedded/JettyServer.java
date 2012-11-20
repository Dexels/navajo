package com.dexels.navajo.tipi.vaadin.embedded;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.application.InstallationPathResolver;
import com.dexels.navajo.tipi.vaadin.application.servlet.TipiVaadinServlet;



public class JettyServer {
	
	private final static Logger logger = LoggerFactory
			.getLogger(JettyServer.class);
	
	public void init(int port,final String contextPath, final Bundle bundle) {
		Server jettyServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		jettyServer.addConnector(connector);
		HandlerList handlers = new HandlerList();
		LoggerContext lc =(LoggerContext)LoggerFactory.getILoggerFactory();

		StatusPrinter.print(lc);
	    
		boolean useTouch = "true".equals( System.getProperty("tipi.vaadin.touch"));
		
	    try {
	      JoranConfigurator configurator = new JoranConfigurator();
	      configurator.setContext(lc);
	      // the context was probably already configured by default configuration 
	      // rules
	      lc.reset(); 
	      configurator.doConfigure(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/vaadin/embedded/logback.xml"));
			StatusPrinter.print(lc);

	    } catch (JoranException je) {
	       logger.error("Error: ",je);
	    }
		StringTokenizer tokenizeContext = new StringTokenizer(contextPath,",");
		List<String> contexts = new LinkedList<String>();
		while (tokenizeContext.hasMoreTokens()) {
			String context = tokenizeContext.nextToken();
			contexts.add(context);
			try {
				addVaadinContext(context, bundle, jettyServer, handlers,useTouch);
			} catch (ClassNotFoundException e) {
				logger.error("Error: ",e);
			}
			
		}

		
		

		
		jettyServer.setHandler(handlers);


//		handlers.setHandlers(new Handler[] {webAppContext,resource_handler });

		//		VaadinFileServlet vaadinFile = new VaadinFileServlet();
//		ServletHolder s2 = new ServletHolder(vaadinFile);



		//		webAppContext.addServlet(s2,"/*");

		try {
			jettyServer.start();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
		logger.info("Started: "+contexts.size()+" contexts.");
		int i = 1;
		for (String context : contexts) {
			
			logger.info("Context #"+i+". Open with:\nhttp://localhost:"+port+context+"/app\n");
			i++;
		}

	}

	private void addVaadinContext(final String contextPath, final Bundle bundle, Server jettyServer, HandlerList handlers, boolean useTouch) throws ClassNotFoundException {
		ServletContextHandler webAppContext = new ServletContextHandler(jettyServer,contextPath,true,false);
		webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
//		TipiVaadinTouchServlet vaadin = new TipiVaadinTouchServlet();
		Class<? extends Servlet> cls;
		Servlet vaadin = null;
		if (useTouch) {
			cls = (Class<? extends Servlet>) Class.forName("com.dexels.navajo.tipi.vaadin.touch.servlet.TipiVaadinTouchServlet");
		} else {
			cls = TipiVaadinServlet.class;
		}
		try {
			vaadin = cls.newInstance();
		} catch (InstantiationException e2) {
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
		}
		
		ServletHolder sh = new ServletHolder(vaadin);
		if (useTouch) {
			sh.setInitParameter("application", "com.dexels.navajo.tipi.vaadin.touch.application.TipiVaadinTouchApplication");
			sh.setInitParameter("widgetset", "com.example.vaadintest.widgetset.VaadintestWidgetset");
		} else {
			sh.setInitParameter("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
			sh.setInitParameter("widgetset", "com.dexels.navajo.tipi.vaadin.widgetset.VaadintestWidgetset");
		}

		// Jetty style, OSGi style would be /app/
		webAppContext.addServlet(sh,"/app/*");

		
		ResourceHandler resource_handler = new ResourceHandler() {

			@Override
			public Resource getResource(String s) throws MalformedURLException {
//				logger.info("Getting resource: "+s);
				if(s.startsWith(contextPath)) {
//					logger.info("Cropping");
					s = s.substring(contextPath.length());
				}
				if(s==null || s.isEmpty()) {
					return null;
				}
				Resource r =  super.getResource(s);
				if(!r.exists()) {
					Resource ur=null;
					try {
						ur = Resource.newResource(bundle.getResource(s));
					} catch (IOException e) {
						logger.error("Error: ",e);
					}
						if(ur!=null) {
//							logger.info("Name: "+ur.getName()+" found: "+ur.exists());
//							logger.info(":: "+ur.length()+" mod: "+ur.lastModified());
//							logger.info("Resolved!!! returning...");
							return ur;
						}
				}
				return r;
			}
			
		};

		resource_handler.setDirectoriesListed(false);
		// should be configurable, maybe?
		resource_handler.setAliases(false);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });

		String installationFolder;
		try {
			installationFolder = InstallationPathResolver.getInstallationFromPath(contextPath).get(0);
			logger.info("Resolved install: "+installationFolder);
			resource_handler.setResourceBase(installationFolder);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TipiException e1) {
			e1.printStackTrace();
		}
		handlers.addHandler(webAppContext);
		handlers.addHandler(resource_handler);
	}
}
