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
package org.akrogen.tkui.css.tipi.properties;


import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * Abstract CSS Property Swing Handler to check if the <code>element</code>
 * coming from applyCSSProperty and retrieveCSSProperty methods is Swing
 * component.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSPropertySwingHandler implements
		ICSSPropertyHandler {

	protected Object getElement(Object element) {
		return TipiElementHelpers.getComponent(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.ICSSPropertyHandler#applyCSSProperty(java.lang.Object,
	 *      java.lang.String, org.w3c.dom.css.CSSValue, java.lang.String,
	 *      org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			// The Swing component is retrieved
			// the apply CSS property can be done.
			this.applyCSSProperty(component, property, value, pseudo, engine);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.properties.ICSSPropertyHandler#retrieveCSSProperty(java.lang.Object,
	 *      java.lang.String, org.akrogen.tkui.core.css.engine.CSSEngine)
	 */
	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			// The Swing component is retrieved
			// the retrieve CSS property can be done.
			return retrieveCSSProperty(component, property, engine);
		}
		return null;
	}

	/**
	 * Apply CSS Property <code>property</code> (ex : background-color) with
	 * CSSValue <code>value</code> (ex : red) into the Swing
	 * <code>component</code> (ex : Swing JTextField, Swing JLabel).
	 * 
	 * @param component
	 * @param property
	 * @param value
	 * @param pseudo
	 * @param engine
	 * @throws Exception
	 */
	protected abstract void applyCSSProperty(TipiComponent component,
			String property, CSSValue value, String pseudo, CSSEngine engine)
			throws Exception;

	/**
	 * Retrieve CSS value (ex : red) of CSS Property <code>property</code> (ex :
	 * background-color) from the Swing <code>component</code> (ex : Swing
	 * JTextField, Swing JLabel).
	 * 
	 * @param component
	 * @param property
	 * @param engine
	 * @return
	 * @throws Exception
	 */
	public abstract String retrieveCSSProperty(TipiComponent component,
			String property, CSSEngine engine) throws Exception;

}
