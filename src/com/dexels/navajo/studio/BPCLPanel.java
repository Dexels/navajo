package com.dexels.navajo.studio;


import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.lang.reflect.*;

import com.dexels.navajo.xml.*;
import com.dexels.navajo.util.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.util.*;

import org.xml.sax.*;
import org.w3c.dom.*;
import com.dexels.navajo.mapping.*;


// import javax.swing.event.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class BPCLPanel extends BaseStudioPanel {// implements ActionListener  {

    // fields
    private boolean doEdit = false; // flag for entry of new node.
    // true for you want to edit the current node
    // false for you want to add a new node to
    // GridBagLayout gridBagLayout1 = new GridBagLayout();
    // int bla;

    // the button bar section
    private JPanel iconsbarPanel = new JPanel();
    private JButton addMessageButton = new JButton();
    private JButton addPropertyButton = new JButton();
    private JButton addParamButton = new JButton();
    private JButton addFieldButton = new JButton();
    private JButton removeButton = new JButton();
    private JButton editButton = new JButton();
    private JButton moveUpButton = new JButton();
    private JButton moveDownButton = new JButton();
    private JButton useObjectButton = new JButton();
    private JButton addExpressionButton = new JButton();
    private JButton addMapButton = new JButton();
    private JButton addMethodButton = new JButton();

    // the treeview section
    private JScrollPane treeScrollPane = new JScrollPane();
    private JPanel treePanel = new JPanel();
    private BorderLayout treePanelLayout = new BorderLayout(5, 5);

    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();

    JSeparator iconSeparator3 = new JSeparator(JSeparator.VERTICAL);
    private boolean expressionIsSelected = false;

    ImageIcon addObjectIcon = new ImageIcon("images/add_object.gif");
    ImageIcon addPropertyIcon = new ImageIcon("images/add_property.gif");
    ImageIcon addParamIcon = new ImageIcon("images/add_param.gif");
    ImageIcon addMapIcon = new ImageIcon("images/add_map.gif");
    ImageIcon addMessageIcon = new ImageIcon("images/add_message.gif");
    ImageIcon addFieldIcon = new ImageIcon("images/add_field.gif");
    ImageIcon addExpressionIcon = new ImageIcon("images/add_expression.gif");

    ImageIcon editButtonIcon = new ImageIcon("images/edit_off.gif");
    ImageIcon editButtonIconOn = new ImageIcon("images/edit_on.gif");
    ImageIcon editButtonIconDisabled = new ImageIcon("images/edit_disabled.gif");
    ImageIcon editButtonIconPressed = new ImageIcon("images/edit_pressed.gif");

    ImageIcon moveUpButtonIcon = new ImageIcon("images/arrow_up_off.gif");
    ImageIcon moveUpButtonIconOn = new ImageIcon("images/arrow_up_on.gif");
    ImageIcon moveUpButtonIconDisabled = new ImageIcon("images/arrow_up_disabled.gif");
    ImageIcon moveUpButtonIconPressed = new ImageIcon("images/arrow_up_pressed.gif");

    ImageIcon moveDownButtonIcon = new ImageIcon("images/arrow_down_off.gif");
    ImageIcon moveDownButtonIconOn = new ImageIcon("images/arrow_down_on.gif");
    ImageIcon moveDownButtonIconDisabled = new ImageIcon("images/arrow_down_disabled.gif");
    ImageIcon moveDownButtonIconPressed = new ImageIcon("images/arrow_down_pressed.gif");

    ImageIcon removeButtonIcon = new ImageIcon("images/remove_off.gif");
    ImageIcon removeButtonIconOn = new ImageIcon("images/remove_on.gif");
    ImageIcon removeButtonIconDisabled = new ImageIcon("images/remove_disabled.gif");
    ImageIcon removeButtonIconPressed = new ImageIcon("images/remove_pressed.gif");

    ImageIcon addMethodButtonIcon = new ImageIcon("images/new_method_off.gif");
    ImageIcon addMethodButtonIconOn = new ImageIcon("images/new_method_on.gif");
    ImageIcon addMethodButtonIconDisabled = new ImageIcon("images/new_method_disabled.gif");
    ImageIcon addMethodButtonIconPressed = new ImageIcon("images/new_method_pressed.gif");

    private NavajoTreeNode copiedNode = null;

    public BPCLPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public BPCLPanel(RootStudioPanel rootPanel1) {

        try {

            rootPanel = rootPanel1;
            jbInit();
            treeScrollPane.getViewport().add(rootPanel.getBPCLTree());
            rootPanel.getBPCLTree().addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            tslTree_mousePressed(e);
                        }

                        public void mouseReleased(MouseEvent e) {
                            tslTree_mouseReleased(e);
                        }
                    }
                    );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        setLayout(borderLayout1);
        title = "Script Editor";
        // thisLayout.setRows(3);

        // init the buttons
        addMessageButton.setBorder(null);
        addMessageButton.setMaximumSize(new Dimension(24, 24));
        addMessageButton.setMinimumSize(new Dimension(24, 24));
        addMessageButton.setPreferredSize(new Dimension(24, 24));
        addMessageButton.setToolTipText("Add Message");
        addMessageButton.setFocusPainted(false);
        addMessageButton.setIcon(addMessageIcon);
        addMessageButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        addMessageButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        addMessageButton_mouseExited(e);
                    }
                }
                );
        addMessageButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addMessageButton_actionPerformed(e);
                    }
                }
                );
        addPropertyButton.setBorder(null);
        addPropertyButton.setMaximumSize(new Dimension(24, 24));
        addPropertyButton.setMinimumSize(new Dimension(24, 24));
        addPropertyButton.setPreferredSize(new Dimension(24, 24));
        addPropertyButton.setToolTipText("Add Property");
        addPropertyButton.setFocusPainted(false);
        addPropertyButton.setIcon(addPropertyIcon);
        addPropertyButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        addPropertyButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        addPropertyButton_mouseExited(e);
                    }
                }
                );
        addPropertyButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addPropertyButton_actionPerformed(e);
                    }
                }
                );
        addParamButton.setBorder(null);
        addParamButton.setMaximumSize(new Dimension(24, 24));
        addParamButton.setMinimumSize(new Dimension(24, 24));
        addParamButton.setPreferredSize(new Dimension(24, 24));
        addParamButton.setToolTipText("Add Param");
        addParamButton.setFocusPainted(false);
        addParamButton.setIcon(addParamIcon);
        addParamButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        addParamButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        addParamButton_mouseExited(e);
                    }
                }
                );
        addParamButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addParamButton_actionPerformed(e);
                    }
                }
                );
        addFieldButton.setBorder(null);
        addFieldButton.setMaximumSize(new Dimension(24, 24));
        addFieldButton.setMinimumSize(new Dimension(24, 24));
        addFieldButton.setPreferredSize(new Dimension(24, 24));
        addFieldButton.setToolTipText("Add Field");
        addFieldButton.setFocusPainted(false);
        addFieldButton.setIcon(addFieldIcon);
        addFieldButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        addFieldButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        addFieldButton_mouseExited(e);
                    }
                }
                );
        addFieldButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addFieldButton_actionPerformed(e);
                    }
                }
                );
        removeButton.setBorder(null);
        removeButton.setMaximumSize(new Dimension(24, 24));
        removeButton.setMinimumSize(new Dimension(24, 24));
        removeButton.setPreferredSize(new Dimension(24, 24));
        removeButton.setToolTipText("Remove node");
        removeButton.setDisabledIcon(removeButtonIconDisabled);
        removeButton.setFocusPainted(false);
        removeButton.setIcon(removeButtonIcon);
        removeButton.setPressedIcon(removeButtonIconPressed);
        removeButton.setSelectedIcon(removeButtonIconOn);
        removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        removeButton_mouseEntered(e);
                    }
                }
                );
        removeButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        removeButton_actionPerformed(e);
                    }
                }
                );
        editButton.setBorder(null);
        editButton.setMaximumSize(new Dimension(24, 24));
        editButton.setMinimumSize(new Dimension(24, 24));
        editButton.setPreferredSize(new Dimension(24, 24));
        editButton.setToolTipText("Edit node");
        editButton.setDisabledIcon(editButtonIconDisabled);
        editButton.setFocusPainted(false);
        editButton.setIcon(editButtonIcon);
        editButton.setPressedIcon(editButtonIconPressed);
        editButton.setSelectedIcon(editButtonIconOn);
        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        editButton_mouseEntered(e);
                    }
                }
                );
        editButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        editButton_actionPerformed(e);
                    }
                }
                );
        moveUpButton.setBorder(null);
        moveUpButton.setMaximumSize(new Dimension(24, 24));
        moveUpButton.setMinimumSize(new Dimension(24, 24));
        moveUpButton.setPreferredSize(new Dimension(24, 24));
        moveUpButton.setToolTipText("Move up");
        moveUpButton.setDisabledIcon(moveUpButtonIconDisabled);
        moveUpButton.setFocusPainted(false);
        moveUpButton.setIcon(moveUpButtonIcon);
        moveUpButton.setPressedIcon(moveUpButtonIconPressed);
        moveUpButton.setSelectedIcon(moveUpButtonIconOn);
        moveUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        moveUpButton_mouseEntered(e);
                    }
                }
                );
        moveUpButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        moveUpButton_actionPerformed(e);
                    }
                }
                );
        moveDownButton.setBorder(null);
        moveDownButton.setMaximumSize(new Dimension(24, 24));
        moveDownButton.setMinimumSize(new Dimension(24, 24));
        moveDownButton.setPreferredSize(new Dimension(24, 24));
        moveDownButton.setToolTipText("Move down");
        moveDownButton.setDisabledIcon(moveDownButtonIconDisabled);
        moveDownButton.setFocusPainted(false);
        moveDownButton.setIcon(moveDownButtonIcon);
        moveDownButton.setPressedIcon(moveDownButtonIconPressed);
        moveDownButton.setSelectedIcon(moveDownButtonIconOn);
        moveDownButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        moveDownButton_mouseEntered(e);
                    }
                }
                );
        moveDownButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        moveDownButton_actionPerformed(e);
                    }
                }
                );

        useObjectButton.setBorder(null);
        useObjectButton.setMaximumSize(new Dimension(24, 24));
        useObjectButton.setMinimumSize(new Dimension(24, 24));
        useObjectButton.setPreferredSize(new Dimension(24, 24));
        useObjectButton.setToolTipText("Add Object");
        useObjectButton.setFocusPainted(false);
        useObjectButton.setIcon(addObjectIcon);
        useObjectButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        useObjectButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        useObjectButton_mouseExited(e);
                    }
                }
                );
        useObjectButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        useObjectButton_actionPerformed(e);
                    }
                }
                );
        addExpressionButton.setBorder(null);
        addExpressionButton.setMaximumSize(new Dimension(24, 24));
        addExpressionButton.setMinimumSize(new Dimension(24, 24));
        addExpressionButton.setPreferredSize(new Dimension(24, 24));
        addExpressionButton.setToolTipText("Add Expression");
        addExpressionButton.setFocusPainted(false);
        addExpressionButton.setIcon(addExpressionIcon);
        addExpressionButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        addExpressionButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        addExpressionButton_mouseExited(e);
                    }
                }
                );
        addExpressionButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addExpressionButton_actionPerformed(e);
                    }
                }
                );

        addMapButton.setBorder(null);
        addMapButton.setMaximumSize(new Dimension(24, 24));
        addMapButton.setMinimumSize(new Dimension(24, 24));
        addMapButton.setPreferredSize(new Dimension(24, 24));
        addMapButton.setToolTipText("Add Map");
        addMapButton.setFocusPainted(false);
        addMapButton.setIcon(addMapIcon);
        addMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        addMapButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        addMapButton_mouseExited(e);
                    }
                }
                );
        addMapButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addMapButton_actionPerformed(e);
                    }
                }
                );

        addMethodButton.setBorder(null);
        addMethodButton.setMaximumSize(new Dimension(24, 24));
        addMethodButton.setMinimumSize(new Dimension(24, 24));
        addMethodButton.setPreferredSize(new Dimension(24, 24));
        addMethodButton.setIcon(addMethodButtonIcon);
        addMethodButton.setRolloverIcon(addMethodButtonIconOn);
        addMethodButton.setDisabledIcon(addMethodButtonIconDisabled);
        addMethodButton.setPressedIcon(addMethodButtonIconPressed);
        addMethodButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        addMethodButton_mouseEntered(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        addMethodButton_mouseExited(e);
                    }
                }
                );
        addMethodButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addMethodButton_actionPerformed(e);
                    }
                }
                );

        iconsbarPanel.setLayout(flowLayout1);
        iconsbarPanel.setMaximumSize(new Dimension(200, 357));
        iconsbarPanel.setMinimumSize(new Dimension(150, 30));
        iconsbarPanel.setPreferredSize(new Dimension(179, 36));
        iconsbarPanel.setToolTipText("");
        borderLayout1.setHgap(1);
        borderLayout1.setVgap(1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(1);
        flowLayout1.setVgap(6);
        this.setBorder(BorderFactory.createEtchedBorder());
        iconsbarPanel.add(useObjectButton, null);
        iconsbarPanel.add(addPropertyButton, null);
        iconsbarPanel.add(addParamButton, null);
        iconsbarPanel.add(addMapButton, null);
        iconsbarPanel.add(addMessageButton, null);
        iconsbarPanel.add(addMethodButton, null);
        iconsbarPanel.add(addFieldButton, null);
        iconsbarPanel.add(addExpressionButton, null);
        iconSeparator3.setPreferredSize(new Dimension(2, 24));
        iconsbarPanel.add(iconSeparator3);
        iconsbarPanel.add(editButton, null);
        iconsbarPanel.add(removeButton, null);
        iconsbarPanel.add(moveUpButton, null);
        iconsbarPanel.add(moveDownButton, null);

        rootPanel.getBPCLTree().putClientProperty("JTree.lineStyle", "Angled");
        BPCLTreeCellRenderer renderer = new BPCLTreeCellRenderer();

        rootPanel.getBPCLTree().setCellRenderer(renderer);
        // treeScrollPane.getViewport().add(rootPanel.tslTree, null);
        selectedNode = (NavajoTreeNode) rootPanel.getBPCLTree().getLastSelectedPathComponent();

        if (selectedNode != null) {
            System.err.println("DEBUG: TSL selectedNode!=null");
            setButtonsStatus();
        } else {
            System.err.println("DEBUG: TML selectedNode==null");
            NavajoTreeNode root = (NavajoTreeNode) rootPanel.tslModel.getRoot();
            TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(root);
            TreePath path = new TreePath(nodes);

            rootPanel.getBPCLTree().setSelectionPath(path);
            selectedNode = (NavajoTreeNode) rootPanel.getBPCLTree().getLastSelectedPathComponent();
            setButtonsStatus();
        }

        treePanel.setLayout(treePanelLayout);
        treePanel.setPreferredSize(new Dimension(392, 94));
        treePanel.add(treeScrollPane, null);
        // treeScrollPane.getViewport().add(tslTree, null);

        this.add(treePanel, BorderLayout.CENTER);
        this.add(iconsbarPanel, BorderLayout.NORTH);
        // applyTemplate1();
    }

    void addMethodButton_actionPerformed(ActionEvent e) {

        getCurrentNode();

        NavajoTreeNode root = (NavajoTreeNode) rootPanel.getBPCLTree().getModel().getRoot();

        // find <methods> node
        NavajoTreeNode methodsnode = new NavajoTreeNode();

        for (int i = 0; i < root.getChildCount(); i++) {
            NavajoTreeNode tmpNode = (NavajoTreeNode) rootPanel.tslModel.getChild(root, i);

            if (tmpNode.getTag().equals("methods")) {
                methodsnode = tmpNode;
            }
        }

        System.out.println("methodsnode = " + methodsnode);

        if (!methodsnode.getTag().equals("")) {
            // <methods> node found, proceed adding new <method> nodes
            rootPanel.changeContentPane(new BPFLMethodPanel(rootPanel, methodsnode, true),
                                        rootPanel.BPFLMETHODS, true);
        } else {
            // <methods> node not found tell the new node to add
            // first make a new Methods Node before adding a new method node
            methodsnode = new NavajoTreeNode("methods");
            rootPanel.getBPCLTreeModel().insertNodeInto(methodsnode, root, root.getChildCount());
            rootPanel.changeContentPane(new BPFLMethodPanel(rootPanel, methodsnode, true),
                                        rootPanel.BPFLMETHODS, true);
        }
        System.out.println("ABout to call setButtonsStatus()....");
        setButtonsStatus();
    }

    void addMethodButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Add Method");
        addMethodButton.setToolTipText("Add Method");
    }

    void addMethodButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus(" ");
    }

    void removeButton_actionPerformed(ActionEvent e) {

        getCurrentNode();

        NavajoTreeNode sibling = (NavajoTreeNode) selectedNode.getPreviousSibling();

        if (sibling == null) {
            sibling = (NavajoTreeNode) selectedNode.getParent();
        }

        if (selectedNode.getTag().equals("method")) {
            // If the removed node is an <mehtod> then we have to check the number of remaining <method> left
            // after the remove.
            // If there is no <method> left, then we will also delete the parent <methods>
            // because it holds no methods and therefore it lost it's meaning.
             DefaultMutableTreeNode prnt = (DefaultMutableTreeNode) selectedNode.getParent();
             ((DefaultTreeModel) rootPanel.getBPCLTree().getModel()).removeNodeFromParent(selectedNode);
            if (prnt.getChildCount() == 0) {
                sibling = (NavajoTreeNode) prnt.getParent();
                rootPanel.getBPCLTreeModel().removeNodeFromParent(prnt);
            }
        }  else {
           ((DefaultTreeModel) rootPanel.getBPCLTree().getModel()).removeNodeFromParent(selectedNode);
        }

        System.out.println("sibling = " + sibling);

        if (sibling != null) {
            TreeNode[] nodes = rootPanel.getBPCLTreeModel().getPathToRoot(sibling);
            selectedNode = sibling;
            TreePath path = new TreePath(nodes);
            rootPanel.getBPCLTree().setSelectionPath(path);
            setButtonsStatus();

        }
        rootPanel.isModified();

    }

    void addMessageButton_actionPerformed(ActionEvent e) {
        getCurrentNode();
        if (!selectedNode.getTag().equals("")) {
            BPCLMessagePanel messagePanel = new BPCLMessagePanel(rootPanel, selectedNode, true);

            rootPanel.changeContentPane(messagePanel, rootPanel.BPCLMESSAGE, true);
        }
    }

    void addPropertyButton_actionPerformed(ActionEvent e) {
        getCurrentNode();
        Util.debugLog("in addPropertyButton");
        if (!selectedNode.getTag().equals("")) {
            BPCLPropertyPanel propertyPanel = new BPCLPropertyPanel(rootPanel, selectedNode, true, false);
            rootPanel.changeContentPane(propertyPanel, rootPanel.BPCLPROPERTY, true);
        }
    }

    void addParamButton_actionPerformed(ActionEvent e) {
        getCurrentNode();
        Util.debugLog("in addParamButton");
        if (!selectedNode.getTag().equals("")) {
            BPCLPropertyPanel propertyPanel = new BPCLPropertyPanel(rootPanel, selectedNode, true, true);
            rootPanel.changeContentPane(propertyPanel, rootPanel.BPCLPARAM, true);
        }
    }

    void tslTree_mousePressed(MouseEvent e) {
        // TreePath path = rootPanel.tslTree.getPathForLocation(e.getX(), e.getY());
        TreePath path = rootPanel.getBPCLTree().getSelectionPath();

        rootPanel.getBPCLTree().setSelectionPath(path);
        rootPanel.getBPCLTree().scrollPathToVisible(path);
        rootPanel.getBPCLTree().repaint();
        setButtonsStatus();
    }

    void tslTree_mouseReleased(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Util.debugLog("double clicked");
            editButton_actionPerformed(null);
        }
    }

    // void addToPopup(String name, PopupMenu menu, boolean enabled){
    // MenuItem item = new MenuItem(name);
    // item.setEnabled(enabled);
    // menu.add(item);
    // item.addActionListener(this);
    // }


    void addFieldButton_actionPerformed(ActionEvent e) {
        // to do: add disabled and enabled feature
        getCurrentNode();
        if (!selectedNode.getTag().equals("")) {
            // if(!rootPanel.classPath.equals("")){
            BPCLFieldPanel fieldPanel = new BPCLFieldPanel(rootPanel, selectedNode, true);

            rootPanel.changeContentPane(fieldPanel, rootPanel.BPCLFIELD, true);
            // }
            // else{
            // rootPanel.showMsg("please redefine the location for the mapped object");
            // }
        } else {
            rootPanel.showMsg("invalid node, please reselect another node");
        }
    }

    void editButton_actionPerformed(ActionEvent e) {
        // to do: add disabled and enabled feature
        doEdit = true;
        getCurrentNode();
        if (!selectedNode.getTag().equals("")) {
            String tag = selectedNode.getTag();

            if (tag.equals("tsl")) {
               BPCLTslPanel panel = new BPCLTslPanel(rootPanel, selectedNode);
               rootPanel.changeContentPane(panel, rootPanel.BPCLTSL, false);
            } else if (tag.equals("method")) {

              BPFLMethodPanel panel = new BPFLMethodPanel(rootPanel, selectedNode, false);
              rootPanel.changeContentPane(panel, rootPanel.BPFLMETHODS, false);

            } else if (tag.equals("message")) {
                BPCLMessagePanel panel = new BPCLMessagePanel(rootPanel, selectedNode, false);
                rootPanel.changeContentPane(panel, rootPanel.BPCLMESSAGE, false);
            } else if (tag.equals("property")) {
                BPCLPropertyPanel panel = new BPCLPropertyPanel(rootPanel, selectedNode, false, false);

                rootPanel.changeContentPane(panel, rootPanel.BPCLPROPERTY, false);
            } else if (tag.equals("param")) {
                BPCLPropertyPanel panel = new BPCLPropertyPanel(rootPanel, selectedNode, false, true);

                rootPanel.changeContentPane(panel, rootPanel.BPCLPARAM, false);
            } else if (tag.equals("field")) {
                BPCLFieldPanel panel = new BPCLFieldPanel(rootPanel, selectedNode, false);

                rootPanel.changeContentPane(panel, rootPanel.BPCLFIELD, false);
            } else if (tag.equals("expression")) {
                expressionIsSelected = true;
                getCurrentNode();
                BPCLExpressionPanel panel = new BPCLExpressionPanel(rootPanel, selectedNode, false);

                rootPanel.changeContentPane(panel, rootPanel.BPCLEXPRESSION, false);
            } else if (tag.equals("map")) {
                if (selectedNode.getAttribute("object") != null
                        && !selectedNode.getAttribute("object").equals("")) {
                    // an object map
                    BPCLObjectPanel panel = new BPCLObjectPanel(rootPanel, selectedNode, false);

                    rootPanel.changeContentPane(panel, rootPanel.BPCLOBJECT, false);
                } else {
                    // an ref map
                    BPCLMapPanel panel = new BPCLMapPanel(rootPanel, selectedNode, false);

                    rootPanel.changeContentPane(panel, rootPanel.BPCLMAP, false);
                }
            } else {
                showError("unknown tag:" + tag);
            }
        }
        doEdit = false;
    }

    void moveUpButton_actionPerformed(ActionEvent e) {
        // to do: add disabled and enabled feature

        NavajoTreeNode selectedNode = (NavajoTreeNode) rootPanel.getBPCLTree().getLastSelectedPathComponent();

        getCurrentNode();
        if (!selectedNode.getTag().equals("") && !selectedNode.isRoot()) {
            NavajoTreeNode parentNode = (NavajoTreeNode) selectedNode.getParent();
            int currentPos = rootPanel.tslModel.getIndexOfChild(parentNode, selectedNode);

            if (currentPos > 0) {
                NavajoTreeNode neighbour = (NavajoTreeNode) parentNode.getChildAt(currentPos - 1);

                rootPanel.tslModel.removeNodeFromParent(selectedNode);
                rootPanel.tslModel.insertNodeInto(selectedNode, parentNode, currentPos - 1);
                rootPanel.tslModel.removeNodeFromParent(neighbour);
                rootPanel.tslModel.insertNodeInto(neighbour, parentNode, currentPos);
                rootPanel.tslModel.nodeChanged(parentNode);
                rootPanel.isModified();

                TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(selectedNode);

                currentPos = rootPanel.tslModel.getIndexOfChild(parentNode, selectedNode);

                TreePath path1 = new TreePath(nodes);

                rootPanel.getBPCLTree().setSelectionPath(path1);
                rootPanel.getBPCLTree().scrollPathToVisible(path1);
                rootPanel.getBPCLTree().repaint();
            } else {
                System.err.println("WARNING in BPCLPanel: selectedNode allready at top");
            }
        }
        setButtonsStatus();
    }

    void moveDownButton_actionPerformed(ActionEvent e) {
        // to do: add disabled and enabled feature

        getCurrentNode();
        if (!selectedNode.getTag().equals("") && !selectedNode.isRoot()) {
            NavajoTreeNode parentNode = (NavajoTreeNode) selectedNode.getParent();
            int currentPos = rootPanel.tslModel.getIndexOfChild(parentNode, selectedNode);

            if (currentPos < parentNode.getChildCount() - 1) {
                NavajoTreeNode neighbour = (NavajoTreeNode) parentNode.getChildAt(currentPos + 1);

                rootPanel.tslModel.removeNodeFromParent(selectedNode);
                rootPanel.tslModel.insertNodeInto(selectedNode, parentNode, currentPos + 1);
                rootPanel.tslModel.removeNodeFromParent(neighbour);
                rootPanel.tslModel.insertNodeInto(neighbour, parentNode, currentPos);
                rootPanel.tslModel.nodeChanged(parentNode);
                rootPanel.isModified();

                TreeNode[] nodes = rootPanel.tslModel.getPathToRoot(selectedNode);

                currentPos = rootPanel.tslModel.getIndexOfChild(parentNode, selectedNode);

                TreePath path1 = new TreePath(nodes);

                rootPanel.getBPCLTree().setSelectionPath(path1);
                rootPanel.getBPCLTree().scrollPathToVisible(path1);
                rootPanel.getBPCLTree().repaint();
            } else {
                System.err.println("WARNING in BPCLPanel: selectedNode allready at bottom");
            }
        }
        setButtonsStatus();
    }

    void useObjectButton_actionPerformed(ActionEvent e) {
        getCurrentNode();
        NavajoTreeNode root = (NavajoTreeNode) rootPanel.tslModel.getRoot();

        System.err.println("root: " + root.getTag());
        NavajoTreeNode mapnode = selectedNode;// new NavajoTreeNode();

        rootPanel.changeContentPane(new BPCLObjectPanel(rootPanel, selectedNode, true), rootPanel.BPCLOBJECT, true);
    }

    void addExpressionButton_actionPerformed(ActionEvent e) {
        expressionIsSelected = true;
        getCurrentNode();
        if (!selectedNode.getTag().equals("")) {
            rootPanel.changeContentPane(new BPCLExpressionPanel(rootPanel, selectedNode, true), rootPanel.BPCLEXPRESSION, true);
        }
    }

    void setButtonsStatus() {
        getCurrentNode();
        NavajoTreeNode tmpNode = selectedNode;

        // the following sets the buttons to be enabled or disabled for the current node
        if (tmpNode != null) {
            if (tmpNode.isRoot()) {
                addMessageButton.setEnabled(true);
                addPropertyButton.setEnabled(false);
                addParamButton.setEnabled(true);
                addFieldButton.setEnabled(false);
                addMapButton.setEnabled(false);
                addExpressionButton.setEnabled(false);
                editButton.setEnabled(true);
                removeButton.setEnabled(false);
                moveDownButton.setEnabled(false);
                moveUpButton.setEnabled(false);
                rootPanel.setEditCopyEnabled(false);
            } else {
                addMessageButton.setEnabled(true);
                addPropertyButton.setEnabled(true);
                addParamButton.setEnabled(true);
                addFieldButton.setEnabled(true);
                addMapButton.setEnabled(true);
                addExpressionButton.setEnabled(true);

                editButton.setEnabled(true);
                rootPanel.setEditChangeEnabled(true);
                removeButton.setEnabled(true);
                rootPanel.setEditDeleteEnabled(true);
                rootPanel.setEditCopyEnabled(true);
                moveUpButton.setEnabled(true);
                moveDownButton.setEnabled(true);

                NavajoTreeNode parentNode = (NavajoTreeNode) tmpNode.getParent();
                int currentPos = rootPanel.tslModel.getIndexOfChild(parentNode, tmpNode);

                if (!(currentPos > 0)) {
                    moveUpButton.setEnabled(false);
                }
                if (!(currentPos < parentNode.getChildCount() - 1)) {
                    moveDownButton.setEnabled(false);
                }

                if (tmpNode.getTag().equals("map")) {
                    addExpressionButton.setEnabled(false);
                    addMapButton.setEnabled(false);
                } else if (tmpNode.getTag().equals("option")) {
                    addExpressionButton.setEnabled(false);
                    addMapButton.setEnabled(false);
                    addPropertyButton.setEnabled(false);
                    addParamButton.setEnabled(false);
                    addFieldButton.setEnabled(false);
                    addMessageButton.setEnabled(false);
                } else if (tmpNode.getTag().startsWith("method")) {
                    addExpressionButton.setEnabled(false);
                    addMapButton.setEnabled(false);
                    addPropertyButton.setEnabled(false);
                    addParamButton.setEnabled(false);
                    addFieldButton.setEnabled(false);
                    addMessageButton.setEnabled(false);
                } else if (tmpNode.getTag().equals("message")) {
                    addExpressionButton.setEnabled(false);
                    addFieldButton.setEnabled(false);
                    if (tmpNode.getFirstChildByTag("map") != null) {
                        addMapButton.setEnabled(false);
                    }
                    // addMapButton.setEnabled(false);
                } else if (tmpNode.getTag().equals("property")
                        || tmpNode.getTag().equals("field")
                        || tmpNode.getTag().equals("param")) {
                    addPropertyButton.setEnabled(false);
                    addParamButton.setEnabled(false);
                    addFieldButton.setEnabled(false);

                    if (tmpNode.getTag().equals("field"))
                        addMessageButton.setEnabled(false);

                    // getCurrentNode();
                    // Util.debugLog("Got current node");
                    Util.debugLog(tmpNode.getAttribute("name"));
                    Util.debugLog("Current object is " + rootPanel.currentObject);

                    if (rootPanel.currentObject != null) {
                        Class[] interfaces = rootPanel.currentObject.getInterfaces();
                        boolean isMappable = false;

                        for (int i = 0; i < interfaces.length; i++) {
                            Class c = interfaces[i];

                            Util.debugLog("interface " + i + " is " + c.getName());
                            if (c.getName().equals("com.dexels.navajo.mapping.Mappable")) {
                                isMappable = true;
                                Util.debugLog("isMappable");
                                break;
                            }
                        }
                        if (!isMappable) {
                            addExpressionButton.setEnabled(true);
                            addMapButton.setEnabled(false);
                        } else {
                            addExpressionButton.setEnabled(false);
                            addMapButton.setEnabled(true);
                        }
                    }

                    if (tmpNode.getFirstChildByTag("map") != null) {
                        addMapButton.setEnabled(false);
                        addMessageButton.setEnabled(false);
                    }

                    if (tmpNode.getTag().equals("param")) {
                        addMapButton.setEnabled(false);
                        addMessageButton.setEnabled(false);
                    }

                    if (tmpNode.getTag().equals("property")
                            || tmpNode.getTag().equals("param")) {
                        addExpressionButton.setEnabled(true);
                    }
                } else if (tmpNode.getTag().equals("expression")) {
                    addMapButton.setEnabled(false);
                    addMessageButton.setEnabled(false);
                    addPropertyButton.setEnabled(false);
                    addParamButton.setEnabled(false);
                    addFieldButton.setEnabled(false);
                    addExpressionButton.setEnabled(false);
                }
            }
        }
        rootPanel.showStatus(tmpNode.getTag());
    }

    private void getCurrentNode() {
        // showMsg("getCurrentNode");
        TreePath path = rootPanel.getBPCLTree().getSelectionPath();
        boolean refIsProperty = false;
        boolean haveSetRefContext = false;

        try {
            selectedNode = (NavajoTreeNode) path.getLastPathComponent();
            System.out.println("tag of selectedNode: " + selectedNode.getTag());
            System.out.println(">>>>>>>>>>>>>>path: " + path.toString());
            Object[] nodePath = path.getPath();

            Field tmpField;

            int limit = 0;

            int min = 0;

            if (expressionIsSelected) {
                min++;
                expressionIsSelected = false;
                // rootPanel.showMsg("expressionIsSelected->false");
            }

            if (doEdit) {
                min++;
            }

            limit = nodePath.length - min;

            boolean foundObjectMap = false; // flag for have founded the map with attribute object

            for (int i = 1; i < limit; i++) {
                // i=0 dit is de <tsl>
                // eerst zoeken naar de eerste <map> met attribute object
                // daarna de treepath doorlopen en zoeken naar <field> en <map>

                NavajoTreeNode tmpNode = (NavajoTreeNode) nodePath[i];

                Util.debugLog("tmpNode = " + tmpNode);

                if (tmpNode.getTag().equals("map")) {
                    // we should have a <map> with attribute object
                    String objectName = tmpNode.getAttribute("object");

                    if (objectName == null || objectName.equals("")) {} else {
                        // check if it is mappable object
                        try {
                            if (isMappableObject(Class.forName(objectName))) {
                                rootPanel.currentObject = Class.forName(objectName);
                                foundObjectMap = true;
                                // rootPanel.showMsg("getCurrentNode main root Object ="+rootPanel.currentObject.getName());
                            }
                        } catch (ClassNotFoundException ce) {

                            /**
                             * @todo moet de foutmelding ook geven in de GUI
                             */
                            ce.printStackTrace(System.out);
                            rootPanel.showError("can't find one or more class of the interfaces");
                        } catch (Exception e) {

                            /**
                             * @todo moet de foutmelding ook geven in de GUI
                             */
                            e.printStackTrace(System.out);
                            rootPanel.showError("something is wrong");
                        }
                    }
                }

                if (!haveSetRefContext) {
                    if (tmpNode.getTag().equals("property")
                            || tmpNode.getTag().equals("message")) {
                        refIsProperty = true;
                        haveSetRefContext = true;
                    }
                    if (tmpNode.getTag().equals("field")) {
                        refIsProperty = false;
                        haveSetRefContext = true;
                    }
                }

                if (foundObjectMap) {
                    if (refIsProperty) {
                        if (tmpNode.getTag().equals("map")) { // we have a <map> with attribute ref
                            try {
                                tmpField = rootPanel.currentObject.getField(tmpNode.getAttribute("ref"));
                                rootPanel.showMsg("ref: " + tmpNode.getAttribute("ref"));
                                String fieldtype = tmpField.getType().getName();

                                if (fieldtype.startsWith("[L")) {
                                    try {
                                        rootPanel.currentObject = Class.forName(fieldtype.substring(2, (fieldtype.length() - 1)));
                                        // rootPanel.showMsg("getCurrentNode ref ="+rootPanel.currentObject.getName());
                                    } catch (Exception e) {
                                        System.err.println(">>>>ERROR<<< cannot find class: " + fieldtype.substring(2, (fieldtype.length() - 1)));
                                        e.printStackTrace(System.out);
                                    }
                                } else {
                                    try {
                                        rootPanel.currentObject = tmpField.getType();
                                        rootPanel.showMsg("getCurrentNode ref =" + rootPanel.currentObject.getName());
                                        // System.err.println("rootPanel.currentObject: " + tmpField.getType().getName());
                                    } catch (Exception e) {
                                        e.printStackTrace(System.out);
                                        rootPanel.showError("somethig went wrong in getCurrentNode() in BPCLPanel");
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace(System.out);
                                rootPanel.showError("malformed BPCL: please check the syntax of this document");
                            }
                        }
                    }
                    // else{
                    if (tmpNode.getTag().equals("field")) {
                        try {
                            tmpField = rootPanel.currentObject.getField(tmpNode.getAttribute("name"));
                            String fieldtype = tmpField.getType().getName();

                            System.err.println(">>>>>>>>>>>>>>>>fieldtype" + fieldtype);
                            if (fieldtype.startsWith("[L")) {
                                try {
                                    Class tmpClass = Class.forName(fieldtype.substring(2, (fieldtype.length() - 1)));

                                    rootPanel.currentObject = tmpClass;
                                    // if(isMappableObject(tmpClass)){
                                    // rootPanel.showMsg("getCurrentNode field [L (Mapable) ="+rootPanel.currentObject.getName());
                                    // }else{
                                    // rootPanel.showMsg("getCurrentNode field [L (NOT Mapable) ="+rootPanel.currentObject.getName());
                                    // }

                                } catch (Exception e) {
                                    System.err.println(">>>>ERROR<<< cannot find class: " + fieldtype.substring(2, (fieldtype.length() - 1)));
                                    e.printStackTrace(System.out);
                                }
                            } else {
                                try {
                                    Class tmpClass = tmpField.getType();

                                    rootPanel.currentObject = tmpClass;

                                    // if(isMappableObject(tmpClass)){
                                    // rootPanel.showMsg("getCurrentNode field(Mapable) ="+rootPanel.currentObject.getName());
                                    // }
                                    // else{
                                    // rootPanel.showMsg("getCurrentNode field(NOT Mapable) ="+rootPanel.currentObject.getName());
                                    // }

                                } catch (Exception e) {
                                    e.printStackTrace(System.out);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace(System.out);
                        }
                        // }
                    }
                }
            }
            if (rootPanel.currentObject != null) {
                // create new JTree for the object get methods
                ClassTreeNode objectNode = new ClassTreeNode(rootPanel.currentObject);

                Util.debugLog("in getCurrentNode(): before calling setObjectNode GET");
                objectNode = rootPanel.setObjectNode(rootPanel.currentObject, objectNode, RootStudioPanel.GET_METHOD);
                DefaultTreeModel objcetTree = new DefaultTreeModel(objectNode);

                rootPanel.currentMappableObjectTree = new JTree(objcetTree);
                // create new JTree for the object set methods
                ClassTreeNode objectNodeSet = new ClassTreeNode(rootPanel.currentObject);

                Util.debugLog("in getCurrentNode(): before calling setObjectNode SET");
                objectNodeSet = rootPanel.setObjectNode(rootPanel.currentObject, objectNodeSet, RootStudioPanel.SET_METHOD);
                DefaultTreeModel objcetTreeSet = new DefaultTreeModel(objectNodeSet);

                rootPanel.currentMappableObjectTreeSet = new JTree(objcetTreeSet);
            }
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            rootPanel.showMsg("please select a node");
            System.err.println("WARNING in BPCLPanel: no node selected");
        }
    }

    void addMapButton_actionPerformed(ActionEvent e) {
        getCurrentNode();
        if (!selectedNode.getTag().equals("")) {
            rootPanel.changeContentPane(new BPCLMapPanel(rootPanel, selectedNode, true), rootPanel.BPCLMAP, true);
        }
    }

    boolean isMappableObject(Class testClass) {

        try {
            Mappable dummy = (Mappable) testClass.newInstance();
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    void refreshTree() {
        rootPanel.getBPCLTree().revalidate();
        setButtonsStatus();
    }

    // override actions
    void editPaste_actionPerformed(ActionEvent e) {
        getCurrentNode();
        Util.debugLog("PASTE Currently in: " + selectedNode.getTag() + ": " + selectedNode.getAttribute("name"));
        NavajoTreeNode realCopy = copyNode(copiedNode);

        if (realCopy != null) {
            Util.debugLog("Pasting node: " + realCopy.getTag() + ": " + realCopy.getAttribute("name"));
            rootPanel.getBPCLTreeModel().insertNodeInto(realCopy, selectedNode, selectedNode.getChildCount());
            TreeNode[] nodes = rootPanel.getBPCLTreeModel().getPathToRoot(selectedNode);
            TreePath path = new TreePath(nodes);

            rootPanel.getBPCLTree().setSelectionPath(path);
            setButtonsStatus();
        }
    }

    void editCopy_actionPerformed(ActionEvent e) {
        getCurrentNode();
        Util.debugLog("COPY: " + selectedNode.getTag() + ": " + selectedNode.getAttribute("name"));
        copiedNode = copyNode(selectedNode);
        // copiedNode = selectedNode;
        rootPanel.editPaste.setEnabled(true);
        rootPanel.pasteButton.setEnabled(true);
    }

    private NavajoTreeNode copyNode(NavajoTreeNode in) {
        NavajoTreeNode out = (NavajoTreeNode) in.clone();
        NavajoTreeNode[] children = in.getAllChilderen();

        if (children != null) {
            for (int i = 0; i < children.length; i++) {
                NavajoTreeNode newChild = copyNode(children[i]);

                out.add(newChild);
            }
        }
        return out;
    }

    void editDelete_actionPerformed(ActionEvent e) {
        getCurrentNode();
        Util.debugLog("DELETE: " + selectedNode.getTag() + ": " + selectedNode.getAttribute("name"));
        this.removeButton_actionPerformed(null);
    }

    void useObjectButton_mouseEntered(MouseEvent e) {
        if (useObjectButton.isEnabled()) {
            rootPanel.showStatus("Add Object");
        }
    }

    void useObjectButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus("");
    }

    void addPropertyButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Add Property");
    }

    void addParamButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Add Param");
    }

    void addMapButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Add Map");
    }

    void addMessageButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Add Message");
    }

    void addFieldButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Add Field");
    }

    void addExpressionButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Add Expression");
    }

    void editButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Edit selected node");
    }

    void moveUpButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Move selected node up");
    }

    void moveDownButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Move selected node down");
    }

    void removeButton_mouseEntered(MouseEvent e) {
        rootPanel.showStatus("Remove selected node");
    }

    void addExpressionButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus("");
    }

    void addFieldButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus("");
    }

    void addMessageButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus("");
    }

    void addMapButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus("");
    }

    void addParamButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus("");
    }

    void addPropertyButton_mouseExited(MouseEvent e) {
        rootPanel.showStatus("");
    }

}
