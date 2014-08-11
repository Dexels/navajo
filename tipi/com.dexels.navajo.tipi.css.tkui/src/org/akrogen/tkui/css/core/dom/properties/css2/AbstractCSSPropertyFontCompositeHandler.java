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

import org.akrogen.tkui.css.core.css2.CSS2FontHelper;
import org.akrogen.tkui.css.core.dom.properties.AbstractCSSPropertyCompositeHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * Abstract class which dispatch font CSS Property defined to call the
 * applyCSSProperty methods CSS Properties font-style, font-family, font-weight,
 * font-size.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSPropertyFontCompositeHandler extends
		AbstractCSSPropertyCompositeHandler {

	private static final String[] FONT_CSSPROPERTIES = { "font-style",
			"font-variant", "font-weight", "font-size", "font-family" };

	public void applyCSSProperty(Object element, CSSValue value, String pseudo,
			CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			String property = CSS2FontHelper
					.getCSSFontPropertyName((CSSPrimitiveValue) value);
			if (property != null) {
				engine.applyCSSProperty(element, property, value, pseudo);
			}
		}
	}

	public boolean isCSSPropertyComposite(String property) {
		return false; // "font".equals(property);
	}

	public String[] getCSSPropertiesNames(String property) {
		if ("font".equals(property))
			return FONT_CSSPROPERTIES;
		return null;
	}
}
