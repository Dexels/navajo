package com.dexels.navajo.server.listener.http.continuation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.listeners.NavajoDoneException;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.ClientInfo;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.server.listener.http.TmlScheduler;
import com.dexels.navajo.server.listener.http.standard.TmlStandardRunner;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZOutputStream;

public class TmlContinuationRunner extends TmlStandardRunner {

	private final Continuation continuation;
	private Navajo outDoc;
	

	private final static Logger logger = LoggerFactory
			.getLogger(TmlContinuationRunner.class);

	public TmlContinuationRunner(HttpServletRequest request, Navajo inputDoc,
			HttpServletResponse response, String sendEncoding,
			String recvEncoding, Object cert) {
		super(request, inputDoc, response, sendEncoding, recvEncoding, cert);
		connectedAt = System.currentTimeMillis();
		continuation = ContinuationSupport.getContinuation(request);
		continuation.setTimeout(Long.MAX_VALUE);

	}
	
	@Override
	public void abort(String reason) {
		super.abort(reason);
		try {
			logger.warn("Abort: "+reason+" generating outdoc and resuming");
			outDoc = DispatcherFactory.getInstance().generateErrorMessage(null, reason, -1, 0, null);
//			endTransaction();
			resumeContinuation();
		} catch (FatalException e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public void endTransaction() throws IOException {
		try {
			// writeOutput moved from execute to here, as the scheduler thread shouldn't touch the response output stream
			writeOutput(getInputNavajo(), outDoc);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		if ( getRequestQueue() != null ) { // Check whether there is a request queue available.
			getRequestQueue().finished();
		}
		super.endTransaction();
	}

	
	public InputStream getRequestInputStream() throws IOException {
		return request.getInputStream();
	}

	// 
	public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException, UnsupportedEncodingException, NavajoException {
		OutputStream out = null;

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Connection", "close"); // THIS IS NOT SUPPORTED, I.E. IT DOES NOT WORK...EH.. PROBABLY..
		// Should be refactored to a special filter.
		
		
		//System.err.println("Content-Encoding: "+recvEncoding);
//		System.err.println("Accept-Encoding: "+sendEncoding);
	if ( recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {		
		  response.setHeader("Content-Encoding", COMPRESS_JZLIB);
		  out = new ZOutputStream(response.getOutputStream(), JZlib.Z_BEST_SPEED);
	  } else if ( recvEncoding != null && recvEncoding.equals(COMPRESS_GZIP)) {

		  response.setHeader("Content-Encoding", COMPRESS_GZIP);
		  out = new java.util.zip.GZIPOutputStream(response.getOutputStream());
	  }
	  else {
		  //System.err.println("No content encoding specified: (" + sendEncoding + "/" + recvEncoding + ")");
		  out = response.getOutputStream();
	  }
	
	
	  long finishedScriptAt = System.currentTimeMillis();
	  // postTime = 
	  long postTime = scheduledAt - connectedAt;
	  long queueTime = startedAt - scheduledAt;
	  long serverTime = finishedScriptAt - connectedAt;

	  if ( outDoc != null ) {
		  outDoc.getHeader().setHeaderAttribute("postTime", ""+postTime);
		  outDoc.getHeader().setHeaderAttribute("queueTime", ""+queueTime);
		  outDoc.getHeader().setHeaderAttribute("serverTime", ""+serverTime);
		  outDoc.getHeader().setHeaderAttribute("threadName", ""+Thread.currentThread().getName());
	  }
	  
	  TmlScheduler ts = getTmlScheduler();
	  String threadStatus = null;
	  if(ts!=null) {
		  threadStatus = ts.getSchedulingStatus();
	  } else {
		  threadStatus = "Schedule status unknown, no scheduler found.";
	  }
//	  int threadsActive = getActiveCount();
	  //System.err.println("StreamClass: "+out.getClass().getName());
	  //System.err.println("ResponseClass: "+response.getClass().getName());
	  long startWrite = System.currentTimeMillis();
	  if ( outDoc != null ) {
		  outDoc.write(out);
	  } else {
		  System.err.println("WARNING. EMPTY OUTDOC FOUND.");
	  }
//	  out.flush();
	  out.close();
//	  response.getOutputStream().flush();
//	  response.getOutputStream().close();
//	  
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
						  ",cwt="+(System.currentTimeMillis()-startWrite)+")" + " (" + sendEncoding + "/" + recvEncoding +
						  
				  ")" );
	  }

	  

	
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
		  startedAt = System.currentTimeMillis();
		  setCommitted(true);
//		  BufferedReader r = null;
		  try {
			  Navajo in = getInputNavajo();
			  in.getHeader().setHeaderAttribute("useComet", "true");
			  

				  boolean continuationFound = false;
				  try {
					  
					  String sendEncoding = request.getHeader("Accept-Encoding");
				      String recvEncoding = request.getHeader("Content-Encoding");
						
				      TmlRunnable tml = getTmlRunnable();
				      int queueSize = tml.getRequestQueue().getQueueSize();
				      String queueId = tml.getRequestQueue().getId();
				      
					  ClientInfo clientInfo = 	new ClientInfo(request.getRemoteAddr(), "unknown",
								recvEncoding, (int) (scheduledAt - connectedAt), (int) (startedAt - scheduledAt), queueSize, queueId, ( recvEncoding != null && ( recvEncoding.equals(COMPRESS_GZIP) || recvEncoding.equals(COMPRESS_JZLIB))), 
								( sendEncoding != null && ( sendEncoding.equals(COMPRESS_GZIP) || sendEncoding.equals(COMPRESS_JZLIB))), 
								request.getContentLength(), new java.util.Date( connectedAt ) );
					  
					  outDoc = DispatcherFactory.getInstance().removeInternalMessages(DispatcherFactory.getInstance().handle(in, this,cert, clientInfo));
					  // Do do: Support async services in a more elegant way.
				  } catch (NavajoDoneException e) {
					  // temp catch, to be able to pre
					  continuationFound = true;
					  //.println("Navajo done in service runner. Thread disconnected...");
					  throw(e);
				  }
				  finally {
					  if(!continuationFound) {
						  resumeContinuation();
					  }
				  }
//			  }
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
					  continuation.undispatch();
					  throw new ServletException("500.13");
				  }
			  }
			  throw new ServletException(e);
		  } 
	  }



	private void resumeContinuation() {
		continuation.resume();
		
		
	}



	public void suspendContinuation() {
		continuation.suspend();
	}
	


	@Override
	public void run() {
		try {
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

}
