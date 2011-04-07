package com.dexels.navajo.server.embedded;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.rhino.RhinoHandler;
import com.dexels.navajo.server.listener.NavajoContextListener;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.nql.NqlServlet;

/**
 * The activator class controls the plug-in life cycle
 */
public class EmbeddedServerActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.dexels.navajo.server.embedded"; //$NON-NLS-1$

	// The shared instance
	private static EmbeddedServerActivator plugin;

	protected Server jettyServer;

	private ServletContextHandler webappContextHandler;
	
	/**
	 * The constructor
	 */
	public EmbeddedServerActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		System.err.println("Navajo embedded server activating!");
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EmbeddedServerActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	
	public Server startServer(final int port, final String navajoPath) throws Exception, InterruptedException {
		RhinoHandler r;

		jettyServer = initializeServer(port, navajoPath);	
		startServer();
		return jettyServer;
	}
	
	private void startServer() {
		Thread t = new Thread() {

			@Override
			public void run() {
					ClassLoader currentContextLoader = Thread.currentThread().getContextClassLoader();
					Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
					try {
						EmbeddedServerActivator.this.jettyServer.start();
						EmbeddedServerActivator.this.jettyServer.join();
						EmbeddedServerActivator.this.serverStopped(jettyServer);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Thread.currentThread().setContextClassLoader(currentContextLoader );
		        }
			
		};
		t.start();
		
	}

	protected void serverStopped(Server server) {
		// TODO Auto-generated method stub
		
	}

	public void stopServer() {
		System.err.println("Stopping server");
		try {
//			Connector[] cc = this.jettyServer.getConnectors();
//			for (Connector connector : cc) {
//				connector.getCo
//			}
//			webappContextHandler.getServletContext().s
			if(this.jettyServer==null) {
				return;
			}
			NavajoContextListener.destroyContext(webappContextHandler.getServletContext());
			webappContextHandler.stop();
			System.err.println("Context stopped");

			
			this.jettyServer.stop();
			System.err.println("Server stopped");
			this.jettyServer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Server initializeServer(int port, String navajoPath) {
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		server.addConnector(connector);
		webappContextHandler = new ServletContextHandler(ServletContextHandler.NO_SECURITY | ServletContextHandler.NO_SESSIONS);
		webappContextHandler.setContextPath("/");
		NavajoContextListener.initializeContext(webappContextHandler.getServletContext(),navajoPath);
		webappContextHandler.addServlet(new ServletHolder(new TmlHttpServlet()),"/Postman");
		webappContextHandler.addServlet(new ServletHolder(new NqlServlet()),"/Nql");
		webappContextHandler.addServlet(new ServletHolder(new NqlServlet()),"/Nssql");
		server.setHandler(webappContextHandler);

		return server;
		}
}
