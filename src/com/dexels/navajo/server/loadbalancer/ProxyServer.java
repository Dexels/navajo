package com.dexels.navajo.server.loadbalancer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.client.MultiUserNavajoClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.FatalException;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

public class ProxyServer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8168763665273997694L;

	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
	public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";
	public static final String DEFAULT_SERVER_XML = "config/server.xml";

	protected String configurationPath = "";
	protected String rootPath = null;
	private Map<String, String> callbackSet = Collections.synchronizedMap(new HashMap<String,String>());
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		configurationPath = config.getInitParameter("configuration");
		System.setProperty(DOC_IMPL, QDSAX);
		System.err.println("Configuration path: " + configurationPath);

		String serversParam = config.getInitParameter("servers");
		if ( serversParam == null ) {
			throw new ServletException("Could not initialize proxy, servers are missing");
		} 
		StringTokenizer sts = new StringTokenizer(serversParam, ";");
		String [] servers = new String[sts.countTokens()];
		int index = 0;
		while ( sts.hasMoreTokens() ) {
			String name = sts.nextToken();
			servers[index++] = name;
			System.err.println("PROXY: Added server: " + name);
		}
		
		boolean verified = false;

		URL configUrl;
		InputStream is = null;
		try {
			configUrl = new URL(configurationPath);
			is = configUrl.openStream();
			verified = true;

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (configurationPath == null || "".equals(configurationPath)
				|| !verified) {
			rootPath = config.getServletContext().getRealPath("");
		}
		System.err.println("Resolved Configuration path: " + configurationPath);
		System.err.println("Resolved Root path: " + rootPath);
		
		setServers(servers);
	}

	private void setServers(String [] servers) {
		MultiUserNavajoClient nc = new MultiUserNavajoClient(servers);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Date created = new java.util.Date();
		long start = created.getTime();

		String sendEncoding = request.getHeader("Accept-Encoding");
		String recvEncoding = request.getHeader("Content-Encoding");

		BufferedReader r = null;
		BufferedWriter out = null;
		Navajo outDoc = null;
		Navajo in = null;
		boolean hasCallBack = false;
		String callbackKey = null;
		
//		MultiUserNavajoClient nc = new MultiUserNavajoClient();
//		try {
//			nc.remoteDispatch(nc.getCurrentHost(), request, response);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace(System.err);
//			throw new ServletException(e);
//		}
		try {

			if (sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {
				r = new BufferedReader(new java.io.InputStreamReader(new ZInputStream(request.getInputStream())));
			} else if (sendEncoding != null
					&& sendEncoding.equals(COMPRESS_GZIP)) {
				r = new BufferedReader(new java.io.InputStreamReader(new java.util.zip.GZIPInputStream(request.getInputStream()), "UTF-8"));
			} else {
				r = new BufferedReader(request.getReader());
			}
			
			long decompress = System.currentTimeMillis();
			
			in = NavajoFactory.getInstance().createNavajo(r);
			r.close();
			
			long parse = System.currentTimeMillis();
			
			r = null;

			if (in == null) {
				throw new ServletException("Invalid request.");
			}

			Header header = in.getHeader();
			if (header == null) {
				throw new ServletException("Empty Navajo header.");
			}

			MultiUserNavajoClient nc = new MultiUserNavajoClient();
			String server = nc.getCurrentHost();

			// Check for asynchronous adapters. If they exist, make sure that
			// same server is used for subsequent calls.
			
			if (in.getHeader().hasCallBackPointers()) {
				hasCallBack = true;
				callbackKey = in.getHeader().getCallBackSignature();
				if (callbackSet.containsKey(callbackKey)) {
					server = callbackSet.get(callbackKey);
					System.err.println("USING SAME SERVER: " + server + "@" + callbackKey);
				} 
			}

			outDoc = nc.doSimpleSend(in, server);
		
			long serviceCall = System.currentTimeMillis();
			
            // Check for asynchronous adapters. If they exist, make sure that
			// same server is used for subsequent calls. 
			if ( outDoc != null && outDoc.getHeader().hasCallBackPointers() ) {
				callbackKey = outDoc.getHeader().getCallBackSignature();
				if ( !callbackSet.containsKey(callbackKey)) {
					callbackSet.put(callbackKey, server);
					System.err.println("PUTTING NEW ASYNC: " + server + "@" + callbackKey + " IN CALLBACKSET()");
				}
			}
			
			response.setContentType("text/xml; charset=UTF-8");

			if (recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {
				response.setHeader("Content-Encoding", COMPRESS_JZLIB);
				out = new BufferedWriter(new OutputStreamWriter(
						new ZOutputStream(response.getOutputStream(),
								JZlib.Z_BEST_SPEED), "UTF-8"));
			} else if (recvEncoding != null
					&& recvEncoding.equals(COMPRESS_GZIP)) {
				response.setHeader("Content-Encoding", COMPRESS_GZIP);
				out = new BufferedWriter(new OutputStreamWriter(
						new java.util.zip.GZIPOutputStream(response
								.getOutputStream()), "UTF-8"));
			} else {
				out = new BufferedWriter(response.getWriter());
			}

			outDoc.write(out);
			out.flush();
			out.close();

			long end = System.currentTimeMillis();
			
			long decompressTime = ( decompress - start);
			long parseTime = ( parse - decompress );
			long serviceTime = ( serviceCall - parse );
			long compressTime = ( end - serviceCall );
			long totalTime = ( end - start );
			
			System.err.println("PROXY for " + in.getHeader().getRPCName() + ". Decompress: " + decompressTime + ", Parstime: " + parseTime + ", Servicetime: " + serviceTime + 
					"(" + Long.parseLong(outDoc.getHeader().getHeaderAttribute("serverTime")) + "), CompressTime: " + compressTime + ", Totaltime: " +  totalTime);
			
			//System.err.println("PROXY: " + in.getHeader().getRPCName() + " took  " + ( end - Long.parseLong(outDoc.getHeader().getHeaderAttribute("serverTime")))  + " millis" + "( server time was: " + Long.parseLong(outDoc.getHeader().getHeaderAttribute("serverTime")) + "), CBSIZE: " + callbackSet.size());
			out = null;

		} catch (Throwable e) {
			e.printStackTrace(System.err);
			if (e instanceof FatalException) {
				FatalException fe = (FatalException) e;
				if (fe.getMessage().equals("500.13")) {
					// Server too busy.
					throw new ServletException("500.13");
				}
			}
			throw new ServletException(e);
		} finally {
			// Remove callback keys if async was finished or callback was null.
			if (hasCallBack) {
				if (in != null && outDoc == null || outDoc.getHeader().isCallBackFinished() ) {
					System.err.println("REMOVING FROM CALLBACKSET: " + callbackKey);
					callbackSet.remove(callbackKey);
				}
			}
			if (r != null) {
				try {
					r.close();
				} catch (Exception e) {
					// NOT INTERESTED.
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					// NOT INTERESTED.
				}
			}
		}
	}
	
}
