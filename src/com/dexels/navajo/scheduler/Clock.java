package com.dexels.navajo.scheduler;

import java.util.Calendar;

import com.dexels.navajo.scheduler.triggers.TimeTrigger;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.scheduler.ClockInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

/**
 * A Singleton Class that is used to implement a global Clock.
 * Clock can be used to register Trigger objects that use time as a trigger.
 * 
 * @author arjen
 *
 */
public class Clock extends GenericThread implements ClockMXBean, ClockInterface {

	private static volatile Clock instance = null;
	private static Object semaphore = new Object();
	private static String id = "Navajo Clock";
	public static final String VERSION = "$Id$";
	
	private static final int CLOCK_RESOLUTION = 500;
	
	public Clock() {
		super(id);
		setSleepTime(CLOCK_RESOLUTION);
	}
	
	public static Clock getInstance() {

		if (instance!=null) {
			return instance;
		}

		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			}

			instance = new Clock();	
			try {
			JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, "Clock");
			} catch (Throwable t) {}
			instance.startThread(instance);

			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started clock process $Id$");
			return instance;
		}
	}
	
	public final void addClockListener(TimeTrigger cl) {
		synchronized ( semaphore ) {
			ListenerStore.getInstance().addListener(cl);
		}
	}
	
	public final void removeClockListener(TimeTrigger cl) {
		synchronized ( semaphore ) {
			ListenerStore.getInstance().removeListener(cl);
		}
	}
	
	/**
	 * Worker checks whether timetriggers should be set to 'fired' and 'activates' those timetriggers.
	 * 
	 */
	public final void worker() {

		// Only the tribe chief may perform a clock operation.
		if ( TribeManagerFactory.getInstance().getIsChief() ) {
			Calendar c = Calendar.getInstance();
			//synchronized ( semaphore ) {
				//System.err.println("Calling Clock worker()");

				Listener [] all = ListenerStore.getInstance().getListeners(TimeTrigger.class.getName());
				for (int i = 0; i < all.length; i++) {
					TimeTrigger cl = (TimeTrigger) all[i];
					//System.err.println("Got TimeTrigger: " + cl.getDescription() );
					// Set fired status if appropriate.
					if ( cl.timetick(c) ) {
						ListenerStore.getInstance().activate(cl);
						if ( cl.isSingleEvent() ) { // Remove blue print also, do not wait for lock!
							ListenerStore.getInstance().removeListener(cl);
						}
					}
				}
			//}
		}
	}

	public int getResolution() {
		return CLOCK_RESOLUTION;
	}

	public int getListeners() {
		return 0;
	}
	
	public String getId() {
		return id;
	}
	
	public void terminate() {
		try {
			JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
		} catch (Throwable e) {
		}
	}

	public String getVERSION() {
		return VERSION;
	}
}
