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
package org.akrogen.tkui.css.core.impl.sac;

import java.util.Stack;

import org.akrogen.tkui.css.core.dom.CSSProperty;
import org.akrogen.tkui.css.core.impl.dom.CSSFontFaceRuleImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSImportRuleImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSMediaRuleImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSPageRuleImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSPropertyImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSRuleListImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSStyleDeclarationImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSStyleRuleImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSStyleSheetImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSUnknownRuleImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSValueImpl;
import org.akrogen.tkui.css.core.impl.dom.MediaListImpl;
import org.akrogen.tkui.css.core.sac.ExtendedDocumentHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;

/**
 * This class provides an implementation for the
 * {@link org.akrogen.tkui.css.core.sac.ExtendedDocumentHandler} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSDocumentHandlerImpl implements ExtendedDocumentHandler {

	private static Log logger = LogFactory.getLog(CSSDocumentHandlerImpl.class);

	private Stack nodeStack;
	private Object nodeRoot = null;

	private CSSStyleSheet parentStyleSheet;

	public CSSDocumentHandlerImpl() {
	}

	public Object getRoot() {
		return nodeRoot;
	}

	public void startDocument(InputSource source) throws CSSException {
		if (getNodeStack().empty()) {
			CSSStyleSheetImpl styleSheet = new CSSStyleSheetImpl();
			parentStyleSheet = styleSheet;

			// Create the rule list
			CSSRuleListImpl rules = new CSSRuleListImpl();
			styleSheet.setRuleList(rules);
			getNodeStack().push(styleSheet);
			getNodeStack().push(rules);
		} else {
			// Error
		}
	}

	public void endDocument(InputSource source) throws CSSException {

		// Pop the rule list and style sheet nodes
		getNodeStack().pop();
		nodeRoot = getNodeStack().pop();
	}

	public void comment(String text) throws CSSException {
	}

	public void ignorableAtRule(String atRule) throws CSSException {

		// Create the unknown rule and add it to the rule list
		CSSUnknownRuleImpl ir = new CSSUnknownRuleImpl(parentStyleSheet, null,
				atRule);
		if (!getNodeStack().empty()) {
			((CSSRuleListImpl) getNodeStack().peek()).add(ir);
		} else {
			// _getNodeStack().push(ir);
			nodeRoot = ir;
		}
	}

	public void namespaceDeclaration(String prefix, String uri)
			throws CSSException {
		if (logger.isDebugEnabled()) {
			logger.debug("Declare namespace [prefix=" + prefix + ", uri=" + uri
					+ "]");
		}
	}

	public void importStyle(String uri, SACMediaList media,
			String defaultNamespaceURI) throws CSSException {

		// Create the import rule and add it to the rule list
		CSSImportRuleImpl ir = new CSSImportRuleImpl(parentStyleSheet, null,
				uri, new MediaListImpl(media));
		if (!getNodeStack().empty()) {
			((CSSRuleListImpl) getNodeStack().peek()).add(ir);
		} else {
			// _getNodeStack().push(ir);
			nodeRoot = ir;
		}
	}

	public void startMedia(SACMediaList media) throws CSSException {

		// Create the media rule and add it to the rule list
		CSSMediaRuleImpl mr = new CSSMediaRuleImpl(parentStyleSheet, null,
				new MediaListImpl(media));
		if (!getNodeStack().empty()) {
			((CSSRuleListImpl) getNodeStack().peek()).add(mr);
		}

		// Create the rule list
		CSSRuleListImpl rules = new CSSRuleListImpl();
		mr.setRuleList(rules);
		getNodeStack().push(mr);
		getNodeStack().push(rules);
	}

	public void endMedia(SACMediaList media) throws CSSException {

		// Pop the rule list and media rule nodes
		getNodeStack().pop();
		nodeRoot = getNodeStack().pop();
	}

	public void startPage(String name, String pseudo_page) throws CSSException {

		// // Create the page rule and add it to the rule list
		CSSPageRuleImpl pageRule = new CSSPageRuleImpl(parentStyleSheet, null,
				name, pseudo_page);
		if (!getNodeStack().empty()) {
			((CSSRuleListImpl) getNodeStack().peek()).add(pageRule);
		}

		// Create the style declaration
		CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(pageRule);
		pageRule.setStyle(decl);
		getNodeStack().push(pageRule);
		getNodeStack().push(decl);
	}

	public void endPage(String name, String pseudo_page) throws CSSException {

		// Pop both the style declaration and the page rule nodes
		getNodeStack().pop();
		nodeRoot = getNodeStack().pop();
	}

	public void startFontFace() throws CSSException {

		// Create the font face rule and add it to the rule list
		CSSFontFaceRuleImpl fontFaceRule = new CSSFontFaceRuleImpl(
				parentStyleSheet, null);
		if (!getNodeStack().empty()) {
			((CSSRuleListImpl) getNodeStack().peek()).add(fontFaceRule);
		}

		// Create the style declaration
		CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(fontFaceRule);
		fontFaceRule.setStyle(decl);
		getNodeStack().push(fontFaceRule);
		getNodeStack().push(decl);
	}

	public void endFontFace() throws CSSException {

		// Pop both the style declaration and the font face rule nodes
		getNodeStack().pop();
		nodeRoot = getNodeStack().pop();
	}

	public void startSelector(SelectorList selectors) throws CSSException {

		// Create the style rule and add it to the rule list
		CSSStyleRuleImpl rule = new CSSStyleRuleImpl(parentStyleSheet, null,
				selectors);
		if (!getNodeStack().empty()) {
			((CSSRuleListImpl) getNodeStack().peek()).add(rule);
		}

		// Create the style declaration
		CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(rule);
		rule.setStyle(decl);
		getNodeStack().push(rule);
		getNodeStack().push(decl);
	}

	public void endSelector(SelectorList selectors) throws CSSException {

		// Pop both the style declaration and the style rule nodes
		getNodeStack().pop();
		nodeRoot = getNodeStack().pop();
	}

	public void property(String name, LexicalUnit value, boolean important)
			throws CSSException {
		CSSStyleDeclarationImpl decl = (CSSStyleDeclarationImpl) getNodeStack()
				.peek();
		decl.addProperty(getCSSProperty(decl, name, value, important));
	}

	protected CSSProperty getCSSProperty(CSSStyleDeclaration styleDeclaration,
			String name, LexicalUnit value, boolean important) {
		return new CSSPropertyImpl(name, new CSSValueImpl(value), important);
	}

	public Object getNodeRoot() {
		return nodeRoot;
	}

	public void setNodeStack(Stack nodeStack) {
		this.nodeStack = nodeStack;
	}

	public Stack getNodeStack() {
		if (nodeStack == null)
			nodeStack = new Stack();
		return nodeStack;
	}

}
