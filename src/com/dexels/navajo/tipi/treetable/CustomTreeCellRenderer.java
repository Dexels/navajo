package com.dexels.navajo.tipi.treetable;

import java.awt.Component;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

  private JLabel myLabel = new JLabel();

  public CustomTreeCellRenderer() {
  }
  public Component getTreeCellRendererComponent(JTree tree,Object value,boolean isSelected,boolean expanded,boolean leaf, int row, boolean hasFocus) {
//    System.err.println("Using custom tree cell renderer");
//    Component c = super.getTreeCellRendererComponent( tree,value ,isSelected ,expanded ,leaf ,  row,false);
//    Component c = new JLabel(value.toString());
    myLabel.setText(value.toString());
//    int ii = ((VisibleRowable)tree).getVisibleRow();
//    setComponentColor(c,isSelected,row+ii);
//    System.err.println("BTW, the component class is: "+c.getClass());
//    System.err.println("And my tree instance is: "+tree.getClass());
//    System.err.println(">>>>"+jtt.getVisibleRow());
    return myLabel;
  }

//  private void setComponentColor(Component c, boolean isSelected, int row) {
//    JComponent cc = (JComponent)c;
//    cc.setOpaque(false);
//    cc.setBorder(null);
//    if (isSelected) {
//      c.setBackground(new Color(220,220,255));
//    } else {
//      if (row%2==0) {
//        c.setBackground(Color.white);
//      } else {
//        c.setBackground(new Color(240,240,240));
//      }
//    }
//  }

}