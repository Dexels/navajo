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
 * This class implements the {@link org.w3c.css.sac.ElementSelector} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSElementSelectorImpl extends AbstractElementSelector {

	/**
	 * Creates a new ElementSelector object.
	 */
	public CSSElementSelectorImpl(String uri, String name) {
		super(uri, name);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.Selector#getSelectorType()}.
	 */
	public short getSelectorType() {
		return SAC_ELEMENT_NODE_SELECTOR;
	}

	/**
	 * Tests whether this selector matches the given element.
	 */
	public boolean match(Element e, String pseudoE) {
		String name = getLocalName();
		if (name == null) {
			if (namespaceURI != null)
				return namespaceURI.equals(e.getNamespaceURI());
			else
				return true;
		}
		String eName;
		if (e.getPrefix() == null)
			eName = e.getNodeName();
		else
			eName = e.getLocalName();
		// According to CSS 2 section 5.1 element
		// names in selectors are case-sensitive for XML.
		if (eName.equals(name)) {
			if (namespaceURI != null)
				return namespaceURI.equals(e.getNamespaceURI());
			else
				return true;
		}
		return false;
		// For HTML
		// return eName.equalsIgnoreCase(name);
	}

	/**
	 * Returns the specificity of this selector.
	 */
	public int getSpecificity() {
		return (getLocalName() == null) ? 0 : 1;
	}

	/**
	 * Returns a representation of the selector.
	 */
	public String toString() {
		String name = getLocalName();
		if (name == null) {
			return "*";
		}
		return name;
	}
}
