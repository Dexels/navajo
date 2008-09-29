package com.dexels.navajo.scheduler.tribe;

import com.dexels.navajo.scheduler.ListenerRunner;
import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;

/**
 * This SmokeSignal class can be used to wake up a ListenerRunner that is waiting on a semaphore notify.
 * 
 * @author arjen
 *
 */
public class ListenerRunnerActivationSmokeSignal extends SmokeSignal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4483796642842303444L;

	public ListenerRunnerActivationSmokeSignal(String sender, String key, Object value) {
		super(sender, key, value);
	}

	@Override
	public void processMessage() {
		if ( !iAmTheSender() ) { // Of course I am already awake, I was the sender stupid.
			// Let's wake it up.
			ListenerRunner.getInstance().activate();
		}
	}

}
