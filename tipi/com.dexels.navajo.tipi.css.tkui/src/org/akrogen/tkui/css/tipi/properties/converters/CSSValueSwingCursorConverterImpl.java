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

import java.awt.Cursor;

import org.akrogen.tkui.css.core.dom.properties.converters.AbstractCSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverterConfig;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingCursorHelper;
import org.w3c.dom.css.CSSValue;

public class CSSValueSwingCursorConverterImpl extends AbstractCSSValueConverter {

	public static final ICSSValueConverter INSTANCE = new CSSValueSwingCursorConverterImpl();

	public CSSValueSwingCursorConverterImpl() {
		super(Cursor.class);
	}

	public Object convert(CSSValue value, CSSEngine engine, Object context) {
		return CSSSwingCursorHelper.getCursor(value);
	}

	public String convert(Object value, CSSEngine engine, Object context,
			ICSSValueConverterConfig config) throws Exception {
		Cursor cursor = (Cursor) value;
		return CSSSwingCursorHelper.getCursor(cursor);
	}

}
