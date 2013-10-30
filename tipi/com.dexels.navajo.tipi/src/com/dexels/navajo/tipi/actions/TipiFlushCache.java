package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiContext;
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
public class TipiFlushCache extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7104132660717414043L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand contextParam = getEvaluatedParameter("context", event);
		TipiContext context = null;
		if (contextParam != null) {
			context = (TipiContext) contextParam.value;
			if (context == null) {
				context = myContext;
			}

		} else {
			context = myContext;
		}
		context.clearLazyDefinitionCache();

	}
}