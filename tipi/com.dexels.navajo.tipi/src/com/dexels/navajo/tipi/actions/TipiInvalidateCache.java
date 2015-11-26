package com.dexels.navajo.tipi.actions;

import java.io.IOException;

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
public class TipiInvalidateCache extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7104132660717414043L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiInvalidateCache.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		try {
			getContext().getTipiResourceLoader().invalidate();
		} catch (IOException e) {
			logger.error("Error flushing tipi loader: ", e);
		}
		try {
			getContext().getGenericResourceLoader().invalidate();
		} catch (IOException e) {
			logger.error("Error flushing generic loader: ", e);
		}
	}
}