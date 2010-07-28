package com.dexels.navajo.adapter;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.rhino.ContinuationHandler;
import com.dexels.navajo.rhino.ScriptEnvironment;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class NavajoMapContinuations extends NavajoMap {

	@Override
	public void setDoSend(final String method) throws UserException, ConditionErrorException, SystemException, AuthorizationException {
		// magically acquire ScriptEnvironment (from Access?)
		

		ScriptEnvironment se = (ScriptEnvironment) this.access.getScriptEnvironment();
		if(se==null) {
			System.err.println("No rhino environment found. Going old skool.");
			super.setDoSend(method);
			return;
		} else {

			System.err.println("DOSEND WITH INP DOC: "+outDoc.hashCode());
			System.err.println("Entering new style navajomap. Using outputDoc: ");
			try {
				outDoc.write(System.err);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
			final ContinuationPending cp = se.getCurrentContext().captureContinuation();
			final Object c = cp.getContinuation();
			Context.exit();
			System.err.println("Thread free! Actually calling doSend");
 			super.setDoSend(method);
			System.err.println("Script finished without problems.");
			se.continueScript(c, null);
		}
	
		System.err.println("I should not be here!");
	}

}
