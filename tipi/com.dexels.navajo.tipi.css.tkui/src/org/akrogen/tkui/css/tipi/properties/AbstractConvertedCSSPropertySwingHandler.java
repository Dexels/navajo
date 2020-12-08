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
package org.akrogen.tkui.css.tipi.properties;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public abstract class AbstractConvertedCSSPropertySwingHandler extends
		AbstractCSSPropertySwingHandler {

	protected void applyCSSProperty(TipiComponent component, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		Object toType = getToType(value);
		if (toType != null) {
			Object newValue = engine.convert(value, toType, null);
			//if (newValue != null)
				applyCSSPropertyValue(component, property, newValue, pseudo,
						engine);
//			else
//				applyCSSPropertyValue(component, property, value, pseudo,
//						engine);
		} else {
			applyCSSPropertyValue(component, property, value, pseudo, engine);
		}
	}

	protected String retrieveCSSProperty(Object value, CSSEngine engine) {
		Object toType = getToType(value);
		if (toType != null) {
			try {
				String newValue = engine.convert(value, toType, null);
				return newValue;
			}
			catch(Exception e) {
				
			}
		}
		return null;
	}

	protected abstract void applyCSSPropertyValue(TipiComponent component,
			String property, Object value, String pseudo, CSSEngine engine)
			throws Exception;

	protected abstract Object getToType(Object value);
}
