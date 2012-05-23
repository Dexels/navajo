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
import org.w3c.dom.css.RGBColor;

/**
 * w3c {@link RGBColor} implementation built with {@link LexicalUnit}.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class RGBColorImpl implements RGBColor, Serializable {

	private CSSPrimitiveValue red = null;
	private CSSPrimitiveValue green = null;
	private CSSPrimitiveValue blue = null;

	protected RGBColorImpl(LexicalUnit lu) {
		LexicalUnit next = lu;
		red = new CSSValueImpl(next, true);
		next = next.getNextLexicalUnit();
		next = next.getNextLexicalUnit();
		green = new CSSValueImpl(next, true);
		next = next.getNextLexicalUnit();
		next = next.getNextLexicalUnit();
		blue = new CSSValueImpl(next, true);
	}

	protected RGBColorImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.RGBColor#getRed()
	 */
	public CSSPrimitiveValue getRed() {
		return red;
	}

	/**
	 * Set the red value of the RGB color.
	 * 
	 * @param red
	 */
	public void setRed(CSSPrimitiveValue red) {
		this.red = red;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.RGBColor#getGreen()
	 */
	public CSSPrimitiveValue getGreen() {
		return green;
	}

	/**
	 * Set the green value of the RGB color.
	 * 
	 * @param red
	 */
	public void setGreen(CSSPrimitiveValue green) {
		this.green = green;
	}

	/*
	 * (non-Javadoc)
	 * @see org.w3c.dom.css.RGBColor#getBlue()
	 */
	public CSSPrimitiveValue getBlue() {
		return blue;
	}

	/**
	 * Set the blue value of the RGB color.
	 * 
	 * @param red
	 */
	public void setBlue(CSSPrimitiveValue blue) {
		this.blue = blue;
	}

	public String toString() {
		return "rgb(" + red.toString() + ", " + green.toString() + ", "
				+ blue.toString() + ")";
	}
}
