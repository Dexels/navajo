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
package org.akrogen.tkui.css.tipi.properties.css2.lazy.border;

import org.akrogen.tkui.css.core.css2.CSSBorderPropertiesHelpers;
import org.akrogen.tkui.css.core.dom.properties.CSSBorderProperties;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2Delegate;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.impl.dom.properties.CSSBorderPropertiesImpl;
import org.akrogen.tkui.css.tipi.properties.AbstractCSSPropertySwingHandler;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public abstract class AbstractCSSPropertyBorderSwingHandler extends
		AbstractCSSPropertySwingHandler implements ICSSPropertyHandler2Delegate {

	public void applyCSSProperty(TipiComponent component, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		CSSBorderProperties border = null;
		// (CSSBorderProperties) component
		// .getClientProperty(CSSSwingConstants.COMPONENT_CSS2BORDER_KEY);
		if (border == null) {
			border = new CSSBorderPropertiesImpl();
			// component.putClientProperty(
			// CSSSwingConstants.COMPONENT_CSS2BORDER_KEY, border);
		}
		this.applyCSSProperty(border, property, value, pseudo, engine);
	}

	public void applyCSSProperty(CSSBorderProperties border, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		CSSBorderPropertiesHelpers.updateCSSProperty(border, property, value);
	}

	public ICSSPropertyHandler2 getCSSPropertyHandler2() {
		return CSSPropertyBorderSwingHandler2.INSTANCE;
	}

}
