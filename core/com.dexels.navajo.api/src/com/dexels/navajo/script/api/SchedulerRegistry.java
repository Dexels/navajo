package com.dexels.navajo.script.api;

/**
 * Class that is used to register the primary service scheduler.
 * 
 * @author arjen
 *
 */
public class SchedulerRegistry {

	private static volatile Scheduler myScheduler;
	private static volatile Scheduler lowPrioScheduler;

	public static void setScheduler(Scheduler s) {
		myScheduler = s;
	}

	public static Scheduler getScheduler() {
		if ( myScheduler != null ) {
			return myScheduler;
		} else {
			myScheduler = new SimpleScheduler(false);
		}
		return myScheduler;
	}
	
	public static Scheduler getLowPrioScheduler() {
        if ( lowPrioScheduler != null ) {
            return lowPrioScheduler;
        } else {
            lowPrioScheduler = new SimpleScheduler(true);
        }
        return lowPrioScheduler;
    }
}
