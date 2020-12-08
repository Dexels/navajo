/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import org.akrogen.tkui.css.core.engine.CSSEngine;

/**
 * Abstract base class for converters.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSValueConverter implements ICSSValueConverter {

	private Object toType;

	public AbstractCSSValueConverter(Object toType) {
		this.toType = toType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter#getToType()
	 */
	public Object getToType() {
		return toType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter#convert(java.lang.Object,
	 *      org.akrogen.tkui.css.core.engine.CSSEngine, java.lang.Object)
	 */
	public String convert(Object value, CSSEngine engine, Object context)
			throws Exception {
		return convert(value, engine, context, null);
	}
}
