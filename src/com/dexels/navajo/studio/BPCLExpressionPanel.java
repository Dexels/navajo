package com.dexels.navajo.studio;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import javax.swing.border.*;
import com.dexels.navajo.util.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class BPCLExpressionPanel extends BaseStudioPanel  {


  JTabbedPane jTabbedPane2 = new JTabbedPane();
  JPanel objectPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane classScrollPane = new JScrollPane();
  JPanel formPanel = new JPanel();
  JScrollPane formjScrollPane = new JScrollPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JTree classTree = new JTree();
  JTree formTree = new JTree();

  private int tabState = 0;
  JPanel valuePanel = new JPanel();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel conditionPanel = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JTextArea valueTextField = new JTextArea();
  JTextArea conditionTextField = new JTextArea();

  public BPCLExpressionPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @todo explain this constructor
   */
  public BPCLExpressionPanel(RootStudioPanel root, NavajoTreeNode selected, boolean isNew) {
    newEntry = isNew;
    rootPanel = root;
    selectedNode = selected;
    try {
      formTree =new JTree(rootPanel.getBPFL_in_TreeModel());

      Util.debugLog("in BPCLExpressionPanel constructor, before getCurrentMappableObjectTreeModelSet");
      //classTree = new JTree(rootPanel.getCurrentMappableObjectTreeModel());
      classTree = rootPanel.currentMappableObjectTree;

      String className = (String)((DefaultMutableTreeNode)(classTree.getModel()).getRoot()).getUserObject();
      rootPanel.showMsg("class: " + className);

//      if(className==null||className.equals("")){
//        classTree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("no object defient")));
//      }

      jbInit();
      if(!newEntry){
        String name = selectedNode.getAttribute("name");
        valueTextField.setText(name);
        conditionTextField.setText(selectedNode.getAttribute("condition"));
        rootPanel.setEditOk(true);
        rootPanel.setEditOkStatusMsg("Ready");
      }else{
        rootPanel.setEditOk(false);
        rootPanel.setEditOkStatusMsg("Please define value");
        System.err.println("SET TO FLASE");
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {

    valueTextField.setLineWrap(true);
    valueTextField.setWrapStyleWord(true);
    conditionTextField.setLineWrap(true);
    conditionTextField.setWrapStyleWord(true);
    objectPanel.setLayout(borderLayout1);
    formPanel.setLayout(borderLayout4);
    jTabbedPane2.setTabPlacement(JTabbedPane.BOTTOM);
    classTree.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        classTree_mouseReleased(e);
      }
    });
    formTree.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        formTree_mouseReleased(e);
      }
    });
    valuePanel.setLayout(borderLayout2);
    jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        jTabbedPane1_mouseClicked(e);
      }
    });
    conditionPanel.setLayout(borderLayout3);
    valueTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        valueTextField_keyReleased(e);
      }
    });
    this.add(jTabbedPane2, BorderLayout.CENTER);

    this.add(jTabbedPane1, BorderLayout.NORTH);
    jTabbedPane1.add(valuePanel, "Value");
    valuePanel.add(valueTextField, BorderLayout.NORTH);
    jTabbedPane1.add(conditionPanel, "Condition");
    conditionPanel.add(conditionTextField, BorderLayout.NORTH);
    jTabbedPane2.add(objectPanel, "Object");
    objectPanel.add(classScrollPane, BorderLayout.CENTER);
    classScrollPane.getViewport().add(classTree, null);
    jTabbedPane2.add(formPanel, "Incoming BPFL");
    formPanel.add(formjScrollPane, BorderLayout.CENTER);
    formjScrollPane.getViewport().add(formTree, null);
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    rootPanel.changeContentPane( rootPanel.BPCLPANEL);
  }

  void okButton_actionPerformed(ActionEvent e) {
    String name = valueTextField.getText();
    name = name.replace('\n', ' ');
    Util.debugLog("Expression = " + name);
    String condition = conditionTextField.getText();
    condition = condition.replace('\n', ' ');
    Util.debugLog("Condition  = " + condition);

    boolean ok = com.dexels.navajo.parser.Verifier.verifyExpression(name);
    if (!ok) {
      rootPanel.showMsg("Invalid expression");
    } else {
      ok = com.dexels.navajo.parser.Verifier.verifyCondition(conditionTextField.getText());
      if (!ok) {
        rootPanel.showMsg("Invalid condition");
      }
    }

    if (ok) {
      if(newEntry){
        NavajoTreeNode expressionNode = new NavajoTreeNode("expression");
        expressionNode.putAttributes("name", name);
        expressionNode.putAttributes("condition", conditionTextField.getText());
  //      TreePath path = rootPanel.tslTree.getSelectionPath();
        rootPanel.tslModel.insertNodeInto(expressionNode, selectedNode, selectedNode.getChildCount());
        TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(expressionNode);
        TreePath path = new TreePath(nodes);
        rootPanel.tslTree.setSelectionPath(path);
      }
      else{
        selectedNode.setUserObject("<expression> " + name);
        selectedNode.putAttributes("name", name);
        selectedNode.putAttributes("condition", conditionTextField.getText());
      }
      rootPanel.isModified();
      rootPanel.changeContentPane( rootPanel.BPCLPANEL);
    }
  }

  void deleteButton_actionPerformed(ActionEvent e) {
    rootPanel.tslModel.removeNodeFromParent(selectedNode);
    rootPanel.isModified();
    rootPanel.changeContentPane( rootPanel.BPCLPANEL);
  }

  void classTree_mouseReleased(MouseEvent e) {
    if (e.getClickCount() == 2) {

      TreePath treepath= classTree.getSelectionPath();

      if(treepath!=null){
        classTree.setSelectionPath(treepath);
        classTree.scrollPathToVisible(treepath);

        ClassTreeNode fieldNode = (ClassTreeNode)treepath.getLastPathComponent();
  //      String field = fieldNode.getFieldName();


        TreeNode nodes[] = ((DefaultTreeModel)classTree.getModel()).getPathToRoot(fieldNode);
        int depth=0;
        String mapPath="$";

        for(int i = 1; i< nodes.length; i++){
          ClassTreeNode tmpNode = (ClassTreeNode)nodes[i];
          String field = tmpNode.getFieldName();
          int pos = field.lastIndexOf(" ");
          field=field.substring(pos+1);
          mapPath=mapPath+field;
          if(i<nodes.length-1){
            mapPath=mapPath+".";
          }
        }

        if(tabState==0){
          valueTextField.setText(valueTextField.getText()+mapPath);
          rootPanel.setEditOk(true);
        }
        else{
          conditionTextField.setText(conditionTextField.getText()+mapPath);
        }
      }
    }
  }

  void formTree_mouseReleased(MouseEvent e) {
    if (e.getClickCount() > 1) {
      TreePath treepath= formTree.getSelectionPath();
      String completeName="";

      if(treepath!=null){
        formTree.scrollPathToVisible(treepath);
        formTree.setSelectionPath(treepath);
        NavajoTreeNode propertyNode = (NavajoTreeNode)formTree.getLastSelectedPathComponent();
        //treepath.getLastPathComponent();


        if(propertyNode.getTag().equals("property")){
          //get attribute "name" from tml, set attribute "name" of tml
          TreeNode nodes[] = propertyNode.getPath();
          for(int i = 1; i<nodes.length; i++){
            NavajoTreeNode tmpNode = (NavajoTreeNode)nodes[i];
            completeName=completeName+tmpNode.getAttribute("name");
            if(i<nodes.length-1)
              completeName=completeName+"/";
          }
        }

        if(tabState==0){
          valueTextField.setText(valueTextField.getText()+"[/"+completeName+"]");
        }
        else{
          conditionTextField.setText(conditionTextField.getText()+"[/"+completeName+"]");
        }
      }
    }
  }

  void jTabbedPane1_mouseClicked(MouseEvent e) {
      tabState =jTabbedPane1.getSelectedIndex();
  }

  void valueTextField_keyReleased(KeyEvent e) {
    if(valueTextField.getText().equals("")){
      rootPanel.setEditOk(false);
      rootPanel.setEditOkStatusMsg("Please fill all required fields");
    }
    else{
      rootPanel.setEditOk(true);
      rootPanel.setEditOkStatusMsg("Ready");
    }
  }


  void updatePanel(NavajoEvent ne){
    boolean IncomingBPFL = ne.getIncomingBPFL();
    if(IncomingBPFL){
      System.err.println("incoming BPFL changed");
      formTree.setModel(rootPanel.getBPFL_in_TreeModel());
    }
  }

}