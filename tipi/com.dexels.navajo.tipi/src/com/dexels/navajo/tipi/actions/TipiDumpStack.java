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
public class TipiDumpStack extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3731646268988692805L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDumpStack.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		logger.error("JAVA SAYS:");
		Thread.dumpStack();
		logger.error("TIPI SAYS:");
		dumpStack("Dumpstack");

		logger.error("Event params: ");
		for (String s : event.getEventKeySet()) {
			logger.error("Param: " + s + " value: "
					+ event.getEventParameter(s));

		}
	}

}