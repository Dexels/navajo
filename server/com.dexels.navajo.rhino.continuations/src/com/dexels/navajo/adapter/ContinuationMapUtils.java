package com.dexels.navajo.adapter;

import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;

import com.dexels.navajo.rhino.ContinuationRunnable;
import com.dexels.navajo.rhino.ScriptEnvironment;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.server.Access;

public class ContinuationMapUtils {

	public static ContinuationRunnable getContinuation(Access a) {
		ScriptEnvironment se = getScriptEnvironment(a);
		Context cc = Context.getCurrentContext();
		if (cc != null) {
			se.setCurrentContext(cc);
		} else {
			throw new UnsupportedOperationException(
					"This environment does not support continuations. Did you select javascript as compiler language?");
		}
		ContinuationPending continuationPending = cc.captureContinuation();
		if (continuationPending == null) {
			throw new UnsupportedOperationException(
					"This environment does not support continuations. Did you select javascript as compiler language?");
		}
		Context.exit();
		ContinuationRunnable cr = new ContinuationRunnable(se,
				continuationPending);
		cr.setAccess(a);
		return cr;
	}

	protected static ScriptEnvironment getScriptEnvironment(Access a) {
		ScriptEnvironment se = (ScriptEnvironment) a.getScriptEnvironment();
		return se;
	}

	//

	protected static void scheduleAndContinue(ContinuationRunnable continueWith) {
		try {
			SchedulerRegistry.getScheduler().submit(continueWith, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
