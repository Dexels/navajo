package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;

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
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		// TODO Add support for multi-servers
		final Operand locale = getEvaluatedParameter("locale", event);
		// final Operand context = getEvaluatedParameter("context", event);
		// myContext.initRemoteDescriptionProvider((String)context.value,
		// (String)locale.value);
		NavajoClientFactory.getClient().setLocaleCode("" + locale.value);
	}
}