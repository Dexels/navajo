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
package org.akrogen.tkui.css.tipi.engine;

import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyBackgroundHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyBorderHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyClassificationHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyFontHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyTextHandler;
import org.akrogen.tkui.css.tipi.properties.css2.CSSPropertyBackgroundSwingHandler;
import org.akrogen.tkui.css.tipi.properties.css2.CSSPropertyBorderSwingHandler;
import org.akrogen.tkui.css.tipi.properties.css2.CSSPropertyClassificationSwingHandler;
import org.akrogen.tkui.css.tipi.properties.css2.CSSPropertyFontSwingHandler;
import org.akrogen.tkui.css.tipi.properties.css2.CSSPropertyTextSwingHandler;
import org.akrogen.tkui.css.xml.properties.css2.CSSPropertyBackgroundXMLHandler;
import org.akrogen.tkui.css.xml.properties.css2.CSSPropertyFontXMLHandler;
import org.akrogen.tkui.css.xml.properties.css2.CSSPropertyTextXMLHandler;

/**
 * CSS Swing Engine implementation which configure CSSEngineImpl to apply styles
 * to Swing containers.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSTipiEngineImpl extends AbstractCSSSwingEngineImpl {

	public CSSTipiEngineImpl() {
		super();
	}

	public CSSTipiEngineImpl(boolean lazyApplyingStyles) {
		super(lazyApplyingStyles);
	}

	protected void initializeCSSPropertyHandlers() {
		// Register Swing CSS Property Background Handler
		super.registerCSSPropertyHandler(ICSSPropertyBackgroundHandler.class,
				CSSPropertyBackgroundSwingHandler.INSTANCE);
		// Register Swing CSS Property Border Handler
		super.registerCSSPropertyHandler(ICSSPropertyBorderHandler.class,
				CSSPropertyBorderSwingHandler.INSTANCE);
		// Register Swing CSS Property Classification Handler
		super.registerCSSPropertyHandler(
				ICSSPropertyClassificationHandler.class,
				CSSPropertyClassificationSwingHandler.INSTANCE);
		// Register Swing CSS Property Text Handler
		super.registerCSSPropertyHandler(ICSSPropertyTextHandler.class,
				CSSPropertyTextSwingHandler.INSTANCE);
		// Register Swing CSS Property Font Handler
		super.registerCSSPropertyHandler(ICSSPropertyFontHandler.class,
				CSSPropertyFontSwingHandler.INSTANCE);

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
