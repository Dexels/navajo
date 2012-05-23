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

import org.akrogen.tkui.css.core.css2.CSS2FontPropertiesHelpers;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2Delegate;
import org.akrogen.tkui.css.core.dom.properties.css2.CSS2FontProperties;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingFontHelper;
import org.akrogen.tkui.css.tipi.properties.AbstractCSSPropertySwingHandler;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public abstract class AbstractCSSPropertyFontSwingHandler extends
		AbstractCSSPropertySwingHandler implements ICSSPropertyHandler2Delegate {

	public void applyCSSProperty(TipiComponent component, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		CSS2FontProperties fontProperties = CSSSwingFontHelper
				.getCSS2FontProperties(component, engine
						.getCSSElementContext(component));
		this.applyCSSProperty(fontProperties, property, value, pseudo, engine);
	}

	public ICSSPropertyHandler2 getCSSPropertyHandler2() {
		return CSSPropertyFontSwingHandler2.INSTANCE;
	}

	public void applyCSSProperty(CSS2FontProperties font, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		CSS2FontPropertiesHelpers.updateCSSPropertyFont(font, property, value);
	}

	public abstract String retrieveCSSProperty(TipiComponent component,
			String property, CSSEngine engine) throws Exception;
}
