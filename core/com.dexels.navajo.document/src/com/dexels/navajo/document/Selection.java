/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface Selection extends java.io.Serializable, Comparable<Selection> {


    //Public constants for selection node.
    public static final String SELECTION_DEFINITION = "option";
    public static final String SELECTION_NAME = "name";
    public static final String SELECTION_VALUE = "value";
    public static final String SELECTION_SELECTED = "selected";
    public static final String SELECTION_ON = "1";
    public static final String SELECTION_OFF = "0";
    public static final String DUMMY_SELECTION = "___DUMMY_SELECTION___";
    public static final String DUMMY_ELEMENT = "___DUMMY_ELEMENT___";

    /**
     * Get the name of this Selection object
     * @return String
     */
    public String getName();

    /**
     * Set the name of this Selection object
     * @param name String
     */
    public void setName(String name);

    /**
     * Get the value of this Selection object
     * @return String
     */
    public String getValue();

    /**
     * Set the value of this Selection object
     * @param value String
     */
    public void setValue(String value);

    /**
     * Check if this Selection object is selected
     * @return boolean
     */
    public boolean isSelected();

    /**
     * Set the selection state of this Selection object
     * @param b boolean
     */
    public void setSelected(boolean b);

    /**
     * Return the internal implementation specific representation of the Selection.
     * @return Object
     */
    public Object getRef();
    
    public void setRootDoc(Navajo n);
     
    public Navajo getRootDoc();
 
}
