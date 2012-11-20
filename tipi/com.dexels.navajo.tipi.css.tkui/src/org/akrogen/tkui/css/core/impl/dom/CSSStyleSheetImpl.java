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
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;
import org.w3c.dom.stylesheets.StyleSheet;

/**
 * w3c {@link CSSStyleSheet} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSStyleSheetImpl extends AbstractCSSNode implements
		CSSStyleSheet, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801248828963658491L;
	private boolean disabled = false;
	private Node ownerNode = null;
	private StyleSheet parentStyleSheet = null;
	private String href = null;
	private String title = null;
	private MediaList media = null;
	private CSSRule ownerRule = null;
	private boolean readOnly = false;
	private CSSRuleList rules = null;

	public CSSStyleSheetImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#getType()
	 */
	public String getType() {
		return "text/css";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#getDisabled()
	 */
	public boolean getDisabled() {
		return disabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#setDisabled(boolean)
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#getOwnerNode()
	 */
	public Node getOwnerNode() {
		return ownerNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#getParentStyleSheet()
	 */
	public StyleSheet getParentStyleSheet() {
		return parentStyleSheet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#getHref()
	 */
	public String getHref() {
		return href;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.stylesheets.StyleSheet#getMedia()
	 */
	public MediaList getMedia() {
		return media;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSStyleSheet#getOwnerRule()
	 */
	public CSSRule getOwnerRule() {
		return ownerRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSStyleSheet#getCssRules()
	 */
	public CSSRuleList getCssRules() {
		return rules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSStyleSheet#insertRule(java.lang.String, int)
	 */
	public int insertRule(String rule, int index) throws DOMException {
		if (readOnly) {
			throw new DOMExceptionImpl(
					DOMException.NO_MODIFICATION_ALLOWED_ERR,
					DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
		}

		try {
			InputSource is = new InputSource(new StringReader(rule));
			CSSParser parser = getCSSParser();
			parser.setParentStyleSheet(this);
			CSSRule r = parser.parseRule(is);

			if (getCssRules().getLength() > 0) {

				// We need to check that this type of rule can legally go into
				// the requested position.
				int msg = -1;
				if (r.getType() == CSSRule.CHARSET_RULE) {

					// Index must be 0, and there can be only one charset rule
					if (index != 0) {
						msg = DOMExceptionImpl.CHARSET_NOT_FIRST;
					} else if (getCssRules().item(0).getType() == CSSRule.CHARSET_RULE) {
						msg = DOMExceptionImpl.CHARSET_NOT_UNIQUE;
					}
				} else if (r.getType() == CSSRule.IMPORT_RULE) {

					// Import rules must preceed all other rules (except
					// charset rules)
					if (index <= getCssRules().getLength()) {
						for (int i = 0; i < index; i++) {
							int rt = getCssRules().item(i).getType();
							if ((rt != CSSRule.CHARSET_RULE)
									|| (rt != CSSRule.IMPORT_RULE)) {
								msg = DOMExceptionImpl.IMPORT_NOT_FIRST;
								break;
							}
						}
					}
				}

				if (msg > -1) {
					throw new DOMExceptionImpl(
							DOMException.HIERARCHY_REQUEST_ERR, msg);
				}
			}

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
	 * 
	 * @see org.w3c.dom.css.CSSStyleSheet#deleteRule(int)
	 */
	public void deleteRule(int index) throws DOMException {
		if (readOnly) {
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
	 * Return true if readonly and false othrewise.
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * Set readonly
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Set owner {@link Node}.
	 */
	public void setOwnerNode(Node ownerNode) {
		this.ownerNode = ownerNode;
	}

	/**
	 * Set parent {@link StyleSheet}.
	 */
	public void setParentStyleSheet(StyleSheet parentStyleSheet) {
		this.parentStyleSheet = parentStyleSheet;
	}

	/**
	 * Set href.
	 * 
	 * @param href
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Set title.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set media text.
	 * 
	 * @param mediaText
	 */
	public void setMedia(String mediaText) {
		// MediaList _media = null;
	}

	/**
	 * Set owner {@link CSSRule} rule.
	 * 
	 * @param ownerRule
	 */
	public void setOwnerRule(CSSRule ownerRule) {
		this.ownerRule = ownerRule;
	}

	/**
	 * Set {@link CSSRuleList} rules.
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
		return rules.toString();
	}

}
