package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface Selection extends java.io.Serializable, Comparable {

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
    public static final String DUMMY_ELEMENT = "___DUMMY_ELEMENT___";

    public String getName();

    public void setName(String name);

    public String getValue();

    public void setValue(String value);

    public boolean isSelected();

    public void setSelected(boolean b);

    /**
     * Return the internal implementation specific representation of the Message.
     *
     * @return
     */
    public Object getRef();
}