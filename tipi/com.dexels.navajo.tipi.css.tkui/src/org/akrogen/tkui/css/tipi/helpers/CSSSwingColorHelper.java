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
package org.akrogen.tkui.css.tipi.helpers;

import java.awt.Color;

import org.akrogen.tkui.css.core.css2.CSS2ColorHelper;
import org.akrogen.tkui.css.core.css2.CSS2RGBColorImpl;
import org.akrogen.tkui.css.core.dom.properties.Gradient;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.RGBColor;

public class CSSSwingColorHelper {

	public static Color getSwingColor(String name) {
		RGBColor color = CSS2ColorHelper.getRGBColor(name);
		if (color != null) {
			return getSwingColor(color);
		}
		return null;
	}

	public static Color getSwingColor(RGBColor color) {
		return new Color((int) color.getRed().getFloatValue(
				CSSPrimitiveValue.CSS_NUMBER), (int) color.getGreen()
				.getFloatValue(CSSPrimitiveValue.CSS_NUMBER), (int) color
				.getBlue().getFloatValue(CSSPrimitiveValue.CSS_NUMBER));
	}

	public static Color getSwingColor(CSSValue value) {
		if (value.getCssValueType() != CSSValue.CSS_PRIMITIVE_VALUE)
			return null;
		Color color = null;
		CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
		switch (primitiveValue.getPrimitiveType()) {
		case CSSPrimitiveValue.CSS_IDENT:
			String string = primitiveValue.getStringValue();
			color = getSwingColor(string);
			break;
		case CSSPrimitiveValue.CSS_RGBCOLOR:
			RGBColor rgbColor = primitiveValue.getRGBColorValue();
			color = getSwingColor(rgbColor);
			break;
		}
		return color;
	}

	public static Gradient getGradient(CSSValueList list) {
		Gradient gradient = new Gradient();
		for (int i = 0; i < list.getLength(); i++) {
			CSSValue value = list.item(i);
			if (value.getCssText().equals("gradient"))
				continue;
			if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
				switch (((CSSPrimitiveValue) value).getPrimitiveType()) {
				case CSSPrimitiveValue.CSS_IDENT:
				case CSSPrimitiveValue.CSS_RGBCOLOR:
					Color rgb = getSwingColor((CSSPrimitiveValue) value);
					if (rgb != null)
						gradient.addRGB(rgb);
					break;
				case CSSPrimitiveValue.CSS_PERCENTAGE:
					gradient.addPercent(getPercent((CSSPrimitiveValue) value));
					break;
				}
			}
		}
		return gradient;
	}

	public static Integer getPercent(CSSPrimitiveValue value) {
		int percent = 0;
		switch (value.getPrimitiveType()) {
		case CSSPrimitiveValue.CSS_PERCENTAGE:
			percent = (int) value
					.getFloatValue(CSSPrimitiveValue.CSS_PERCENTAGE);
		}
		return new Integer(percent);
	}

	public static RGBColor getRGBColor(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		return new CSS2RGBColorImpl(red, green, blue);
	}

}
