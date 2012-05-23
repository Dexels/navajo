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
package org.akrogen.tkui.css.core.impl.sac;

import org.akrogen.tkui.css.core.dom.CSSStylableElement;
import org.w3c.dom.Element;

/**
 * This class provides an implementation of the
 * {@link org.w3c.css.sac.AttributeCondition} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSClassConditionImpl extends CSSAttributeConditionImpl {

	/**
	 * Creates a new CSSAttributeCondition object.
	 */
	public CSSClassConditionImpl(String localName, String namespaceURI,
			String value) {
		super(localName, namespaceURI, true, value);
	}

	public boolean match(Element e, String pseudoE) {
		String attr = null;
		if ((e instanceof CSSStylableElement))
			attr = ((CSSStylableElement) e).getCSSClass();
		else
			attr = e.getAttribute("class");
		if (attr == null || attr.length() < 1)
			return false;
		String val = getValue();
		int attrLen = attr.length();
		int valLen = val.length();
		for (int i = attr.indexOf(val); i != -1; i = attr.indexOf(val, i
				+ valLen))
			if ((i == 0 || Character.isSpaceChar(attr.charAt(i - 1)))
					&& (i + valLen == attrLen || Character.isSpaceChar(attr
							.charAt(i + valLen))))
				return true;

		return false;
	}
}
