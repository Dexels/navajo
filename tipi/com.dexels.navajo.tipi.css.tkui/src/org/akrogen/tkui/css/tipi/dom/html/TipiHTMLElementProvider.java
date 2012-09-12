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
package org.akrogen.tkui.css.tipi.dom.html;

import org.akrogen.tkui.css.core.dom.IElementProvider;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.w3c.dom.Element;

import com.dexels.navajo.tipi.TipiComponent;

/**
 * IElementProvider Swing implementation to retrieve w3c Element SwingElement
 * from Swing container.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class TipiHTMLElementProvider implements IElementProvider {

	public static final IElementProvider INSTANCE = new TipiHTMLElementProvider();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.dom.IElementProvider#getElement(java.lang.Object)
	 */
	public Element getElement(Object element, CSSEngine engine) {
		if (element instanceof TipiComponent) {
			TipiComponent component = (TipiComponent) element;
			return TipiElementHelpers.getHTMLElement(component, engine);
		}
		return null;
	}

}
