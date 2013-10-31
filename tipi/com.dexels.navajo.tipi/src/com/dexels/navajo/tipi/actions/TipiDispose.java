package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
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
public class TipiDispose extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2702919442460647651L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDispose.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		try {
			TipiComponent tp = (TipiComponent) getEvaluatedParameterValue(
					"path", event);
			if (tp != null) {
				myContext.disposeTipiComponent(tp);
			} else {
				logger.warn("ATTEMPTING TO DISPOSE NULL component. ");
			}
		} catch (Exception ex) {
			logger.error("Error: ",ex);
		}
	}
}
