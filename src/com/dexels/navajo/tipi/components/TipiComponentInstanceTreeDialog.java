package com.dexels.navajo.tipi.components;

import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.studio.tree.*;
import com.dexels.navajo.tipi.*;
import javax.swing.tree.TreePath;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentInstanceTreeDialog extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton okButton = new JButton();
  TipiComponentTree tipiTree = new TipiComponentTree();

  public TipiComponentInstanceTreeDialog() {
    try {
      jbInit();
      TipiContext context = TipiContext.getInstance();
      tipiTree.setElement(context.getComponentTree());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setDebugGraphicsOptions(0);
    jPanel1.setLayout(flowLayout1);
    okButton.setHorizontalAlignment(SwingConstants.RIGHT);
    okButton.setText("Ok");
    this.setTitle("Select TipiComponent");
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(okButton, null);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(tipiTree, null);
    okButton.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent e){
         kill();
       }
    });

  }

  private void kill(){
    this.hide();
  }

  public TreePath getPath(){
    TreePath tp = tipiTree.getSelectionPath();
    System.err.println("TreePath: " + tp.toString());
    return tp;
  }
}