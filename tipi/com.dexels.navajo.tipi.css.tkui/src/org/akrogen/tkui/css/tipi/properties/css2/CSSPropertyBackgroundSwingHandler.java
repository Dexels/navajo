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

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.akrogen.tkui.css.core.dom.properties.Gradient;
import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyBackgroundHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyBackgroundHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.akrogen.tkui.css.tipi.properties.GradientBackgroundDecorator;
import org.akrogen.tkui.css.tipi.properties.ImageBackgroundDecorator;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyBackgroundSwingHandler extends
		AbstractCSSPropertyBackgroundHandler {

	public final static ICSSPropertyBackgroundHandler INSTANCE = new CSSPropertyBackgroundSwingHandler();

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

	public void applyCSSPropertyBackgroundColor(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		Component component = (Component) element;
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color color = (Color) engine.convert(value, Color.class, null);
			component.setBackground(color);
		} else if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
			Gradient grad = (Gradient) engine.convert(value, Gradient.class,
					null);
			GradientBackgroundDecorator.handle((JComponent) component, grad);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.css2.AbstractCSSPropertyBackgroundHandler#applyCSSPropertyBackgroundImage(java.lang.Object,
	 *      org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void applyCSSPropertyBackgroundImage(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		Component component = (Component) element;
		Image image = (Image) engine.convert(value, Image.class, null);
		if (image != null) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setIcon(new ImageIcon(image));
			} else
				new ImageBackgroundDecorator((JComponent) component, image);
		} else {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setIcon(null);
			}
		}
	}

	public String retrieveCSSPropertyBackgroundAttachment(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyBackgroundColor(Object element,
			CSSEngine engine) throws Exception {
		Component component = (Component) element;
		Color color = component.getBackground();
		return engine.convert(color, Color.class, null);
	}

	public String retrieveCSSPropertyBackgroundImage(Object widget,
			CSSEngine engine) throws Exception {
		// TODO : manage path of Image.
		return "none";
	}

	public String retrieveCSSPropertyBackgroundPosition(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyBackgroundRepeat(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
