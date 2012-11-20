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
import org.w3c.dom.css.CSSPageRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;

/**
 * w3c {@link CSSPageRule} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSPageRuleImpl extends AbstractCSSNode implements CSSPageRule,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1721626387917821911L;
	private CSSStyleSheet parentStyleSheet = null;
	private CSSRule parentRule = null;
	private String ident = null;
	private String pseudoPage = null;
	private CSSStyleDeclaration style = null;

	public CSSPageRuleImpl(CSSStyleSheet parentStyleSheet, CSSRule parentRule,
			String ident, String pseudoPage) {
		this.parentStyleSheet = parentStyleSheet;
		this.parentRule = parentRule;
		this.ident = ident;
		this.pseudoPage = pseudoPage;
	}

	public short getType() {
		return PAGE_RULE;
	}

	public String getCssText() {
		String sel = getSelectorText();
		return "@page " + sel + ((sel.length() > 0) ? " " : "")
				+ getStyle().getCssText();
	}

	public void setCssText(String cssText) throws DOMException {
		if (this.parentStyleSheet != null /* && _parentStyleSheet.isReadOnly() */) {
			throw new DOMExceptionImpl(
					DOMException.NO_MODIFICATION_ALLOWED_ERR,
					DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
		}

		try {
			InputSource is = new InputSource(new StringReader(cssText));
			CSSParser parser = getCSSParser();
			CSSRule r = parser.parseRule(is);

			// The rule must be a page rule
			if (r.getType() == CSSRule.PAGE_RULE) {
				this.ident = ((CSSPageRuleImpl) r).ident;
				this.pseudoPage = ((CSSPageRuleImpl) r).pseudoPage;
				this.style = ((CSSPageRuleImpl) r).style;
			} else {
				throw new DOMExceptionImpl(
						DOMException.INVALID_MODIFICATION_ERR,
						DOMExceptionImpl.EXPECTING_PAGE_RULE);
			}
		} catch (CSSException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		} catch (IOException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
	}

	public CSSStyleSheet getParentStyleSheet() {
		return parentStyleSheet;
	}

	public CSSRule getParentRule() {
		return parentRule;
	}

	public String getSelectorText() {
		return ((ident != null) ? ident : "")
				+ ((pseudoPage != null) ? ":" + pseudoPage : "");
	}

	public void setSelectorText(String selectorText) throws DOMException {
	}

	public CSSStyleDeclaration getStyle() {
		return style;
	}

	protected void setIdent(String ident) {
		this.ident = ident;
	}

	protected void setPseudoPage(String pseudoPage) {
		this.pseudoPage = pseudoPage;
	}

	public void setStyle(CSSStyleDeclarationImpl style) {
		this.style = style;
	}

}
