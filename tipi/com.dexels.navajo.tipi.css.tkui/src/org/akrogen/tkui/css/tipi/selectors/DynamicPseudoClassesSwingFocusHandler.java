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
package org.akrogen.tkui.css.tipi.selectors;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import org.akrogen.tkui.css.core.dom.selectors.IDynamicPseudoClassesHandler;
import org.akrogen.tkui.css.core.engine.CSSElementContext;
import org.akrogen.tkui.css.core.engine.CSSEngine;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * Swing class to manage dynamic pseudo classes handler ...:focus with Swing
 * JComponent.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class DynamicPseudoClassesSwingFocusHandler extends
		AbstractDynamicPseudoClassesJComponentHandler {

	public static final IDynamicPseudoClassesHandler INSTANCE = new DynamicPseudoClassesSwingFocusHandler();

	private static String FOCUS_LISTENER = "org.akrogen.tkui.core.css.swing.selectors.FOCUS_LISTENER";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.swing.selectors.AbstractDynamicPseudoClassesJComponentHandler#intialize(javax.swing.JComponent,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void intialize(final TipiComponent component, final CSSEngine engine) {
		FocusListener focusListener = new FocusListener() {
			public void focusGained(FocusEvent e) {
				engine.applyStyles(component, false, true);
			}

			public void focusLost(FocusEvent e) {
				engine.applyStyles(component, false, true);
			}
		};
		CSSElementContext context = engine.getCSSElementContext(component);
		context.setData(FOCUS_LISTENER, focusListener);
//		component.addFocusListener(focusListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.swing.selectors.AbstractDynamicPseudoClassesJComponentHandler#dispose(javax.swing.JComponent,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void dispose(final TipiComponent component, final CSSEngine engine) {
		CSSElementContext context = engine.getCSSElementContext(component);
//		FocusListener focusListener = (FocusListener) context
//				.getData(FOCUS_LISTENER);
//		if (focusListener != null)
//			component.removeFocusListener(focusListener);
		context.setData(FOCUS_LISTENER, null);
	}

}
