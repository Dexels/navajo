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

import org.akrogen.tkui.css.core.dom.properties.AbstractCSSPropertyCompositeHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * Abstract CSS property composite background which is enable to dispatch to well
 * CSS Property background-color, background-image...
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSPropertyBackgroundCompositeHandler extends
		AbstractCSSPropertyCompositeHandler {

	private static final String[] BACKROUND_CSSPROPERTIES = {
			"background-attachment", "background-color", "background-image",
			"background-position", "background-repeat" };

	public void applyCSSProperty(Object element, CSSValue value, String pseudo,
			CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			short type = primitiveValue.getPrimitiveType();
			switch (type) {
			case CSSPrimitiveValue.CSS_IDENT:
			case CSSPrimitiveValue.CSS_RGBCOLOR:
				engine.applyCSSProperty(element, "background-color", value,
						pseudo);
				break;
			case CSSPrimitiveValue.CSS_URI:
				engine.applyCSSProperty(element, "background-image", value,
						pseudo);
				break;
			}
		}
	}

	public boolean isCSSPropertyComposite(String property) {
		return "background".equals(property);
	}

	public String[] getCSSPropertiesNames(String property) {
		if ("background".equals(property))
			return BACKROUND_CSSPROPERTIES;
		return null;
	}
}
