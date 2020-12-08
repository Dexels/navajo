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
package org.akrogen.tkui.css.xml.properties.css2;

import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyTextHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyTextHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSPropertyTextXMLHandler extends AbstractCSSPropertyTextHandler {

	public final static ICSSPropertyTextHandler INSTANCE = new CSSPropertyTextXMLHandler();

	public boolean applyCSSProperty(Object node, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (node instanceof Element) {
			super.applyCSSProperty((Element) node, property, value, pseudo,
					engine);
			return true;
		}
		return false;
	}

	public void applyCSSPropertyColor(Object node, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			// Add color attribute
			Element element = (Element) node;
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			element.setAttribute("color", primitiveValue.getStringValue());
		}
	}

	public String retrieveCSSPropertyColor(Object node, CSSEngine engine)
			throws Exception {
		Element element = (Element) node;
		return element.getAttribute("color");
	}

}
