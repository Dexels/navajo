package com.dexels.navajo.tipi.components.swingimpl.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
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
public final class TipiSetCookie extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		Operand nameO = getEvaluatedParameter("name", event);
		Operand valueO = getEvaluatedParameter("value", event);
		if(nameO==null || valueO==null) {
			return;
		}
		String name = (String) nameO.value;
		String value = (String) valueO.value;
	
	}

//	private Cookie createCookie(String s) {
//		Cookie cc = new Cookie(s, "");
//		cc.setPath("/");
//		cc.setMaxAge(60*60*24*365);
//
//		return cc;
//	}
//
//	private Cookie getCookie(String s) {
//		ContainerContext containerContext = (ContainerContext) ApplicationInstance.getActive().getContextProperty(
//				ContainerContext.CONTEXT_PROPERTY_NAME);
//		Cookie[] cc = containerContext.getCookies();
//		for (int i = 0; i < cc.length; i++) {
//			if (cc[i].getName().equals(s)) {
//				return cc[i];
//			}
//		}
//		return null;
//	}
}