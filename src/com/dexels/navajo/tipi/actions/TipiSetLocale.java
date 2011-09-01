package com.dexels.navajo.tipi.actions;

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
public class TipiSetLocale extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 979757447437751360L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// TODO Add support for multi-servers
		final Operand locale = getEvaluatedParameter("locale", event);
		// final Operand context = getEvaluatedParameter("context", event);
		// myContext.initRemoteDescriptionProvider((String)context.value,
		// (String)locale.value);
		myContext.getClient().setLocaleCode("" + locale.value);
	}
}