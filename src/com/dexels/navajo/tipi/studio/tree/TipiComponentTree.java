package com.dexels.navajo.tipi.studio.tree;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import nanoxml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentTree extends JTree {
  private TipiComponentTreeModel myModel;
  private XMLElement myRoot;

  public TipiComponentTree() {
    myModel = new TipiComponentTreeModel();
    this.setCellRenderer(new TipiComponentTreeCellRenderer());
  }

  public void setElement(XMLElement doc){
    myRoot = doc;
    buildTree();
  }

  private void buildTree(){
    myModel.setElement(myRoot);
    setModel(myModel);
  }


}