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

import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SimpleSelector;

/**
 * This class provides an abstract implementation of the {@link
 * org.w3c.css.sac.DescendantSelector} interface.
 *
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractDescendantSelector
    implements DescendantSelector,
               ExtendedSelector {

    /**
     * The ancestor selector.
     */
    protected Selector ancestorSelector;

    /**
     * The simple selector.
     */
    protected SimpleSelector simpleSelector;

    /**
     * Creates a new DescendantSelector object.
     */
    protected AbstractDescendantSelector(Selector ancestor,
                                         SimpleSelector simple) {
        ancestorSelector = ancestor;
        simpleSelector = simple;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        AbstractDescendantSelector s = (AbstractDescendantSelector)obj;
        return s.simpleSelector.equals(simpleSelector);
    }

    /**
     * Returns the specificity of this selector.
     */
    public int getSpecificity() {
        return ((ExtendedSelector)ancestorSelector).getSpecificity() +
               ((ExtendedSelector)simpleSelector).getSpecificity();
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.DescendantSelector#getAncestorSelector()}.
     */
    public Selector getAncestorSelector() {
        return ancestorSelector;
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.DescendantSelector#getSimpleSelector()}.
     */
    public SimpleSelector getSimpleSelector() {
        return simpleSelector;
    }
}
