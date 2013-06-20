package com.dexels.navajo.server.listener.http.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.script.api.TmlScheduler;

public abstract class BaseServiceRunner  implements
		TmlRunnable {

	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
	public static final String COMPRESS_NONE = "";

	private final static Logger logger = LoggerFactory
			.getLogger(BaseServiceRunner.class);

	// private InputStream is;
	private boolean committed = false;
	private boolean isAborted = false;
	private final Map<String, Object> attributes = new HashMap<String, Object>();
	private Navajo responseNavajo = null;
	// public void setResponse(HttpServletResponse response) {
	// this.response = response;
	// }


	protected long scheduledAt = -1;
	protected long startedAt = -1;
	// private TmlScheduler tmlScheduler;
	private RequestQueue myQueue;
	private final AsyncRequest myRequest;
	private final LocalClient localClient;
	
	public BaseServiceRunner(AsyncRequest asyncRequest, LocalClient lc) {
		if(asyncRequest==null) {
			throw new RuntimeException("Boom! Null asyncrequest?!");
		}
		this.myRequest = asyncRequest;
		this.localClient = lc;
		// this.event = event;
	}

	public LocalClient getLocalClient() {
		return this.localClient;
	}
	
	public long getStartedAt() {
		return startedAt;
	}

	
	public final void setScheduledAt(long scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public final long getScheduledAt() {
		return this.scheduledAt;
	}

	public final TmlScheduler getTmlScheduler() {
		if (myQueue == null) {
			return null;
		}
		return (TmlScheduler) myQueue.getTmlScheduler();
	}

	public RequestQueue getRequestQueue() {
		return myQueue;
	}

	public void setRequestQueue(RequestQueue myQueue) {
		this.myQueue = myQueue;
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

		// BufferedReader r = null;
		try {
			Navajo in = getInputNavajo();
			in.getHeader().setHeaderAttribute("useComet", "true");

			// Check the availability of the resource...
			// ServiceAvailability sa =
			// ResourceCheckerManager.getInstance().getResourceChecker(in.getHeader().getRPCName(),
			// in).getServiceAvailability();

			if (in.getHeader().getHeaderAttribute("callback") != null) {

				String callback = in.getHeader().getHeaderAttribute("callback");

				try {
					Navajo callbackNavajo = getLocalClient().handleCallback(getNavajoInstance(), in, callback);
					writeOutput(in, callbackNavajo);

				} finally {
					endTransaction();
				}

			} else {
				boolean continuationFound = false;
				try {

					String queueId = myQueue.getId();
					int queueLength = myQueue.getQueueSize();

					ClientInfo clientInfo = getRequest().createClientInfo(
							scheduledAt, startedAt, queueLength, queueId);
					Navajo outDoc = getLocalClient().handleInternal(getNavajoInstance(), in, getRequest().getCert(), clientInfo);
					// Do do: Support async services in a more elegant way.
					if (!isAborted()) {
						writeOutput(in, outDoc);
					} else {
						logger.warn("Aborted: Can't write output!");
					}
				} catch (NavajoDoneException e) {
					// temp catch, to be able to pre
					continuationFound = true;
					logger.info("Navajo done in service runner. Thread disconnected...");
					throw (e);
				} finally {
					if (!continuationFound) {
						// this will be the responsibility of the next thread,
						// who will finish the continuation.
						endTransaction();
					}
				}
			}
		} catch (NavajoDoneException e) {
			throw (e);
		} catch (Throwable e) {
			if (e instanceof FatalException) {
				FatalException fe = (FatalException) e;
				if (fe.getMessage().equals("500.13")) {
					// Server too busy.
					throw new ServletException("500.13");
				}
			}
			throw new ServletException(e);
		}
	}

	public AsyncRequest getRequest() {
		return myRequest;
	}

	@Override
	public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException,
			FileNotFoundException, UnsupportedEncodingException,
			NavajoException {
		System.err.println("BSR: --");
		TmlScheduler ts = getTmlScheduler();
		String schedulingStatus = null;
		if (ts != null) {
			schedulingStatus = ts.getSchedulingStatus();
		}
		getRequest().writeOutput(inDoc, outDoc, scheduledAt, startedAt,
				schedulingStatus);

	}

	@Override
	public void run() {
		try {
			this.startedAt = System.currentTimeMillis();
			execute();
		} catch (NavajoDoneException e) {
			logger.info("NavajoDoneException caught. This thread fired a continuation. Another thread will finish it in the future.");
		} catch (Exception e) {
			getRequest().fail(e);
		}
	}

	public final void setCommitted(boolean b) {
		this.committed = b;
	}

	public final boolean isCommitted() {
		return committed;
	}



	@Override
	public void abort(String reason) {
		isAborted = true;
	}

	@Override
	public boolean isAborted() {
		return isAborted;
	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	@Override
	public Set<String> getAttributeNames() {
		return Collections.unmodifiableSet(attributes.keySet());
	}
	@Override
	public void setResponseNavajo(Navajo n) {
		responseNavajo = n;

	}

	public Navajo getResponseNavajo() {
		return responseNavajo;
	}
	
	@Override
	public String getNavajoInstance() {
		return this.myRequest.getInstance();
	}

}