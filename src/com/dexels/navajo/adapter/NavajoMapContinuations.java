package com.dexels.navajo.adapter;

import java.io.IOException;

import com.dexels.navajo.client.async.AsyncClient;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.listeners.NavajoResponseCallback;
import com.dexels.navajo.listeners.RunnableTools;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.rhino.ContinuationRunnable;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class NavajoMapContinuations extends NavajoMapUpdated {

	@Override
	public void setDoSend(final String method) throws UserException,
			ConditionErrorException, SystemException, AuthorizationException {
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
			super.run();
		}
	}

	protected void scheduleExternalNavajoCall(String method, TmlRunnable on2) {
		try {
			System.err
					.println("Calling navajoMapContinuations. Using currentOutdoc: "
							+ useCurrentOutDoc + "\nresolved out doc;");
			outDoc.write(System.err);
			if (useCurrentOutDoc) {
				this.outDoc = access.getOutputDoc().copy();
			} else {
				this.outDoc = access.getInDoc().copy();
			}
			System.err
					.println("STARTING MASSIVE DEBUG=========================");
			System.err.println("Access indoc:");
			access.getInDoc().write(System.err);
			System.err.println("Access outdoc:");
			access.getOutputDoc().write(System.err);
			System.err.println("Map outdoc:");
			this.outDoc.write(System.err);

			System.err.println("ENDING MASSIVE DEBUG=========================");
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

			AsyncClient.getInstance().callService(this.access, outDoc, method,
					on2, null, new NavajoResponseCallback() {

						@Override
						public void responseReceived(Navajo n) {
							setResponseNavajo(n);
							System.err
									.println("STARTING MASSIVE DEBUG=========================");
							System.err.println("Map indoc:");
							try {
								NavajoMapContinuations.this.inDoc
										.write(System.err);
							} catch (NavajoException e) {
								e.printStackTrace();
							}
							System.err
									.println("ENDING MASSIVE DEBUG=========================");

						}
					});
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (NavajoException e2) {
			e2.printStackTrace();
		}
	}

}
