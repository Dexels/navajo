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

import java.util.ArrayList;
import java.util.List;

import org.akrogen.tkui.css.core.dom.CSSProperty;
import org.akrogen.tkui.css.core.dom.CSSPropertyList;

/**
 * w3c {@link CSSPropertyList} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSPropertyListImpl implements CSSPropertyList {

	private List properties = null;

	public CSSPropertyListImpl() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.akrogen.tkui.css.core.dom.CSSPropertyList#getLength()
	 */
	public int getLength() {
		return (properties != null) ? properties.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.akrogen.tkui.css.core.dom.CSSPropertyList#item(int)
	 */
	public CSSProperty item(int index) {
		return (properties != null) ? (CSSProperty) properties.get(index)
				: null;
	}

	/**
	 * Add {@link CSSProperty}.
	 */
	public void add(CSSProperty property) {
		if (properties == null) {
			properties = new ArrayList();
		}
		properties.add(property);
	}

	/**
	 * Insert {@link CSSProperty} at <code>index</code>.
	 * @param property
	 * @param index
	 */
	public void insert(CSSProperty property, int index) {
		if (properties == null) {
			properties = new ArrayList();
		}
		properties.add(index, property);
	}

	/**
	 * Delete {@link CSSProperty} at <code>index</code>.
	 * @param index
	 */
	public void delete(int index) {
		if (properties == null) {
			properties = new ArrayList();
		}
		properties.remove(index);
	}

	/*
	 * (non-Javadoc)
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
