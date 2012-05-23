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


import org.akrogen.tkui.css.core.dom.selectors.IDynamicPseudoClassesHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * Abstract Swing class to manage dynamic pseudo classes handler like
 * (...:focus, ...:hover) with Swing JComponent.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractDynamicPseudoClassesJComponentHandler implements
		IDynamicPseudoClassesHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.selectors.IDynamicPseudoClassesHandler#intialize(java.lang.Object,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void intialize(final Object element, final CSSEngine engine) {
		final TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component == null)
			return;
		intialize(component, engine);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.selectors.IDynamicPseudoClassesHandler#dispose(java.lang.Object,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void dispose(Object element, CSSEngine engine) {
		final TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component == null)
			return;
		dispose(component, engine);
	}

	/**
	 * Initialize the JComponent <code>component</code>. In this method you
	 * can add Swing Listener to the component.
	 * 
	 * @param component
	 * @param engine
	 */
	protected abstract void intialize(TipiComponent component, CSSEngine engine);

	/**
	 * Dispose the JComponent <code>component</code>. In this method you can
	 * remove Swing Listener to the component.
	 * 
	 * @param component
	 * @param engine
	 */
	protected abstract void dispose(TipiComponent component, CSSEngine engine);

}
