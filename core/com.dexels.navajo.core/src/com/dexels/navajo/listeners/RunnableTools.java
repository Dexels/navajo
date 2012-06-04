package com.dexels.navajo.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;

public class RunnableTools {

	public static TmlRunnable prependRunnable(final TmlRunnable target, final Runnable runn) {
		return mergeRunnable(target, runn, true);
	}

	public static TmlRunnable appendRunnable(final TmlRunnable target, final Runnable runn) {
		return mergeRunnable(target, runn, false);
	}
	
	public static TmlRunnable mergeRunnable(final TmlRunnable target, final Runnable runn, final boolean before) {
		TmlRunnable t = new TmlRunnable() {

			public void run() {
				if(before) {
					runn.run();					
					target.run();
				} else {
					target.run();
					runn.run();					
				}
			}

			public boolean isCommitted() {
				return target.isCommitted();
			}

			public void setCommitted(boolean b) {
				target.setCommitted(b);
			}

			public void setScheduledAt(long currentTimeMillis) {
				target.setScheduledAt(currentTimeMillis);
			}

			public void endTransaction() throws IOException {
				target.endTransaction();
			}

			public Navajo getInputNavajo() throws IOException {
				return target.getInputNavajo();
			}

			public RequestQueue getRequestQueue() {
				return target.getRequestQueue();
			}

			public void setRequestQueue(RequestQueue rq) {
				target.setRequestQueue(rq);
			}

			public boolean isAborted() {
				return target.isAborted();
			}

			public void abort(String reason) {
				target.abort(reason);
			}

			public String getUrl() {
				return target.getUrl();
			}

//			public Access getAccess() {
//				return target.getAccess();
//			}
//
//			public void setAccess(Access access) {
//				target.setAccess(access);
//			}

			public void setResponseNavajo(Navajo n) {
				target.setResponseNavajo(n);

			}

			public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException,
					UnsupportedEncodingException, NavajoException {
				throw new UnsupportedOperationException();
			}

			@Override
			public Object getAttribute(String name) {
				return target.getAttribute(name);
			}

			@Override
			public void setAttribute(String name, Object value) {
				target.setAttribute(name, value);
			}

			@Override
			public Set<String> getAttributeNames() {
				return Collections.unmodifiableSet(target.getAttributeNames());
			}

			
		};
		return t;
	}
}
