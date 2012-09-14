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

import java.awt.LayoutManager;

import org.akrogen.tkui.css.core.dom.properties.converters.AbstractCSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverterConfig;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

public class CSSValueSwingLayoutConverterImpl extends AbstractCSSValueConverter {

	public static final ICSSValueConverter INSTANCE = new CSSValueSwingLayoutConverterImpl();
	
	private final static Logger logger = LoggerFactory
			.getLogger(CSSValueSwingLayoutConverterImpl.class);
	public CSSValueSwingLayoutConverterImpl() {
		super(LayoutManager.class);
	}

	public Object convert(CSSValue value, CSSEngine engine, Object context)
			throws Exception {
		// layout: circle 60% 20° 123°
		String circle = null;
		int rayon = -1;
		int startInterval = -1;
		int endInterval = -1;
		if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
			CSSValueList valueList = (CSSValueList) value;
			for (int i = 0; i < valueList.getLength(); i++) {
				CSSValue value2 = valueList.item(i);
				if (value2.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
					CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value2;
					switch (i) {
					case 0:
						circle = primitiveValue.getStringValue();
						break;
					case 1:
						rayon = (int) primitiveValue
								.getFloatValue(CSSPrimitiveValue.CSS_PERCENTAGE);
						break;
					case 2:
						startInterval = (int) primitiveValue
								.getFloatValue(CSSPrimitiveValue.CSS_DEG);
						break;
					case 3:
						endInterval = (int) primitiveValue
								.getFloatValue(CSSPrimitiveValue.CSS_DEG);
						break;
					}
				}
			}
		}
		// TODO : build custom CircleLayoutManager
		logger.info("CSSValueSwingLayoutConverterImpl=> circle="
				+ circle + ", rayon=" + rayon +  ", startInterval="
				+ startInterval + ", endInterval=" + endInterval);
		return null;
	}

	public String convert(Object value, CSSEngine engine, Object context,
			ICSSValueConverterConfig config) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
