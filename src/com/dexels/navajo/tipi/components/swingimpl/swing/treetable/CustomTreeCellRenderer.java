package com.dexels.navajo.tipi.components.swingimpl.swing.treetable;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class CustomTreeCellRenderer
    extends DefaultTreeCellRenderer {
  private JLabel myLabel = new JLabel();
  public CustomTreeCellRenderer() {
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    myLabel.setText(value.toString());
    return myLabel;
  }
}