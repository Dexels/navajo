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

import java.awt.Component;
import java.awt.Font;

import org.akrogen.tkui.css.core.css2.CSS2FontHelper;
import org.akrogen.tkui.css.core.css2.CSS2FontPropertiesHelpers;
import org.akrogen.tkui.css.core.css2.CSS2PrimitiveValueImpl;
import org.akrogen.tkui.css.core.dom.properties.css2.CSS2FontProperties;
import org.akrogen.tkui.css.core.dom.properties.css2.CSS2FontPropertiesImpl;
import org.akrogen.tkui.css.core.engine.CSSElementContext;
import org.w3c.dom.css.CSSPrimitiveValue;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * CSS Swing Font Helper to :
 * <ul>
 * <li>get CSS2FontProperties from Font of Component.</li>
 * <li>get Font of Component from CSS2FontProperties.</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSSwingFontHelper {

	/**
	 * Get CSS2FontProperties from Component stored into ClientProperty of
	 * Component. If CSS2FontProperties doesn't exist, create it from Font of
	 * Component and store it into ClientProperty of Component.
	 * 
	 * @param component
	 * @return
	 */
	public static CSS2FontProperties getCSS2FontProperties(TipiComponent component,
			CSSElementContext context) {
		// Search into ClientProperty of Component if CSS2FontProperties exist.
		CSS2FontProperties fontProperties = CSS2FontPropertiesHelpers
				.getCSS2FontProperties(context);
//		if (fontProperties == null) {
//			// CSS2FontProperties doesn't exist, create it
//			Font font = component.getFont();
//			fontProperties = getCSS2FontProperties(font);
//			// store into ClientProperty the CSS2FontProperties
//			CSS2FontPropertiesHelpers.setCSS2FontProperties(fontProperties,
//					context);
//		}
		// TODO commented out Frank
		return fontProperties;
	}

	/**
	 * Build CSS2FontProperties from Swing Font.
	 * 
	 * @param font
	 * @return
	 */
	public static CSS2FontProperties getCSS2FontProperties(Font font) {
		// Create CSS Font Properties
		CSS2FontProperties fontProperties = new CSS2FontPropertiesImpl();
		// Update font-family
		String fontFamily = getFontFamily(font);
		fontProperties.setFamily(new CSS2PrimitiveValueImpl(fontFamily));
		// Update font-size
		int fontSize = (font != null ? font.getSize() : 0);
		fontProperties.setSize(new CSS2PrimitiveValueImpl(fontSize));
		// Update font-weight
		String fontWeight = getFontWeight(font);
		fontProperties.setWeight((new CSS2PrimitiveValueImpl(fontWeight)));
		// Update font-style
		String fontStyle = getFontStyle(font);
		fontProperties.setStyle((new CSS2PrimitiveValueImpl(fontStyle)));
		return fontProperties;

	}

	public static Font getFont(CSS2FontProperties fontProperties,
			Component component) {
		// Get style of the old font
		Font oldFont = (component != null ? component.getFont() : null);
		String fontFamily = (oldFont != null ? oldFont.getFamily() : "");
		int fontSize = (oldFont != null ? oldFont.getSize() : 0);
		boolean isBold = (oldFont != null ? oldFont.isBold() : false);
		boolean isItalic = (oldFont != null ? oldFont.isItalic() : false);
		// Merge it with fontProperties
		// font-family
		CSSPrimitiveValue cssFontFamily = fontProperties.getFamily();
		if (cssFontFamily != null) {
			fontFamily = ((CSSPrimitiveValue) cssFontFamily).getStringValue();
		}
		// font-size
		CSSPrimitiveValue cssFontSize = fontProperties.getSize();
		if (cssFontSize != null) {
			fontSize = ((int) ((CSSPrimitiveValue) cssFontSize)
					.getFloatValue(CSSPrimitiveValue.CSS_PT));
		}
		// font-weight
		CSSPrimitiveValue cssFontWeight = fontProperties.getWeight();
		if (cssFontWeight != null) {
			String weight = cssFontWeight.getStringValue();
			isBold = "bold".equals(weight.toLowerCase());
		}
		// font-style
		CSSPrimitiveValue cssFontStyle = fontProperties.getStyle();
		if (cssFontStyle != null) {
			String style = cssFontStyle.getStringValue();
			isItalic = "italic".equals(style);
		}
		int fontStyle = 0;
		if (isBold)
			fontStyle = fontStyle + java.awt.Font.BOLD;
		if (isItalic)
			fontStyle = fontStyle + java.awt.Font.ITALIC;
		Font newfont = new Font(fontFamily, fontStyle, fontSize);
		return newfont;
	}

	/**
	 * Return CSS Value font-family from Component Font
	 * 
	 * @param control
	 * @return
	 */
	public static String getFontFamily(TipiComponent component) {
//		return getFontFamily(component.getFont());
		// TODO Frank
		return null;
	}

	/**
	 * Return CSS Value font-family from Swing Font
	 * 
	 * @param font
	 * @return
	 */
	public static String getFontFamily(Font font) {
		if (font == null)
			return null;
		String family = font.getFamily();
		return CSS2FontHelper.getFontFamily(family);
	}

	/**
	 * Return CSS Value font-size from Component Font
	 * 
	 * @param component
	 * @return
	 */
	public static String getFontSize(TipiComponent component) {
		// TODO Frank
		return "10";
//		return getFontSize(component.getFont());
	}

	/**
	 * Return CSS Value font-size from Swing Font
	 * 
	 * @param font
	 * @return
	 */
	public static String getFontSize(Font font) {
		if (font == null)
			return null;
		return CSS2FontHelper.getFontSize(font.getSize());
	}

	/**
	 * Return CSS Value font-style from Component Font
	 * 
	 * @param component
	 * @return
	 */
	public static String getFontStyle(TipiComponent component) {
//		return getFontStyle(component.getFont());
		// TODO Frank
		return "plain";
	}

	/**
	 * Return CSS Value font-style from Swing Font
	 * 
	 * @param font
	 * @return
	 */
	public static String getFontStyle(Font font) {
		if (font == null)
			return null;
		return CSS2FontHelper.getFontStyle(font.isItalic());
	}

	/**
	 * Return CSS Value font-weight from Component Font
	 * 
	 * @param component
	 * @return
	 */
	public static String getFontWeight(TipiComponent component) {
//		return getFontWeight(component.getFont());
		// TODO Frank
		return "plain";
	}

	/**
	 * Return CSS Value font-weight from Control Font
	 * 
	 * @param font
	 * @return
	 */
	public static String getFontWeight(Font font) {
		if (font == null)
			return null;
		return CSS2FontHelper.getFontWeight(font.isBold());
	}

}
