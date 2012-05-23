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

/**
 * Abstract CSS property background which is enable to manage 
 * apply CSS Property background, background-color, background-image...
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSPropertyBackgroundHandler extends
		AbstractCSSPropertyBackgroundCompositeHandler implements
		ICSSPropertyBackgroundHandler {

	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if ("background".equals(property)) {
			applyCSSPropertyBackground(element, value, pseudo, engine);
		}
		if ("background-attachment".equals(property)) {
			applyCSSPropertyBackgroundAttachment(element, value, pseudo, engine);
		}
		if ("background-color".equals(property)) {
			applyCSSPropertyBackgroundColor(element, value, pseudo, engine);
		}
		if ("background-image".equals(property)) {
			applyCSSPropertyBackgroundImage(element, value, pseudo, engine);
		}
		if ("background-position".equals(property)) {
			applyCSSPropertyBackgroundPosition(element, value, pseudo, engine);
		}
		if ("background-repeat".equals(property)) {
			applyCSSPropertyBackgroundRepeat(element, value, pseudo, engine);
		}
		return false;
	}

	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception {
		if ("background-attachment".equals(property)) {
			return retrieveCSSPropertyBackgroundAttachment(element, engine);
		}
		if ("background-color".equals(property)) {
			return retrieveCSSPropertyBackgroundColor(element, engine);
		}
		if ("background-image".equals(property)) {
			return retrieveCSSPropertyBackgroundImage(element, engine);
		}
		if ("background-position".equals(property)) {
			return retrieveCSSPropertyBackgroundPosition(element, engine);
		}
		if ("background-repeat".equals(property)) {
			return retrieveCSSPropertyBackgroundRepeat(element, engine);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.css2.ICSSPropertyBackgroundHandler#applyCSSPropertyBackground(java.lang.Object,
	 *      org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void applyCSSPropertyBackground(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		super.applyCSSPropertyComposite(element, "background", value, pseudo, engine);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.css2.ICSSPropertyBackgroundHandler#applyCSSPropertyBackgroundAttachment(java.lang.Object,
	 *      org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void applyCSSPropertyBackgroundAttachment(Object element,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("background-attachment");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.css2.ICSSPropertyBackgroundHandler#applyCSSPropertyBackgroundColor(java.lang.Object,
	 *      org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void applyCSSPropertyBackgroundColor(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("background-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.css2.ICSSPropertyBackgroundHandler#applyCSSPropertyBackgroundImage(java.lang.Object,
	 *      org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void applyCSSPropertyBackgroundImage(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("background-image");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.css2.ICSSPropertyBackgroundHandler#applyCSSPropertyBackgroundPosition(java.lang.Object,
	 *      org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void applyCSSPropertyBackgroundPosition(Object element,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("background-position");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.css2.ICSSPropertyBackgroundHandler#applyCSSPropertyBackgroundRepeat(java.lang.Object,
	 *      org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public void applyCSSPropertyBackgroundRepeat(Object element,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		throw new UnsupportedPropertyException("background-repeat");
	}
}
