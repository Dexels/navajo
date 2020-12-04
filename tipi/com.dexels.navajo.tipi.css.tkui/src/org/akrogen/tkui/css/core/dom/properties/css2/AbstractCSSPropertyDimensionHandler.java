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
package org.akrogen.tkui.css.core.dom.properties.css2;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.css.CSSValue;

public abstract class AbstractCSSPropertyDimensionHandler implements ICSSPropertyDimensionHandler  {

	public void applyCSSPropertyHeight(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		
	}

	public void applyCSSPropertyLineHeight(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void applyCSSPropertyMaxHeight(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void applyCSSPropertyMaxWidth(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void applyCSSPropertyMinHeight(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void applyCSSPropertyMinWidth(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void applyCSSPropertyWidth(Object element, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String retrieveCSSPropertyHeight(Object widget, String property,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyMaxHeight(Object widget, String property,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyMinHeight(Object widget, String property,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyMinWidth(Object widget, String property,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean applyCSSProperty(Object widget, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public String retrieveCSSProperty(Object widget, String property,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
