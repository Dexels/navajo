package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.TipiBreakException;
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
public class TipiBreakEvent extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3933846694102296515L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		throw new TipiBreakException(TipiBreakException.BREAK_EVENT);
	}
}