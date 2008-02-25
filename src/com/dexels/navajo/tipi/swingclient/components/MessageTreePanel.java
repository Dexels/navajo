package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import javax.swing.tree.*;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureEvent;
import java.util.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class MessageTreePanel extends JTree
     {

  public MessageTreePanel() {
  }
  public void setMessage(Message m, String labelProperty) {
    MessageTreeNode newRoot = new MessageTreeNode(m,labelProperty);
    DefaultTreeModel dtm = new DefaultTreeModel(newRoot);
    setModel(dtm);
  }

  public void expandAll() {
  expandAll(this, true);
}

public void expandAll(JTree tree, boolean expand) {
  TreeNode root = (TreeNode) tree.getModel().getRoot();
  expandAll(tree, new TreePath(root), expand);
}

private final void expandAll(JTree tree, TreePath parent, boolean expand) {

  TreeNode node = (TreeNode) parent.getLastPathComponent();
  if (node == null) {
    return;
  }

  if (node.getChildCount() >= 0) {
    Enumeration e = node.children();
    if (e == null) {
      return;
    }

    while (e.hasMoreElements()) {
      TreeNode n = (TreeNode) e.nextElement();
      if (n != null) {
        TreePath path = parent.pathByAddingChild(n);
        expandAll(tree, path, expand);
      }
    }
  }

  // Expansion or collapse must be done bottom-up
  if (expand) {
    tree.expandPath(parent);
  }
  else {
    tree.collapsePath(parent);
  }
}

}
