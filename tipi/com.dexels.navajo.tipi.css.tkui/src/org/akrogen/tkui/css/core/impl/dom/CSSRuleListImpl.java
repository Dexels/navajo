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
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;

/**
 * w3c {@link CSSRuleList} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSRuleListImpl implements CSSRuleList, Serializable {

	private List rules = null;

	public CSSRuleListImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRuleList#getLength()
	 */
	public int getLength() {
		return (rules != null) ? rules.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSSRuleList#item(int)
	 */
	public CSSRule item(int index) {
		return (rules != null) ? (CSSRule) rules.get(index) : null;
	}

	/**
	 * Add {@link CSSRule} to the collection of {@link CSSRule}.
	 */
	public void add(CSSRule rule) {
		if (rules == null) {
			rules = new ArrayList();
		}
		rules.add(rule);
	}

	/**
	 * Insert {@link CSSRule} rule at <code>index</code>.
	 */
	public void insert(CSSRule rule, int index) {
		if (rules == null) {
			rules = new ArrayList();
		}
		rules.add(index, rule);
	}

	/**
	 * Delete {@link CSSRule} rule at <code>index</code>.
	 * 
	 * @param index
	 */
	public void delete(int index) {
		if (rules == null) {
			rules = new ArrayList();
		}
		rules.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < getLength(); i++) {
			sb.append(item(i).toString()).append("\r\n");
		}
		return sb.toString();
	}

}
