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
package org.akrogen.tkui.css.core.dom.properties.css2;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.exceptions.UnsupportedPropertyException;
import org.w3c.dom.css.CSSValue;

public abstract class AbstractCSSPropertyClassificationHandler implements
		ICSSPropertyClassificationHandler {

	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if ("clear".equals(property)) {
			applyCSSPropertyClear(element, value, pseudo, engine);
		}
		if ("cursor".equals(property)) {
			applyCSSPropertyCursor(element, value, pseudo, engine);
		}
		if ("display".equals(property)) {
			applyCSSPropertyDisplay(element, value, pseudo, engine);
		}		
		if ("float".equals(property)) {
			applyCSSPropertyFloat(element, value, pseudo, engine);
		}		
		if ("position".equals(property)) {
			applyCSSPropertyPosition(element, value, pseudo, engine);
		}			
		if ("visibility".equals(property)) {
			applyCSSPropertyVisibility(element, value, pseudo, engine);
		}						
		return false;
	}

	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception {
		if ("clear".equals(property)) {
			return retrieveCSSPropertyClear(element, engine);
		}
		if ("cursor".equals(property)) {
			return retrieveCSSPropertyCursor(element, engine);
		}
		if ("display".equals(property)) {
			return retrieveCSSPropertyDisplay(element, engine);
		}
		if ("float".equals(property)) {
			return retrieveCSSPropertyFloat(element, engine);
		}
		if ("position".equals(property)) {
			return retrieveCSSPropertyPosition(element, engine);
		}
		if ("visibility".equals(property)) {
			return retrieveCSSPropertyVisibility(element, engine);
		}
		return null;
	}

	public void applyCSSPropertyClear(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("clear");
	}

	public void applyCSSPropertyCursor(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("cursor");
	}

	public void applyCSSPropertyDisplay(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("display");		
	}

	public void applyCSSPropertyFloat(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("float");
	}

	public void applyCSSPropertyPosition(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("position");
	}

	public void applyCSSPropertyVisibility(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("visibility");
	}

	public String retrieveCSSPropertyClear(Object element, CSSEngine engine)
			throws Exception {
		return null;
	}

	public String retrieveCSSPropertyCursor(Object element, CSSEngine engine)
			throws Exception {
		return null;
	}

	public String retrieveCSSPropertyDisplay(Object element, CSSEngine engine)
			throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFloat(Object element, CSSEngine engine)
			throws Exception {
		return null;
	}

	public String retrieveCSSPropertyPosition(Object element, CSSEngine engine)
			throws Exception {
		return null;
	}

	public String retrieveCSSPropertyVisibility(Object element, CSSEngine engine)
			throws Exception {
		return null;
	}
	
}
