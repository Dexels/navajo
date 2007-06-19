package com.dexels.navajo.scheduler;

import java.util.ArrayList;
import java.util.Calendar;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.scheduler.ClockInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class Clock extends GenericThread implements ClockMXBean, ClockInterface {

	private static volatile Clock instance = null;
	private static Object semaphore = new Object();
	private final ArrayList listeners = new ArrayList();
	private static String id = "Navajo Clock";
	
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
	
	public final void addClockListener(ClockListener cl) {
		synchronized ( semaphore ) {
			listeners.add(cl);
		}
	}
	
	public final void removeClockListener(ClockListener cl) {
		synchronized ( semaphore ) {
			listeners.remove(cl);
		}
	}
	
	public final void worker() {
		Calendar c = Calendar.getInstance();
		synchronized ( semaphore ) {
			for ( int i = 0; i < listeners.size(); i++ ) {
				ClockListener cl = (ClockListener) listeners.get(i);
				cl.timetick(c);
			}
		}
	}

	public int getResolution() {
		return CLOCK_RESOLUTION;
	}

	public int getListeners() {
		return listeners.size();
	}
}
