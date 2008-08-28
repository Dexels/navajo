package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.lang.reflect.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import javax.swing.*;
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
public final class TipiRequestFocus extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		// NOT IN USE
		Operand component = getEvaluatedParameter("path", event);
		if(component==null) {
			return;
		}
		TipiComponent tt = (TipiComponent)component.value;
		if(tt==null) {
			throw new TipiException("Error: No such component: "+getParameter("path"));
		}
		Object container = tt.getContainer();

		if(container instanceof JComponent) {
			final JComponent cc = (JComponent)container;
			try {
				SwingUtilities.invokeAndWait(new Runnable(){

					public void run() {
						cc.requestFocusInWindow();
						
					}});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			throw new TipiException("Error: No swing component: "+getParameter("path"));
			
		}
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