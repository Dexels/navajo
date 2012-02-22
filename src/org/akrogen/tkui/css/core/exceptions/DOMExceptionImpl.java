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
package org.akrogen.tkui.css.core.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

import org.w3c.dom.DOMException;

/**
 * DOM exception implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class DOMExceptionImpl extends DOMException {

	public static final int SYNTAX_ERROR = 0;
	public static final int ARRAY_OUT_OF_BOUNDS = 1;
	public static final int READ_ONLY_STYLE_SHEET = 2;
	public static final int EXPECTING_UNKNOWN_RULE = 3;
	public static final int EXPECTING_STYLE_RULE = 4;
	public static final int EXPECTING_CHARSET_RULE = 5;
	public static final int EXPECTING_IMPORT_RULE = 6;
	public static final int EXPECTING_MEDIA_RULE = 7;
	public static final int EXPECTING_FONT_FACE_RULE = 8;
	public static final int EXPECTING_PAGE_RULE = 9;
	public static final int FLOAT_ERROR = 10;
	public static final int STRING_ERROR = 11;
	public static final int COUNTER_ERROR = 12;
	public static final int RECT_ERROR = 13;
	public static final int RGBCOLOR_ERROR = 14;
	public static final int CHARSET_NOT_FIRST = 15;
	public static final int CHARSET_NOT_UNIQUE = 16;
	public static final int IMPORT_NOT_FIRST = 17;
	public static final int NOT_FOUND = 18;
	public static final int NOT_IMPLEMENTED = 19;

	private static ResourceBundle exceptionResource = ResourceBundle.getBundle(
			ExceptionResource.class.getName(), Locale.getDefault());

	public DOMExceptionImpl(short code, int messageKey) {
		super(code, exceptionResource.getString(keyString(messageKey)));
	}

	public DOMExceptionImpl(int code, int messageKey) {
		super((short) code, exceptionResource.getString(keyString(messageKey)));
	}

	public DOMExceptionImpl(short code, int messageKey, String info) {
		super(code, exceptionResource.getString(keyString(messageKey)));
	}

	private static String keyString(int key) {
		return "s" + String.valueOf(key);
	}

}
