package com.dexels.navajo.studio;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import javax.swing.border.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class BPFLMessagePanel extends BaseStudioPanel {
  String header="";
  JTextField nameField = new JTextField();

  JTree tmlTree = new JTree();
//  NavajoTreeNode selectedNode;

  String name="";

  private BPFLPanel parent;

  private String [] columnNames =
  {"Attribute", "Value"};


  JLabel jLabel2 = new JLabel();


  TitledBorder titledBorder1;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();


  public BPFLMessagePanel(BPFLPanel parent) {

    this.parent = parent;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public BPFLMessagePanel(RootStudioPanel rootPanel1, JTree jtree, NavajoTreeNode selectedNode1, BPFLPanel parent, boolean isNewEntry) {
    this.parent = parent;

    rootPanel = rootPanel1;
    tmlTree = jtree;
    selectedNode = selectedNode1;
    newEntry = isNewEntry;
    rootPanel.setNewEdit(isNewEntry);

    if(newEntry){
      title="Add message";
      rootPanel.setEditOk(false);
    }
    else{
      title="Edit message";
    }


    if(!newEntry){
      name=(String)selectedNode.getAttributes().get("name");
    }

    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {

    //for test only

    titledBorder1 = new TitledBorder("");
    nameField.setText(name);
    nameField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        nameField_keyReleased(e);
      }
    });

    this.setBorder(titledBorder1);
    this.setMinimumSize(new Dimension(10, 10));
    this.setPreferredSize(new Dimension(100, 100));
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel2.setText("Message name");
    jPanel1.setLayout(gridBagLayout1);
    this.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(123, 22, 144, 0), 4, 0));
    jPanel1.add(nameField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(123, 0, 144, 101), 169, 0));
    applyTemplate2();
  }

  void cancelButton_actionPerformed(ActionEvent e) {
//    BPFLPanel formPanel =new BPFLPanel(rootPanel, tmlTree);
    rootPanel.changeContentPane( rootPanel.BPFLPANEL);
  }

  void okButton_actionPerformed(ActionEvent e) {
    name=nameField.getText();
    if(name.equals("")){
      rootPanel.showMsg("name is empty!");
    }
    else{
      TreeNode[] nodes = null;
      DefaultTreeModel tmpModel = rootPanel.getBPFLTreeModel();
      if(newEntry){
        NavajoTreeNode newNode = new NavajoTreeNode("message");
        newNode.putAttributes("name", name);
        tmpModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
//        nodes= rootPanel.tmlModel.getPathToRoot(newNode);
        nodes= tmpModel.getPathToRoot(newNode);
        parent.selectedNode = newNode;
      }
      else{
        selectedNode.putAttributes("name", name);
        nodes= tmpModel.getPathToRoot(selectedNode);
      }

      TreePath path = new TreePath(nodes);
      tmlTree.setSelectionPath(path);
      rootPanel.isModified();
//      System.out.println("Before change content pane");
      parent.setButtonsStatus();

      rootPanel.changeContentPane( rootPanel.BPFLPANEL);
      rootPanel.getParent().repaint();
//      System.out.println("After change content pane");
    }
  }

  void deleteButton_actionPerformed(ActionEvent e) {
    if(selectedNode.getTag().equals("method")){
      if(selectedNode.getParent().getChildCount()==1){
        rootPanel.getBPFLTreeModel().removeNodeFromParent((DefaultMutableTreeNode)selectedNode.getParent());
      }
    }
    rootPanel.getBPFLTreeModel().removeNodeFromParent(selectedNode);
    rootPanel.isModified();
    rootPanel.changeContentPane( rootPanel.BPFLPANEL);
  }

  void nameField_keyReleased(KeyEvent e) {
    if(nameField.getText().equals("")){
      rootPanel.setEditOk(false);
    }
    else{
      rootPanel.setEditOk(true);
    }
  }
}