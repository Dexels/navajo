

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl;

import java.util.*;

import org.w3c.dom.*;

import com.dexels.navajo.document.*;

public class PointImpl implements Point {

    private Vector<String> v;
    private Property parent;
    private Element ref;

    public PointImpl(Property p) throws NavajoException {
        if (!p.getType().equals(Property.POINTS_PROPERTY))
            throw new NavajoExceptionImpl("Points can only be created for points properties");
        v = new Vector<String>();
        parent = p;
        Document d = ((Document) parent.getRef()).getOwnerDocument();

        ref = d.createElement("value");
        ((Element) parent.getRef()).appendChild(ref);
     }

    public void setValue(String s) {
        int index = v.size();
        String attrName = "x" + index;
        ref.setAttribute(attrName, s);
        v.add(s);
    }

    public void setValue(int position, String s) {
        ref.setAttribute("x" + position, s);
        v.add(position, s);
    }

    public String getValue(int position) {
        return v.get(position);
    }

    public int getSize() {
        return v.size();
    }
}
