package com.dexels.navajo.tipi.studio.tree;

import javax.swing.tree.TreeCellRenderer;
import java.awt.Component;
import javax.swing.*;
import java.awt.*;
import tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentTreeCellRenderer extends JLabel implements TreeCellRenderer {
  public TipiComponentTreeCellRenderer() {
    setOpaque(true);
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    if (TipiComponentTreeNode.class.isInstance(value)) {
      TipiComponentTreeNode n = (TipiComponentTreeNode) value;

      if(selected){
        setBackground(Color.decode("#ffe400"));
      }else{
        setBackground(Color.white);
      }

      if (n.toString().equals("tid")) {
        setText("Tipi!");
        setIcon(new ImageIcon(MainApplication.class.getResource("tip-icon.jpg")));
      }
      else {
        setText(n.toString());
        setIcon(null);
      }
      return this;
    }
    else {
      //System.err.println("Class: " + value.getClass().toString());
      setText("");
      return this;
    }
  }

}