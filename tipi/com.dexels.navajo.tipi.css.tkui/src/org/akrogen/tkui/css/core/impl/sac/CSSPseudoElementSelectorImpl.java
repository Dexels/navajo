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
public class CSSPseudoElementSelectorImpl extends AbstractElementSelector {

    /**
     * Creates a new CSSPseudoElementSelector object.
     */
    public CSSPseudoElementSelectorImpl(String uri, String name) {
        super(uri, name);
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.Selector#getSelectorType()}.
     */
    public short getSelectorType() {
        return SAC_PSEUDO_ELEMENT_SELECTOR;
    }

    /**
     * Tests whether this selector matches the given element.
     */
    public boolean match(Element e, String pseudoE) {
        return getLocalName().equalsIgnoreCase(pseudoE);
    }

    /**
     * Returns the specificity of this selector.
     */
    public int getSpecificity() {
        return 0;
    }

    /**
     * Returns a representation of the selector.
     */
    public String toString() {
        return ":" + getLocalName();
    }
}
