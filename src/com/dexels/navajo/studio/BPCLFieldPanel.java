package com.dexels.navajo.studio;


import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.beans.*;
import javax.swing.border.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author ALbert Lo
 * @version 1.0
 */

public class BPCLFieldPanel extends BaseStudioPanel {

    JTree jTree1 = new JTree();
    JTextField fieldTextField = new JTextField();
    DefaultTreeModel model = null;
    JLabel jLabel2 = new JLabel();
    JPanel jPanel2 = new JPanel();
    // JLabel statusLabel1         = new JLabel();
    // JLabel statusLabel2         = new JLabel();
    JPanel  centerPanel = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel conditionLabel = new JLabel();
    JTextField conditionField = new JTextField();
    String condition = "";
    BorderLayout borderLayout2 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    XYLayout xYLayout1 = new XYLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    TitledBorder titledBorder1;

    /*
     boolean submapping          = false;
     JLabel jLabel7              = new JLabel();
     JTextField refTextField1    = new JTextField();
     */
    public BPCLFieldPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPCLFieldPanel(RootStudioPanel rootPanel1, NavajoTreeNode selected, boolean isNew) {
        newEntry = isNew;
        rootPanel = rootPanel1;
        selectedNode = selected;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        this.setLayout(borderLayout1);
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel2.setText("Name field");
        jPanel2.setLayout(borderLayout3);
        fieldTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        fieldTextField_keyReleased(e);
                    }
                }
                );
        // jLabel7.setFont(new java.awt.Font("Dialog", 1, 12));
        // jLabel7.setText("ref");

        centerPanel.setLayout(borderLayout2);
        conditionLabel.setFont(new java.awt.Font("Dialog", 1, 12));
        conditionLabel.setToolTipText("");
        conditionLabel.setText("Condition:");
        conditionField.setText("");
        JPanel footerPanel = new JPanel(xYLayout1);

        footerPanel.setPreferredSize(new Dimension(53, 80));
        footerPanel.setLayout(xYLayout1);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel1.setText("Select a field");
        this.setBorder(titledBorder1);
        footerPanel.add(conditionField, new XYConstraints(1, 58, 562, -1));
        footerPanel.add(conditionLabel, new XYConstraints(1, 41, 657, -1));
        footerPanel.add(jLabel2, new XYConstraints(2, 2, -1, -1));
        footerPanel.add(fieldTextField, new XYConstraints(2, 19, 563, 22));
        centerPanel.add(jPanel2, BorderLayout.CENTER);
        // jPanel2.add(statusLabel1, new XYConstraints(6, 218, 445, 22));
        // jPanel2.add(statusLabel2, new XYConstraints(6, 240, 445, 22));
        jPanel2.add(jScrollPane1, BorderLayout.CENTER);
        jPanel2.add(jLabel1, BorderLayout.NORTH);
        centerPanel.add(footerPanel, BorderLayout.SOUTH);
        // this.add(jLabel6, new XYConstraints(115, 56, 377, 21));

        // if(rootPanel.useobject!=null){
        // jTree1 = rootPanel.mainMappableObjectTree;//new JTree(model);
        if (rootPanel.currentObject != null) {
            // Get class tree with only set methods!!
            jTree1 = rootPanel.currentMappableObjectTreeSet;// new JTree(model);
            jScrollPane1.getViewport().add(jTree1, null);
            jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            jTree1_mousePressed(e);
                        }
                    }
                    );
        } else {
            jScrollPane1.getViewport().setVisible(false);
        }

        if (!newEntry) {
            title = "Edit field";

            fieldTextField.setText(selectedNode.getAttribute("name"));
            conditionField.setText(selectedNode.getAttribute("condition"));
            // if(selectedNode.getAttribute("multi")!=null){
            // if(selectedNode.getAttribute("multi").equals("true")){
            // multiCheckBox.setSelected(true);
            // }
            // }
            // if(selectedNode.getAttribute("selection")!=null){
            // if(selectedNode.getAttribute("selection").equals("true")){
            // selectionCheckBox.setSelected(true);
            // }
            // }
        } else {
            title = "Add field";
            rootPanel.setEditOk(false);
        }
        applyTemplate2();
        this.add(centerPanel, BorderLayout.CENTER);
    }

    void okButton_actionPerformed(ActionEvent e) {

        String field = fieldTextField.getText();

        condition = conditionField.getText();

        if (newEntry) {
            NavajoTreeNode fieldNode = new NavajoTreeNode("field");

            addAttributesToNode(fieldNode);
            rootPanel.tslModel.insertNodeInto(fieldNode, selectedNode, selectedNode.getChildCount());

            TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(fieldNode);
            TreePath path = new TreePath(nodes);

            rootPanel.tslTree.setSelectionPath(path);
        } else {
            selectedNode.setUserObject("<field> " + field);
            addAttributesToNode(selectedNode);
        }
        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void addAttributesToNode(NavajoTreeNode node) {

        node.putAttributes("name", fieldTextField.getText());
        // if(multiCheckBox.isSelected()){
        // node.putAttributes("multi", "true");
        // }
        // else{
        // node.putAttributes("multi", "false");
        // }
        // if(selectionCheckBox.isSelected()){
        // node.putAttributes("selection", "true");
        // }
        // else{
        // node.putAttributes("selection", "false");
        // }

        if (condition.equals("")) {} else {
            node.putAttributes("condition", condition);
        }
    }

    void jTree1_mousePressed(MouseEvent e) {
        TreePath treepath = jTree1.getClosestPathForLocation(e.getX(), e.getY());

        if (treepath != null) {
            jTree1.setSelectionPath(treepath);
            jTree1.scrollPathToVisible(treepath);
            ClassTreeNode fieldNode = (ClassTreeNode) treepath.getLastPathComponent();
            String field = fieldNode.getFieldName();

            // String mapPath = field;

            /*
             // get fullpath
             DefaultTreeModel model1 = (DefaultTreeModel)jTree1.getModel();
             TreeNode nodes[] = model1.getPathToRoot(fieldNode);
             int depth=0;
             mapPath="";

             for(int i = 1; i< nodes.length; i++){
             DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode)nodes[i];
             String field = (String)tmpNode.getUserObject();
             int pos = field.lastIndexOf(" ");
             field=field.substring(pos+1);
             mapPath=mapPath+field;
             if(i<nodes.length-1){
             mapPath=mapPath+".";
             }
             }
             */
            // if(fieldNode.getChildCount()==0){
            fieldTextField.setText(field);
            rootPanel.setEditOk(true);
            // }else{
            // rootPanel.setEditOk(false);
            // }

        }
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        rootPanel.tslModel.removeNodeFromParent(selectedNode);
        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    private void fieldTextField_keyReleased(KeyEvent e) {
        if (fieldTextField.getText().equals("")) {
            rootPanel.setEditOk(false);
        } else {
            rootPanel.setEditOk(true);
        }
    }

}
