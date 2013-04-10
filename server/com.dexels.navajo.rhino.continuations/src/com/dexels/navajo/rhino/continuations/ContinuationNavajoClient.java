package com.dexels.navajo.rhino.continuations;

import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.client.async.AsyncClientFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.rhino.ScriptEnvironment;

public class ContinuationNavajoClient {

	private final static Logger logger = LoggerFactory
			.getLogger(ContinuationNavajoClient.class);

	public void callService(final String service, Navajo n,
			final ScriptEnvironment se) throws IOException, NavajoException,
			ContinuationPending {

		try {
			Context context = Context.enter();
			final ContinuationPending cp = context.captureContinuation();
			final Object c = cp.getContinuation();

			final Object continuation = c;// = reserialize(c);
			NavajoResponseHandler nrh = new NavajoResponseHandler() {
				@Override
				public void onResponse(Navajo n) {
					System.err.println("Result received");
					se.callFinished(service, n);
					se.continueScript(continuation, n);
				}

				@Override
				public void onFail(Throwable t) throws IOException {
					throw new IOException("Navajo failed.", t);
				}
			};
			logger.info("Calling server: " + getClient().getServer());
			getClient().callService(n, service, nrh);
			// System.err.println("Freezing!");
			throw (cp);
		} finally {
			Context.exit();
		}
	}

	public ManualAsyncClient getClient() {
		return AsyncClientFactory.getManualInstance();
	}
}
