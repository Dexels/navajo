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
import org.w3c.dom.css.CSSMediaRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

/**
 * w3c {@link CSSMediaRule} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSMediaRuleImpl implements CSSMediaRule, Serializable {

	private CSSStyleSheet parentStyleSheet = null;
	private CSSRule parentRule = null;
	private MediaList media = null;
	private CSSRuleList rules = null;

	public CSSMediaRuleImpl(CSSStyleSheet parentStyleSheet, CSSRule parentRule,
			MediaList media) {
		this.parentStyleSheet = parentStyleSheet;
		this.parentRule = parentRule;
		this.media = media;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSRule#getType()
	 */
	public short getType() {
		return MEDIA_RULE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSRule#getCssText()
	 */
	public String getCssText() {
		StringBuffer sb = new StringBuffer("@media ");
		sb.append(getMedia().toString()).append(" {");
		for (int i = 0; i < getCssRules().getLength(); i++) {
			CSSRule rule = getCssRules().item(i);
			sb.append(rule.getCssText()).append(" ");
		}
		sb.append("}");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
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
			// CSSOMParser parser = new CSSOMParser();
			CSSParser parser = null;
			CSSRule r = parser.parseRule(is);

			// The rule must be a media rule
			if (r.getType() == CSSRule.MEDIA_RULE) {
				this.media = ((CSSMediaRuleImpl) r).media;
				this.rules = ((CSSMediaRuleImpl) r).rules;
			} else {
				throw new DOMExceptionImpl(
						DOMException.INVALID_MODIFICATION_ERR,
						DOMExceptionImpl.EXPECTING_MEDIA_RULE);
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
	 * @see org.w3c.dom.css.CSSRule#getParentStyleSheet()
	 */
	public CSSStyleSheet getParentStyleSheet() {
		return parentStyleSheet;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSRule#getParentRule()
	 */
	public CSSRule getParentRule() {
		return parentRule;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSMediaRule#getMedia()
	 */
	public MediaList getMedia() {
		return media;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSMediaRule#getCssRules()
	 */
	public CSSRuleList getCssRules() {
		return rules;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSMediaRule#insertRule(java.lang.String, int)
	 */
	public int insertRule(String rule, int index) throws DOMException {
		if (parentStyleSheet != null /* && _parentStyleSheet.isReadOnly() */) {
			throw new DOMExceptionImpl(
					DOMException.NO_MODIFICATION_ALLOWED_ERR,
					DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
		}

		try {
			InputSource is = new InputSource(new StringReader(rule));
			CSSParser parser = null;
			// parser.setParentStyleSheet(_parentStyleSheet);
			// parser.setParentRule(_parentRule);
			CSSRule r = parser.parseRule(is);

			// Insert the rule into the list of rules
			((CSSRuleListImpl) getCssRules()).insert(r, index);

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new DOMExceptionImpl(DOMException.INDEX_SIZE_ERR,
					DOMExceptionImpl.ARRAY_OUT_OF_BOUNDS, e.getMessage());
		} catch (CSSException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		} catch (IOException e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
		return index;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSMediaRule#deleteRule(int)
	 */
	public void deleteRule(int index) throws DOMException {
		if (parentStyleSheet != null /* && _parentStyleSheet.isReadOnly() */) {
			throw new DOMExceptionImpl(
					DOMException.NO_MODIFICATION_ALLOWED_ERR,
					DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
		}
		try {
			((CSSRuleListImpl) getCssRules()).delete(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new DOMExceptionImpl(DOMException.INDEX_SIZE_ERR,
					DOMExceptionImpl.ARRAY_OUT_OF_BOUNDS, e.getMessage());
		}
	}

	/**
	 * Set {@link CSSRuleList}. 
	 * @param rules
	 */
	public void setRuleList(CSSRuleList rules) {
		this.rules = rules;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getCssText();
	}

}
