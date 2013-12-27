package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
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
public class TipiDebugOutput extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1427497523393178499L;

	private final static Logger logger = LoggerFactory
		.getLogger(TipiDebugOutput.class);

	public TipiDebugOutput() {
	}

	@Override
	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		Operand value = getEvaluatedParameter("value", event);
		logger.info("******** DEBUG *********");
		if (value != null) {
			logger.info("VALUE: >" + value.value + "<");
			logger.info("TYPE: >" + value.type + "<");
		} else {
			logger.info("NULL EVALUATION!");
		}
		logger.info("******** END *********");
	}
}
