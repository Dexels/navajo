package com.dexels.navajo.tipi.studio.tree;

import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.io.*;
import java.net.*;
import tipi.*;
import javax.swing.event.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentTreeTestPanel extends JScrollPane {
  private TipiComponentTree tree;

  public TipiComponentTreeTestPanel() {
    try{
      tree = new TipiComponentTree();
      tree.addTreeSelectionListener(new TreeSelectionListener(){
         public void valueChanged(TreeSelectionEvent e){
           selectionChanged(e);
         }
      });
      this.getViewport().add(tree);
      this.setPreferredSize(new Dimension(600,600));
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private void selectionChanged(TreeSelectionEvent e){
    System.err.println("You clicked: " + e.getPath());
  }

  public void setElement(XMLElement e){
    tree.setElement(e);
  }
}