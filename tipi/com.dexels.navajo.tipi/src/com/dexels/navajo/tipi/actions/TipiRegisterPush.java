package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.client.jabber.NavajoClientJabber;
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
public class TipiRegisterPush extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7929307722153364746L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		String agentId = (String) getEvaluatedParameterValue("agentId", event);
		// TODO FIX!
//		boolean bb = myContext.getClient().attemptPushRegistration(agentId);
		boolean bb = NavajoClientJabber.getInstance().attemptPushRegistration(agentId);
		if (!bb) {
			throw new TipiBreakException(TipiBreakException.BREAK_BLOCK);
		}
		System.err.println("Push register attempted!.");
	}
}