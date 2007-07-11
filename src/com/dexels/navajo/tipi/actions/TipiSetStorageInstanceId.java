package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
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
public class TipiSetStorageInstanceId extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		String instanceId = (String) evaluate(getParameter("id").getValue(), event).value;
		if (instanceId != null) {
			myContext.getStorageManager().setInstanceId(instanceId);
		} else {
			throw new TipiException("File is NULL!");
		}
	}
}
