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

        tableModel = new DefaultTableModel(col_names, 6) {

                    public String[] prop_names = { "Name", "Type", "Value", "Description", "Cardinality", "Direction" };
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

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (parameter) {
            jSplitPane1.remove(jScrollPane1);
            this.remove(jLabel3);
        }

    }

    void jbInit() throws Exception {
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

        if (!parameter) {
            jScrollPane1.getViewport().add(tmpBPFLTree);
            tmpBPFLTree.repaint();
        }
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
        jScrollPane2.getViewport().add(jTable1, null);
        jSplitPane1.add(jScrollPane1, JSplitPane.TOP);

        jScrollPane1.getViewport().add(tmpBPFLTree, null);

        if (!newEntry) {
            jTable1.setValueAt(selectedNode.getAttribute("name"), NAME, 1);
            jTable1.setValueAt(selectedNode.getAttribute("type"), TYPE, 1);
            jTable1.setValueAt(selectedNode.getAttribute("value"), VALUE, 1);
            jTable1.setValueAt(selectedNode.getAttribute("description"), DESCRIPTION, 1);
            jTable1.setValueAt(selectedNode.getAttribute("cardinality"), CARDINALITY, 1);
            jTable1.setValueAt(selectedNode.getAttribute("direction"), DIRECTION, 1);

        } // if(selectedNode.getAttribute("type")!=null){
        // typeComboBox.setSelectedItem(selectedNode.getAttribute("type"));
        // }
        // if(selectedNode.getAttribute("direction")!=null){
        // directionComboBox.setSelectedItem(selectedNode.getAttribute("direction"));
        // }
        else {
            rootPanel.setEditOk(false);
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

                // System.err.println("button: " +  buttonGroup1.getSelection().getActionCommand());
                // tslPropertyNode.putAttributes("direction", buttonGroup1.getSelection().getActionCommand());

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

                // System.err.println("button: " +  buttonGroup1.getSelection().getActionCommand());
                // selectedNode.putAttributes("direction", buttonGroup1.getSelection().getActionCommand());
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
    }

}
