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
import java.awt.Font;

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyFontHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.CSS2FontProperties;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyFontHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingFontHelper;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyFontSwingHandler extends AbstractCSSPropertyFontHandler
		implements ICSSPropertyHandler2 {

	public final static ICSSPropertyFontHandler INSTANCE = new CSSPropertyFontSwingHandler();

	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			CSS2FontProperties fontProperties = CSSSwingFontHelper
					.getCSS2FontProperties(component, engine
							.getCSSElementContext(element));
			super.applyCSSProperty(fontProperties, property, value, pseudo,
					engine);
			return true;
		} else {
			if (element instanceof CSS2FontProperties) {
				super
						.applyCSSProperty(element, property, value, pseudo,
								engine);
				return true;
			}
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

	public String retrieveCSSPropertyFontAdjust(Object element, CSSEngine engine)
			throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontFamily(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = (TipiComponent) element;
		return CSSSwingFontHelper.getFontFamily(component);
	}

	public String retrieveCSSPropertyFontSize(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = (TipiComponent) element;
		return CSSSwingFontHelper.getFontSize(component);
	}

	public String retrieveCSSPropertyFontStretch(Object element,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontStyle(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = (TipiComponent) element;
		return CSSSwingFontHelper.getFontStyle(component);
	}

	public String retrieveCSSPropertyFontVariant(Object element,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontWeight(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = (TipiComponent) element;
		return CSSSwingFontHelper.getFontWeight(component);
	}

	public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			CSS2FontProperties fontProperties = CSSSwingFontHelper
					.getCSS2FontProperties(component, engine
							.getCSSElementContext(element));
			if (fontProperties == null)
				return;
			Font newFont = (Font) engine.convert(fontProperties, Font.class,
					component);
//			if (newFont != null)
//				component.setFont(newFont);
			// TODO commented out Frank
		}
	}
}
