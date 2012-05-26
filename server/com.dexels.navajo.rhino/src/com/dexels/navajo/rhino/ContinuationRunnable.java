package com.dexels.navajo.rhino;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;

public class ContinuationRunnable extends BasicRunnable implements TmlRunnable {

	private final Object continuation;
	private final ContinuationPending pending;
	private ScriptEnvironment environment;
	// private long scheduledAt;
	private Access access;
	private RequestQueue requestQueue;
	private final Map<String,Object> attributes = new HashMap<String,Object>();

	private final static Logger logger = LoggerFactory
			.getLogger(ContinuationRunnable.class);
	
	public ContinuationRunnable(ScriptEnvironment se,
			ContinuationPending pending) {
		this.pending = pending;
		this.continuation = pending.getContinuation();
		this.environment = se;
	}

	@Override
	public Navajo getInputNavajo() throws IOException {
		return environment.getAccess().getInDoc();
	}

	@Override
	public void setResponseNavajo(Navajo n) {
		environment.getAccess().setInDoc(n);

	}

	public Object reserialize(Object c) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(c);
			oos.flush();
			oos.close();
			byte[] data = out.toByteArray();
			logger.info("Serialized continuation size: " + data.length);
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(data));
			Object oo = ois.readObject();
			logger.info("Reserialize successful!");
			return oo;
		} catch (IOException e) {
			logger.error("Error: ", e);
		} catch (ClassNotFoundException e) {
			logger.error("Error: ", e);
		}
		return c;
	}

	@Override
	public void run() {
		try {
			continueScript(null);
		} catch (NavajoDoneException e) {
			// TO
			// e.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			logger.info("exiting continuescript");
		}
	}

	public void releaseCurrentThread() {
		logger.info(">>>>>>>>>>>>>>>>>>>Releasing current thread!!");
		throw pending;
	}

	@Override
	public void endTransaction() throws IOException {
		logger.info("Finalizing access...");
		Access access = environment.getAccess();
		// TODO: Make pretty, remove stupid params
		DispatcherFactory.getInstance().finalizeService(access.getInDoc(),
				access, access.getOutputDoc(), access.getRpcName(),
				access.getRpcUser(), null, null, false, false, null);

		TmlRunnable originalRunnable = access.getOriginalRunnable();
		if (originalRunnable != null) {
			logger.info("Relaying endTransaction to original: "
					+ originalRunnable.getClass());
			try {
				logger.info("Writing to output! ");
				originalRunnable.writeOutput(getAccess().getInDoc(),
						getAccess().getOutputDoc());
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			originalRunnable.endTransaction();
		} else {
			logger.info("No original runnable");
		}
	}

	private Access getAccess() {
		return (Access) getAttribute("access");
	}

	public void continueScript(final Object functionResult)
			throws ContinuationPending, NavajoDoneException {
		Context context = Context.enter();
		Header header = environment.getAccess().getOutputDoc().getHeader();
		String threadHistory = header.getHeaderAttribute("threadName");
		if (threadHistory == null) {
			threadHistory = Thread.currentThread().getName();
		} else {
			threadHistory = threadHistory + ";"
					+ Thread.currentThread().getName();
		}
		header.setHeaderAttribute("threadName", threadHistory);
		try {
			environment.setCurrentContext(context);
			context.resumeContinuation(continuation,
					environment.getGlobalScope(), functionResult);
			endTransaction();
			NavajoScopeManager.getInstance().releaseScope(
					environment.getGlobalScope());
			Context.exit();
		} catch (ContinuationPending e) {
			try {
				// Context.exit();
				reschedule(environment, e);
			} catch (IOException e1) {
				logger.info("Continuation: ", e1);
			}
			throw new NavajoDoneException(e);
		} catch (IOException e) {
			logger.error("Error: ", e);
			NavajoScopeManager.getInstance().releaseScope(
					environment.getGlobalScope());
		}
	}

	// if another continuation is captured.
	private void reschedule(ScriptEnvironment env, ContinuationPending e)
			throws IOException {
		logger.info("Multiple continuations:  " + e.hashCode());
		// Thread.dumpStack();
		// SchedulerRegistry.getScheduler().submit(new ContinuationRunnable(env,
		// e.getContinuation()), false);
	}

	@Override
	public String getUrl() {
		return environment.getAccess().getRequestUrl();
	}

	@Override
	public boolean isCommitted() {
		TmlRunnable originalRunnable = environment.getAccess()
				.getOriginalRunnable();
		if (originalRunnable != null) {
			return originalRunnable.isCommitted();
		}

		return true;
	}

	@Override
	public void setCommitted(boolean b) {
		TmlRunnable originalRunnable = environment.getAccess()
				.getOriginalRunnable();
		if (originalRunnable != null) {
			originalRunnable.setCommitted(b);
		}

	}



	public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException,
			FileNotFoundException, UnsupportedEncodingException,
			NavajoException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This runnable is not capable of writing the output, it is not linked to any stream.");
	}

	@Override
	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	@Override
	public void setRequestQueue(RequestQueue rq) {
		this.requestQueue = rq;

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

public void setAccess(Access a) {
	setAttribute("access", a);
}

}
