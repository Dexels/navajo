/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.document.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public class BaseSelectionImpl extends BaseNode implements Selection {

    private static final long serialVersionUID = 1548716501966033367L;

    private static final Logger logger = LoggerFactory.getLogger(BaseSelectionImpl.class);

    protected String name = "";

    protected String value = "-1";

    protected boolean isSelected = false;

    private Property myProperty = null;

    public BaseSelectionImpl(Navajo n, String name, String value, boolean isSelected) {

        super(n);
        this.name = name;
        this.value = value;
        this.isSelected = isSelected;
    }

    public BaseSelectionImpl(Navajo n) {
        super(n);
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final void setName(String name) {
        this.name = name;
    }

    @Override
    public final String getValue() {
        return value;
    }

    @Override
    public final void setValue(String value) {
        this.value = value;
    }

    @Override
    public final boolean isSelected() {
        return isSelected;
    }

    @Override
    public final void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public final String toString() {

        if (getName() != null) {
            return getName().trim();
        } else {
            return "";
        }
    }

    public final Selection copy(Navajo n) {

        BaseSelectionImpl cp = (BaseSelectionImpl) NavajoFactory.getInstance().createSelection(n,
                getName(), getValue(), isSelected());
        cp.setRootDoc(n);

        return cp;
    }

    public final Property getParent() {
        return myProperty;
    }

    public final void setParent(Property m) {
        myProperty = m;
    }

    public final String getPath() {

        if (myProperty != null) {
            try {
                return myProperty.getFullPropertyName() + "/" + getName();
            } catch (NavajoException exc) {
                logger.error("Error: ", exc);
                return null;
            }
        } else {
            return "/" + getName();
        }
    }

    @Override
    public final boolean equals(Object o) {
        return super.equals(o);
    }

    // Suggested implemention for equals (replace the above logging version by this when ok)
    public final boolean newEquals(Object o) {
        return (o instanceof Selection)
                ? (compareTo((Selection) o) == 0)
                : false;
    }

    @Override
    public final int compareTo(Selection o) {

        if (o.getValue() == null && getValue() == null) {
            return 0;
        }

        if (o.getValue() != null && getValue() == null) {
            return -1;
        }

        if (o.getValue() == null && getValue() != null) {
            return 1;
        }

        return (getValue().compareTo(o.getValue()));
    }

    @Override
    public Map<String, String> getAttributes() {

        Map<String, String> m = new HashMap<>();
        m.put("name", name);
        m.put("value", value);
        m.put("selected", isSelected ? "1" : "0");

        return m;
    }

    @Override
    public List<? extends BaseNode> getChildren() {
        return null;
    }

    @Override
    public final String getTagName() {
        return Selection.SELECTION_DEFINITION;
    }

    @Override
    public Object getRef() {
        throw new UnsupportedOperationException(
                "getRef not possible on base type. Override it if you need it");
    }

}
