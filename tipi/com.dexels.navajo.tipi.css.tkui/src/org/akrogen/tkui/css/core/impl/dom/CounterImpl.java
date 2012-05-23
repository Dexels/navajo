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

import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.css.Counter;

/**
 * w3c {@link Counter} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CounterImpl implements Counter, Serializable {

	private String identifier;
	private String listStyle;
	private String separator;

	/** Creates new CounterImpl */
	public CounterImpl(boolean separatorSpecified, LexicalUnit lu) {
		LexicalUnit next = lu;
		identifier = next.getStringValue();
		next = next.getNextLexicalUnit();
		if (separatorSpecified && (next != null)) {
			next = next.getNextLexicalUnit();
			separator = next.getStringValue();
			next = next.getNextLexicalUnit();
		}
		if (next != null) {
			listStyle = next.getStringValue();
			next = next.getNextLexicalUnit();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.Counter#getIdentifier()
	 */
	public String getIdentifier() {
		return identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.Counter#getListStyle()
	 */
	public String getListStyle() {
		return listStyle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.Counter#getSeparator()
	 */
	public String getSeparator() {
		return separator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (separator == null) {
			// This is a 'counter()' function
			sb.append("counter(");
		} else {
			// This is a 'counters()' function
			sb.append("counters(");
		}
		sb.append(identifier);
		if (separator != null) {
			sb.append(", \"").append(separator).append("\"");
		}
		if (listStyle != null) {
			sb.append(", ").append(listStyle);
		}
		sb.append(")");
		return sb.toString();
	}
}