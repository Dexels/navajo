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
package org.akrogen.tkui.css.tipi.properties.converters;

import java.awt.Color;

import org.akrogen.tkui.css.core.css2.CSS2ColorHelper;
import org.akrogen.tkui.css.core.dom.properties.converters.AbstractCSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverterConfig;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingColorHelper;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.RGBColor;

/**
 * CSS Value converter to convert :
 * <ul>
 * <li>CSS Value to {@link Color}</li>.
 * <li>{@link Color} to String CSS Value</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSValueSwingColorConverterImpl extends AbstractCSSValueConverter {

	public static final ICSSValueConverter INSTANCE = new CSSValueSwingColorConverterImpl();

	public CSSValueSwingColorConverterImpl() {
		super(Color.class);
	}

	public Object convert(CSSValue value, CSSEngine engine, Object context) {
		return CSSSwingColorHelper.getSwingColor(value);
	}

	public String convert(Object value, CSSEngine engine, Object context,
			ICSSValueConverterConfig config) throws Exception {
		Color color = (Color) value;
		RGBColor rgbColor = CSSSwingColorHelper.getRGBColor(color);
		return CSS2ColorHelper.getColorStringValue(rgbColor, config);
	}
}
