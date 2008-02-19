package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
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
public class TipiDeclareAlias extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		final String value = getParameter("value").getValue();
		final Operand name = getEvaluatedParameter("name", event);
		
		if(name==null || value==null || name.value ==null ) {
			throw new TipiException("Null values detected while declaring alias");
		}
//		super.getComponent().
		getComponent().setAlias((String)name.value, value);
	}
}