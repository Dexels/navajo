package com.dexels.navajo.studio;

import com.dexels.navajo.studio.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class BPCLTreeCellRenderer extends DefaultTreeCellRenderer {

  Icon rootIcon = new ImageIcon("images/bpcl.gif");
  Icon objectIcon = new ImageIcon("images/object.gif");
  Icon mapIcon  = new ImageIcon("images/map.gif");
  Icon messageIcon = new ImageIcon("images/message.gif");
  Icon propertyIcon = new ImageIcon("images/property.gif");
  Icon fieldIcon = new ImageIcon("images/field.gif");
  Icon paramIcon = new ImageIcon("images/param.gif");
//  Icon expressionIcon= new ImageIcon("methods2.gif");
  Icon expressionIcon= new ImageIcon("images/expression.gif");
  Icon unknownIcon= new ImageIcon("images/unknown.gif");

  public BPCLTreeCellRenderer() {
  }

  public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);

//        NavajoTreeNode node =(NavajoTreeNode)value;
//        System.err.println(node.getTag());

        if (isRoot(value)){
            setIcon(rootIcon);
            setToolTipText("root");
        }
        else if (isMessage(value)) {
            setIcon(messageIcon);
            setToolTipText("message");
        }
        else if(isProperty(value)){
            setIcon(propertyIcon);
            setToolTipText("property");
        }
        else if(isParam(value)){
            setIcon(paramIcon);
            setToolTipText("param");
        }
        else if(isField(value)){
            setIcon(fieldIcon);
            setToolTipText("option");
        }
        else if(isObjectMap(value)){
            setIcon(objectIcon);
            setToolTipText("use object");
        }
        else if(isRefMap(value)){
            setIcon(mapIcon);
            setToolTipText("map");
        }
        else if(isExpression(value)){
            setIcon(expressionIcon);
            setToolTipText("method");
        }
        else{
            setIcon(unknownIcon);
            setToolTipText("unknown->not definied in BPCL syntax or not yet implemented");
        }
        return this;
    }


    protected boolean isRoot(Object value) {
        NavajoTreeNode node = (NavajoTreeNode)value;
        if(node.getTag().equals("tsl")){
            return true;
        }

       return false;
    }

    protected boolean isObjectMap(Object value) {
        NavajoTreeNode node = (NavajoTreeNode)value;
        if(node.getTag().equals("map") && node.getAttribute("object")!=null && (!node.getAttribute("object").equals(""))){
            return true;
        }

       return false;
    }

    protected boolean isRefMap(Object value) {
        NavajoTreeNode node = (NavajoTreeNode)value;
        if(node.getTag().equals("map") && (!isObjectMap(value))){
            return true;
        }

       return false;
    }

    protected boolean isMessage(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("message") ){
            return true;
        }

       return false;
    }

     protected boolean isParam(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("param") ){
            return true;
        }

       return false;
    }

    protected boolean isProperty(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("property") ){
            return true;
        }

       return false;
    }

    protected boolean isField(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("field") ){
            return true;
        }

       return false;
    }
    protected boolean isExpression(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("expression") ){
            return true;
        }

       return false;
    }
//    protected boolean isMethod(Object value) {
//        NavajoTreeNode node =
//                (NavajoTreeNode)value;
//        if(node.getTag().equals("method") ){
//            return true;
//        }
//
//       return false;
//    }
//    protected boolean isRequired(Object value) {
//        NavajoTreeNode node =
//                (NavajoTreeNode)value;
//        if(node.getTag().equals("required") ){
//            return true;
//        }
//
//       return false;
//    }

}