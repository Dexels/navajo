package com.dexels.navajo.studio;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.util.*;

import com.borland.jbcl.layout.*;
import java.awt.event.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class BPFLPropertyPanel extends BaseStudioPanel {

    private ButtonGroup buttonGroup1 = new ButtonGroup();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel generalPanel = new JPanel();
    private JTextField descriptionField = new JTextField();
    private JCheckBox cardinalityCheckBox = new JCheckBox();
    private JComboBox typeComboBox = new JComboBox();
    private JComboBox directionComboBox = new JComboBox();
    private JLabel jLabel8 = new JLabel();
    private JTextField lengthField = new JTextField();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JTextField nameField = new JTextField();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JTextField valueField = new JTextField();
    private JPanel selectionPanel = new JPanel();
    private JTextField optionValueField = new JTextField();
    private JLabel jLabel13 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JButton addButton = new JButton();
    private JTextField optionNameField = new JTextField();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JButton removeOptionButton = new JButton();
    private JLabel jLabel7 = new JLabel();
    private DefaultTableModel tModel = new DefaultTableModel();
    private JTable optionTable = new JTable(tModel);
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel centerPanel = new JPanel();
    XYLayout xYLayout1 = new XYLayout();
    XYLayout xYLayout2 = new XYLayout();

    public BPFLPropertyPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPFLPropertyPanel(RootStudioPanel root, NavajoTreeNode selectedNode1, boolean isNew) {

        newEntry = isNew;
        rootPanel = root;
        selectedNode = selectedNode1;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        // setup the layout, buttons and fields on the panel
        cardinalityCheckBox.setEnabled(false);
        typeComboBox.addItem("float");
        typeComboBox.addItem("date");
        typeComboBox.addItem("string");
        typeComboBox.addItem("selection");
        typeComboBox.addItem("boolean");
        typeComboBox.addItem("integer");
        directionComboBox.addItem("in");
        directionComboBox.addItem("out");

        optionTable = new JTable(tModel);

        tModel.setColumnIdentifiers(new String[] {"name", "value"}
                );
        generalPanel.setLayout(xYLayout2);
        cardinalityCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel8.setText("Cardinality");
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel6.setText("Type");
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel5.setText("Direction");
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel4.setToolTipText("");
        jLabel4.setText("Value");
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel3.setText("Description");
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel2.setText("Length");
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel1.setText("Name");
        // lengthField.setText(length);
        // nameField.setText(name);
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        nameField_keyReleased(e);
                    }
                }
                );

        selectionPanel.setLayout(xYLayout1);
        // optionValueField.setText("");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel13.setText("value");
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel12.setText("name");
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel11.setText("Add new option");
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addButton_actionPerformed(e);
                    }
                }
                );
        // optionNameField.setText("");
        // removeOptionButton.setToolTipText("");
        removeOptionButton.setText("remove option");
        removeOptionButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        removeOptionButton_actionPerformed(e);
                    }
                }
                );
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel7.setText("Current options List");
        centerPanel.setLayout(gridBagLayout1);
        this.setMinimumSize(new Dimension(10, 10));
        this.setPreferredSize(new Dimension(100, 100));
        centerPanel.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 54, 17));

        jTabbedPane1.add(generalPanel, "General");
        generalPanel.add(jLabel1, new XYConstraints(39, 2, -1, -1));
        generalPanel.add(descriptionField, new XYConstraints(104, 99, 226, -1));
        generalPanel.add(jLabel8, new XYConstraints(13, 171, -1, -1));
        generalPanel.add(nameField, new XYConstraints(104, 0, 226, -1));
        generalPanel.add(jLabel5, new XYConstraints(22, 204, -1, -1));
        generalPanel.add(jLabel6, new XYConstraints(44, 36, -1, -1));
        generalPanel.add(valueField, new XYConstraints(104, 133, 226, -1));
        generalPanel.add(jLabel2, new XYConstraints(37, 68, -1, -1));
        generalPanel.add(jLabel3, new XYConstraints(13, 102, -1, -1));
        generalPanel.add(jLabel4, new XYConstraints(45, 136, -1, -1));
        generalPanel.add(directionComboBox, new XYConstraints(104, 202, -1, -1));
        generalPanel.add(typeComboBox, new XYConstraints(104, 37, -1, -1));
        generalPanel.add(lengthField, new XYConstraints(104, 68, 96, -1));
        generalPanel.add(cardinalityCheckBox, new XYConstraints(104, 171, -1, -1));
        jTabbedPane1.add(selectionPanel, "Selection");
        jTabbedPane1.setEnabledAt(1, false);

        selectionPanel.add(jScrollPane2, new XYConstraints(243, 29, 116, 138));
        selectionPanel.add(removeOptionButton, new XYConstraints(243, 167, 122, -1));
        selectionPanel.add(optionNameField, new XYConstraints(77, 30, 154, -1));
        selectionPanel.add(jLabel13, new XYConstraints(19, 66, -1, -1));
        selectionPanel.add(optionValueField, new XYConstraints(77, 63, 157, -1));
        selectionPanel.add(jLabel12, new XYConstraints(19, 35, -1, -1));
        selectionPanel.add(addButton, new XYConstraints(12, 91, -1, -1));
        selectionPanel.add(jLabel11, new XYConstraints(25, 9, 128, -1));
        selectionPanel.add(jLabel7, new XYConstraints(245, 7, 116, -1));
        jScrollPane2.getViewport().add(optionTable, null);

        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        typeComboBox_actionPerformed(e);
                    }
                }
                );

        if (!newEntry) {
            title = "Edit property";
            rootPanel.setEditOk(true);

            // filling the fields with data from selected node
            nameField.setText(selectedNode.getAttribute("name"));
            typeComboBox.setSelectedItem(selectedNode.getAttribute("type"));
            lengthField.setText(selectedNode.getAttribute("length"));
            descriptionField.setText(selectedNode.getAttribute("description"));
            valueField.setText(selectedNode.getAttribute("value"));
            directionComboBox.setSelectedItem(selectedNode.getAttribute("direction"));

            if (selectedNode.getAttribute("cardinality") != null) {
                if (selectedNode.getAttribute("cardinality").equals("+")) {
                    cardinalityCheckBox.setSelected(true);
                }
            }

            String type = (String) typeComboBox.getSelectedItem();

            if (type.equals("selection")) {
                jTabbedPane1.setEnabledAt(1, true);
                cardinalityCheckBox.setEnabled(true);
            }

            // get options node from selected node
            int optionsCount = selectedNode.getChildCount();
            DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();

            for (int i = 0; i < optionsCount; i++) {
                NavajoTreeNode option = (NavajoTreeNode) selectedNode.getChildAt(i);

                tmpModel.addRow(new Object[] {option.getAttribute("name"), option.getAttribute("value")}
                        );
            }
        } else {
            // adding new property, fields stays empty
            title = "Add property";
            rootPanel.setEditOk(false);
        }
        this.add(centerPanel, BorderLayout.CENTER);
        applyTemplate2();
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        // BPFLPanel formPanel =new BPFLPanel(rootPanel);
        rootPanel.changeContentPane(rootPanel.BPFLPANEL);
    }

    void okButton_actionPerformed(ActionEvent e) {

        NavajoTreeNode newNode = new NavajoTreeNode("property");

        if (newEntry) {
            addAtrributesToNodes(newNode);
            rootPanel.getBPFLTreeModel().insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            TreeNode[] nodes = rootPanel.getBPFLTreeModel().getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);

            rootPanel.getBPFLTree().setSelectionPath(path);
        } else {
            NavajoTreeNode root = (NavajoTreeNode) selectedNode.getParent();

            rootPanel.getBPFLTreeModel().removeNodeFromParent(selectedNode);
            selectedNode = new NavajoTreeNode("property");
            addAtrributesToNodes(selectedNode);

            rootPanel.getBPFLTreeModel().insertNodeInto(selectedNode, root, root.getChildCount());
            TreeNode[] nodes = rootPanel.getBPFLTreeModel().getPathToRoot(selectedNode);
            TreePath path = new TreePath(nodes);

            rootPanel.getBPFLTree().setSelectionPath(path);
        }

        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPFLPANEL);
    }

    void addAtrributesToNodes(NavajoTreeNode node) {
        String name = nameField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        String length = lengthField.getText();
        String description = descriptionField.getText();
        String value = valueField.getText();
        String direction = (String) directionComboBox.getSelectedItem();
        String cardinality = "";

        node.putAttributes("name", name);
        node.putAttributes("type", type);
        node.putAttributes("length", length);
        node.putAttributes("description", description);
        node.putAttributes("value", value);
        node.putAttributes("direction", direction);

        // String type = (String)typeComboBox.getSelectedItem();
        // boolean selection  = ;

        if (type.equals("selection")) {
            // there are options assigned to this node, so here we add the option nodes to the property node
            DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();
            int options = tmpModel.getRowCount();

            for (int i = 0; i < options; i++) {
                String optionName = (String) tmpModel.getValueAt(i, 0);
                String optionValue = (String) tmpModel.getValueAt(i, 1);
                NavajoTreeNode tmpOptionNode = new NavajoTreeNode("option");

                tmpOptionNode.putAttributes("name", optionName);
                tmpOptionNode.putAttributes("value", optionValue);
                if (i == 0) {
                    tmpOptionNode.putAttributes("selected", "1");
                } else {
                    tmpOptionNode.putAttributes("selected", "0");
                }
                rootPanel.getBPFLTreeModel().insertNodeInto(tmpOptionNode, node, node.getChildCount());

                /*
                 if(newEntry){
                 rootPanel.getBPFLTree().insertNodeInto(tmpOptionNode, newNode, newNode.getChildCount());
                 }
                 else{
                 rootPanel.getBPFLTree().insertNodeInto(tmpOptionNode, selectedNode, selectedNode.getChildCount());
                 }
                 */
            }
            if (cardinalityCheckBox.isSelected()) {
                cardinality = "+";
            } else {
                cardinality = "1";
            }
            node.putAttributes("cardinality", cardinality);

        }
    }

    void addButton_actionPerformed(ActionEvent e) {

        String newOptionName = optionNameField.getText();

        optionNameField.setText("");
        String newOptionValue = optionValueField.getText();

        optionValueField.setText("");

        if (!newOptionName.equals("")) {
            DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();

            tmpModel.addRow(new Object[] {newOptionName, newOptionValue}
                    );
        }
    }

    void removeOptionButton_actionPerformed(ActionEvent e) {
        DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();
        int selected = optionTable.getSelectedRow();

        if (selected != -1) {
            System.err.println("selected" + selected);
            tmpModel.removeRow(selected);
        }

    }

    void typeComboBox_actionPerformed(ActionEvent e) {

        String selected = (String) typeComboBox.getSelectedItem();
        boolean isSelection = selected.equals("selection");

        if (isSelection) {
            jTabbedPane1.setEnabledAt(1, true);
            cardinalityCheckBox.setEnabled(true);
        } else {
            jTabbedPane1.setEnabledAt(1, false);
            cardinalityCheckBox.setEnabled(false);
        }
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        if (selectedNode.getTag().equals("method")) {
            if (selectedNode.getParent().getChildCount() == 1) {
                rootPanel.getBPFLTreeModel().removeNodeFromParent((DefaultMutableTreeNode) selectedNode.getParent());
            }
        }
        rootPanel.getBPFLTreeModel().removeNodeFromParent(selectedNode);
        // rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPFLPANEL);
    }

    private void nameField_keyReleased(KeyEvent e) {
        if (nameField.getText().equals("")) {
            rootPanel.setEditOk(false);
        } else {
            rootPanel.setEditOk(true);
        }
    }

}
