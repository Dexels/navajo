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
package org.akrogen.tkui.css.core.dom.properties.providers;

import org.akrogen.tkui.css.core.dom.CSSStylableElement;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandlerProvider;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 * Abstract CSS Property handler.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSPropertyHandlerProvider implements
		ICSSPropertyHandlerProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandlerProvider#getDefaultCSSStyleDeclaration(org.akrogen.tkui.css.core.engine.CSSEngine,
	 *      java.lang.Object, org.w3c.dom.css.CSSStyleDeclaration)
	 */
	public CSSStyleDeclaration getDefaultCSSStyleDeclaration(CSSEngine engine,
			Object widget, CSSStyleDeclaration newStyle, String pseudoE) throws Exception {
		Element elt = engine.getElement(widget);
		if (elt != null) {
			if (elt instanceof CSSStylableElement) {
				CSSStylableElement stylableElement = (CSSStylableElement) elt;
				return getDefaultCSSStyleDeclaration(engine, stylableElement,
						newStyle, pseudoE);
			}
		}
		return null;
	}

	/**
	 * Return the CSS property from the CSS <code>propertyName</code> of the
	 * <code>stylableElement</code>.
	 * 
	 * @param engine
	 * @param stylableElement
	 * @param propertyName
	 * @return
	 */
	protected String getCSSPropertyStyle(CSSEngine engine,
			CSSStylableElement stylableElement, String propertyName) {
		String propertyValue = engine.retrieveCSSProperty(stylableElement,
				propertyName);
		StringBuffer style = null;
		if (propertyValue != null) {
			if (style == null)
				style = new StringBuffer();
			style.append(propertyName);
			style.append(":");
			style.append(propertyValue);
			style.append(";");
		}
		return (style == null ? null : style.toString());
	}

	/**
	 * Return the default CSS style declaration of the
	 * {@link CSSStylableElement} <code>stylableElement</code> before apply
	 * the <code>newStyle</code> {@link CSSStyleDeclaration}.
	 * 
	 * @param engine
	 * @param stylableElement
	 * @param newStyle
	 * @return
	 * @throws Exception
	 */
	protected abstract CSSStyleDeclaration getDefaultCSSStyleDeclaration(
			CSSEngine engine, CSSStylableElement stylableElement,
			CSSStyleDeclaration newStyle, String pseudoE) throws Exception;
}
