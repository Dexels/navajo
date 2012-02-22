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
package org.akrogen.tkui.css.core.dom;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.Element;

/**
 * Element provider to retrieve w3c {@link Element} which wrap the native widget
 * (SWT Control, Swing JComponent...).
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface IElementProvider {

	/**
	 * Return the w3c Element which wrap the native widget <code>element</code>
	 * (SWT Control, Swing JComponent). The <code>element</code> can be the
	 * w3c Element.
	 * 
	 * @param element
	 * @param engine
	 * 
	 * @return
	 */
	public Element getElement(Object element, CSSEngine engine);

}
