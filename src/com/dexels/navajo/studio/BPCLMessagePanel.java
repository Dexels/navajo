package com.dexels.navajo.studio;


import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class BPCLMessagePanel extends BaseStudioPanel {

    JLabel jLabel1 = new JLabel();
    JTextField messageTextField = new JTextField();
    JLabel jLabel3 = new JLabel();
    JTextField countTextField = new JTextField();
    JLabel jLabel4 = new JLabel();
    JTextField conditionTextField = new JTextField();

    JPanel centerPanel = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    XYLayout xYLayout1 = new XYLayout();

    public BPCLMessagePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPCLMessagePanel(RootStudioPanel root, NavajoTreeNode selected, boolean isNew) {
        newEntry = isNew;
        rootPanel = root;
        selectedNode = selected;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel1.setText("Message name");
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel3.setText("Count");
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel4.setText("Condition");
        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        messageTextField_keyReleased(e);
                    }
                }
                );
        countTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        countTextField_keyReleased(e);
                    }
                }
                );
        centerPanel.setLayout(xYLayout1);
        centerPanel.add(jLabel1, new XYConstraints(64, 113, -1, -1));
        centerPanel.add(messageTextField, new XYConstraints(158, 111, 209, -1));
        centerPanel.add(jLabel3, new XYConstraints(64, 151, -1, -1));
        centerPanel.add(countTextField, new XYConstraints(158, 148, 209, -1));
        centerPanel.add(jLabel4, new XYConstraints(64, 180, -1, -1));
        centerPanel.add(conditionTextField, new XYConstraints(158, 177, 209, -1));

        if (!newEntry) {
            title = "Edit message";
            messageTextField.setText(selectedNode.getAttribute("name"));
            countTextField.setText(selectedNode.getAttribute("count"));
            conditionTextField.setText(selectedNode.getAttribute("condition"));
        } else {
            title = "Add message";
            rootPanel.setEditOk(false);
        }
        applyTemplate2();
        this.add(centerPanel, BorderLayout.CENTER);
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void okButton_actionPerformed(ActionEvent e) {
        String name = messageTextField.getText();

        if (name.equals("")) {
            showError("name is empty");
            System.err.println("BPCLMessagePanel: name == empty");
        } else {
            if (newEntry) {
                NavajoTreeNode tslMessageNode = new NavajoTreeNode("message");

                tslMessageNode.putAttributes("name", name);
                tslMessageNode.putAttributes("count", countTextField.getText());
                tslMessageNode.putAttributes("condition", conditionTextField.getText());
                rootPanel.tslModel.insertNodeInto(tslMessageNode, selectedNode, selectedNode.getChildCount());
                TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(tslMessageNode);
                TreePath path = new TreePath(nodes);

                rootPanel.tslTree.setSelectionPath(path);
            } else {
                selectedNode.setUserObject("<message> " + name);
                selectedNode.putAttributes("name", name);
                selectedNode.putAttributes("count", countTextField.getText());
                selectedNode.putAttributes("condition", conditionTextField.getText());
            }
            rootPanel.isModified();
            rootPanel.changeContentPane(rootPanel.BPCLPANEL);
        }
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        rootPanel.tslModel.removeNodeFromParent(selectedNode);
        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void messageTextField_keyReleased(KeyEvent e) {
        setReadyToProcess();
    }

    void countTextField_keyReleased(KeyEvent e) {
        setReadyToProcess();
    }

    void setReadyToProcess() {
        if (messageTextField.getText().equals("")
                || countTextField.getText().equals("")) {
            rootPanel.setEditOk(false);
        } else {
            rootPanel.setEditOk(true);
        }

    }
}
