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
package org.akrogen.tkui.css.core.impl.dom.parsers;

import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.dom.parsers.CSSParserFactory;

/**
 * {@link CSSParserFactory} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSParserFactoryImpl extends CSSParserFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.ICSSParserFactory#makeCSSParser()
	 */
	public CSSParser makeCSSParser() {
		return new CSSParserImpl();
	}
}
