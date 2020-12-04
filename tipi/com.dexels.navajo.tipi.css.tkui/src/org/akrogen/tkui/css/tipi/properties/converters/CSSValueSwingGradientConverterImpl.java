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

import org.akrogen.tkui.css.core.dom.properties.Gradient;
import org.akrogen.tkui.css.core.dom.properties.converters.AbstractCSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverterConfig;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingColorHelper;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

/**
 * CSS Value converter to convert :
 * <ul>
 * <li>CSS Value to {@link Gradient}</li>.
 * <li>{@link Gradient} to String CSS Value</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSValueSwingGradientConverterImpl extends
		AbstractCSSValueConverter {

	public static final ICSSValueConverter INSTANCE = new CSSValueSwingGradientConverterImpl();

	public CSSValueSwingGradientConverterImpl() {
		super(Gradient.class);
	}

	public Object convert(CSSValue value, CSSEngine engine, Object context) {
		if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST)
			return CSSSwingColorHelper.getGradient((CSSValueList) value);
		return null;
	}

	public String convert(Object value, CSSEngine engine, Object context,
			ICSSValueConverterConfig config) throws Exception {
		return null;
	}

}
