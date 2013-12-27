package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.TipiException;
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
public class TipiSetStorageInstanceId extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1697176557470171607L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		String instanceId = (String) evaluate(getParameter("id").getValue(),
				event).value;
		if (instanceId != null) {
			myContext.getStorageManager().setInstanceId(instanceId);
		} else {
			throw new TipiException("File is NULL!");
		}
	}
}
