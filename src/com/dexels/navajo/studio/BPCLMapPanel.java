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

public class BPCLMapPanel extends BaseStudioPanel {

    JPanel attributePanel = new JPanel();
    JTextField refTextField = new JTextField();
    JScrollPane jScrollPane1 = new JScrollPane();
    JLabel jLabel2 = new JLabel();
    JTree thisTree = new JTree();
    boolean contextIsProperty = false;

  JTextField filterTextField = new JTextField();
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel refLabel = new JLabel();
  JLabel filterLabel = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

    public BPCLMapPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPCLMapPanel(RootStudioPanel root, NavajoTreeNode selected, boolean isNew) {
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
        refTextField.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        refTextField_keyReleased(e);
                    }
                }
                );
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Select referention node");

        this.setLayout(borderLayout1);
        refTextField.setMinimumSize(new Dimension(40, 200));
    attributePanel.setLayout(gridBagLayout1);
    refLabel.setToolTipText("");
    refLabel.setText("ref");
    filterLabel.setToolTipText("");
    filterLabel.setText("filter");
    attributePanel.setMinimumSize(new Dimension(40, 40));
    filterTextField.setToolTipText("");
    this.add(jScrollPane1,  BorderLayout.CENTER);
        this.add(jLabel2, BorderLayout.NORTH);
    this.add(attributePanel, BorderLayout.SOUTH);
    attributePanel.add(refLabel,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 8, 0, 0), 0, 0));
    attributePanel.add(refTextField,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 95, 0));
    attributePanel.add(filterLabel,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 16, 2, 0), 0, 0));
    attributePanel.add(filterTextField, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 9), 83, 0));

        NavajoTreeNode contextNode = new NavajoTreeNode();

        if (newEntry) {
            contextNode = selectedNode;
        } else {
            contextNode = (NavajoTreeNode) selectedNode.getParent();
        }

        System.err.println("checking node");
        if (contextNode.getTag().equals("field")) {
            System.err.println("context=field");
            thisTree = rootPanel.BPFL_in_Tree;
            // thisTree=rootPanel.tmlTree;
            jScrollPane1.getViewport().add(thisTree);
            contextIsProperty = false;
            thisTree.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            thisTree_BPFL_mousePressed(e);
                        }
                    }
                    );

        } else if (contextNode.getTag().equals("property")
                || contextNode.getTag().equals("message")) {
            System.err.println("context=property");
            if (rootPanel.mainMappableObjectTree == null) {
                jScrollPane1.getViewport().add(new JTextArea("mapObject not available"));
            } else {
                thisTree = rootPanel.currentMappableObjectTree;// mainMappableObjectTree;//mapObjectTree;
                jScrollPane1.getViewport().add(thisTree);
                thisTree.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mousePressed(MouseEvent e) {
                                thisTree_Object_mousePressed(e);
                            }
                        }
                        );
            }
            contextIsProperty = true;
        } else {
            // jScrollPane1.getViewport().add(rootPanel.tslTree);
            showError("context tag = " + contextNode.getTag() + ", can't determine wheter context is field or property");
            System.err.println("context tag = " + contextNode.getTag());
            System.err.println("can't determine wheter context is field or property");
        }

        if (!newEntry) {
            title = "Edit Map";
            refTextField.setText(selectedNode.getAttribute("ref"));
            filterTextField.setText(selectedNode.getAttribute("filter"));
        } else {
            title = "Add Map";
            // rootPanel.setEditOk(false);
        }
        applyTemplate2();
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void okButton_actionPerformed(ActionEvent e) {
        String ref = refTextField.getText();

        // if(ref.equals("")){
        // showError("ref is empty");
        // System.err.println("BPCLMapPanel: ref == empty");
        // }
        // else{
        if (newEntry) {
            NavajoTreeNode tslMapNode = new NavajoTreeNode(selectedNode, "map");
            tslMapNode.putAttributes("ref", refTextField.getText());
            if (!filterTextField.getText().equals(""))
              tslMapNode.putAttributes("filter", filterTextField.getText());
            rootPanel.tslModel.insertNodeInto(tslMapNode, selectedNode, selectedNode.getChildCount());
            TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(tslMapNode);
            TreePath path = new TreePath(nodes);
            rootPanel.tslTree.setSelectionPath(path);
        } else {
            selectedNode.setUserObject("<map>");
            selectedNode.putAttributes("ref", refTextField.getText());
            if (!filterTextField.getText().equals(""))
              selectedNode.putAttributes("filter", filterTextField.getText());
        }
        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
        // }
    }

    void deleteButton_actionPerformed(ActionEvent e) {
        rootPanel.tslModel.removeNodeFromParent(selectedNode);
        rootPanel.isModified();
        rootPanel.changeContentPane(rootPanel.BPCLPANEL);
    }

    void refTextField_keyReleased(KeyEvent e) {
        setReadyToProcess();
    }

    void setReadyToProcess() {
        if (refTextField.getText().equals("")) {
            rootPanel.setEditOk(false);
        } else {
            rootPanel.setEditOk(true);
        }

    }

    void thisTree_BPFL_mousePressed(MouseEvent e) {
        TreePath treepath = thisTree.getClosestPathForLocation(e.getX(), e.getY());

        if (treepath != null) {
            thisTree.setSelectionPath(treepath);
            thisTree.scrollPathToVisible(treepath);
            NavajoTreeNode thisNode = (NavajoTreeNode) treepath.getLastPathComponent();

            if (thisNode != (NavajoTreeNode) thisNode.getRoot()) {
                String field = (String) thisNode.getUserObject();
                int pos = field.lastIndexOf(" ");

                field = field.substring(pos + 1);
                String mapPath = field;
                // get fullpath
                NavajoTreeNode thisNode2 = (NavajoTreeNode) treepath.getLastPathComponent();

                if ((thisNode2.getTag()).equals("message")
                        || (thisNode2.getTag()).equals("property")) {
                    DefaultTreeModel model1 = (DefaultTreeModel) thisTree.getModel();
                    TreeNode nodes[] = model1.getPathToRoot(thisNode2);
                    int depth = 0;

                    mapPath = "";

                    for (int i = 1; i < nodes.length; i++) {
                        DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) nodes[i];

                        field = (String) tmpNode.getUserObject();
                        pos = field.lastIndexOf(" ");
                        field = field.substring(pos + 1);
                        mapPath = mapPath + field;
                        if (i < nodes.length - 1) {
                            mapPath = mapPath + "/";
                        }
                    }
                    refTextField.setText(mapPath);
                    rootPanel.setEditOk(true);
                } else {
                    refTextField.setText("");
                    // rootPanel.setEditOk(false);
                }
            } else {
                refTextField.setText("");
                // rootPanel.setEditOk(false);
            }
        }
    }

    void thisTree_Object_mousePressed(MouseEvent e) {
        TreePath treepath = thisTree.getLeadSelectionPath();// ClosestPathForLocation(e.getX(), e.getY());

        if (treepath != null) {
            thisTree.setSelectionPath(treepath);
            thisTree.scrollPathToVisible(treepath);
            ClassTreeNode thisNode = (ClassTreeNode) treepath.getLastPathComponent();

            if (thisNode != (ClassTreeNode) thisNode.getRoot()) {
                String field = thisNode.getFieldName();

                refTextField.setText(field);
                rootPanel.setEditOk(true);
            } else {
                refTextField.setText("");
                // rootPanel.setEditOk(false);
            }
        } else {
            refTextField.setText("");
            // rootPanel.setEditOk(false);
        }
    }

    void updatePanel(NavajoEvent ne) {
        boolean IncomingBPFL = ne.getIncomingBPFL();

        if (IncomingBPFL) {
            System.err.println("incoming BPFL changed");
            thisTree = rootPanel.BPFL_in_Tree;
            jScrollPane1.getViewport().removeAll();
            jScrollPane1.getViewport().add(thisTree);
            contextIsProperty = false;
            thisTree.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            thisTree_BPFL_mousePressed(e);
                        }
                    }
                    );
        }
    }
}
