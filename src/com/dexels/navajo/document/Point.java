
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
package com.dexels.navajo.document;

import org.w3c.dom.*;
import java.util.Vector;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.xml.XMLutils;

public class Point {

  private Vector v;
  private Property parent;
  private Element ref;

  public Point(Property p) throws NavajoException {
    if (!p.getType().equals(Property.POINTS_PROPERTY))
        throw new NavajoException("Points can only be created for points properties");
    v = new Vector();
    parent = p;
    Document d = (Document) parent.ref.getOwnerDocument();
    ref = (Element) d.createElement("value");
    parent.ref.appendChild(ref);
    //System.out.println("Created value element tag under property, ref = " + ref);
  }

  public void setValue(String s) {
    int index = v.size();
    String attrName = "x"+index;
    //System.out.println("in setValue() Points (ref = " + ref + ", vector = " + v + "), attrName = " + attrName + ", value = " + s);
    ref.setAttribute(attrName, s);
    v.add(s);
  }

  public void setValue(int position, String s) {
    ref.setAttribute("x"+position, s);
    v.add(position, s);
  }

  public String getValue(int position) {
    return (String) v.get(position);
  }

  public int getSize() {
    return v.size();
  }
}