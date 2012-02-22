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
package org.akrogen.tkui.css.core.impl.dom;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.exceptions.DOMExceptionImpl;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSFontFaceRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;

/**
 * w3c {@link CSSFontFaceRule} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSFontFaceRuleImpl extends AbstractCSSNode implements
		CSSFontFaceRule, Serializable {

	private CSSStyleSheet parentStyleSheet = null;
	private CSSRule parentRule = null;
	private CSSStyleDeclaration style = null;

	public CSSFontFaceRuleImpl(CSSStyleSheet parentStyleSheet,
			CSSRule parentRule) {
		this.parentStyleSheet = parentStyleSheet;
		this.parentRule = parentRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getType()
	 */
	public short getType() {
		return FONT_FACE_RULE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getCssText()
	 */
	public String getCssText() {
		return "@font-face " + getStyle().getCssText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#setCssText(java.lang.String)
	 */
	public void setCssText(String cssText) throws DOMException {
		if (parentStyleSheet != null /* && _parentStyleSheet.isReadOnly() */) {
			throw new DOMExceptionImpl(
					DOMException.NO_MODIFICATION_ALLOWED_ERR,
					DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
		}

		try {
			InputSource is = new InputSource(new StringReader(cssText));
			CSSParser parser = getCSSParser();
			CSSRule r = parser.parseRule(is);

			// The rule must be a font face rule
			if (r.getType() == CSSRule.FONT_FACE_RULE) {
				this.style = ((CSSFontFaceRuleImpl) r).style;
			} else {
				throw new DOMExceptionImpl(
						DOMException.INVALID_MODIFICATION_ERR,
						DOMExceptionImpl.EXPECTING_FONT_FACE_RULE);
			}
		} catch (CSSException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		} catch (IOException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getParentStyleSheet()
	 */
	public CSSStyleSheet getParentStyleSheet() {
		return parentStyleSheet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getParentRule()
	 */
	public CSSRule getParentRule() {
		return parentRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSFontFaceRule#getStyle()
	 */
	public CSSStyleDeclaration getStyle() {
		return style;
	}

	/**
	 * Set {@link CSSStyleDeclaration}.
	 */
	public void setStyle(CSSStyleDeclaration style) {
		this.style = style;
	}

}
