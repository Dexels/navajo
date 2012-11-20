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

import org.akrogen.tkui.css.core.dom.CSSProperty;
import org.w3c.dom.css.CSSValue;

/**
 * w3c {@link CSSProperty} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSPropertyImpl implements CSSProperty, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2014016277827864349L;
	private String name;
	private CSSValue value;
	private boolean important;

	/** Creates new Property */
	public CSSPropertyImpl(String name, CSSValue value, boolean important) {
		this.name = name;
		this.value = value;
		this.important = important;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSProperty#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSProperty#getValue()
	 */
	public CSSValue getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSProperty#isImportant()
	 */
	public boolean isImportant() {
		return important;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSProperty#setValue(org.w3c.dom.css.CSSValue)
	 */
	public void setValue(CSSValue value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.CSSProperty#setImportant(boolean)
	 */
	public void setImportant(boolean important) {
		this.important = important;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + ": " + value.toString()
				+ (important ? " !important" : "");
	}

}
