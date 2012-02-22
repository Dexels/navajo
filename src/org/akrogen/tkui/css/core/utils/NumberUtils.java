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
package org.akrogen.tkui.css.core.utils;

/**
 * Helper for Number.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class NumberUtils {

	/**
	 * Return true if long <code>x</code> is odd and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isOdd(long x) {
		return ((x % 2) == 1);
	}

	/**
	 * Return true if int <code>x</code> is odd and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isOdd(int x) {
		return isOdd((long) x);
	}

	/**
	 * Return true if short <code>x</code> is odd and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isOdd(short x) {
		return isOdd((long) x);
	}

	/**
	 * Return true if byte <code>x</code> is odd and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isOdd(byte x) {
		return isOdd((long) x);
	}

	/**
	 * Return true if long <code>x</code> is even and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isEven(long x) {
		return ((x % 2) == 0);
	}

	/**
	 * Return true if int <code>x</code> is event and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isEven(int x) {
		return isEven((long) x);
	}

	/**
	 * Return true if short <code>x</code> is even and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isEven(short x) {
		return isEven((long) x);
	}

	/**
	 * Return true if byte <code>x</code> is even and false otherwise.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isEven(byte x) {
		return isEven((long) x);
	}
}
