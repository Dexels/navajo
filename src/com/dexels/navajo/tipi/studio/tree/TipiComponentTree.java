package com.dexels.navajo.tipi.studio.tree;

import javax.swing.*;
import javax.swing.tree.*;
import com.dexels.navajo.tipi.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentTree extends JTree {
  private DefaultTreeModel myModel;

  public TipiComponentTree() {
    myModel = new DefaultTreeModel((TipiComponent)TipiContext.getInstance().getDefaultTopLevel());
    this.setCellRenderer(new TipiComponentTreeCellRenderer());
    setModel(myModel);
  }

  public void setStandardComponentTree() {
    this.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        this_valueChanged(e);
      }
    });

  }
  void this_valueChanged(TreeSelectionEvent e) {
    TreePath p = e.getPath();
    if (p==null) {
      TipiContext.getInstance().setSelectedComponent(null);
      return;
    }
    if (p.getLastPathComponent()!=null) {
      TipiComponent tc = (TipiComponent)p.getLastPathComponent();
       TipiContext.getInstance().setSelectedComponent(tc);
    }

  }

}