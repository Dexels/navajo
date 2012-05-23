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

import org.w3c.dom.css.CSSValue;

/**
 * CSS property interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface CSSProperty {

	/**
	 * Return name of CSS property.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Return {@link CSSValue} value of CSS property.
	 * 
	 * @return
	 */
	public CSSValue getValue();

	/**
	 * Set the {@link CSSValue} value of CSS property.
	 * 
	 * @param value
	 */
	public void setValue(CSSValue value);

	/**
	 * Return true if CSS property is important and false otherwise.
	 * 
	 * @return
	 */
	public boolean isImportant();

	public void setImportant(boolean important);

}
