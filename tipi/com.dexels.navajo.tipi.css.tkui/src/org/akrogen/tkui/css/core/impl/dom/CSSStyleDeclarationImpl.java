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

import java.io.Serializable;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import org.akrogen.tkui.css.core.dom.CSSProperty;
import org.akrogen.tkui.css.core.dom.CSSPropertyList;
import org.akrogen.tkui.css.core.dom.ExtendedCSSRule;
import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.exceptions.DOMExceptionImpl;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;

/**
 * w3c {@link CSSStyleDeclaration} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSStyleDeclarationImpl extends AbstractCSSNode implements
		CSSStyleDeclaration, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5517288842283510636L;
	private CSSRule parentRule;
	private Vector properties = new Vector();

	public CSSStyleDeclarationImpl(CSSRule parentRule) {
		this.parentRule = parentRule;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getCssText()
	 */
	public String getCssText() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		// if newlines requested in text
		// sb.append("\n");
		for (int i = 0; i < properties.size(); ++i) {
			CSSProperty p = (CSSProperty) properties.elementAt(i);
			if (p != null) {
				sb.append(p.toString());
			}
			if (i < properties.size() - 1) {
				sb.append("; ");
			}
			// if newlines requested in text
			// sb.append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#setCssText(java.lang.String)
	 */
	public void setCssText(String cssText) throws DOMException {
		try {
			InputSource is = new InputSource(new StringReader(cssText));
			CSSParser parser = getCSSParser();
			properties.removeAllElements();
			parser.parseStyleDeclaration(this, is);
		} catch (Exception e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyValue(java.lang.String)
	 */
	public String getPropertyValue(String propertyName) {
		CSSProperty p = getCSSProperty(propertyName);
		return (p != null) ? p.getValue().toString() : "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyCSSValue(java.lang.String)
	 */
	public CSSValue getPropertyCSSValue(String propertyName) {
		CSSProperty p = getCSSProperty(propertyName);
		return (p != null) ? p.getValue() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#removeProperty(java.lang.String)
	 */
	public String removeProperty(String propertyName) throws DOMException {
		for (int i = 0; i < properties.size(); i++) {
			CSSProperty p = (CSSProperty) properties.elementAt(i);
			if (p.getName().equalsIgnoreCase(propertyName)) {
				properties.removeElementAt(i);
				return p.getValue().toString();
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getPropertyPriority(java.lang.String)
	 */
	public String getPropertyPriority(String propertyName) {
		CSSProperty p = getCSSProperty(propertyName);
		if (p != null) {
			return p.isImportant() ? "important" : "";
		} else {
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#setProperty(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setProperty(String propertyName, String value, String priority)
			throws DOMException {
		try {
			InputSource is = new InputSource(new StringReader(value));
			CSSParser parser = getCSSParser();
			CSSValue expr = parser.parsePropertyValue(is);
			CSSProperty p = getCSSProperty(propertyName);
			boolean important = (priority != null) ? priority
					.equalsIgnoreCase("important") : false;
			if (p == null) {
				p = new CSSPropertyImpl(propertyName, expr, important);
				addProperty(p);
			} else {
				p.setValue(expr);
				p.setImportant(important);
			}
		} catch (Exception e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR,
					DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getLength()
	 */
	public int getLength() {
		return properties.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#item(int)
	 */
	public String item(int index) {
		CSSProperty p = (CSSProperty) properties.elementAt(index);
		return (p != null) ? p.getName() : "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.CSSStyleDeclaration#getParentRule()
	 */
	public CSSRule getParentRule() {
		return parentRule;
	}

	/**
	 * Add {@link CSSProperty} to the CSS properties list.
	 */
	public void addProperty(CSSProperty p) {
		properties.addElement(p);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private CSSProperty getCSSProperty(String name) {
		for (int i = 0; i < properties.size(); i++) {
			CSSProperty p = (CSSProperty) properties.elementAt(i);
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}

	public String toString() {
		return getCssText();
	}

	/**
	 * Return {@link CSSPropertyList}.
	 */
	public CSSPropertyList getCSSPropertyList() {
		CSSPropertyListImpl l = new CSSPropertyListImpl();
		for (Iterator iterator = properties.iterator(); iterator.hasNext();) {
			CSSProperty property = (CSSProperty) iterator.next();
			l.add(property);
		}
		return l;
	}

	/**
	 * Return the SAC {@link Selector} linked to this {@link CSSStyleDeclaration}.
	 * @return
	 */
	public Selector getSelector() {
		if (parentRule == null)
			return null;
		if (parentRule instanceof ExtendedCSSRule) {
			ExtendedCSSRule rule = (ExtendedCSSRule) parentRule;
			SelectorList selectorList = rule.getSelectorList();
			int length = selectorList.getLength();
			for (int i = 0; i < length; i++) {
				Selector selector = selectorList.item(i);
				return selector;
			}
		}
		return null;
	}

}
