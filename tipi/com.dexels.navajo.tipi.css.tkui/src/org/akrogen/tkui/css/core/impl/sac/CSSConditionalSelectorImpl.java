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
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.SimpleSelector;
import org.w3c.dom.Element;

/**
 * This class provides an implementation of the
 * {@link org.w3c.css.sac.ConditionalSelector} interface.
 *
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSConditionalSelectorImpl
    implements ConditionalSelector,
               ExtendedSelector {

    /**
     * The simple selector.
     */
    protected SimpleSelector simpleSelector;

    /**
     * The condition.
     */
    protected Condition condition;

    /**
     * Creates a new ConditionalSelector object.
     */
    public CSSConditionalSelectorImpl(SimpleSelector s, Condition c) {
        simpleSelector = s;
        condition      = c;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        CSSConditionalSelectorImpl s = (CSSConditionalSelectorImpl)obj;
        return (s.simpleSelector.equals(simpleSelector) &&
                s.condition.equals(condition));
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.Selector#getSelectorType()}.
     */
    public short getSelectorType() {
        return SAC_CONDITIONAL_SELECTOR;
    }

    /**
     * Tests whether this selector matches the given element.
     */
    public boolean match(Element e, String pseudoE) {
        return ((ExtendedSelector)getSimpleSelector()).match(e, pseudoE) &&
               ((ExtendedCondition)getCondition()).match(e, pseudoE);
    }

    /**
     * Fills the given set with the attribute names found in this selector.
     */
    public void fillAttributeSet(Set attrSet) {
        ((ExtendedSelector)getSimpleSelector()).fillAttributeSet(attrSet);
        ((ExtendedCondition)getCondition()).fillAttributeSet(attrSet);
    }

    /**
     * Returns the specificity of this selector.
     */
    public int getSpecificity() {
        return ((ExtendedSelector)getSimpleSelector()).getSpecificity() +
               ((ExtendedCondition)getCondition()).getSpecificity();
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.ConditionalSelector#getSimpleSelector()}.
     */
    public SimpleSelector getSimpleSelector() {
        return simpleSelector;
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.ConditionalSelector#getCondition()}.
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Returns a representation of the selector.
     */
    public String toString() {
        return String.valueOf( simpleSelector ) + condition;
    }
}
