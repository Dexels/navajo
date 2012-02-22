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

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.css.CSSValue;

/**
 * CSS Property Handler interface used to
 * <ul>
 * <li>apply CSS Property value to an element like Swing Component, SWT Widget.</li>
 * <li>retrieve default CSS Property value from element like Swing Component,
 * SWT Widget.</li>
 * </ul>
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface ICSSPropertyHandler {

	/**
	 * Apply CSS Property <code>property</code> (ex : background-color) with
	 * CSSValue <code>value</code> (ex : red) into the <code>element</code>
	 * (ex : Swing Component, SWT Widget).
	 * 
	 * @param element
	 *            Swing Component, SWT Widget...
	 * @param property
	 *            CSS Property
	 * @param value
	 *            CSS value
	 * @param pseudo
	 * @param engine
	 *            CSS Engine
	 * @return
	 * @throws Exception
	 */
	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception;

	/**
	 * Retrieve CSS value (ex : red) of CSS Property <code>property</code> (ex :
	 * background-color) from the <code>element</code> (ex : Swing Component,
	 * SWT Widget).
	 * 
	 * @param element
	 * @param property
	 *            CSS Property
	 * @param engine
	 *            CSS Engine
	 * @return
	 * @throws Exception
	 */
	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception;

}
