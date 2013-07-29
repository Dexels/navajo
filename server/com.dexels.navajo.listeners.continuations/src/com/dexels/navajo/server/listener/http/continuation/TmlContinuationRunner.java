package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;

import javax.servlet.ServletException;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.global.GlobalManager;
import com.dexels.navajo.server.global.GlobalManagerRepositoryFactory;
import com.dexels.navajo.server.listener.http.standard.TmlStandardRunner;

public class TmlContinuationRunner extends TmlStandardRunner {

	private final Continuation continuation;

	private final static Logger logger = LoggerFactory
			.getLogger(TmlContinuationRunner.class);

	public TmlContinuationRunner(AsyncRequest request, LocalClient lc) {
		super(request,lc);
		continuation = ContinuationSupport.getContinuation(request.getHttpRequest());
		continuation.setTimeout(10000000);
	}
	
	@Override
	public void abort(String reason) {
		super.abort(reason);
		try {
			logger.warn("Abort: "+reason+" generating outdoc and resuming");
			setResponseNavajo(getLocalClient().generateAbortMessage(reason));
			resumeContinuation();
		} catch (FatalException e) {
			logger.error("Error: ", e);
		}
	}

	

	@Override
	public void endTransaction() throws IOException {
		TmlScheduler ts = getTmlScheduler();
		String schedulingStatus = null;
		if(ts!=null) {
			schedulingStatus = ts.getSchedulingStatus();
		}
		// Show memory usage.
		long maxMem = 0;
		long usedMem = 0;
		if ( getAttribute("maxmemory") != null && !getAttribute("maxmemory").equals("") ) {
			maxMem = ((Long) getAttribute("maxmemory")) >> 20;
			usedMem = ((Long) getAttribute("usedmemory")) >> 20;
		}
		MDC.put("maxMemory", ""+maxMem);
		MDC.put("usedMemory", ""+usedMem);
		schedulingStatus = schedulingStatus + ", totalmemory=" + maxMem + "Mb, usedmemory=" + usedMem + "Mb";
		getRequest().writeOutput(getInputNavajo(), getResponseNavajo(), scheduledAt, startedAt, schedulingStatus);
		continuation.complete();

		if ( getRequestQueue() != null ) { // Check whether there is a request queue available.
			getRequestQueue().finished();
		}
		super.endTransaction();
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
				      int queueSize = getRequestQueue().getQueueSize();
				      String queueId = getRequestQueue().getId();
					  ClientInfo clientInfo = getRequest().createClientInfo(scheduledAt, startedAt, queueSize, queueId);
					  setResponseNavajo(getLocalClient().handleInternal(getNavajoInstance(), in, getRequest().getCert(), clientInfo));
				  } catch (NavajoDoneException e) {
					  // temp catch, to be able to pre
					  continuationFound = true;
					  //.println("Navajo done in service runner. Thread disconnected...");
					  throw(e);
				  }
				  finally {
					  if(!continuationFound) {
//						  resumeContinuation();
//						  continuation.complete();
						  endTransaction();
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
		if(continuation.isSuspended()) {
			continuation.resume();
		}
	}

	public void suspendContinuation() {
		continuation.suspend();
	}
	


	@Override
	public void run() {
		try {
			String instance = getRequest().getInstance();
			if(instance!=null) {
				GlobalManager gm = GlobalManagerRepositoryFactory.getGlobalManagerInstance().getGlobalManager(instance);
				if(gm!=null) {
					gm.initGlobals(getRequest().getInputDocument());
				} else {
					logger.warn("No global manager found for instance: " + instance);
				}
				
			} else {
				logger.debug("Not using instance based GlobalManager: No instance found in request");
			}
			execute();
		} catch(NavajoDoneException e) {
			logger.debug("NavajoDoneException caught. This thread fired a continuation. Another thread will finish it in the future.");
		} catch (Exception e) {
			logger.error("Continuation problem: ",e);
			getRequest().fail(e);
		}
	}
	
}
