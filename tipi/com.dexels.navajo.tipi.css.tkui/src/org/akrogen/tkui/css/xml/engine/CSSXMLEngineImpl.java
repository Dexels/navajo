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
package org.akrogen.tkui.css.xml.engine;

import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyBackgroundHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyFontHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyTextHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.impl.engine.CSSEngineImpl;
import org.akrogen.tkui.css.xml.properties.css2.CSSPropertyBackgroundXMLHandler;
import org.akrogen.tkui.css.xml.properties.css2.CSSPropertyFontXMLHandler;
import org.akrogen.tkui.css.xml.properties.css2.CSSPropertyTextXMLHandler;

/**
 * {@link CSSEngine} implementation to apply style sheet to XML DOM.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSXMLEngineImpl extends CSSEngineImpl {

	public CSSXMLEngineImpl() {
		// Register XML CSS Property Background Handler
		super.registerCSSPropertyHandler(ICSSPropertyBackgroundHandler.class,
				CSSPropertyBackgroundXMLHandler.INSTANCE);
		// Register XML CSS Property Text Handler
		super.registerCSSPropertyHandler(ICSSPropertyTextHandler.class,
				CSSPropertyTextXMLHandler.INSTANCE);
		// Register XML CSS Property Font Handler
		super.registerCSSPropertyHandler(ICSSPropertyFontHandler.class,
				CSSPropertyFontXMLHandler.INSTANCE);
	}
}
