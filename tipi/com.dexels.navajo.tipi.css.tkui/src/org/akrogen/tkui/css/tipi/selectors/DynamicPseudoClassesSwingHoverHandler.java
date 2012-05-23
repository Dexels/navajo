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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import org.akrogen.tkui.css.core.dom.selectors.IDynamicPseudoClassesHandler;
import org.akrogen.tkui.css.core.engine.CSSElementContext;
import org.akrogen.tkui.css.core.engine.CSSEngine;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * Swing class to manage dynamic pseudo classes handler ...:hover with Swing
 * JComponent.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class DynamicPseudoClassesSwingHoverHandler extends
		AbstractDynamicPseudoClassesJComponentHandler {

	public static final IDynamicPseudoClassesHandler INSTANCE = new DynamicPseudoClassesSwingHoverHandler();

//	private static String MOUSEEXITED_LISTENER = "org.akrogen.tkui.core.css.swing.selectors.MOUSEEXITED_LISTENER";
//
//	private static String MOUSEMOVED_LISTENER = "org.akrogen.tkui.core.css.swing.selectors.MOUSEMOVED_LISTENER";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.swing.selectors.AbstractDynamicPseudoClassesJComponentHandler#intialize(javax.swing.JComponent,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	protected void intialize(final TipiComponent component, final CSSEngine engine) {		
//		MouseAdapter mouseExitedListener = new MouseAdapter() {
//			public void mouseExited(MouseEvent e) {
//				((JComponent)component).putClientProperty("mouseMoved", null);
//				engine.applyStyles(component, false, true);
//			}
//		};
//		MouseMotionAdapter mouseMovedListener = new MouseMotionAdapter() {
//			public void mouseMoved(MouseEvent e) {
//				try {
//					((JComponent)component).putClientProperty("mouseMoved", Boolean.TRUE);
//					engine.applyStyles(component, false, true);
//				} finally {
//					((JComponent)component).putClientProperty("mouseMoved", null);
//				}
//			}
//		};
//		CSSElementContext context = engine.getCSSElementContext(component);
//		context.setData(MOUSEEXITED_LISTENER, mouseExitedListener);
//		context.setData(MOUSEMOVED_LISTENER, mouseMovedListener);
//		component.addMouseListener(mouseExitedListener);
//		component.addMouseMotionListener(mouseMovedListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.swing.selectors.AbstractDynamicPseudoClassesJComponentHandler#dispose(javax.swing.JComponent,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	protected void dispose(TipiComponent component, CSSEngine engine) {
//		CSSElementContext context = engine.getCSSElementContext(component);
//		MouseAdapter mouseExitedListener = (MouseAdapter) context
//				.getData(MOUSEEXITED_LISTENER);
//		if (mouseExitedListener != null)
//			component.removeMouseListener(mouseExitedListener);
//		MouseMotionAdapter mouseMovedListener = (MouseMotionAdapter) context
//				.getData(MOUSEMOVED_LISTENER);
//		if (mouseExitedListener != null)
//			component.removeMouseMotionListener(mouseMovedListener);

//		context.setData(MOUSEEXITED_LISTENER, null);
//		context.setData(MOUSEMOVED_LISTENER, null);
	}
}
