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

public class BPFLTreeCellRenderer extends DefaultTreeCellRenderer {
//  Icon tmlIcon = new ImageIcon("message2.gif");
  Icon rootIcon = new ImageIcon("images/bpfl.gif");
  Icon messageIcon = new ImageIcon("images/message.gif");
  Icon propertyIcon = new ImageIcon("images/property.gif");
  Icon optionIcon = new ImageIcon("images/option.gif");//red-ball.gif");
//  Icon methodsIcon= new ImageIcon("methods2.gif");
  Icon methodIcon= new ImageIcon("images/method.gif");
  Icon requiredIcon= new ImageIcon("images/required.gif");//message.gif");

  public BPFLTreeCellRenderer() {
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

        if(isRoot(value)){
            setIcon(rootIcon);
            setToolTipText("root");
        }
        else if(isMessage(value)) {
            setIcon(messageIcon);
            setToolTipText("message");
        }
        else if(isProperty(value)){
            setIcon(propertyIcon);
            setToolTipText("property");
        }
        else if(isOption(value)){
            setIcon(optionIcon);
            setToolTipText("option");
        }
//        else if(isMethods(value)){
//            setIcon(methodsIcon);
//            setToolTipText("methods");
//        }
        else if(isMethod(value)){
            setIcon(methodIcon);
            setToolTipText("method");
        }
        else if(isRequired(value)){
            setIcon(requiredIcon);
            setToolTipText("required Message");
        }
        else{
//          System.err.println("shit");
        }
        return this;
    }

    protected boolean isRoot(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("tml") ){
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


    protected boolean isProperty(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("property") ){
            return true;
        }

       return false;
    }


    protected boolean isOption(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("option") ){
            return true;
        }

       return false;
    }
    protected boolean isMethods(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("methods") ){
            return true;
        }

       return false;
    }
    protected boolean isMethod(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("method") ){
            return true;
        }

       return false;
    }
    protected boolean isRequired(Object value) {
        NavajoTreeNode node =
                (NavajoTreeNode)value;
        if(node.getTag().equals("required") ){
            return true;
        }

       return false;
    }

}