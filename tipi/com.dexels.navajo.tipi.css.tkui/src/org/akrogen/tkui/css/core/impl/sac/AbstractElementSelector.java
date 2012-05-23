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

import org.w3c.css.sac.ElementSelector;

/**
 * This class provides an abstract implementation of the ElementSelector
 * interface.
 *
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractElementSelector
    implements ElementSelector,
               ExtendedSelector {

    /**
     * The namespace URI.
     */
    protected String namespaceURI;

    /**
     * The local name.
     */
    protected String localName;

    /**
     * Creates a new ElementSelector object.
     */
    protected AbstractElementSelector(String uri, String name) {
        namespaceURI = uri;
        localName    = name;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        AbstractElementSelector s = (AbstractElementSelector)obj;
        return (s.namespaceURI.equals(namespaceURI) &&
                s.localName.equals(localName));
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.ElementSelector#getNamespaceURI()}.
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.ElementSelector#getLocalName()}.
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Fills the given set with the attribute names found in this selector.
     */
    public void fillAttributeSet(Set attrSet) {
    }
}
