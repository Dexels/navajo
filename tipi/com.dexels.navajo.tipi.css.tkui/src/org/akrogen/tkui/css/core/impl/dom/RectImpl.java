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
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Rect;

/**
 * w3c {@link Rect} implementation built with {@link LexicalUnit}.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class RectImpl implements Rect, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7676864031033210647L;
	private CSSPrimitiveValue left;
	private CSSPrimitiveValue top;
	private CSSPrimitiveValue right;
	private CSSPrimitiveValue bottom;

	/** Creates new RectImpl */
	public RectImpl(LexicalUnit lu) {
		LexicalUnit next = lu;
		this.left = new CSSValueImpl(next, true);
		next = next.getNextLexicalUnit();
		next = next.getNextLexicalUnit();
		this.top = new CSSValueImpl(next, true);
		next = next.getNextLexicalUnit();
		next = next.getNextLexicalUnit();
		this.right = new CSSValueImpl(next, true);
		next = next.getNextLexicalUnit();
		next = next.getNextLexicalUnit();
		this.bottom = new CSSValueImpl(next, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.Rect#getTop()
	 */
	public CSSPrimitiveValue getTop() {
		return top;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.Rect#getRight()
	 */
	public CSSPrimitiveValue getRight() {
		return right;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.Rect#getBottom()
	 */
	public CSSPrimitiveValue getBottom() {
		return bottom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.Rect#getLeft()
	 */
	public CSSPrimitiveValue getLeft() {
		return left;
	}

	public String toString() {
		return new StringBuffer().append("rect(").append(left.toString())
				.append(", ").append(top.toString()).append(", ").append(
						right.toString()).append(", ")
				.append(bottom.toString()).append(")").toString();
	}
}