package com.dexels.navajo.tipi.swingclient.components.treetable;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
  private transient TreeTableModel myModel;
  private JLabel myLabel = new JLabel();
  private ImageIcon branchIcon, leafIcon, rootIcon;


  public CustomTreeCellRenderer(TreeTableModel mod) {
    myModel = mod;
    branchIcon = new ImageIcon(CustomTreeCellRenderer.class.getResource("14dot5a.gif"));
    leafIcon = new ImageIcon(CustomTreeCellRenderer.class.getResource("14dot2a.gif"));
    rootIcon = new ImageIcon(CustomTreeCellRenderer.class.getResource("14dot4a.gif"));
  }

  @Override
public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row,
                                                boolean hasFocus) {
//    System.err.println("Using custom tree cell renderer");
//    Component c = super.getTreeCellRendererComponent( tree,value ,isSelected ,expanded ,leaf ,  row,false);
//    Component c = new JLabel(value.toString());

    myLabel.setText(value.toString());
    myLabel.setToolTipText(value.toString());
    TreePath tp = tree.getPathForRow(row);
    int depth = 0;
    if(tp != null){
      depth = tp.getPathCount();
    }
    if(depth > 0){
      if(depth == 1){
        myLabel.setIcon(rootIcon);
      }else if(depth == 2){
        myLabel.setIcon(branchIcon);
      }else{
        myLabel.setIcon(leafIcon);
      }
    }
    if(myModel != null){
      Color c = myModel.getNodeColor("" + depth);
      myLabel.setForeground(c);
    }
//    int ii = ((VisibleRowable)tree).getVisibleRow();
//    setComponentColor(c,isSelected,row+ii);
//    System.err.println("BTW, the component class is: "+c.getClass());
//    System.err.println("And my tree instance is: "+tree.getClass());
//    System.err.println(">>>>"+jtt.getVisibleRow());
    return myLabel;
  }

//  private final void setComponentColor(Component c, boolean isSelected, int row) {
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
