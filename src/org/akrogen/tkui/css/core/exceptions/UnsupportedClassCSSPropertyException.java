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

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.dom.properties.providers.CSSPropertyHandlerLazyProviderImpl;

/**
 * Exception used when java Class CSS property is not retrieved.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * @see CSSPropertyHandlerLazyProviderImpl
 */
public class UnsupportedClassCSSPropertyException extends Exception {
	private static final long serialVersionUID = 1L;
	private Class clazz;

	public UnsupportedClassCSSPropertyException(Class clazz) {
		this.clazz = clazz;
	}

	public String getMessage() {
		return clazz + " must implement " + ICSSPropertyHandler.class.getName();
	}

}
