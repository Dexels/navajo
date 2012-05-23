/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.tipi.engine;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ContainerEvent;

import org.akrogen.tkui.css.core.engine.CSSEngine;

/**
 * AWTEventListener listener which listen ContainerEvent.COMPONENT_ADDED event
 * (fired when Swing Component is added) to apply CSS Style with the CSS Engine
 * (stored into ThreadLocale) as soon as the Swing Component is added.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSSwingApplyStylesListener implements AWTEventListener {

	private static CSSSwingApplyStylesListener listener = null;

	private CSSSwingApplyStylesListener() {
	}

	public void eventDispatched(AWTEvent evt) {
		if (evt instanceof ContainerEvent) {
			ContainerEvent cevt = (ContainerEvent) evt;
			switch (cevt.getID()) {
			case ContainerEvent.COMPONENT_ADDED:
				// Get current CSS Engine
				CSSEngine engine = CSSTipiEngineImpl.getCurrentCSSEngine();
				if (engine != null) {
					// Apply style with CSS Engine
					Component component = cevt.getChild();
					engine.applyStyles(component, false);
				}
				break;
			}
		}
	}

	/**
	 * Start CSS Swing ApplyStyles Listener.
	 */
	public static void start() {
		if (listener == null) {
			listener = new CSSSwingApplyStylesListener();
			Toolkit tk = Toolkit.getDefaultToolkit();
			tk.addAWTEventListener(listener, AWTEvent.CONTAINER_EVENT_MASK);
		}
	}
}
