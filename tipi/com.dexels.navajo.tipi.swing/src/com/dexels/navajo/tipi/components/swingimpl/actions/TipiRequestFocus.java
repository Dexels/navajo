/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiComponent;
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
public final class TipiRequestFocus extends TipiAction {

	private static final long serialVersionUID = 7937488763201435563L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiRequestFocus.class);
	
	@Override
	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		// NOT IN USE
		Operand component = getEvaluatedParameter("path", event);
		if (component == null) {
			return;
		}
		TipiComponent tt = (TipiComponent) component.value;
		if (tt == null) {
			throw new TipiException("Error: No such component: "
					+ getParameter("path"));
		}
		Object container = tt.getContainer();

		if (container instanceof JComponent) {
			final JComponent cc = (JComponent) container;
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					@Override
					public void run() {
						cc.requestFocusInWindow();

					}
				});
			} catch (InterruptedException e) {
				logger.error("Error detected",e);
			} catch (InvocationTargetException e) {
				logger.error("Error detected",e);
			}
		} else {
			throw new TipiException("Error: No swing component: "
					+ getParameter("path"));

		}
	}

	// private Cookie createCookie(String s) {
	// Cookie cc = new Cookie(s, "");
	// cc.setPath("/");
	// cc.setMaxAge(60*60*24*365);
	//
	// return cc;
	// }
	//
	// private Cookie getCookie(String s) {
	// ContainerContext containerContext = (ContainerContext)
	// ApplicationInstance.getActive().getContextProperty(
	// ContainerContext.CONTEXT_PROPERTY_NAME);
	// Cookie[] cc = containerContext.getCookies();
	// for (int i = 0; i < cc.length; i++) {
	// if (cc[i].getName().equals(s)) {
	// return cc[i];
	// }
	// }
	// return null;
	// }
}