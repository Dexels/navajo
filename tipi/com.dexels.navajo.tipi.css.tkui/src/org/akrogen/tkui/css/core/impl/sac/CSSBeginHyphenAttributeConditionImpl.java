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

import org.w3c.dom.Element;

/**
 * This class provides an implementation of the
 * {@link org.w3c.css.sac.AttributeCondition} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSBeginHyphenAttributeConditionImpl extends
		CSSAttributeConditionImpl {

	/**
	 * Creates a new CSSAttributeCondition object.
	 */
	public CSSBeginHyphenAttributeConditionImpl(String localName,
			String namespaceURI, boolean specified, String value) {
		super(localName, namespaceURI, specified, value);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.Condition#getConditionType()}.
	 */
	public short getConditionType() {
		return SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION;
	}

	/**
	 * Tests whether this condition matches the given element.
	 */
	public boolean match(Element e, String pseudoE) {
		return e.getAttribute(getLocalName()).startsWith(getValue());
	}

	/**
	 * Returns a text representation of this object.
	 */
	public String toString() {
		return '[' + getLocalName() + "|=\"" + getValue() + "\"]";
	}
}
