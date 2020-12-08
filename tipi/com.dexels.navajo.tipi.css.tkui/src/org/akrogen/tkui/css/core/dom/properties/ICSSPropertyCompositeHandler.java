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

/**
 * CSS Property Handler interface to manage composite CSS Property (ex:
 * background is CSS Property composed with background-color,
 * background-image..).
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface ICSSPropertyCompositeHandler extends ICSSPropertyHandler {

	/**
	 * Return true if <code>property</code> is CSS Property composite and
	 * false otherwise.
	 * 
	 * @param property
	 * @return
	 */
	public boolean isCSSPropertyComposite(String property);

	/**
	 * Return the CSS Properties names if the CSS Property <code>property</code>
	 * is composite and null otherwise.
	 * 
	 * @param property
	 * @return
	 */
	public String[] getCSSPropertiesNames(String property);
}
