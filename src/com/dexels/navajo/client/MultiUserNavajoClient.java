package com.dexels.navajo.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;


public class MultiUserNavajoClient   {

	private static String [] serverUrls;
	private static double[] serverLoads;
	private final static Random randomize = new Random(System.currentTimeMillis());
	private static int currentServerIndex;
	private static transient boolean pingStarted = false;
	private static boolean killed = false;
	private static final HashMap disabledServers = new HashMap();
	private static final long serverDisableTimeout = 60000;
	 

	 public MultiUserNavajoClient() {
	 }
	 
	 public MultiUserNavajoClient(String [] servers) {
		 setServers(servers);
	 }
	 
	 public void remoteDispatch(String server, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 URL url = new URL("http://" + server);

		 HttpURLConnection con = null;

		 con = (HttpURLConnection) url.openConnection();
		 ((HttpURLConnection) con).setRequestMethod("POST");

		 try {
			 java.lang.reflect.Method timeout = con.getClass().getMethod("setConnectTimeout", new Class[] { int.class });
			 timeout.invoke(con, new Object[] { new Integer(5000) });
		 } catch (SecurityException e) {
		 } catch (Throwable e) {
			 System.err
			 .println("setConnectTimeout does not exist, upgrade to java 1.5+");
		 }

		 con.setDoOutput(true);
		 con.setDoInput(true);
		 con.setUseCaches(false);
		 con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");

		 try {
			 java.lang.reflect.Method chunked = con.getClass().getMethod("setChunkedStreamingMode", new Class[] { int.class });
			 chunked.invoke(con, new Object[] { new Integer(1024) });
			 con.setRequestProperty("Transfer-Encoding", "chunked");
		 } catch (SecurityException e) {
		 } catch (Throwable e) {
			 System.err.println("setChunkedStreamingMode does not exist, upgrade to java 1.5+");
		 }

		 OutputStream os = con.getOutputStream();
		 copyResource(os, request.getInputStream());
		 os.close();

		 InputStream is = con.getInputStream();
		 copyResource(response.getOutputStream(), is);
		 is.close();
	 }
	 
	 protected InputStream doTransaction(String name, Navajo d, HttpURLConnection myCon)
			throws IOException, ClientException, NavajoException,
			javax.net.ssl.SSLHandshakeException {
		URL url;

		url = new URL("http://" + name);

		HttpURLConnection con = null;

		con = (HttpURLConnection) url.openConnection();
		((HttpURLConnection) con).setRequestMethod("POST");

		myCon = con;

		try {
			java.lang.reflect.Method timeout = con.getClass().getMethod("setConnectTimeout", new Class[] { int.class });
			timeout.invoke(con, new Object[] { new Integer(5000) });
		} catch (SecurityException e) {
		} catch (Throwable e) {
			System.err
					.println("setConnectTimeout does not exist, upgrade to java 1.5+");
		}

		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");

		try {
			java.lang.reflect.Method chunked = con.getClass().getMethod("setChunkedStreamingMode", new Class[] { int.class });
			chunked.invoke(con, new Object[] { new Integer(1024) });
			con.setRequestProperty("Transfer-Encoding", "chunked");
		} catch (SecurityException e) {
		} catch (Throwable e) {
			System.err.println("setChunkedStreamingMode does not exist, upgrade to java 1.5+");
		}

		BufferedWriter os = null;
		try {
			os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
			d.write(os);
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return con.getInputStream();

	}
	 
	 private final void copyResource(OutputStream out, InputStream in){
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
	 public final Navajo doSimpleSend(Navajo out, String server) throws ClientException {

		 InputStream in = null;
		 Navajo n = null;
		 HttpURLConnection myCon = null;
		 try {
			 in = doTransaction(server, out, myCon);
			 n = NavajoFactory.getInstance().createNavajo(in);
		 } catch (Exception e) {
			 disabledServers.put(server, new Long(System.currentTimeMillis()));
			 System.err.println("Disabled server: "+server+" for "+serverDisableTimeout+" millis." );
		 }
		 return n;
	 }
	 
	private final void ping() {
		if ( !pingStarted) {
			new Thread() {
				public void run() {

					System.err.println("Started ping thread.");
					Navajo out = NavajoFactory.getInstance().createNavajo();
					Header outHeader = NavajoFactory.getInstance().createHeader(out, "navajo_logon", "ROOT", "", -1);
					out.addHeader(outHeader);
					while (!killed) {
						try {
							Thread.sleep(10000);
							for (int i = 0; i < serverUrls.length; i++) {
								try {
									HttpURLConnection myCon = null;
									InputStream in  = doTransaction(serverUrls[i], out, myCon);
									Navajo n = NavajoFactory.getInstance().createNavajo(in);
									in.close();
									Header h = n.getHeader();
									String load =  h.getHeaderAttribute("cpuload");
									serverLoads[i] = Double.parseDouble(load);
								} catch (Throwable e) {
								}
								
							}
							switchServer();
						} catch (InterruptedException e) {
						}
					}

				}
			}.start();
			pingStarted = true;
		}
	}
	
	private final void switchServer() {
		if (serverUrls==null || serverUrls.length==0) {
			return;
		}
		if (serverUrls.length==1) {
			// Nothing to switch
			return;
		}
		
		double minload = 1000000.0;
		int candidate = -1;
		
		for (int i = 0; i < serverUrls.length; i++) {
			if ( serverLoads[i] < minload && serverLoads[i] != -1.0 ) { // If there is really a server with a lower load, use this server as candidate.
				minload = serverLoads[i];
				candidate = i;
			}
		}
		
		if ( candidate == -1 ) {
			// Round robin...
			if (currentServerIndex==(serverUrls.length-1)) {
				currentServerIndex = 0;
			} else {
				currentServerIndex++;
			}
		} else {
			currentServerIndex = candidate;
		}
		
		System.err.println("currentServer = " + serverUrls[currentServerIndex] + " with load: " + serverLoads[currentServerIndex]);
		
		String nextServer = serverUrls[currentServerIndex];
		
		if (disabledServers.containsKey(nextServer)) {
			Long timeout = (Long)disabledServers.get(nextServer);
			long t = timeout.longValue();
			if (t+serverDisableTimeout<System.currentTimeMillis()) {
				// The disabling time has passed.
				System.err.println("Reinstating server: "+ nextServer+" its timeout has passed.");
				disabledServers.remove(nextServer);
				return;
			} else {
				switchServer();
			}
		}
		
	}
	
	public String getCurrentHost() {
		if (serverUrls!=null && serverUrls.length>0) {
			return serverUrls[currentServerIndex];
		}
		return null;
	}

	private void setServers(String[] servers) {
		serverUrls = servers;
		serverLoads = new double[serverUrls.length];
		if (servers.length>0) {
			currentServerIndex = randomize.nextInt(servers.length);
			System.err.println("Starting at server # "+currentServerIndex);
		}
		ping();
	}
}
