package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiSleep extends TipiAction {

	private static final long serialVersionUID = 4260115036720073332L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSleep.class);
	
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		int sleep = (Integer) getEvaluatedParameter("time", event).value;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			logger.error("Error: ",e);
		}
		logger.info("Slept for: " + sleep + " millis!");
	}

}