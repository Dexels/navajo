package com.dexels.navajo.studio;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class BPCLTslPanel extends BaseStudioPanel {
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField4 = new JTextField();
  JTextField jTextField5 = new JTextField();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

  public BPCLTslPanel(RootStudioPanel root, NavajoTreeNode selected) {
    rootPanel = root;
    selectedNode = selected;
    title = "Comments";
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {

    title = "Edit notes";

    this.setLayout(new BorderLayout());
    jLabel1.setToolTipText("");
    jLabel1.setText("Author");
    this.setLayout(verticalFlowLayout1);
    jLabel2.setToolTipText("Notes");
    jLabel2.setText("Notes");
    jLabel3.setToolTipText("");
    jLabel3.setText("Id");
    jLabel4.setToolTipText("");
    jLabel4.setText("Repository");

    jTextField1.setText(selectedNode.getAttribute("author"));
    jTextField4.setText(selectedNode.getAttribute("notes"));
    jTextField3.setText(selectedNode.getAttribute("id"));
    jTextField5.setText(selectedNode.getAttribute("repository"));

    this.add(jLabel1, null);
    this.add(jTextField1, null);
    this.add(jLabel4, null);
    this.add(jTextField5, null);
    this.add(jLabel3, null);
    this.add(jTextField3, null);
    this.add(jLabel2, null);
    this.add(jTextField4, null);
  }

  void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
  }

  void okButton_actionPerformed(ActionEvent e) {
      selectedNode.putAttributes("notes", jTextField4.getText());
      selectedNode.putAttributes("author", jTextField1.getText());
      selectedNode.putAttributes("id", jTextField3.getText());
      selectedNode.putAttributes("repository", jTextField5.getText());
      rootPanel.isModified();
      rootPanel.changeContentPane(rootPanel.BPCLPANEL);
  }
}