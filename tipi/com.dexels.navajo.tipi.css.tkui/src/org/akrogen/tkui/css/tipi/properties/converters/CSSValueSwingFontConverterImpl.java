/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;

import org.akrogen.tkui.css.core.dom.properties.converters.AbstractCSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverterConfig;
import org.akrogen.tkui.css.core.dom.properties.css2.CSS2FontProperties;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingCursorHelper;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingFontHelper;
import org.w3c.dom.css.CSSValue;

public class CSSValueSwingFontConverterImpl extends AbstractCSSValueConverter {

	public static final ICSSValueConverter INSTANCE = new CSSValueSwingFontConverterImpl();

	public CSSValueSwingFontConverterImpl() {
		super(Font.class);
	}

	public Object convert(CSSValue value, CSSEngine engine, Object context) {
		if (value instanceof CSS2FontProperties) {
			Component component = (Component) context;
			return CSSSwingFontHelper.getFont((CSS2FontProperties) value,
					component);
		}
		return null;
	}

	public String convert(Object value, CSSEngine engine, Object context,
			ICSSValueConverterConfig config) throws Exception {
		Cursor cursor = (Cursor) value;
		return CSSSwingCursorHelper.getCursor(cursor);
	}

}
