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
public class CSSPseudoClassConditionImpl extends AbstractAttributeCondition {
	/**
	 * The namespaceURI.
	 */
	protected String namespaceURI;

	/**
	 * Creates a new CSSAttributeCondition object.
	 */
	public CSSPseudoClassConditionImpl(String namespaceURI, String value) {
		super(value);
		this.namespaceURI = namespaceURI;
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
		CSSPseudoClassConditionImpl c = (CSSPseudoClassConditionImpl) obj;
		return c.namespaceURI.equals(namespaceURI);
	}

	/**
	 * equal objects should have equal hashCodes.
	 * 
	 * @return hashCode of this CSSPseudoClassCondition
	 */
	public int hashCode() {
		return namespaceURI.hashCode();
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.Condition#getConditionType()}.
	 */
	public short getConditionType() {
		return SAC_PSEUDO_CLASS_CONDITION;
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
		return null;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.AttributeCondition#getSpecified()}.
	 */
	public boolean getSpecified() {
		return false;
	}

	/**
	 * Tests whether this selector matches the given element.
	 */
	public boolean match(Element e, String pseudoE) {
		if (pseudoE != null && !pseudoE.equals(getValue()))
			// pseudo instance is filled, it is not valid.
			return false;
		if (!(e instanceof CSSStylableElement))
			return false;
		CSSStylableElement element = (CSSStylableElement) e;
		boolean isPseudoInstanceOf = element.isPseudoInstanceOf(getValue());
		if (!isPseudoInstanceOf)
			return false;
		if (pseudoE == null) {
			// pseudo element is not filled.
			// test if this CSSPseudoClassCondition is NOT a static pseudo
			// instance
			return (!element.isStaticPseudoInstance(getValue()));
		}
		return true;
	}

	/**
	 * Fills the given set with the attribute names found in this selector.
	 */
	public void fillAttributeSet(Set attrSet) {
	}

	/**
	 * Returns a text representation of this object.
	 */
	public String toString() {
		return ":" + getValue();
	}
}
