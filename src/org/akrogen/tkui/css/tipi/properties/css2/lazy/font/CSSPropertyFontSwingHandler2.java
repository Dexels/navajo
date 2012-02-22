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
package org.akrogen.tkui.css.tipi.properties.css2.lazy.font;

import java.awt.Component;
import java.awt.Font;

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.dom.properties.css2.CSS2FontProperties;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingFontHelper;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyFontSwingHandler2 implements ICSSPropertyHandler2 {

	public static final ICSSPropertyHandler2 INSTANCE = new CSSPropertyFontSwingHandler2();

	public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			CSS2FontProperties fontProperties = CSSSwingFontHelper
					.getCSS2FontProperties(component, engine
							.getCSSElementContext(element));
			if (fontProperties == null)
				return;
			Font newFont = (Font) engine.convert(fontProperties, Font.class,
					component);
			if (newFont != null) {
//				component.setFont(newFont);
				// TODO Frank
			}
		}
	}

}
