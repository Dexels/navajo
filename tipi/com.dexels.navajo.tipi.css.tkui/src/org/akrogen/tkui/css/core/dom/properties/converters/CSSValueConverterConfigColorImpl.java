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
package org.akrogen.tkui.css.core.dom.properties.converters;

/**
 * CSS Value converter color config to format the CSSValue string color.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSValueConverterConfigColorImpl implements
		ICSSValueConverterColorConfig {

	public static final ICSSValueConverterConfig COLOR_HEXA_FORMAT_CONFIG = new CSSValueConverterConfigColorImpl(
			COLOR_HEXA_FORMAT);

	public static final ICSSValueConverterConfig COLOR_NAME_FORMAT_CONFIG = new CSSValueConverterConfigColorImpl(
			COLOR_NAME_FORMAT);

	public static final ICSSValueConverterConfig COLOR_RGB_FORMAT_CONFIG = new CSSValueConverterConfigColorImpl(
			COLOR_RGB_FORMAT);

	private int format;

	public CSSValueConverterConfigColorImpl(int format) {
		this.format = format;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverterColorConfig#getFormat()
	 */
	public int getFormat() {
		return format;
	}

}
