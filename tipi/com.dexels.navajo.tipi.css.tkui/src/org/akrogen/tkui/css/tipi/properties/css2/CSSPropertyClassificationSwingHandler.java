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
package org.akrogen.tkui.css.tipi.properties.css2;

import java.awt.Component;
import java.awt.Cursor;

import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyClassificationHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyClassificationHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyClassificationSwingHandler extends
		AbstractCSSPropertyClassificationHandler {

	public final static ICSSPropertyClassificationHandler INSTANCE = new CSSPropertyClassificationSwingHandler();

	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			super.applyCSSProperty(component, property, value, pseudo, engine);
			return true;
		}
		return false;

	}

	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			return super.retrieveCSSProperty(component, property, engine);
		}
		return null;
	}

	public void applyCSSPropertyCursor(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Component component = (Component) element;
			Cursor cursor = (Cursor) engine.convert(value, Cursor.class, null);
			component.setCursor(cursor);
		}
	}

	public void applyCSSPropertyVisibility(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Component component = (Component) element;
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			String visibility = primitiveValue.getStringValue();
			if ("hidden".equals(visibility))
				component.setVisible(false);
			else if ("collapse".equals(visibility)) {
				// TODO : manage collapse
				component.setVisible(false);
			} else
				component.setVisible(true);
		}
	}

	public String retrieveCSSPropertyCursor(Object element, CSSEngine engine)
			throws Exception {
		Component component = (Component) element;
		Cursor cursor = component.getCursor();
		return engine.convert(cursor, Cursor.class, null);
	}

	public String retrieveCSSPropertyVisibility(Object element, CSSEngine engine)
			throws Exception {
		return "visible";
	}
}
