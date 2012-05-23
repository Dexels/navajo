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

import java.util.Set;

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
public class CSSIdConditionImpl extends AbstractAttributeCondition {

	/**
	 * The id attribute namespace URI.
	 */
	protected String namespaceURI;

	/**
	 * The id attribute local name.
	 */
	protected String localName;

	/**
	 * Creates a new CSSAttributeCondition object.
	 */
	public CSSIdConditionImpl(String ns, String ln, String value) {
		super(value);
		namespaceURI = ns;
		localName = ln;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.Condition#getConditionType()}.
	 */
	public short getConditionType() {
		return SAC_ID_CONDITION;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.AttributeCondition#getNamespaceURI()}.
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.AttributeCondition#getLocalName()}.
	 */
	public String getLocalName() {
		return localName;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.AttributeCondition#getSpecified()}.
	 */
	public boolean getSpecified() {
		return true;
	}

	/**
	 * Tests whether this condition matches the given element.
	 */
	public boolean match(Element e, String pseudoE) {
		String id = null;
		if (e instanceof CSSStylableElement) {
			id = ((CSSStylableElement) e).getCSSId();
		} else
			id = e.getAttribute("id");
		if (id == null)
			return false;
		return id.equals(getValue());
		// return super.match(e, pseudoE);
	}

	/**
	 * Fills the given set with the attribute names found in this selector.
	 */
	public void fillAttributeSet(Set attrSet) {
		attrSet.add(localName);
	}

	/**
	 * Returns the specificity of this condition.
	 */
	public int getSpecificity() {
		return 1 << 16;
	}

	/**
	 * Returns a text representation of this object.
	 */
	public String toString() {
		return '#' + getValue();
	}
}
