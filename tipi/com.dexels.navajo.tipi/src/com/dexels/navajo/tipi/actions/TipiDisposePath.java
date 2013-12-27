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
 * @deprecated
 * @author not attributable
 * @version 1.0
 */
@Deprecated
public class TipiDisposePath extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1778781089671484309L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDisposePath.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		try {
			String pathVal = (String) getEvaluatedParameter("path", event).value;
			TipiComponent tp = myContext.getTipiComponentByPath(pathVal);
			if (tp != null) {
				myContext.disposeTipiComponent(tp);
			} else {
				logger.warn("ATTEMPTING TO DISPOSE NULL component. ");
			}
			// path);
		} catch (Exception ex) {
			logger.error("Error: ",ex);
		}
	}
}
