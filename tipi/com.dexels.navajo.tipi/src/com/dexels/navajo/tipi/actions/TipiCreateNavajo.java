package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
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
public class TipiCreateNavajo extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4373186598132544364L;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Operand nameOperand = getEvaluatedParameter("name", event);
		if (nameOperand == null || nameOperand.value == null) {
			throw new TipiException(
					"Error in TipiCreateNavajo action: name parameter missing!");
		}
		String name = (String) nameOperand.value;
		Navajo newNavajo = NavajoFactory.getInstance().createNavajo();
		Header newHeader = NavajoFactory.getInstance().createHeader(newNavajo,
				name, null, null, -1);
		newNavajo.addHeader(newHeader);
		myContext.addNavajo(name, newNavajo);
		myContext.loadNavajo(newNavajo, name);
	}

}