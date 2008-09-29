package com.dexels.navajo.scheduler.triggers;

import com.dexels.navajo.document.Navajo;


public abstract class WebserviceTrigger extends Trigger {

	public abstract Navajo perform();
	
}
