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
package org.akrogen.tkui.css.core.impl.engine;

import org.akrogen.tkui.css.core.engine.CSSErrorHandler;

/**
 * Basic implementation for CSS Engine error handlers which print stack trace of
 * the exception throwed.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSErrorHandlerImpl implements CSSErrorHandler {

	public static final CSSErrorHandler INSTANCE = new CSSErrorHandlerImpl();

	/*
	 * (non-Javadoc)
	 * @see org.akrogen.tkui.css.core.engine.CSSErrorHandler#error(java.lang.Exception)
	 */
	public void error(Exception e) {
		e.printStackTrace();
	}
}
