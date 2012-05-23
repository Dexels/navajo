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

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingFontHelper;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyFontSizeHandler extends
		AbstractCSSPropertyFontSwingHandler {

	public String retrieveCSSProperty(TipiComponent component, String property,
			CSSEngine engine) throws Exception {
		return CSSSwingFontHelper.getFontSize(component);
	}

}
