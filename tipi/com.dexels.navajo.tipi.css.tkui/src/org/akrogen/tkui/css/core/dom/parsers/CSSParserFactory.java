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
package org.akrogen.tkui.css.core.dom.parsers;

import org.akrogen.tkui.css.core.impl.dom.parsers.CSSParserFactoryImpl;

/**
 * CSS Parser factory to manage instance of {@link ICSSParserFactory}.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class CSSParserFactory implements ICSSParserFactory {

	/**
	 * Obtain a new instance of a {@link ICSSParserFactory}.
	 * 
	 * @return
	 */
	public static ICSSParserFactory newInstance() {
		return new CSSParserFactoryImpl();
	}

}
