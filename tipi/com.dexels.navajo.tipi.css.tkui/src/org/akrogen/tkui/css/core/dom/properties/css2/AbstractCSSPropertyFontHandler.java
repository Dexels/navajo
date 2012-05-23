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

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.exceptions.UnsupportedPropertyException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

public abstract class AbstractCSSPropertyFontHandler extends
		AbstractCSSPropertyFontCompositeHandler implements
		ICSSPropertyFontHandler {

	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if ("font".equals(property))
			applyCSSPropertyFont(element, value, pseudo, engine);
		else if ("font-family".equals(property))
			applyCSSPropertyFontFamily(element, value, pseudo, engine);
		else if ("font-size".equals(property))
			applyCSSPropertyFontSize(element, value, pseudo, engine);
		else if ("font-adjust".equals(property))
			applyCSSPropertyFontSizeAdjust(element, value, pseudo, engine);
		else if ("font-stretch".equals(property))
			applyCSSPropertyFontStretch(element, value, pseudo, engine);
		else if ("font-style".equals(property))
			applyCSSPropertyFontStyle(element, value, pseudo, engine);
		else if ("font-variant".equals(property))
			applyCSSPropertyFontVariant(element, value, pseudo, engine);
		else if ("font-weight".equals(property))
			applyCSSPropertyFontWeight(element, value, pseudo, engine);
		return false;
	}

	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception {
		if ("font-family".equals(property)) {
			return retrieveCSSPropertyFontFamily(element, engine);
		}
		if ("font-size".equals(property)) {
			return retrieveCSSPropertyFontSize(element, engine);
		}
		if ("font-adjust".equals(property)) {
			return retrieveCSSPropertyFontAdjust(element, engine);
		}
		if ("font-stretch".equals(property)) {
			return retrieveCSSPropertyFontStretch(element, engine);
		}
		if ("font-style".equals(property)) {
			return retrieveCSSPropertyFontStyle(element, engine);
		}
		if ("font-variant".equals(property)) {
			return retrieveCSSPropertyFontVariant(element, engine);
		}
		if ("font-weight".equals(property)) {
			return retrieveCSSPropertyFontWeight(element, engine);
		}
		return null;
	}

	public void applyCSSPropertyFont(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		super.applyCSSPropertyComposite(element, "font", value, pseudo, engine);
	}

	public void applyCSSPropertyFontFamily(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (element instanceof CSS2FontProperties) {
			applyCSSPropertyFontFamily((CSS2FontProperties) element, value,
					pseudo, engine);
			return;
		}
		throw new UnsupportedPropertyException("font-family");
	}

	protected void applyCSSPropertyFontFamily(CSS2FontProperties font,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			font.setFamily((CSSPrimitiveValue) value);
		}
	}

	public void applyCSSPropertyFontSize(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (element instanceof CSS2FontProperties) {
			applyCSSPropertyFontSize((CSS2FontProperties) element, value,
					pseudo, engine);
			return;
		}
		throw new UnsupportedPropertyException("font-size");
	}

	protected void applyCSSPropertyFontSize(CSS2FontProperties font,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			font.setSize((CSSPrimitiveValue) value);
		}
	}

	public void applyCSSPropertyFontSizeAdjust(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("font-adjust");
	}

	public void applyCSSPropertyFontStretch(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("font-stretch");
	}

	public void applyCSSPropertyFontStyle(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (element instanceof CSS2FontProperties) {
			applyCSSPropertyFontStyle((CSS2FontProperties) element, value,
					pseudo, engine);
			return;
		}
		throw new UnsupportedPropertyException("font-style");
	}

	protected void applyCSSPropertyFontStyle(CSS2FontProperties font,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			font.setStyle((CSSPrimitiveValue) value);
		}
	}

	public void applyCSSPropertyFontVariant(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("font-variant");
	}

	public void applyCSSPropertyFontWeight(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (element instanceof CSS2FontProperties) {
			applyCSSPropertyFontWeight((CSS2FontProperties) element, value,
					pseudo, engine);
			return;
		}
		throw new UnsupportedPropertyException("font-weight");
	}

	protected void applyCSSPropertyFontWeight(CSS2FontProperties font,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			font.setWeight((CSSPrimitiveValue) value);
		}
	}

}
