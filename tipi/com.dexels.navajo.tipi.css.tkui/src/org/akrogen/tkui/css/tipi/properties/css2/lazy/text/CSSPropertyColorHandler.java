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
package org.akrogen.tkui.css.tipi.properties.css2.lazy.text;

import java.awt.Color;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.properties.AbstractConvertedCSSPropertySwingHandler;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * Swing CSS Property Color Handler.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 * @see http://www.w3schools.com/css/css_reference.asp#text
 */
public class CSSPropertyColorHandler extends
		AbstractConvertedCSSPropertySwingHandler {

	public void applyCSSPropertyValue(TipiComponent component, String property,
			Object value, String pseudo, CSSEngine engine) throws Exception {
		if (value instanceof Color) {
			Color color = (Color) value;
//			component.setForeground(color);
		}
	}

	public String retrieveCSSProperty(TipiComponent component, String property,
			CSSEngine engine) throws Exception {
//		Color color = component.getForeground();
//		return retrieveCSSProperty(color, engine);
		return null;
	}

	protected Object getToType(Object value) {
		return Color.class;
	}

}
