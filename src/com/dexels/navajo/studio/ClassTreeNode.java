package com.dexels.navajo.studio;


import java.lang.reflect.*;
import javax.swing.tree.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class ClassTreeNode extends DefaultMutableTreeNode {

    protected String className = "";
    protected String fieldName = "";
    protected boolean isarray = false;

    public ClassTreeNode() {
        super();
    }

    public ClassTreeNode(String className1) {
        super(className1);
        className = className1;
    }

    public ClassTreeNode(String className1, String fieldName1) {
        super(fieldName1 + " (" + className1 + ")");
        className = className1;
        fieldName = fieldName1;
    }

    public ClassTreeNode(Class object) {
        super(object.getName());
        className = object.getName();
    }

    public boolean isArray() {
        return isarray;
    }

    public String getClassName() {
        return className;
    }

    public String getFieldName() {
        return fieldName;
    }
}
