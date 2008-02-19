package com.dexels.navajo.tipi.actions;

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
public class TipiFlushCache extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		myContext.clearLazyDefinitionCache();
		try {
			myContext.getStateNavajo().write(System.err);
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}