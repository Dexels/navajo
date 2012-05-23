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
package org.akrogen.tkui.css.xml.properties.css2;

import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyBackgroundHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyBackgroundHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSValue;

/**
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSPropertyBackgroundXMLHandler extends
		AbstractCSSPropertyBackgroundHandler {

	public final static ICSSPropertyBackgroundHandler INSTANCE = new CSSPropertyBackgroundXMLHandler();

	public boolean applyCSSProperty(Object node, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (node instanceof Element) {
			super.applyCSSProperty((Element) node, property, value, pseudo, engine);
			return true;
		}
		return false;
	}

	public String retrieveCSSPropertyBackgroundAttachment(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyBackgroundColor(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyBackgroundImage(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyBackgroundPosition(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyBackgroundRepeat(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
