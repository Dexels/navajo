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
package org.akrogen.tkui.css.tipi.properties.css2.lazy.classification;

import java.awt.Component;
import java.awt.Cursor;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.properties.AbstractCSSPropertySwingHandler;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyCursorHandler extends AbstractCSSPropertySwingHandler {

	public void applyCSSProperty(TipiComponent component, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		Cursor cursor = (Cursor) engine.convert(value, Cursor.class, null);
		// TODO Frank
//		component.setCursor(cursor);
	}

	public String retrieveCSSProperty(TipiComponent component, String property,
			CSSEngine engine) throws Exception {
//		Cursor cursor = component.getCursor();
//		return engine.convert(cursor, Cursor.class, null);
		// TODO Frank
		return null;
	}
}
