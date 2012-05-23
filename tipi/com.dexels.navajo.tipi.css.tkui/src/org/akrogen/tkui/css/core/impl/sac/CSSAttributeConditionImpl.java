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

import org.w3c.dom.Element;

/**
 * This class provides an implementation of the
 * {@link org.w3c.css.sac.AttributeCondition} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSAttributeConditionImpl extends AbstractAttributeCondition {
	/**
	 * The attribute's local name.
	 */
	protected String localName;

	/**
	 * The attribute's namespace URI.
	 */
	protected String namespaceURI;

	/**
	 * Whether this condition applies to specified attributes.
	 */
	protected boolean specified;

	/**
	 * Creates a new CSSAttributeCondition object.
	 */
	public CSSAttributeConditionImpl(String localName, String namespaceURI,
			boolean specified, String value) {
		super(value);
		this.localName = localName;
		this.namespaceURI = namespaceURI;
		this.specified = specified;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj
	 *            the reference object with which to compare.
	 */
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		CSSAttributeConditionImpl c = (CSSAttributeConditionImpl) obj;
		return (c.namespaceURI.equals(namespaceURI)
				&& c.localName.equals(localName) && c.specified == specified);
	}

	/**
	 * equal objects should have equal hashCodes.
	 * 
	 * @return hashCode of this CSSAttributeCondition
	 */
	public int hashCode() {
		return namespaceURI.hashCode() ^ localName.hashCode()
				^ (specified ? -1 : 0);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.Condition#getConditionType()}.
	 */
	public short getConditionType() {
		return SAC_ATTRIBUTE_CONDITION;
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
		return specified;
	}

	/**
	 * Tests whether this condition matches the given element.
	 */
	public boolean match(Element e, String pseudoE) {
		String val = getValue();
		if (val == null) {
			return !e.getAttribute(getLocalName()).equals("");
		}
		return e.getAttribute(getLocalName()).equals(val);
	}

	/**
	 * Fills the given set with the attribute names found in this selector.
	 */
	public void fillAttributeSet(Set attrSet) {
		attrSet.add(localName);
	}

	/**
	 * Returns a text representation of this object.
	 */
	public String toString() {
		if (value == null) {
			return '[' + localName + ']';
		}
		return '[' + localName + "=\"" + value + "\"]";
	}
}
