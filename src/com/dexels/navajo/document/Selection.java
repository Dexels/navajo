
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;

import org.w3c.dom.*;
import com.dexels.navajo.xml.XMLutils;

public class Selection {

  public Element ref;

  /**
   * Public constants for selection node.
   */
  public static final String SELECTION_DEFINITION = "option";
  public static final String SELECTION_NAME = "name";
  public static final String SELECTION_VALUE = "value";
  public static final String SELECTION_SELECTED = "selected";
  public static final String SELECTION_ON = "1";
  public static final String SELECTION_OFF = "0";

  public static final String DUMMY_SELECTION = "___DUMMY_SELECTION___";

  public Selection(Element e) {
    this.ref = e;
  }

  /**
   * Create a new selection object.
   */
  public static Selection create(Navajo tb, String name, String value, boolean selected) {
    Selection p = null;

    Document d = tb.getMessageBuffer();
    Element n = (Element) d.createElement(Selection.SELECTION_DEFINITION);
    p = new Selection(n);
    p.setName(name);
    p.setValue(value);
    p.setSelected(selected);

    return p;
  }

  /**
   * Create a dummy selection object for easy processing of option that are not present.
   * Selected flag is ALWAYS put to false.
   * (For use see @Property.class).
   */

  public static Selection createDummy() {

    Selection p = null;
    Element n = null;

    p = new Selection(n);

    p.setName(Selection.DUMMY_SELECTION);
    p.setValue("-1");
    p.setSelected(false);

    return p;
  }

  /**
   * Several methods for setting/getting selection attributes.
   */
  public String getName() {
    return ref.getAttribute(Selection.SELECTION_NAME);
  }

  public void setName(String name) {
    ref.setAttribute(Selection.SELECTION_NAME, name); //XMLutils.string2unicode(name));
  }

  public String getValue() {
    return ref.getAttribute(Selection.SELECTION_VALUE);
  }

  public void setValue(String value) {
    ref.setAttribute(Selection.SELECTION_VALUE, value); //XMLutils.string2unicode(value));
  }

  public boolean isSelected() {
    return ref.getAttribute(Selection.SELECTION_SELECTED).equals(Selection.SELECTION_ON);
  }

  public void setSelected(boolean b) {
    if (b)
      ref.setAttribute(Selection.SELECTION_SELECTED, Selection.SELECTION_ON);
    else
      ref.setAttribute(Selection.SELECTION_SELECTED, Selection.SELECTION_OFF);
  }
}