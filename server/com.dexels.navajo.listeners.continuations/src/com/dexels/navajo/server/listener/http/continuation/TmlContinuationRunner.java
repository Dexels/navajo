package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.global.GlobalManager;
import com.dexels.navajo.server.global.GlobalManagerRepository;
import com.dexels.navajo.server.global.GlobalManagerRepositoryFactory;
import com.dexels.navajo.server.listener.http.standard.TmlStandardRunner;

public class TmlContinuationRunner extends TmlStandardRunner {

    private static final Logger logger = LoggerFactory.getLogger(TmlContinuationRunner.class);

	private static boolean clearThreadLocal;

    private final Continuation continuation;

    public TmlContinuationRunner(AsyncRequest request, LocalClient lc, long timeout) {

        super(request, lc);

        continuation = ContinuationSupport.getContinuation(request.getHttpRequest());

        if (continuation.isExpired()) {
            logger.error("Expired continuation at request start!");
            abort("Internal server error");
        } else if (!continuation.isInitial()) {
            logger.error("Non-initial continuation at request start!");
            abort("Internal server error");
        } else {
            continuation.setTimeout(timeout);
            continuation.addContinuationListener(new ContinuationListener() {

                @Override
                public void onTimeout(Continuation continuation) {
                    abort("timeout after: " + timeout);
                }

                @Override
                public void onComplete(Continuation continuation) {}
            });
        }
    }

    @Override
    public void abort(String reason) {

        super.abort(reason);
        try {
            logger.warn("Aborting: {}. Generating outdoc and resuming", reason);
            setResponseNavajo(getLocalClient().generateAbortMessage(reason));
        } catch (FatalException e) {
            logger.error("Error: ", e);
        }
        continuation.complete();
        getRequest().fail(new ServletException(reason));
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
		  } finally {
			  MDC.clear();
		  }
	  }

	@SuppressWarnings("unused")
    private void resumeContinuation() {
		if(continuation.isSuspended()) {
			continuation.resume();
		}
	}

	public void suspendContinuation() {
		continuation.suspend();
	}

    public void suspendContinuation(HttpServletResponse resp) {
        continuation.suspend(resp);
    }


	@Override
	public void run() {
		try {
			final GlobalManagerRepository globalManagerInstance = GlobalManagerRepositoryFactory.getGlobalManagerInstance();
			if(globalManagerInstance == null) {
				logger.warn("No global manager found");
			}
			clearThreadLocal(true);
			String instance = getRequest().getInstance();
			if(instance!=null && globalManagerInstance!=null) {

				GlobalManager gm = globalManagerInstance.getGlobalManager(instance);
				if(gm!=null) {
					gm.initGlobals(getRequest().getInputDocument());
				} else {
					logger.warn("No global manager found for instance: " + instance);
				}

			} else {
				logger.debug("Not using instance based GlobalManager: No instance found in request");
			}
			execute();
		} catch (Exception e) {
			logger.error("Continuation problem: ",e);
			getRequest().fail(e);
		}
	}

	public static boolean clearThreadLocal() {
	    return clearThreadLocal;
	}

	private static void clearThreadLocal(boolean clear) {
	    clearThreadLocal = clear;
    }


}
