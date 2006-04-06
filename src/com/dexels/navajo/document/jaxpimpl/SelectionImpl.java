

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.*;
import org.w3c.dom.*;

public final class SelectionImpl implements Selection {

    public Element ref;
    private Navajo myRootDoc;
    
    public SelectionImpl(Element e) {
        this.ref = e;
    }

    /**
     * Create a new selection object.
     */
    public static Selection create(Navajo tb, String name, String value, boolean selected) {
        Selection p = null;

        Document d = (Document) tb.getMessageBuffer();
        Element n = (Element) d.createElement(Selection.SELECTION_DEFINITION);

        p = new SelectionImpl(n);
        p.setRootDoc(tb);
        p.setName(name);
        p.setValue(value);
        p.setSelected(selected);

        return p;
    }

   
    public void setRootDoc(Navajo n) {
    	this.myRootDoc = n;
    }
    
    public Navajo getRootDoc() {
    	return myRootDoc;
    }

    /**
     * Create a dummy selection object for easy processing of option that are not present.
     * Selected flag is ALWAYS put to false.
     * (For use see @Property.class).
     */
    
    public static Selection createDummy() {

        Selection p = null;
        Element n = null;


        Navajo tb = new NavajoImpl();
        Document d = (Document) tb.getMessageBuffer();

        n = (Element) d.createElement(Selection.DUMMY_ELEMENT);

        p = new SelectionImpl(n);

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
        ref.setAttribute(Selection.SELECTION_NAME, name); // XMLutils.string2unicode(name));
    }

    public String getValue() {
        return ref.getAttribute(Selection.SELECTION_VALUE);
    }

    public void setValue(String value) {
        ref.setAttribute(Selection.SELECTION_VALUE, value); // XMLutils.string2unicode(value));
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

       /**
     * Return the internal implementation specific representation of the Message.
     *
     * @return
     */
    public Object getRef() {
      return this.ref;
    }

/**
 * Some swingclient components use the toString function for displaying. Added by frank.
 */

    public String toString() {
      return ref.getAttribute(Selection.SELECTION_NAME);
    }

    public final int compareTo(Object o) {
      if (!(o instanceof Selection)) {
        return 0;
      }


      if (((Selection) o).getName() == null && getName() == null) {
        return 0;
      }

      if (((Selection) o).getName() != null && getName() == null) {
        return -1;
      }

      if (((Selection) o).getName() == null && getName() != null) {
        return 1;
      }

      return (getName().compareTo(((Selection) o).getName()));

    }

}
