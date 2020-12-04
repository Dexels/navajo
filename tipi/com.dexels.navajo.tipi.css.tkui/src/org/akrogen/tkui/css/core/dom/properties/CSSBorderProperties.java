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
package org.akrogen.tkui.css.core.dom.properties;

import org.w3c.dom.css.CSSPrimitiveValue;

/**
 * CSS Border properties interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface CSSBorderProperties {

	/**
	 * Return border-color value.
	 * 
	 * @return
	 */
	public CSSPrimitiveValue getColor();	

	/**
	 * Set  border-color value.
	 * 
	 * @return
	 */
	public void setColor(CSSPrimitiveValue color);	
	
	/**
	 * Return border-width value.
	 * 
	 * @return
	 */	
	public int getWidth();
	
	/**
	 * Set border-width value.
	 * 
	 * @return
	 */	
	public void setWidth(int width);
	
	/**
	 * Return border-style value.
	 * 
	 * @return
	 */	
	public String getStyle();
	
	/**
	 * Set border-style value.
	 * 
	 * @return
	 */	
	public void setStyle(String style);
	
}
