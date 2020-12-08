/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
package org.akrogen.tkui.css.core.dom.properties.css2;

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.css.CSSValue;

/**
 * CSS2 Text Property Handler.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 * @see http://www.w3schools.com/css/css_reference.asp#text
 */
public interface ICSSPropertyTextHandler extends ICSSPropertyHandler {

	/**
	 * Sets the color of a text.
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @throws Exception
	 */
	public void applyCSSPropertyColor(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	/**
	 * Controls the letters in an element. Available values are : none
	 * capitalize uppercase lowercase
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @throws Exception
	 */
	public void applyCSSPropertyTextTransform(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	public String retrieveCSSPropertyColor(Object element, CSSEngine engine)
			throws Exception;

	public String retrieveCSSPropertyTextTransform(Object element, CSSEngine engine)
	throws Exception;
}
