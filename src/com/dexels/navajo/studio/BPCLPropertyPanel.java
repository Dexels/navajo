package com.dexels.navajo.studio;


import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import java.awt.event.*;
import javax.swing.border.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class BPCLPropertyPanel extends BaseStudioPanel {

    static final int NAME = 0;
    static final int TYPE = 1;
    static final int VALUE = 2;
    static final int DESCRIPTION = 3;
    static final int CARDINALITY = 4;
    static final int DIRECTION = 5;
    static final int LENGTH = 6;

    JComboBox typeComboBox = new JComboBox();
    JTextField nameTextField = new JTextField();
    JTextField valueTextField = new JTextField();
    private String[] col_names = {"Name", "Value"};
    private DefaultTableModel tableModel = new DefaultTableModel();
    // ButtonGroup buttonGroup1        = new ButtonGroup();
    JPanel centerPanel = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();

    private boolean parameter = false;
    private String context = "";
    JTextField descriptionTextField = new JTextField();
    JTextField lengthTextField = new JTextField();
    JScrollPane jScrollPane1 = new JScrollPane();
    JComboBox cardinalityComboBox = new JComboBox();
    TitledBorder titledBorder1;
    JLabel jLabel3 = new JLabel();
    JComboBox directionComboBox = new JComboBox();
    JTree tmpBPFLTree = new JTree();
    JSplitPane jSplitPane1 = new JSplitPane();
    GridLayout gridLayout1 = new GridLayout();
    JScrollPane jScrollPane2 = new JScrollPane();
    JTableX jTable1 = new JTableX(tableModel);
    JPanel selectionPanel = new JPanel();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JScrollPane jScrollPane3 = new JScrollPane();
    JButton removeOptionButton = new JButton();
    JTextField optionNameField = new JTextField();
    JLabel jLabel13 = new JLabel();
    JTextField optionValueField = new JTextField();
    JLabel jLabel12 = new JLabel();
    JButton addButton = new JButton();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel7 = new JLabel();
    DefaultTableModel tModel = new DefaultTableModel();
    JTable optionTable = new JTable(tModel);
    JCheckBox cardinalityCheckBox = new JCheckBox();


    // JLabel jLabel7                  = new JLabel();
    // JTextField refTextField1        = new JTextField();
    // boolean submapping              = false;

    public BPCLPropertyPanel(boolean parameter) {
        this.parameter = parameter;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPCLPropertyPanel(RootStudioPanel root, NavajoTreeNode selected, boolean isNew, boolean parameter) {
        newEntry = isNew;
        rootPanel = root;
        selectedNode = selected;
        this.parameter = parameter;
        tmpBPFLTree.setModel(rootPanel.getBPFLTreeModel());

        tableModel = new DefaultTableModel(col_names, 7) {

                    public String[] prop_names = { "Name", "Type", "Value", "Description", "Cardinality", "Direction", "Length" };
                    public Object getValueAt(int row, int col) {
                        if (col == 0)
                            return prop_names[row];
                        return super.getValueAt(row, col);
                    }

                    public boolean isCellEditable(int row, int col) {
                        if (col == 0)
                            return false;
                        return true;

                    }
                };
        // create a RowEditorModel... this is used to hold the extra
        // information that is needed to deal with row specific editors
        RowEditorModel rm = new RowEditorModel();

        // tell the JTableX which RowEditorModel we are using
        jTable1.setRowEditorModel(rm);

        DefaultCellEditor ed = new DefaultCellEditor(nameTextField);

        rm.addEditorForRow(0, ed);
        ed = new DefaultCellEditor(typeComboBox);
        rm.addEditorForRow(1, ed);
        ed = new DefaultCellEditor(valueTextField);
        rm.addEditorForRow(2, ed);
        ed = new DefaultCellEditor(descriptionTextField);
        rm.addEditorForRow(3, ed);
        ed = new DefaultCellEditor(cardinalityComboBox);
        rm.addEditorForRow(4, ed);
        ed = new DefaultCellEditor(directionComboBox);
        rm.addEditorForRow(5, ed);
        ed = new DefaultCellEditor(lengthTextField);
        rm.addEditorForRow(6, ed);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //if (parameter) {
            jSplitPane1.remove(jScrollPane1);
            this.remove(jLabel3);
        //}

    }

    void jbInit() throws Exception {

        XYLayout xYLayout1 = new XYLayout();
        selectionPanel.setLayout(xYLayout1);

        cardinalityCheckBox.setEnabled(false);

        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("Define a property or select from list");
        tmpBPFLTree.setBorder(BorderFactory.createLineBorder(Color.black));
        titledBorder1 = new TitledBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(212, 208, 200)), "");
        jScrollPane1.setBorder(null);
        centerPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        centerPanel.setMinimumSize(new Dimension(1, 1));
        centerPanel.setPreferredSize(new Dimension(200, 272));

        // System.out.println("jbInit(), parameter = " + parameter);

        this.setLayout(borderLayout1);

        tmpBPFLTree.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        tmpBPFLTree_mouseClicked(e);
                    }
                }
                );
        // nameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
        // public void keyReleased(KeyEvent e) {
        // nameTextField_keyReleased(e);
        // }
        // });
        // jLabel7.setFont(new java.awt.Font("Dialog", 1, 12));
        // jLabel7.setText("ref");
        this.setMinimumSize(new Dimension(10, 10));
        this.setPreferredSize(new Dimension(100, 100));

        //if (!parameter) {
        //    jScrollPane1.getViewport().add(tmpBPFLTree);
        //    tmpBPFLTree.repaint();
        //}
        typeComboBox.addItem("string");
        typeComboBox.addItem("integer");
        typeComboBox.addItem("float");
        typeComboBox.addItem("date");
        typeComboBox.addItem("selection");
        typeComboBox.addItem("boolean");
        typeComboBox.addItem("points");

        directionComboBox.addItem("in");
        directionComboBox.addItem("out");

        cardinalityComboBox.addItem("1");
        cardinalityComboBox.addItem("+");

        centerPanel.setLayout(gridLayout1);

        jTable1.setModel(tableModel);
        jTable1.setRowHeight(20);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        jTable1_keyReleased(e);
                    }
                }
                );
        this.add(jLabel3, BorderLayout.NORTH);

        this.add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.add(centerPanel, JSplitPane.BOTTOM);
        centerPanel.add(jScrollPane2, null);

        jTabbedPane1.add(jTable1, "General");
        jTabbedPane1.add(selectionPanel, "Selection");
        jTabbedPane1.setEnabledAt(1, false);
        //centerPanel.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 54, 17));

        //jScrollPane2.getViewport().add(jTable1, null);
        jScrollPane2.getViewport().add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 54, 17));
        jSplitPane1.add(jScrollPane1, JSplitPane.TOP);

        jScrollPane1.getViewport().add(tmpBPFLTree, null);
        jScrollPane1.setVisible(false);

        if (!newEntry) {
            jTable1.setValueAt(selectedNode.getAttribute("name"), NAME, 1);
            jTable1.setValueAt(selectedNode.getAttribute("type"), TYPE, 1);
            jTable1.setValueAt(selectedNode.getAttribute("value"), VALUE, 1);
            jTable1.setValueAt(selectedNode.getAttribute("length"), LENGTH, 1);
            jTable1.setValueAt(selectedNode.getAttribute("description"), DESCRIPTION, 1);
            jTable1.setValueAt(selectedNode.getAttribute("cardinality"), CARDINALITY, 1);
            jTable1.setValueAt(selectedNode.getAttribute("direction"), DIRECTION, 1);

        }
        else {
            rootPanel.setEditOk(false);
        }

        removeOptionButton.setText("remove option");
        removeOptionButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        removeSelectionOptionButton_actionPerformed(e);
                    }
                }
                );

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addButton_actionPerformed(e);
                    }
                }
                );

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel13.setText("value");
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel12.setText("name");
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel11.setText("Add new option");
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel7.setText("Current options List");

        optionTable = new JTable(tModel);
        tModel.setColumnIdentifiers(new String[] {"name", "value"});

        selectionPanel.add(jScrollPane3, new XYConstraints(243, 29, 116, 138));
        selectionPanel.add(removeOptionButton, new XYConstraints(243, 167, 122, -1));
        selectionPanel.add(optionNameField, new XYConstraints(77, 30, 154, -1));
        selectionPanel.add(jLabel13, new XYConstraints(19, 66, -1, -1));
        selectionPanel.add(optionValueField, new XYConstraints(77, 63, 157, -1));
        selectionPanel.add(jLabel12, new XYConstraints(19, 35, -1, -1));
        selectionPanel.add(addButton, new XYConstraints(12, 91, -1, -1));
        selectionPanel.add(jLabel11, new XYConstraints(25, 9, 128, -1));
        selectionPanel.add(jLabel7, new XYConstraints(245, 7, 116, -1));
        jScrollPane3.getViewport().add(optionTable, null);

        // get options node from selected node
        int optionsCount = selectedNode.getChildCount();
        DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();

        for (int i = 0; i < optionsCount; i++) {
                NavajoTreeNode option = (NavajoTreeNode) selectedNode.getChildAt(i);
                tmpModel.addRow(new Object[] {option.getAttribute("name"), option.getAttribute("value")}
                        );
        }

        String type = (String) jTable1.getValueAt(TYPE, 1);
        if ((type != null) && type.equals("selection")) {
                        jTabbedPane1.setEnabledAt(1, true);
                        cardinalityCheckBox.setEnabled(true);
        }

        applyTemplate2();
        jSplitPane1.setDividerLocation(150);
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void okButton_actionPerformed(ActionEvent e) {
        jTable1.repaint();
        String name = (String) jTable1.getValueAt(NAME, 1);

        if (name == null || name.equals("")) {
            showMsg("field name is empty");
            rootPanel.showStatus("field name is empty");
            rootPanel.setEditOk(false);
        } else {
            if (newEntry) {
                NavajoTreeNode tslPropertyNode = new NavajoTreeNode((parameter) ? "param" : "property");

                tslPropertyNode.putAttributes("name", (String) jTable1.getValueAt(NAME, 1));
                tslPropertyNode.putAttributes("type", (String) jTable1.getValueAt(TYPE, 1));
                tslPropertyNode.putAttributes("value", (String) jTable1.getValueAt(VALUE, 1));
                tslPropertyNode.putAttributes("description", (String) jTable1.getValueAt(DESCRIPTION, 1));
                tslPropertyNode.putAttributes("direction", (String) jTable1.getValueAt(DIRECTION, 1));
                tslPropertyNode.putAttributes("cardinality", (String) jTable1.getValueAt(CARDINALITY, 1));
                tslPropertyNode.putAttributes("length", (String) jTable1.getValueAt(LENGTH, 1));

                String type = (String) jTable1.getValueAt(TYPE, 1);
                if ((type != null) && type.equals("selection")) {
                        jTabbedPane1.setEnabledAt(1, true);
                        cardinalityCheckBox.setEnabled(true);


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
                      rootPanel.getBPCLTreeModel().insertNodeInto(tmpOptionNode, tslPropertyNode,
                                                                  tslPropertyNode.getChildCount());
                  }
                }

                rootPanel.tslModel.insertNodeInto(tslPropertyNode, selectedNode, selectedNode.getChildCount());
                TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(tslPropertyNode);
                TreePath path = new TreePath(nodes);
                rootPanel.tslTree.setSelectionPath(path);

            } else {

                selectedNode.setUserObject((parameter) ? "<param>" + name : "<property> " + name);
                selectedNode.putAttributes("name", (String) jTable1.getValueAt(NAME, 1));
                selectedNode.putAttributes("type", (String) jTable1.getValueAt(TYPE, 1));
                selectedNode.putAttributes("value", (String) jTable1.getValueAt(VALUE, 1));
                selectedNode.putAttributes("description", (String) jTable1.getValueAt(DESCRIPTION, 1));
                selectedNode.putAttributes("direction", (String) jTable1.getValueAt(DIRECTION, 1));
                selectedNode.putAttributes("cardinality", (String) jTable1.getValueAt(CARDINALITY, 1));

                 String type = (String) jTable1.getValueAt(TYPE, 1);
                if ((type != null) && type.equals("selection")) {
                        jTabbedPane1.setEnabledAt(1, true);
                        cardinalityCheckBox.setEnabled(true);


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
                      rootPanel.getBPCLTreeModel().insertNodeInto(tmpOptionNode, selectedNode,
                                                                  selectedNode.getChildCount());
                  }
                }

            }
            rootPanel.isModified();
            rootPanel.changeContentPane(rootPanel.BPCLPANEL);
        }
    }

    void tmpBPFLTree_mouseClicked(MouseEvent e) {
        TreePath treepath = tmpBPFLTree.getSelectionPath();

        // getClosestPathForLocation(e.getX(), e.getY());

        if (treepath != null) {
            tmpBPFLTree.scrollPathToVisible(treepath);
            tmpBPFLTree.setSelectionPath(treepath);
            NavajoTreeNode propertyNode = (NavajoTreeNode) tmpBPFLTree.getLastSelectedPathComponent();

            // treepath.getLastPathComponent();


            if (propertyNode.getTag().equals("property")) {
                // get attribute "name" from tml, set attribute "name" of tml
                TreeNode nodes[] = propertyNode.getPath();
                String completeName = "";

                for (int i = 1; i < nodes.length; i++) {
                    NavajoTreeNode tmpNode = (NavajoTreeNode) nodes[i];

                    completeName = completeName + tmpNode.getAttribute("name");
                    if (i < nodes.length - 1)
                        completeName = completeName + "/";
                }
                System.err.println("completeName: " + completeName);

                rootPanel.setEditOk(true);
                jTable1.setValueAt(completeName, NAME, 1);
                jTable1.setValueAt(propertyNode.getAttribute("type"), TYPE, 1);
                jTable1.setValueAt(propertyNode.getAttribute("value"), VALUE, 1);
                jTable1.setValueAt(propertyNode.getAttribute("description"), DESCRIPTION, 1);
                jTable1.setValueAt(propertyNode.getAttribute("cardinality"), CARDINALITY, 1);
                jTable1.setValueAt(propertyNode.getAttribute("direction"), DIRECTION, 1);

            }

        }

    }

    void deleteButton_actionPerformed(ActionEvent e) {
        rootPanel.tslModel.removeNodeFromParent(selectedNode);
        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

     void removeOptionButton_actionPerformed(ActionEvent e) {
        DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();
        int selected = optionTable.getSelectedRow();
        if (selected != -1) {
            System.err.println("selected" + selected);
            tmpModel.removeRow(selected);
        }
    }


    void jTable1_keyReleased(KeyEvent e) {

        if (jTable1.getEditingRow() == 0) {
            jTable1.setValueAt(jTable1.getCellEditor(0, 1).getCellEditorValue(), NAME, 1);
        }
        String test = (String) jTable1.getValueAt(NAME, 1);

        if (test == null || test.equals("")) {
            rootPanel.setEditOk(false);
        } else {
            rootPanel.setEditOk(true);
        }

        String type = (String) jTable1.getValueAt(TYPE, 1);
        if ((type != null) && type.equals("selection")) {
            jTabbedPane1.setEnabledAt(1, true);
            cardinalityCheckBox.setEnabled(true);
        } else {
            jTabbedPane1.setEnabledAt(1, false);
            cardinalityCheckBox.setEnabled(false);
        }
    }

     void addButton_actionPerformed(ActionEvent e) {

        String newOptionName = optionNameField.getText();

        optionNameField.setText("");
        String newOptionValue = optionValueField.getText();

        optionValueField.setText("");

        if (!newOptionName.equals("")) {
            DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();
            tmpModel.addRow(new Object[] {newOptionName, newOptionValue});
        }
    }

    void removeSelectionOptionButton_actionPerformed(ActionEvent e) {
        DefaultTableModel tmpModel = (DefaultTableModel) optionTable.getModel();
        int selected = optionTable.getSelectedRow();

        if (selected != -1) {
            System.err.println("selected" + selected);
            tmpModel.removeRow(selected);
        }

    }
}
