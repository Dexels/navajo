package com.dexels.navajo.listeners;

import com.dexels.navajo.script.api.Scheduler;

/**
 * Class that is used to register the primary service scheduler.
 * 
 * @author arjen
 *
 */
public class SchedulerRegistry {

	private static volatile Scheduler myScheduler;

	public static void setScheduler(Scheduler s) {
		myScheduler = s;
	}

	public static Scheduler getScheduler() {
		if ( myScheduler != null ) {
			return myScheduler;
		} else {
			myScheduler = new SimpleScheduler();
		}
		return myScheduler;
	}
}
