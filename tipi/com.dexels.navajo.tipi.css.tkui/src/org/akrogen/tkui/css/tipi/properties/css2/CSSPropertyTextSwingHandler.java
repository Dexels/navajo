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

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyTextHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyTextHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyTextSwingHandler extends AbstractCSSPropertyTextHandler {

	public final static ICSSPropertyTextHandler INSTANCE = new CSSPropertyTextSwingHandler();

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

	public void applyCSSPropertyColor(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		Component component = (Component) element;
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color color = (Color) engine.convert(value, Color.class, null);
			component.setForeground(color);
		}
	}

	public void applyCSSPropertyTextTransform(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		Component component = (Component) element;
		String defaultText = null;// (String) component
		// .getClientProperty(CSSSwingConstants.COMPONENT_TEXT_KEY);
		// if (element instanceof JTextComponent) {
		// JTextComponent text = (JTextComponent) element;
		// text.setText(getTextTransform(text.getText(), value, defaultText));
		// return;
		// }
		if (element instanceof JLabel) {
			JLabel label = (JLabel) element;
			label
					.setText(getTextTransform(label.getText(), value,
							defaultText));
			return;
		}
		if (element instanceof AbstractButton) {
			AbstractButton button = (AbstractButton) element;
			button.setText(getTextTransform(button.getText(), value,
					defaultText));
			return;
		}
	}

	public String retrieveCSSPropertyColor(Object element, CSSEngine engine)
			throws Exception {
		Component component = (Component) element;
		Color color = component.getForeground();
		return engine.convert(color, Color.class, null);
	}

	public String retrieveCSSPropertyTextTransform(Object element,
			CSSEngine engine) throws Exception {
		String text = null;
		Component component = (Component) element;
		if (component instanceof JTextComponent) {
			text = ((JTextComponent) element).getText();
		} else {
			if (component instanceof JLabel) {
				text = ((JLabel) element).getText();
			} else {
				if (component instanceof AbstractButton) {
					text = ((AbstractButton) element).getText();
				}
			}
		}
		// if (text != null)
		// component.putClientProperty(CSSSwingConstants.COMPONENT_TEXT_KEY,
		// text);
		return "none";
	}

}
