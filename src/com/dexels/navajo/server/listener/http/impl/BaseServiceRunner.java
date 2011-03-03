package com.dexels.navajo.server.listener.http.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.listeners.NavajoDoneException;
import com.dexels.navajo.listeners.Scheduler;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.FastDispatcher;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.server.listener.http.AsyncRequest;
import com.dexels.navajo.server.listener.http.TmlScheduler;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

public abstract  class BaseServiceRunner extends BaseInMemoryRequest implements TmlRunnable, AsyncRequest  {

	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
	public static final String COMPRESS_NONE = "";


	protected final HttpServletResponse response;
	protected final HttpServletRequest request;
	
	private final String recvEncoding;
   private final String sendEncoding;
   private final Object cert;
//   private InputStream is;
	private boolean committed;
	 private boolean isAborted = false;

	private long connectedAt = -1;
	private long scheduledAt = -1;
	private long startedAt = -1;
	private TmlScheduler tmlScheduler;
	private String url;

	private Access access;
	
	private long endEventCount = 0;
	
	public long getEndEventCount() {
		return endEventCount;
	}

	public synchronized void updateEndEventCount() {
		this.endEventCount += 1;
	}

	public  BaseServiceRunner(HttpServletRequest request, HttpServletResponse response, String sendEncoding, String recvEncoding, Object cert) {
//		this.event = event;
		this.response = response;
		this.request = request;
		this.recvEncoding = recvEncoding;
		this.sendEncoding = sendEncoding;
		this.cert = cert;
		this.connectedAt = System.currentTimeMillis();
		setUrl(createUrl(this.request));
	}

	public final void setScheduledAt(long scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public  String createUrl(HttpServletRequest req) {
	    String scheme = req.getScheme();             // http
	    String serverName = req.getServerName();     // hostname.com
	    int serverPort = req.getServerPort();        // 80
	    String contextPath = req.getContextPath();   // /mywebapp
	    String servletPath = req.getServletPath();   // /servlet/MyServlet
	    String pathInfo = req.getPathInfo();         // /a/b;c=123
	    String queryString = req.getQueryString();          // d=789

	    // Reconstruct original requesting URL
	    String url = scheme+"://"+serverName+":"+serverPort+contextPath+servletPath;
	    if (pathInfo != null) {
	        url += pathInfo;
	    }
	    if (queryString != null) {
	        url += "?"+queryString;
	    }
	    return url;
	}

	public final long getScheduledAt() {
		return this.scheduledAt;
	}
	
	public final TmlScheduler getTmlScheduler() {
		return tmlScheduler;
	}


	public final void setTmlScheduler(Scheduler tmlScheduler) {
		this.tmlScheduler = (TmlScheduler) tmlScheduler;
	}

	
  /**
   * Handle a request.
   *
   * @param request
   * @param response
   * @throws IOException
   * @throws ServletException
   */
  private final void execute() throws IOException, ServletException {
	  
//	  BufferedReader r = null;
	  try {
		  Navajo in = getInputNavajo();
		  in.getHeader().setHeaderAttribute("useComet", "true");
		  
		  // Check the availability of the resource...
		  // ServiceAvailability sa = ResourceCheckerManager.getInstance().getResourceChecker(in.getHeader().getRPCName(), in).getServiceAvailability();
		  
		  if ( in.getHeader().getHeaderAttribute("callback") != null ) {
			  
			  String callback = in.getHeader().getHeaderAttribute("callback");
			  
			  try {
				  Navajo callbackNavajo = new FastDispatcher().handle(in, callback);
				  writeOutput(in, callbackNavajo);
				  
			  } finally {
				  tmlScheduler.removeTmlRunnable(request);
				  endTransaction();
			  }
			  
		  } else if ( in.getHeader().getHeaderAttribute("fastflow") != null ) {

			  Runnable onFinish = new Runnable() {

				public void run() {
					try {
						tmlScheduler.removeTmlRunnable(request);
						endTransaction();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				  
			  };
			  
			  new FastDispatcher().handle(in, response.getOutputStream(), onFinish);
			  
		  } else {
			  boolean continuationFound = false;
			  try {
				  
				  String sendEncoding = request.getHeader("Accept-Encoding");
			      String recvEncoding = request.getHeader("Content-Encoding");
					
				  ClientInfo clientInfo = 	new ClientInfo(request.getRemoteAddr(), "unknown",
							recvEncoding, (int) (scheduledAt - connectedAt), (int) (startedAt - scheduledAt), ( recvEncoding != null && ( recvEncoding.equals(COMPRESS_GZIP) || recvEncoding.equals(COMPRESS_JZLIB))), 
							( sendEncoding != null && ( sendEncoding.equals(COMPRESS_GZIP) || sendEncoding.equals(COMPRESS_JZLIB))), 
							request.getContentLength(), new java.util.Date( connectedAt ) );
				  
				  Navajo outDoc = DispatcherFactory.getInstance().removeInternalMessages(DispatcherFactory.getInstance().handle(in, this,cert, clientInfo));
				  // Do do: Support async services in a more elegant way.
				  if(!isAborted()) {
					  writeOutput(in, outDoc);			  
				  } else {
					  System.err.println("Aborted: Can't write output!");
				  }
			  } catch (NavajoDoneException e) {
				  // temp catch, to be able to pre
				  continuationFound = true;
				  System.err.println("Navajo done in service runner. Thread disconnected...");
				  throw(e);
			  }
			  finally {
				  tmlScheduler.removeTmlRunnable(request);
				  if(!continuationFound) {
					  // this will be the responsibility of the next thread, who will finish the continuation.
					  endTransaction();
				  }
			  }
		  }
	  }
	  catch (NavajoDoneException e) {
		  throw(e);
	  }
	  catch (Throwable e) {
		  //e.printStackTrace(System.err);
		  if ( e instanceof  FatalException ) {
			  FatalException fe = (FatalException) e;
			  if ( fe.getMessage().equals("500.13")) {
				  // Server too busy.
				  throw new ServletException("500.13");
			  }
		  }
		  throw new ServletException(e);
	  } 
  }

protected final Navajo parseInputNavajo() throws IOException, UnsupportedEncodingException {
	BufferedReader r;
	InputStream is = getRequestInputStream();
	  Navajo in = null;
		  if ( sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {			 
			  r = new BufferedReader(new java.io.InputStreamReader(new ZInputStream(is)));
		  } else if ( sendEncoding != null && sendEncoding.equals(COMPRESS_GZIP)) {
			  r = new BufferedReader(new java.io.InputStreamReader(new java.util.zip.GZIPInputStream(is), "UTF-8")); 
		  }
		  else {
			  r = new BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
		  }
		  in = NavajoFactory.getInstance().createNavajo(r);

	  if (in == null) {
		  throw new IOException("Invalid request.");
	  }

	  Header header = in.getHeader();
	  if (header == null) {
		  throw new IOException("Empty Navajo header.");
	  }

	  is.close();
	
	  if(DispatcherFactory.getInstance()==null) {
		  abort();
		  throw new IllegalStateException("Navajo server not initialized properly. Has the postman been called?");
	  }
	return in;
}

public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException, UnsupportedEncodingException,
		NavajoException {
	OutputStream out;
	response.setContentType("text/xml; charset=UTF-8");
	response.setHeader("Connection", "close"); // THIS IS NOT SUPPORTED, I.E. IT DOES NOT WORK...EH.. PROBABLY..

//		  System.err.println("Encoding using: "+recvEncoding);
	 
//		  //OutputStream output = response.getOutputStream();
//	  File temp = null;
//	  	temp = File.createTempFile("navajo", ".xml");
//		FileOutputStream fos = new FileOutputStream(temp);
		  
	//try {
		if ( recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {		
			response.setHeader("Content-Encoding", COMPRESS_JZLIB);
			  out = new ZOutputStream(response.getOutputStream(), JZlib.Z_BEST_SPEED);
		  } else if ( recvEncoding != null && recvEncoding.equals(COMPRESS_GZIP)) {
			  response.setHeader("Content-Encoding", COMPRESS_GZIP);
			  out = new java.util.zip.GZIPOutputStream(response.getOutputStream());
		  }
		  else {
			  System.err.println("No content encoding specified: (" + sendEncoding + "/" + recvEncoding + ")");
			  out = response.getOutputStream();
		  }
		
		
		  long finishedScriptAt = System.currentTimeMillis();
		  // postTime = 
		  long postTime = scheduledAt - connectedAt;
		  long queueTime = startedAt - scheduledAt;
		  long serverTime = finishedScriptAt - startedAt;

		  outDoc.getHeader().setHeaderAttribute("postTime", ""+postTime);
		  outDoc.getHeader().setHeaderAttribute("queueTime", ""+queueTime);
		  outDoc.getHeader().setHeaderAttribute("serverTime", ""+serverTime);
		  outDoc.getHeader().setHeaderAttribute("threadName", ""+Thread.currentThread().getName());
		  
		  
//		  outDoc.write(output);
//		  output.flush();
//		  output.close();
//		  outDoc.write(fos);
//		  fos.close();
//		  
		  
		  File temp = null;
		  temp = File.createTempFile("navajo", ".xml");
		  FileOutputStream fos = new FileOutputStream(temp);
		  
		  // Maybe remove this flush. Will be more efficient, but the writing time will be less accurate.
		  outDoc.write(fos);
		  fos.close();
		  
		response.setContentLength((int) temp.length());
		  
		  FileInputStream fis = new FileInputStream(temp);
		  copyResource(out, fis);
		  
		  fis.close();
		  
		  //outDoc.write(out);
		  
		  temp.delete();
		  
		  //response.setContentLength("DIT IS HET RESULTAAT VAN DE WEBSERVICE.".length());
		  
		  //out.write("DIT IS HET RESULTAAT VAN DE WEBSERVICE.");
		 
		//  response.flushBuffer();
		  out.flush();
		  out.close();
//		  
		  
		  
		  long writeFinishedAt = System.currentTimeMillis();
		  long writeTime = writeFinishedAt - finishedScriptAt;

//		  System.err.println("Finished write at: "+writeTime);
		  TmlScheduler ts = getTmlScheduler();
		  String threadStatus = null;
		  if(ts!=null) {
			  threadStatus = ts.getSchedulingStatus();
		  } else {
			  threadStatus = "Schedule status unknown, no scheduler found.";
		  }
//		  int threadsActive = getActiveCount();

		  if ( inDoc != null && inDoc.getHeader() != null && outDoc != null && outDoc.getHeader() != null && !Dispatcher.isSpecialwebservice(inDoc.getHeader().getRPCName())) {
			  System.err.println("(" + DispatcherFactory.getInstance().getApplicationId() + "): " + 
					          new java.util.Date(connectedAt) + ": " +
					          outDoc.getHeader().getHeaderAttribute("accessId") + ":" + 
					          inDoc.getHeader().getRPCName() + "(" + inDoc.getHeader().getRPCUser() + "):" + 
					          ( System.currentTimeMillis() - connectedAt ) + " ms. " + 
					          "(st=" + outDoc.getHeader().getHeaderAttribute("serverTime") + 
					          ",rpt=" + outDoc.getHeader().getHeaderAttribute("requestParseTime") + 
					          ",at=" + outDoc.getHeader().getHeaderAttribute("authorisationTime") + 
					          ",pt=" + outDoc.getHeader().getHeaderAttribute("processingTime") + 
					          ",tc=" + outDoc.getHeader().getHeaderAttribute("threadCount") + 
							  ",cpu=" + outDoc.getHeader().getHeaderAttribute("cpuload") + 
							  ",cpt="+postTime+
							  ",cqt="+queueTime+
							  ",qst="+serverTime+
							  ",cta="+threadStatus+
							  ",cwt="+writeTime+ ")" + " (" + sendEncoding + "/" + recvEncoding +
							  
					  ")" );
//			  System.err.println("Thread name: "+Thread.currentThread().getName());
		  }
		  out = null;
//	} finally {
//		temp.delete();
//	}
		  //output.close();

		
//		  ServletOutputStream outputStream = response.getOutputStream();
//		  outputStream.flush();
//		  outputStream.close();
//	}
}

	




@Override
public void run() {
	try {
		this.startedAt = System.currentTimeMillis();
		execute();
	} catch(NavajoDoneException e) {
		System.err.println("NavajoDoneException caught. This thread fired a continuation. Another thread will finish it in the future.");
	} catch (Exception e) {
		e.printStackTrace();
		try {
			response.sendError(500, e.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}


public final void setCommitted(boolean b) {
	this.committed = b;
}


public final boolean isCommitted() {
	return committed;
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
			  e.printStackTrace();
		  }
		  if ( read <= -1) {
			  ready = true;
		  }
	  }
	  try {
		  bout.flush();
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
}




@Override
public void abort() {
	isAborted = true;
}

@Override
public boolean isAborted() {
	return isAborted;
}

public Access getAccess() {
	return access;
}

public void setAccess(Access access) {
	this.access = access;
}




}