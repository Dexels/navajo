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
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

/**
 * w3c {@link CSSImportRule} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSImportRuleImpl extends AbstractCSSNode implements
		CSSImportRule, Serializable {

	private CSSStyleSheet parentStyleSheet = null;
	private CSSRule parentRule = null;
	private String href = null;
	private MediaList media = null;

	public CSSImportRuleImpl(CSSStyleSheet parentStyleSheet,
			CSSRule parentRule, String href, MediaList media) {
		this.parentStyleSheet = parentStyleSheet;
		this.parentRule = parentRule;
		this.href = href;
		this.media = media;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSRule#getType()
	 */
	public short getType() {
		return IMPORT_RULE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSRule#getCssText()
	 */
	public String getCssText() {
		StringBuffer sb = new StringBuffer();
		sb.append("@import url(").append(getHref()).append(")");
		if (getMedia().getLength() > 0) {
			sb.append(" ").append(getMedia().toString());
		}
		sb.append(";");
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
			CSSParser parser = getCSSParser();
			CSSRule r = parser.parseRule(is);

			// The rule must be an import rule
			if (r.getType() == CSSRule.IMPORT_RULE) {
				this.href = ((CSSImportRuleImpl) r).href;
				this.media = ((CSSImportRuleImpl) r).media;
			} else {
				throw new DOMExceptionImpl(
						DOMException.INVALID_MODIFICATION_ERR,
						DOMExceptionImpl.EXPECTING_IMPORT_RULE);
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
	 * @see org.w3c.dom.css.CSSImportRule#getHref()
	 */
	public String getHref() {
		return href;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSImportRule#getMedia()
	 */
	public MediaList getMedia() {
		return media;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSImportRule#getStyleSheet()
	 */
	public CSSStyleSheet getStyleSheet() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getCssText();
	}

}
