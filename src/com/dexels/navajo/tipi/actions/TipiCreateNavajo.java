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
public class TipiCreateNavajo extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		Operand nameOperand = getEvaluatedParameter("name", event);
		if (nameOperand == null || nameOperand.value == null) {
			throw new TipiException("Error in TipiCreateNavajo action: name parameter missing!");
		}
		String name = (String) nameOperand.value;
		Navajo newNavajo = NavajoFactory.getInstance().createNavajo();
		Header newHeader = NavajoFactory.getInstance().createHeader(newNavajo,name,null,null,-1);
		newNavajo.addHeader(newHeader);
		myContext.addNavajo(name, newNavajo);
		myContext.loadNavajo(newNavajo, name);
	}

}