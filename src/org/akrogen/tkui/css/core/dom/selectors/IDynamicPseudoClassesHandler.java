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
package org.akrogen.tkui.css.core.dom.selectors;

import org.akrogen.tkui.css.core.engine.CSSEngine;

/**
 * Interface to manage dynamic pseudo classes handler like (...:focus,
 * ...:hover).
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface IDynamicPseudoClassesHandler {

	/**
	 * Initialize the <code>element</code>. In this method you can add
	 * Listener to the element if it is Swing container, SWT Widget.
	 * 
	 * @param element
	 * @param engine
	 */
	public void intialize(Object element, CSSEngine engine);

	/**
	 * Dispose the <code>element</code>. In this method you can remove
	 * Listener to the element if it is Swing container, SWT Widget.
	 * 
	 * @param element
	 * @param engine
	 */
	public void dispose(Object element, CSSEngine engine);
}
