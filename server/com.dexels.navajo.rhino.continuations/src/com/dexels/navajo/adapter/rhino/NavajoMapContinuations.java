package com.dexels.navajo.adapter.rhino;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.NavajoMapUpdated;
import com.dexels.navajo.client.async.AsyncClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.rhino.ContinuationRunnable;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.RunnableTools;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.script.api.UserException;

public class NavajoMapContinuations extends NavajoMapUpdated {

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoMapContinuations.class);
	
	@Override
	public void setDoSend(final String method) throws UserException,
			SystemException {
		// magically acquire ScriptEnvironment (from Access?)
		setBlock(false);
		setForceInline(true);
		this.method = method;
		ContinuationRunnable cr = ContinuationMapUtils.getContinuation(access);
		scheduleAndContinue(method, cr);
		cr.releaseCurrentThread();
	}

	protected void scheduleAndContinue(String method, TmlRunnable onSuccess) {
		prepareSend(method);
		TmlRunnable on2 = RunnableTools.prependRunnable(onSuccess,
				new Runnable() {
					@Override
					public void run() {
						continueAfterRun();
					}
				});

		// ----

		// ----
		if (this.getUrl() != null) {
			scheduleExternalNavajoCall(method, on2);
		} else {
			Thread t = new Thread() {
				@Override
				public void run() {
					NavajoMapContinuations.this.run();
				}
			};
			t.start();
		}
	}

	protected void scheduleExternalNavajoCall(String method, TmlRunnable on2) {
		try {
			logger.info("Calling navajoMapContinuations. Using currentOutdoc: "
							+ useCurrentOutDoc + "\nresolved out doc;");
//			outDoc.write(System.err);
			if (useCurrentOutDoc) {
				this.outDoc = access.getOutputDoc().copy();
			} else {
				this.outDoc = access.getInDoc().copy();
			}
			// Copy param messages.
			if (inMessage.getMessage("__parms__") != null) {
				Message params = inMessage.getMessage("__parms__").copy(outDoc);
				outDoc.addMessage(params);
			}
			if (inMessage.getMessage("__globals__") != null) {
				Message globals = inMessage.getMessage("__globals__").copy(
						outDoc);
				outDoc.addMessage(globals);
			}

			AsyncClientFactory.getManualInstance().callService(this.access, outDoc, method,
					on2, null, new NavajoResponseCallback() {

						@Override
						public void responseReceived(Navajo n) {
							setResponseNavajo(n);
//							System.err
//									.println("STARTING MASSIVE DEBUG=========================");
//							System.err.println("Map indoc:");
//							try {
//								NavajoMapContinuations.this.inDoc
//										.write(System.err);
//							} catch (NavajoException e) {
//								e.printStackTrace();
//							}
//							System.err
//									.println("ENDING MASSIVE DEBUG=========================");

						}
					});
		} catch (IOException e2) {
			logger.error("Error: ", e2);
		}
	}

}
