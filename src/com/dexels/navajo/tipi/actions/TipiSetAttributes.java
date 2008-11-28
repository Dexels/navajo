package com.dexels.navajo.tipi.actions;

import java.util.*;

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
public final class TipiSetAttributes extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Object o = getEvaluatedParameterValue("path", event);
		if(o==null) {
			throw new TipiException("TipiSetAttributes: Path component missing ");
	
		}
		if(!(o instanceof TipiComponent)) {
			throw new TipiException("TipiSetAttributes: Path component wrong type");
		}
		TipiComponent tc = (TipiComponent)o;
		Set<String> p = getParameterNames();
		for (String name : p) {
			if(name.equals("path")) {
				// Path is reserved
				continue;
			}
			Object oo = getEvaluatedParameterValue(name, event);
			tc.setValue(name, oo);
		}
	}
}
