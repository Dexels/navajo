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
package org.akrogen.tkui.css.tipi.properties.css2.lazy.background;

import java.awt.Color;

import javax.swing.JComponent;

import org.akrogen.tkui.css.core.dom.properties.Gradient;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.properties.AbstractConvertedCSSPropertySwingHandler;
import org.akrogen.tkui.css.tipi.properties.GradientBackgroundDecorator;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyBackgroundColorHandler extends
		AbstractConvertedCSSPropertySwingHandler {

	protected void applyCSSPropertyValue(TipiComponent component, String property,
			Object value, String pseudo, CSSEngine engine) throws Exception {
		if (value instanceof Color) {
			Color color = (Color) value;
//			component.setBackground(color);
// TODO Frank
			return;
		}
		if (value instanceof Gradient) {
			Gradient grad = (Gradient) value;
			GradientBackgroundDecorator.handle((JComponent)component, grad);
			return;
		}
	}

	public String retrieveCSSProperty(TipiComponent component, String property,
			CSSEngine engine) throws Exception {
//		Color color = component.getBackground();
//		return super.retrieveCSSProperty(color, engine);
		// TODO Frank
		return null;
	}

	protected Object getToType(Object value) {
		if (value instanceof CSSValue) {
			if (((CSSValue) value).getCssValueType() == CSSValue.CSS_VALUE_LIST)
				return Gradient.class;
		}
		return Color.class;
	}

}
