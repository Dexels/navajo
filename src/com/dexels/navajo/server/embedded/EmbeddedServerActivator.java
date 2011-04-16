package com.dexels.navajo.server.embedded;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

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
		touchNavajoParts(context);
	}

	private void touchNavajoParts(BundleContext context) {
		System.err.println("Navajo embedded server activating!");
		navajodocument.Version.getRandom();
		navajoclient.Version.getRandom();
		navajolisteners.Version.getRandom();
		navajo.Version.getRandom();
		navajorhino.Version.getRandom();
		navajoadapters.Version.getRandom();
		navajofunctions.Version.getRandom();
		navajoenterprise.Version.getRandom();
		navajoenterpriseadapters.Version.getRandom();
		navajoenterpriselisteners.Version.getRandom();
		System.err.println("Touch complete!");
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


	
	public Server startServer(final int port, final String navajoPath) throws Exception, InterruptedException {
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
		
	}

	public void stopServer() {
		System.err.println("Stopping server");
		try {
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
