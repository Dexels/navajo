package com.dexels.navajo.adapter.rhino;

import java.io.IOException;

import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.rhino.ContinuationRunnable;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.UserException;

// A map that will do a 'scheduled' sleep (i.e. sleep without blocking a thread.)
public class SleepMap implements Mappable {

	private Access access;

	@Override
	public void load(Access access) throws MappableException, UserException {
		this.access = access;
	}

	/**
	 * @param method  
	 * @throws IOException 
	 */
	public void setSleep(final String method) throws IOException {
		ContinuationRunnable cr = ContinuationMapUtils.getContinuation(access);
		ContinuationMapUtils.scheduleAndContinue(cr);
		cr.releaseCurrentThread();

	}

	@Override
	public void store() throws MappableException, UserException {

	}

	@Override
	public void kill() {

	}

}
