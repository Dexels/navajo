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

import org.w3c.css.sac.Condition;
import org.w3c.dom.Element;

/**
 * This class provides an implementation of the
 * {@link org.w3c.css.sac.CombinatorCondition} interface.
 *
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSAndConditionImpl extends AbstractCombinatorCondition {
    /**
     * Creates a new CombinatorCondition object.
     */
    public CSSAndConditionImpl(Condition c1, Condition c2) {
        super(c1, c2);
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.Condition#getConditionType()}.
     */
    public short getConditionType() {
        return SAC_AND_CONDITION;
    }

    /**
     * Tests whether this condition matches the given element.
     */
    public boolean match(Element e, String pseudoE) {
        return ((ExtendedCondition)getFirstCondition()).match(e, pseudoE) &&
               ((ExtendedCondition)getSecondCondition()).match(e, pseudoE);
    }

    /**
     * Fills the given set with the attribute names found in this selector.
     */
    public void fillAttributeSet(Set attrSet) {
        ((ExtendedCondition)getFirstCondition()).fillAttributeSet(attrSet);
        ((ExtendedCondition)getSecondCondition()).fillAttributeSet(attrSet);
    }

    /**
     * Returns a text representation of this object.
     */
    public String toString() {
        return String.valueOf( getFirstCondition() ) + getSecondCondition();
    }
}
