package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;


public abstract class WebserviceTrigger extends Trigger {

	public abstract Navajo perform();
	
}
