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

import org.w3c.css.sac.LangCondition;
import org.w3c.dom.Element;

/**
 * This class provides an implementation of the
 * {@link org.w3c.css.sac.LangCondition} interface.
 *
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSLangConditionImpl
    implements LangCondition,
               ExtendedCondition {
    /**
     * The language.
     */
    protected String lang;

    /**
     * The language with a hyphen suffixed.
     */
    protected String langHyphen;

    /**
     * Creates a new LangCondition object.
     */
    public CSSLangConditionImpl(String lang) {
        this.lang = lang.toLowerCase();
        this.langHyphen = lang + '-';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     */
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        CSSLangConditionImpl c = (CSSLangConditionImpl)obj;
        return c.lang.equals(lang);
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.Condition#getConditionType()}.
     */
    public short getConditionType() {
        return SAC_LANG_CONDITION;
    }

    /**
     * <b>SAC</b>: Implements {@link org.w3c.css.sac.LangCondition#getLang()}.
     */
    public String getLang() {
        return lang;
    }

    /**
     * Returns the specificity of this condition.
     */
    public int getSpecificity() {
        return 1 << 8;
    }

    /**
     * Tests whether this condition matches the given element.
     */
    public boolean match(Element e, String pseudoE) {
        String s = e.getAttribute("lang").toLowerCase();
        if (s.equals(lang) || s.startsWith(langHyphen)) {
            return true;
        }
//        s = e.getAttributeNS(XMLConstants.XML_NAMESPACE_URI,
//                             XMLConstants.XML_LANG_ATTRIBUTE).toLowerCase();
        return s.equals(lang) || s.startsWith(langHyphen);
    }

    /**
     * Fills the given set with the attribute names found in this selector.
     */
    public void fillAttributeSet(Set attrSet) {
        attrSet.add("lang");
    }

    /**
     * Returns a text representation of this object.
     */
    public String toString() {
        return ":lang(" + lang + ')';
    }
}
