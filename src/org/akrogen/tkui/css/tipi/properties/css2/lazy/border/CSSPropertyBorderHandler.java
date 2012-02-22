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

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2Delegate;
import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyBorderCompositeHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyBorderHandler extends
		AbstractCSSPropertyBorderCompositeHandler implements
		ICSSPropertyHandler2Delegate {

	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			super.applyCSSPropertyComposite(element, property, value, pseudo,
					engine);
			return true;
		}
		return false;
	}

	public String retrieveCSSProperty(Object widget, String property,
			CSSEngine engine) throws Exception {
		return null;
	}

	public ICSSPropertyHandler2 getCSSPropertyHandler2() {
		return CSSPropertyBorderSwingHandler2.INSTANCE;
	}
}
