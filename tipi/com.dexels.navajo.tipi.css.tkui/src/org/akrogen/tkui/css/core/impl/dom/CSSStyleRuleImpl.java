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

import org.akrogen.tkui.css.core.dom.CSSPropertyList;
import org.akrogen.tkui.css.core.dom.ExtendedCSSRule;
import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.exceptions.DOMExceptionImpl;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

/**
 * w3c {@link CSSStyleRule} and {@link ExtendedCSSRule} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSStyleRuleImpl extends AbstractCSSNode implements CSSStyleRule,
		ExtendedCSSRule, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5029571524712971239L;
	private CSSStyleSheet parentStyleSheet = null;
	private CSSRule parentRule = null;
	private SelectorList selectors = null;
	private CSSStyleDeclaration styleDeclaration = null;

	public CSSStyleRuleImpl(CSSStyleSheet parentStyleSheet, CSSRule parentRule,
			SelectorList selectors) {
		this.parentStyleSheet = parentStyleSheet;
		this.parentRule = parentRule;
		this.selectors = selectors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRule#getType()
	 */
	public short getType() {
		return CSSStyleRule.STYLE_RULE;
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
	 * @see org.w3c.dom.css.CSSRule#getParentStyleSheet()
	 */
	public CSSStyleSheet getParentStyleSheet() {
		return parentStyleSheet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSStyleRule#getSelectorText()
	 */
	public String getSelectorText() {
		return selectors.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSStyleRule#setSelectorText(java.lang.String)
	 */
	public void setSelectorText(String selectorText) throws DOMException {
		if (parentStyleSheet != null /* && parentStyleSheet.isReadOnly() */) {
			throw new DOMExceptionImpl(
					DOMException.NO_MODIFICATION_ALLOWED_ERR,
					DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
		}

		try {
			InputSource is = new InputSource(new StringReader(selectorText));
			CSSParser parser = getCSSParser();
			selectors = parser.parseSelectors(is);
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
	 * @see org.w3c.dom.css.CSSRule#getCssText()
	 */
	public String getCssText() {
		return getSelectorText() + " " + getStyle().toString();
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

			// The rule must be a style rule
			if (r.getType() == CSSRule.STYLE_RULE) {
				selectors = ((CSSStyleRuleImpl) r).selectors;
				styleDeclaration = ((CSSStyleRuleImpl) r).styleDeclaration;
			} else {
				throw new DOMExceptionImpl(
						DOMException.INVALID_MODIFICATION_ERR,
						DOMExceptionImpl.EXPECTING_STYLE_RULE);
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
	 * @see org.w3c.dom.css.CSSStyleRule#getStyle()
	 */
	public CSSStyleDeclaration getStyle() {
		return styleDeclaration;
	}

	/**
	 * Set {@link CSSStyleDeclaration} CSS style declaration.
	 */
	public void setStyle(CSSStyleDeclaration styleDeclaration) {
		this.styleDeclaration = styleDeclaration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getCssText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.ExtendedCSSRule#getSelectorList()
	 */
	public SelectorList getSelectorList() {
		return selectors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.ExtendedCSSRule#getCSSPropertyList()
	 */
	public CSSPropertyList getCSSPropertyList() {
		CSSStyleDeclarationImpl styleDeclaration = (CSSStyleDeclarationImpl) getStyle();
		return styleDeclaration.getCSSPropertyList();
	}
}
