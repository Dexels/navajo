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
 * CSS2 Classification Property Handler.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 * @see http://www.w3schools.com/css/css_reference.asp#classification
 */
public interface ICSSPropertyClassificationHandler extends ICSSPropertyHandler {

	/**
	 * Sets the sides of an element where other floating elements are not
	 * allowed. Available values are=left,right,both,none
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @return
	 * @throws Exception
	 */
	public void applyCSSPropertyClear(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	/**
	 * Specifies the type of cursor to be displayed. Available values are=url
	 * auto crosshair default pointer move e-resize ne-resize nw-resize n-resize
	 * se-resize sw-resize s-resize w-resize text wait help
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @return
	 * @throws Exception
	 */
	public void applyCSSPropertyCursor(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	/**
	 * Sets how/if an element is displayed. Available values are=none inline
	 * block list-item run-in compact marker table inline-table table-row-group
	 * table-header-group table-footer-group table-row table-column-group
	 * table-column table-cell table-caption
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @throws Exception
	 */
	public void applyCSSPropertyDisplay(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	/**
	 * Sets where an image or a text will appear in another element. Available
	 * values are=left right none
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @throws Exception
	 */
	public void applyCSSPropertyFloat(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	/**
	 * Places an element in a static, relative, absolute or fixed position.
	 * Available values are=static relative absolute fixed
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @throws Exception
	 */
	public void applyCSSPropertyPosition(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	/**
	 * Sets if an element should be visible or invisible. Available values
	 * are=visible hidden collapse
	 * 
	 * @param element
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @throws Exception
	 */
	public void applyCSSPropertyVisibility(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception;

	public String retrieveCSSPropertyClear(Object element, CSSEngine engine)
			throws Exception;

	public String retrieveCSSPropertyCursor(Object element, CSSEngine engine)
			throws Exception;

	public String retrieveCSSPropertyDisplay(Object element, CSSEngine engine)
			throws Exception;

	public String retrieveCSSPropertyFloat(Object element, CSSEngine engine)
			throws Exception;

	public String retrieveCSSPropertyPosition(Object element, CSSEngine engine)
			throws Exception;
	
	public String retrieveCSSPropertyVisibility(Object element, CSSEngine engine)
	throws Exception;	
}
