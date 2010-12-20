package com.dexels.navajo.server.listener.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.MaintainanceRequest;
import com.dexels.navajo.server.enterprise.xmpp.JabberWorkerFactory;
import com.dexels.navajo.server.enterprise.xmpp.JabberWorkerInterface;

public abstract class BaseNavajoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3874256795878319625L;

	protected String configurationPath = "";
	protected String rootPath = null;
	protected boolean extremeEdition = false;
	protected static boolean streamingMode = true; 

	public static final String DEFAULT_SERVER_XML = "config/server.xml";
	public static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";
	
	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";

	private static Object initializationSemaphore = new Object();
	
	protected final DispatcherInterface initDispatcher() throws NavajoException {

		String servletContextRootPath = getServletContext().getRealPath("");
		if (configurationPath!=null) {
			// Old SKOOL. Path provided, notify the dispatcher by passing a null DEFAULT_SERVER_XML
			if(extremeEdition) {
				return DispatcherFactory.getInstance(new File(configurationPath), DEFAULT_SERVER_XML, new com.dexels.navajo.server.FileInputStreamReader(),servletContextRootPath);
			} else {
				return DispatcherFactory.getInstance(configurationPath, null, new com.dexels.navajo.server.FileInputStreamReader(),servletContextRootPath);
			}
		} else {
			return DispatcherFactory.getInstance(rootPath, DEFAULT_SERVER_XML, new com.dexels.navajo.server.FileInputStreamReader(),servletContextRootPath);
		}

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		synchronized( initializationSemaphore ) {
			extremeEdition = config.getInitParameter("extremeEdition")!=null;
			configurationPath = config.getInitParameter("configuration");

			if(extremeEdition) {
				String path;
				try {
					path = setupConfigurationPath(config);
				} catch (IOException e) {
					path = null;
					e.printStackTrace();
				}
				if(path!=null) {
					System.err.println("Path found. Using: "+path);
					File f = new File(path);
					configurationPath = path;
				}
			}
			// Check whether defined bootstrap webservice is present.
			String bootstrapUrl = config.getInitParameter("bootstrap_url");
			String bootstrapService = config.getInitParameter("bootstrap_service");
			String bootstrapUser = config.getInitParameter("bootstrap_user");
			String bootstrapPassword = config.getInitParameter("bootstrap_password");

			System.setProperty(DOC_IMPL,QDSAX);
			System.err.println("Configuration path: "+configurationPath);

			boolean verified = false;

			URL configUrl;
			InputStream is = null;
			try {
				configUrl = new URL(configurationPath);
				is = configUrl.openStream();
				verified = true;
			} catch (MalformedURLException e) {
				//e.printStackTrace(System.err);
			} catch (IOException e) {
			} finally {
				if(is!=null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if( configurationPath==null || "".equals(configurationPath)|| !verified) {
				rootPath = config.getServletContext().getRealPath("");
			}
			System.err.println("Resolved Configuration path: "+configurationPath);
			System.err.println("Resolved Root path: "+rootPath);

			// Startup Navajo instance.
			try {
				DispatcherInterface d = initDispatcher();
				Navajo n = NavajoFactory.getInstance().createNavajo();
				if ( bootstrapService == null ) {
//					Header h = NavajoFactory.getInstance().createHeader(n, MaintainanceRequest.METHOD_NAVAJO_PING, "", "", -1);
//					n.addHeader(h);
					// Don't ping if there is no bootstrap service
				} else {
					Header h = NavajoFactory.getInstance().createHeader(n, bootstrapService, bootstrapUser, bootstrapPassword, -1);
					n.addHeader(h);
					d.handle(n);
					System.err.println("NAVAJO INSTANCE " +  d.getNavajoConfig().getInstanceName() + " BOOTSTRAPPED BY " + n.getHeader().getRPCName());
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}

			// Initialize Jabber.
			initializeJabber(config, bootstrapUrl);
		}

	}

	private String setupConfigurationPath(ServletConfig config) throws IOException {
		String contextName =  config.getServletContext().getContextPath().substring(1);
		String navajoPath = getSystemPath(contextName);
		return navajoPath;
	}

	protected final void dumHttp(HttpServletRequest request, long index, File dir) {
		// Dump stuff.
		if (request != null) {
			StringBuffer sb = new StringBuffer();

			sb.append("HTTP DUMP (" + request.getRemoteAddr() + "/"
					+ request.getRequestURI());
			Enumeration e = request.getHeaderNames();
			while (e.hasMoreElements()) {
				String headerName = (String) e.nextElement();
				sb.append(headerName + "=" + request.getHeader(headerName) + "\n");
			}
			try {

				if (dir != null) {
					FileWriter fw = new FileWriter(new File(dir, "httpdump-"
							+ index));
					fw.write(sb.toString());
					fw.close();
				} else {
					System.err.println(sb.toString());
				}
			} catch (IOException ioe) {
				ioe.printStackTrace(System.err);
			}
		} else {
			System.err.println("EMPTY REQUEST OBJECT!!");
		}
	}

	private String getSystemPath(String name) throws IOException {

		String force = getServletContext().getInitParameter("forcedNavajoPath");
		if(force!=null) {
//			System.err.println("Using the force! navajo.properties will be ignored!");
			return force;
		}
		Map<String,String> systemContexts = new HashMap<String, String>();
		File home = new File(System.getProperty("user.home"));
		File navajo = new File(home,"navajo.properties");
		if(!navajo.exists()) {
			return null;
		}
		BufferedReader br = new BufferedReader(new FileReader(navajo));
		while(true) {
			String line = br.readLine();
			if(line==null) {
				break;
			}
			String[] r = line.split("=");
			systemContexts.put(r[0], r[1]);
		}
		br.close();
		System.err.println("Maps: "+systemContexts);
		return systemContexts.get(name);
	}

	private void initializeJabber(ServletConfig config, String bootstrapUrl) {
		JabberWorkerInterface jwi = JabberWorkerFactory.getInstance();
		if(jwi!=null) {
			String jabberServer = config.getInitParameter("jabber_server");
			String jabberPort = config.getInitParameter("jabber_port");
			String jabberService = config.getInitParameter("jabber_service");
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> jabberServer = " + jabberServer);
			jwi.configJabber(jabberServer, jabberPort, jabberService, bootstrapUrl);
		}
	}

	public void destroy() {
		System.err.println("In NavajoServlet destroy()");
		// Kill Dispatcher.
		Dispatcher.killMe();
	}

	protected final void copyResource(OutputStream out, InputStream in){
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			try {
				read = bin.read(buffer);
				if ( read > -1 ) {
					bout.write(buffer,0,read);
				}
			} catch (IOException e) {
			}
			if ( read <= -1) {
				ready = true;
			}
		}
		try {
			bin.close();
			bout.flush();
			bout.close();
		} catch (IOException e) {

		}
	}
}
