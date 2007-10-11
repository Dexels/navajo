package com.dexels.navajo.scheduler;

import java.util.Calendar;

public interface ClockListener  {

	/**
	 * Determines whether fired status should be set.
	 * @param c
	 * @return
	 */
	public boolean timetick(Calendar c);
	
	
}
