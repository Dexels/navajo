package com.dexels.navajo.tipi.studio.tree;

import javax.swing.tree.TreeCellRenderer;
import java.awt.Component;
import javax.swing.*;
import java.awt.*;
import tipi.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiComponentTreeCellRenderer
    extends JLabel
    implements TreeCellRenderer {
  public TipiComponentTreeCellRenderer() {
    setOpaque(false);
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//    System.err.println("Class: "+value.getClass());
    if (TipiComponent.class.isInstance(value)) {
      TipiComponent n = (TipiComponent) value;
      if (selected) {
          setText(n.toString());
          setIcon(n.getSelectedIcon());
        return this;
      }
      else {
          setText(n.toString());
          setIcon(n.getIcon());
        return this;
      }
    }
    else {
      //System.err.println("Class: " + value.getClass().toString());
      setText("<unknown class: "+value.getClass()+">");
      return this;
    }
  }
}