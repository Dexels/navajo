package com.dexels.navajo.studio;


import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.event.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class BPFLMethodPanel extends BaseStudioPanel {

    private String name = "";
    private JLabel jLabel1 = new JLabel();
    private JTextField nameField = new JTextField();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTable requiredTable = new JTable();
    private JButton removeRequiredButton = new JButton();
    private JButton addRequiredButton = new JButton();
    private JTextField requiredMessageField = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    GridBagLayout gridBagLayout3 = new GridBagLayout();
    JPanel centerPanel = new JPanel();

    public BPFLMethodPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPFLMethodPanel(RootStudioPanel rootPanel1, NavajoTreeNode selectedNode1, boolean isNewEntry) {
        rootPanel = rootPanel1;
        selectedNode = selectedNode1;
        newEntry = isNewEntry;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {

        DefaultTableModel tModel = new DefaultTableModel();

        tModel.setColumnIdentifiers(new String[] {"message"}
                );
        requiredTable = new JTable(tModel);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel1.setText("Method name");

        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        nameField_keyReleased(e);
                    }
                }
                );

        addRequiredButton.setText("add to list");
        addRequiredButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addRequiredButton_actionPerformed(e);
                    }
                }
                );
        removeRequiredButton.setText("Remove selected message from list");
        removeRequiredButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        removeRequiredButton_actionPerformed(e);
                    }
                }
                );
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel2.setText("Current List of Required messages:");
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel3.setText("New  required message");
        jPanel1.setLayout(gridBagLayout1);
        jPanel2.setLayout(gridBagLayout3);

        centerPanel.setLayout(gridBagLayout2);
        this.setMinimumSize(new Dimension(10, 10));
        this.setPreferredSize(new Dimension(100, 100));
        centerPanel.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
        jTabbedPane1.add(jPanel1, "General");
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        jPanel1.add(nameField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 327, 0));
        jTabbedPane1.add(jPanel2, "Required messages");
        jPanel2.add(jScrollPane2, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 20, 0, 0), -118, -275));
        jPanel2.add(requiredMessageField, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 0), 332, 0));
        jPanel2.add(jLabel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 20, 10, 200), 0, 0));
        jPanel2.add(addRequiredButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 20, 10, 135), 0, 0));
        jPanel2.add(removeRequiredButton, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 103, 0));
        jScrollPane2.getViewport().add(requiredTable, null);

        if (newEntry == false) {
            title = "Edit method";
            name = selectedNode.getAttribute("name");
            nameField.setText(name);
            int requiredsCount = selectedNode.getChildCount();
            DefaultTableModel tmpModel = (DefaultTableModel) requiredTable.getModel();

            for (int i = 0; i < requiredsCount; i++) {
                NavajoTreeNode required = (NavajoTreeNode) selectedNode.getChildAt(i);

                tmpModel.addRow(new Object[] {required.getAttribute("message")}
                        );
            }
        } else {
            title = "Add method";
            rootPanel.setEditOk(false);
        }
        this.add(centerPanel, BorderLayout.CENTER);
        applyTemplate2();
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void okButton_actionPerformed(ActionEvent e) {
        name = nameField.getText();
        NavajoTreeNode newNode = new NavajoTreeNode("method");

        newNode.putAttributes("name", name);
        if (newEntry) {
            rootPanel.getBPCLTreeModel().insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
        } else {
            // replace the node by removing the old then adding the new
            NavajoTreeNode parent = (NavajoTreeNode) selectedNode.getParent();

            rootPanel.getBPCLTreeModel().removeNodeFromParent(selectedNode);
            rootPanel.getBPCLTreeModel().insertNodeInto(newNode, parent, parent.getChildCount());
        }

        DefaultTableModel tmpModel = (DefaultTableModel) requiredTable.getModel();
        int options = tmpModel.getRowCount();

        for (int i = 0; i < options; i++) {
            String required = (String) tmpModel.getValueAt(i, 0);
            NavajoTreeNode tmpRequiredNode = new NavajoTreeNode("required");

            tmpRequiredNode.putAttributes("message", required);
            rootPanel.getBPCLTreeModel().insertNodeInto(tmpRequiredNode, newNode, newNode.getChildCount());
        }

        TreeNode[] nodes = rootPanel.getBPCLTreeModel().getPathToRoot(newNode);
        TreePath path = new TreePath(nodes);

        rootPanel.getBPCLTree().setSelectionPath(path);

        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void addRequiredButton_actionPerformed(ActionEvent e) {
        String newRequired = requiredMessageField.getText();

        requiredMessageField.setText("");

        if (!newRequired.equals("")) {
            DefaultTableModel tmpModel = (DefaultTableModel) requiredTable.getModel();

            tmpModel.addRow(new Object[] {newRequired}
                    );
        }

    }

    void removeRequiredButton_actionPerformed(ActionEvent e) {

        DefaultTableModel tmpModel = (DefaultTableModel) requiredTable.getModel();
        int selected = requiredTable.getSelectedRow();

        if (selected != -1) {
            System.err.println("selected" + selected);
            tmpModel.removeRow(selected);
        }

    }

    void deleteButton_actionPerformed(ActionEvent e) {
        if (selectedNode.getTag().equals("method")) {
            if (selectedNode.getParent().getChildCount() == 1) {
                rootPanel.getBPCLTreeModel().removeNodeFromParent((DefaultMutableTreeNode) selectedNode.getParent());
            }
        }
        rootPanel.getBPCLTreeModel().removeNodeFromParent(selectedNode);
        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void nameField_keyReleased(KeyEvent e) {
        // System.err.println("DEBUG fieldTextField.getText(): " + nameField.getText());
        if (nameField.getText().equals("")) {
            rootPanel.setEditOk(false);
        } else {
            rootPanel.setEditOk(true);
        }
    }

}
