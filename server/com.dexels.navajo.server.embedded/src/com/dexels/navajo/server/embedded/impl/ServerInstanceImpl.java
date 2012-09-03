package com.dexels.navajo.server.embedded.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.client.context.NavajoRemoteContext;
import com.dexels.navajo.client.server.ServerInstance;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.listener.NavajoContextListener;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.nql.NqlServlet;

public class ServerInstanceImpl implements ServerInstance {
//	private String projectName;
	private int port;
	
	private Server jettyServer;
	private NavajoRemoteContext localContext;

	private ServletContextHandler webappContextHandler;

	private final List<LifeCycle.Listener> lifeCycleListeners = new ArrayList<LifeCycle.Listener>();
	@SuppressWarnings("unused")
	private final Appendable outputAppendable;

	public ServerInstanceImpl(Appendable a) {
		outputAppendable = a;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.server.embedded.impl.ServerInstance#getPort()
	 */
	@Override
	public int getPort() {
		return port;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.server.embedded.impl.ServerInstance#startServer(java.lang.String)
	 */
	@Override
	public int startServer(final String projectPath) {
		try {
//			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

			
			LifeCycle.Listener lifecycleListener = new LifeCycle.Listener() {
				
				@Override
				public void lifeCycleStopping(LifeCycle l) {
					
				}
				
				@Override
				public void lifeCycleStopped(LifeCycle l) {
//					NavajoBundleManager.getInstance().uninstallAdapterBundles();
//					EmbeddedServerActivator.getDefault().deregisterServerInstance(project);
				}
				
				@Override
				public void lifeCycleStarting(LifeCycle l) {
					
				}
				
				@Override
				public void lifeCycleStarted(LifeCycle l) {
					port = jettyServer.getConnectors()[0].getPort();
					String server = "localhost:"+port+"/Postman";
					setupClient(server, "plugin","plugin");
				}
				
				@Override
				public void lifeCycleFailure(LifeCycle l, Throwable e) {
					l.removeLifeCycleListener(this);
				}
			};
			startServer(projectPath,lifecycleListener);
			port = jettyServer.getConnectors()[0].getPort();
			return port;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}


	public NavajoContext getNavajoContext() {
		return localContext;
	}
	
	protected void setupClient(String server, String user, String pass) {
		localContext = new NavajoRemoteContext();
		localContext.setupClient(server,user, pass);
		
	}



	
	private void startServer(final String projectName, Listener lifecycleListener) throws Exception {
		System.err.println("Project name: "+projectName);
//		File file = navajoProject.getLocation().toFile();

		
		initializeServer();
		jettyServer.addLifeCycleListener(lifecycleListener);
		
		// ordering, allow for listeners to be added befor instantiating the jetty server
		for (LifeCycle.Listener l : lifeCycleListeners) {
			jettyServer.addLifeCycleListener(l);
		}
		startServer();
	}
	
	public void addLifeCycleListener(LifeCycle.Listener lifecycleListener) {
		lifeCycleListeners.add(lifecycleListener);
		if(jettyServer!=null) {
			jettyServer.addLifeCycleListener(lifecycleListener);
		}
	}
	
	public void removeLifeCycleListener(LifeCycle.Listener lifecycleListener) {
		lifeCycleListeners.remove(lifecycleListener);
		if(jettyServer!=null) {
			jettyServer.removeLifeCycleListener(lifecycleListener);
		}
	}
	
	private void startServer() {
		Thread t = new Thread() {

			@Override
			public void run() {
					ClassLoader currentContextLoader = Thread.currentThread().getContextClassLoader();
					Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
					try {
						jettyServer.start();
						jettyServer.join();
//						EmbeddedServerActivator.this.serverStopped(jettyServer);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Thread.currentThread().setContextClassLoader(currentContextLoader );
		        }
			
		};
		t.start();
		
	}

	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.server.embedded.impl.ServerInstance#stopServer()
	 */
	@Override
	public void stopServer() {
		Thread t = new Thread() {
			public void run() {
				System.err.println("Stopping server");
				try {
					if(ServerInstanceImpl.this.jettyServer==null) {
						return;
					}
//					System.err.println("Stopping context seems to hang? Disabled");
//					NavajoContextListener.destroyContext(webappContextHandler.getServletContext());
					System.err.println("Stopping context handler");
					webappContextHandler.stop();
					System.err.println("Context stopped");
					
					
					
					ServerInstanceImpl.this.jettyServer.stop();
					System.err.println("Server stopped");
					ServerInstanceImpl.this.jettyServer = null;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	private void initializeServer() throws IOException {
		port = findFreePort();
//		String ss = folder.getLocation().toString();
		initializeServer(port);
	}
	
	private int findFreePort() throws IOException {
		ServerSocket server = new ServerSocket(0);
		int port = server.getLocalPort();
		server.close();
		return port;
	}

	private void initializeServer(int port)  {
//		   Logger logger = (Logger) LoggerFactory.getLogger("org.eclipse.jetty.util.log");
		   LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		   lc.reset();
//		   logger.setLevel(Level.INFO);
		   
		    PatternLayout patternLayout = new PatternLayout();
		      patternLayout.setContext(lc);
		      patternLayout.setPattern("%-5level %logger - %msg%n");
		      patternLayout.start();
		   // Both of these should be set BEFORE setOutputStream is called.
		   // Otherwise: Nasty silent NPE
		jettyServer = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		jettyServer.addConnector(connector);
		webappContextHandler = new ServletContextHandler(ServletContextHandler.NO_SECURITY | ServletContextHandler.NO_SESSIONS);
		String contextPath = "/";
		webappContextHandler.setContextPath(contextPath);
		String installationPath = NavajoContextListener.getInstallationPath(contextPath);

		NavajoContextListener.initializeServletContext(contextPath,webappContextHandler.getServletContext().getRealPath(""),installationPath);
		webappContextHandler.addServlet(new ServletHolder(new TmlHttpServlet()),"/Postman");
		webappContextHandler.addServlet(new ServletHolder(new NqlServlet()),"/Nql");
//		JspConfig jspConfig = new JspConfig(webappContextHandler.getServletContext());
//		JspServlet jspServlet = new JspServlet();
//		webappContextHandler.addServlet(new ServletHolder(jspServlet),"*.jsp");

		//		webappContextHandler.addServlet(new ServletHolder(new NqlServlet()),"/Nssql");
		
		jettyServer.setHandler(webappContextHandler);

		}

	@Override
	public Navajo callService(Navajo n, String service) throws ClientException {
		 localContext.callService(service, n);
		 return localContext.getNavajo(service);
	}

}
