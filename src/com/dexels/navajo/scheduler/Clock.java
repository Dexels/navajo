package com.dexels.navajo.scheduler;

import java.util.Calendar;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.scheduler.ClockInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.tribe.TribeManager;
import com.dexels.navajo.util.AuditLog;

public class Clock extends GenericThread implements ClockMXBean, ClockInterface {

	private static volatile Clock instance = null;
	private static Object semaphore = new Object();
	private static String id = "Navajo Clock";
	public static final String VERSION = "$Id$";
	
	private static final int CLOCK_RESOLUTION = 1000;
	
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
			JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, "Clock");
			instance.startThread(instance);

			AuditLog.log(AuditLog.AUDIT_MESSAGE_TASK_SCHEDULER, "Started clock process $Id$");
			return instance;
		}
	}
	
	public final void addClockListener(TimeTrigger cl) {
		synchronized ( semaphore ) {
			ListenerStore.getInstance().addListener(cl,TimeTrigger.class.getName(), false);
		}
	}
	
	public final void removeClockListener(TimeTrigger cl) {
		synchronized ( semaphore ) {
			ListenerStore.getInstance().removeListener(cl,TimeTrigger.class.getName(), false);
		}
	}
	
	/**
	 * Worker checks whether timetriggers should be set to 'fired'.
	 */
	public final void worker() {

		// Only the tribe chief may perform a clock operation.
		if ( TribeManager.getInstance().getIsChief() ) {
			Calendar c = Calendar.getInstance();
			synchronized ( semaphore ) {
				//System.err.println("Calling Clock worker()");

				Listener [] all = ListenerStore.getInstance().getListeners(TimeTrigger.class.getName());
				for (int i = 0; i < all.length; i++) {
					TimeTrigger cl = (TimeTrigger) all[i];
					//System.err.println("Got TimeTrigger: " + cl.getDescription() );
					// Set fired status if appropriate.
					if ( cl.timetick(c) ) {
						System.err.println(i + ": >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ACTIVATING!" + cl.getDescription() + ", task:" + cl.getTask().getWebservice() );
						ListenerStore.getInstance().activate(cl, TimeTrigger.class.getName());
						if ( cl.isSingleEvent() ) { // Remove blue print also, do not wait for lock!
							ListenerStore.getInstance().removeListener(cl, TimeTrigger.class.getName(), true);
						}
					}
				}
			}

		}
	}

	public int getResolution() {
		return CLOCK_RESOLUTION;
	}

	public int getListeners() {
		return 0;//listeners.size();
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
