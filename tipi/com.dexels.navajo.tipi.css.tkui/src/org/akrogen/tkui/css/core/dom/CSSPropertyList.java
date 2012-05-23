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

/**
 * CSS property list interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface CSSPropertyList {

	/**
	 * Return length of CSS property list.
	 * 
	 * @return
	 */
	public abstract int getLength();

	/**
	 * Return the CSS property {@link CSSProperty} at item <code>i</code>.
	 * 
	 * @param i
	 * @return
	 */
	public abstract CSSProperty item(int i);
}
