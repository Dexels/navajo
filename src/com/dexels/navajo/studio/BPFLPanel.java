package com.dexels.navajo.studio;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;

import com.dexels.navajo.xml.*;
import com.dexels.navajo.util.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.util.*;

import org.xml.sax.*;
import org.w3c.dom.*;
import javax.swing.border.*;

/**
 * Title:         Navajo
 * Description:
 * Copyright:     Copyright (c) 2002
 * Company:       Dexels
 * @author:       Albert Lo
 * @version 1.0
 */


public class BPFLPanel extends BaseStudioPanel implements ActionListener  {

  //buttons
  private JButton removeButton = new JButton();
  private JButton editButton = new JButton();
  private JButton moveUpButton = new JButton();
  private JButton moveDownButton = new JButton();
  private JButton addMessageButton = new JButton();
  private JButton addPropertyButton = new JButton();
  private JButton addMethodButton = new JButton();

  private ImageIcon editButtonIcon = new ImageIcon("images/edit_off.gif");
  private ImageIcon editButtonIconOn = new ImageIcon("images/edit_on.gif");
  private ImageIcon editButtonIconDisabled = new ImageIcon("images/edit_disabled.gif");
  private ImageIcon editButtonIconPressed = new ImageIcon("images/edit_pressed.gif");

  private ImageIcon moveUpButtonIcon = new ImageIcon("images/arrow_up_off.gif");
  private ImageIcon moveUpButtonIconOn = new ImageIcon("images/arrow_up_on.gif");
  private ImageIcon moveUpButtonIconDisabled = new ImageIcon("images/arrow_up_disabled.gif");
  private ImageIcon moveUpButtonIconPressed = new ImageIcon("images/arrow_up_pressed.gif");

  private ImageIcon moveDownButtonIcon = new ImageIcon("images/arrow_down_off.gif");
  private ImageIcon moveDownButtonIconOn = new ImageIcon("images/arrow_down_on.gif");
  private ImageIcon moveDownButtonIconDisabled = new ImageIcon("images/arrow_down_disabled.gif");
  private ImageIcon moveDownButtonIconPressed = new ImageIcon("images/arrow_down_pressed.gif");

  private ImageIcon removeButtonIcon = new ImageIcon("images/remove_off.gif");
  private ImageIcon removeButtonIconOn = new ImageIcon("images/remove_on.gif");
  private ImageIcon removeButtonIconDisabled = new ImageIcon("images/remove_disabled.gif");
  private ImageIcon removeButtonIconPressed = new ImageIcon("images/remove_pressed.gif");

  private ImageIcon addMessageButtonIcon = new ImageIcon("images/new_message_off.gif");
  private ImageIcon addMessageButtonIconOn = new ImageIcon("images/new_message_on.gif");
  private ImageIcon addMessageButtonIconDisabled = new ImageIcon("images/new_message_disabled.gif");
  private ImageIcon addMessageButtonIconPressed = new ImageIcon("images/new_message_pressed.gif");

  private ImageIcon addPropertyButtonIcon = new ImageIcon("images/new_property_off.gif");
  private ImageIcon addPropertyButtonIconOn = new ImageIcon("images/new_property_on.gif");
  private ImageIcon addPropertyButtonIconDisabled = new ImageIcon("images/new_property_disabled.gif");
  private ImageIcon addPropertyButtonIconPressed = new ImageIcon("images/new_property_pressed.gif");

  private ImageIcon addMethodButtonIcon = new ImageIcon("images/new_method_off.gif");
  private ImageIcon addMethodButtonIconOn = new ImageIcon("images/new_method_on.gif");
  private ImageIcon addMethodButtonIconDisabled = new ImageIcon("images/new_method_disabled.gif");
  private ImageIcon addMethodButtonIconPressed = new ImageIcon("images/new_method_pressed.gif");

  //panels

  private JPanel iconsbarPanel = new JPanel();
  private JPanel mainPanel = new JPanel();

  //labels, panes & separators
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JSeparator iconSeparator3 = new JSeparator(JSeparator.VERTICAL);

  //layout
  BorderLayout borderLayout1 = new BorderLayout();
//  TitledBorder titledBorder1;
//  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  FlowLayout flowLayout1 = new FlowLayout(FlowLayout.LEFT, 1, 6);

  private NavajoTreeNode copiedNode = null;

  public BPFLPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public BPFLPanel(RootStudioPanel rootPanel1) {

    try {
      rootPanel=rootPanel1;
      jbInit();

    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {

    title="Form Editor";

//    titledBorder1 = new TitledBorder("");

    rootPanel.setEditCopyEnabled(false);
    rootPanel.setEditPasteEnabled(false);

    removeButton.setBorder(null);
    removeButton.setMaximumSize(new Dimension(24, 24));
    removeButton.setMinimumSize(new Dimension(24, 24));
    removeButton.setPreferredSize(new Dimension(24, 24));
    removeButton.setIcon(removeButtonIcon);
    removeButton.setRolloverIcon(removeButtonIconOn);
    removeButton.setDisabledIcon(removeButtonIconDisabled);
    removeButton.setPressedIcon(removeButtonIconPressed);
    removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        removeButton_mouseEntered(e);
      }
      public void mouseExited(MouseEvent e) {
        removeButton_mouseExited(e);
      }
    });
    removeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removeButton_actionPerformed(e);
      }
    });

    editButton.setBorder(null);
    editButton.setMaximumSize(new Dimension(24, 24));
    editButton.setMinimumSize(new Dimension(24, 24));
    editButton.setPreferredSize(new Dimension(24, 24));
    editButton.setIcon(editButtonIcon);
    editButton.setRolloverIcon(editButtonIconOn);
    editButton.setDisabledIcon(editButtonIconDisabled);
    editButton.setPressedIcon(editButtonIconPressed);
    editButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        editButton_mouseEntered(e);
      }
      public void mouseExited(MouseEvent e) {
        editButton_mouseExited(e);
      }
    });
    editButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editButton_actionPerformed(e);
      }
    });
    moveUpButton.setBorder(null);
    moveUpButton.setMaximumSize(new Dimension(24, 24));
    moveUpButton.setMinimumSize(new Dimension(24, 24));
    moveUpButton.setPreferredSize(new Dimension(24, 24));
    moveUpButton.setIcon(moveUpButtonIcon);
    moveUpButton.setRolloverIcon(moveUpButtonIconOn);
    moveUpButton.setDisabledIcon(moveUpButtonIconDisabled);
    moveUpButton.setPressedIcon(moveUpButtonIconPressed);
    moveUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        moveUpButton_mouseEntered(e);
      }
      public void mouseExited(MouseEvent e) {
        moveUpButton_mouseExited(e);
      }
    });
    moveUpButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        moveUpButton_actionPerformed(e);
      }
    });
    moveDownButton.setBorder(null);
    moveDownButton.setMaximumSize(new Dimension(24, 24));
    moveDownButton.setMinimumSize(new Dimension(24, 24));
    moveDownButton.setPreferredSize(new Dimension(24, 24));
    moveDownButton.setIcon(moveDownButtonIcon);
    moveDownButton.setRolloverIcon(moveDownButtonIconOn);
    moveDownButton.setDisabledIcon(moveDownButtonIconDisabled);
    moveDownButton.setPressedIcon(moveDownButtonIconPressed);
    moveDownButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        moveDownButton_mouseEntered(e);
      }
      public void mouseExited(MouseEvent e) {
        moveDownButton_mouseExited(e);
      }
    });
    moveDownButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        moveDownButton_actionPerformed(e);
      }
    });



    addMessageButton.setBorder(null);
    addMessageButton.setMaximumSize(new Dimension(24, 24));
    addMessageButton.setMinimumSize(new Dimension(24, 24));
    addMessageButton.setPreferredSize(new Dimension(24, 24));
    addMessageButton.setIcon(addMessageButtonIcon);
    addMessageButton.setRolloverIcon(addMessageButtonIconOn);
    addMessageButton.setDisabledIcon(addMessageButtonIconDisabled);
    addMessageButton.setPressedIcon(addMessageButtonIconPressed);
    addMessageButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        addMessageButton_mouseEntered(e);
      }
      public void mouseExited(MouseEvent e) {
        addMessageButton_mouseExited(e);
      }
    });
    addMessageButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addMessageButton_actionPerformed(e);
      }
    });
    addPropertyButton.setBorder(null);
    addPropertyButton.setMaximumSize(new Dimension(24, 24));
    addPropertyButton.setMinimumSize(new Dimension(24, 24));
    addPropertyButton.setPreferredSize(new Dimension(24, 24));
    addPropertyButton.setIcon(addPropertyButtonIcon);
    addPropertyButton.setRolloverIcon(addPropertyButtonIconOn);
    addPropertyButton.setDisabledIcon(addPropertyButtonIconDisabled);
    addPropertyButton.setPressedIcon(addPropertyButtonIconPressed);
    addPropertyButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        addPropertyButton_mouseEntered(e);
      }
      public void mouseExited(MouseEvent e) {
        addPropertyButton_mouseExited(e);
      }
    });
    addPropertyButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addPropertyButton_actionPerformed(e);
      }
    });
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
    });
    addMethodButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addMethodButton_actionPerformed(e);
      }
    });

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
    });
    addMethodButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addMethodButton_actionPerformed(e);
      }
    });



    rootPanel.getBPFLTree().addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        rootPanel_tmlTree_mouseReleased(e);
      }
      public void mousePressed(MouseEvent e) {
        rootPanel_tmlTree_mousePressed(e);
      }
    });

    this.setBackground(new Color(212, 208, 200));
    this.setAlignmentX((float) 0.0);
    this.setAlignmentY((float) 0.0);
    this.setBorder(BorderFactory.createEtchedBorder());
    this.setMinimumSize(new Dimension(10, 10));
    this.setPreferredSize(new Dimension(100, 100));

    jScrollPane2.setBorder(BorderFactory.createEtchedBorder());
    jScrollPane2.setMinimumSize(new Dimension(10, 10));
    jScrollPane2.setPreferredSize(new Dimension(200, 100));

    iconsbarPanel.setMaximumSize(new Dimension(400, 34));
    iconsbarPanel.setMinimumSize(new Dimension(400, 34));
    iconsbarPanel.setLayout(flowLayout1);

    mainPanel.setLayout(borderLayout1);

    iconSeparator3.setPreferredSize(new Dimension(2, 24));

    this.add(iconsbarPanel, BorderLayout.NORTH);
    iconsbarPanel.add(addMessageButton, null);
    iconsbarPanel.add(addPropertyButton, null);
    iconsbarPanel.add(addMethodButton, null);
    iconsbarPanel.add(iconSeparator3);
    iconsbarPanel.add(editButton, null);
    iconsbarPanel.add(removeButton, null);
    iconsbarPanel.add(moveUpButton, null);
    iconsbarPanel.add(moveDownButton, null);

    this.add(mainPanel, BorderLayout.CENTER);
    mainPanel.add(jScrollPane2, BorderLayout.CENTER);
    rootPanel.showStatus(" ");
    rootPanel.getBPFLTree().putClientProperty("JTree.lineStyle", "Angled");
    selectedNode=(NavajoTreeNode)rootPanel.getBPFLTree().getLastSelectedPathComponent();
    if(selectedNode!=null){
      setButtonsStatus();
    }
    else{
      System.err.println("DEBUG: TML selectedNode==null");
      selectedNode = (NavajoTreeNode)rootPanel.getBPFLTree().getLastSelectedPathComponent();
      setButtonsStatus();
    }
    BPFLTreeCellRenderer renderer = new BPFLTreeCellRenderer();
    rootPanel.getBPFLTree().setCellRenderer(renderer);
    jScrollPane2.getViewport().add(rootPanel.getBPFLTree(), null);
  }


  void removeButton_actionPerformed(ActionEvent e) {

    getCurrentNode();


    NavajoTreeNode sibling = (NavajoTreeNode) selectedNode.getPreviousSibling();
    if (sibling == null) {
      sibling = (NavajoTreeNode) selectedNode.getParent();
    }

    if(selectedNode.getTag().equals("method")){
      // If the removed node is an <mehtod> then we have to check the number of remaining <method> left
      // after the remove.
      // If there is no <method> left, then we will also delete the parent <methods>
      // because it holds no methods and therefore it lost it's meaning.
      if(selectedNode.getSiblingCount()==1){
        rootPanel.getBPFLTreeModel().removeNodeFromParent((DefaultMutableTreeNode)selectedNode.getParent());
      }
    }

    ((DefaultTreeModel)rootPanel.getBPFLTree().getModel()).removeNodeFromParent(selectedNode);


    if (sibling != null) {
        TreeNode[] nodes = rootPanel.getBPFLTreeModel().getPathToRoot(sibling);
        selectedNode = sibling;
        TreePath path = new TreePath(nodes);
        rootPanel.getBPFLTree().setSelectionPath(path);
        setButtonsStatus();

    }

    rootPanel.isModified();

  }


  void addMessageButton_actionPerformed(ActionEvent e) {
//    to do: add disabled and enabled feature

    getCurrentNode();
    if(!selectedNode.getTag().equals("")){
      BPFLMessagePanel messagePanel = new BPFLMessagePanel(rootPanel, rootPanel.getBPFLTree(), selectedNode, this, true);
      rootPanel.changeContentPane(messagePanel, rootPanel.BPFLMESSAGE, true);
    }

  }

  void addPropertyButton_actionPerformed(ActionEvent e) {
//    to do: add disabled and enabled feature

    getCurrentNode();

    if(!selectedNode.getTag().equals("")){
      BPFLPropertyPanel propertyPanel = new BPFLPropertyPanel(rootPanel, selectedNode, true);
      rootPanel.changeContentPane(propertyPanel, rootPanel.BPFLPROPERTY, true);
    }
  }

  public void actionPerformed(ActionEvent e) {
    int bla=0;
    System.err.println(bla +" " + e.getActionCommand());
  }

  void rootPanel_tmlTree_mouseReleased(MouseEvent e) {

//    Util.debugLog("in mouseReleased: " + e);

    if (e.getClickCount() == 2) {

      editButton_actionPerformed(null);
    }
/*
    TreePath path = rootPanel.tmlTree.getSelectionPath();
    if(e.isPopupTrigger() && path!=null){
    NavajoTreeNode selectedNode = (NavajoTreeNode)path.getLastPathComponent();
    String tag = selectedNode.getTag();

    PopupMenu menu = new PopupMenu("choose");
    if(tag.equals("tml")||tag.equals("methods")){
      addToPopup("none", menu, false);
    }
    else{
      if(tag.equals("message")){
        addToPopup("add message", menu, true);
        addToPopup("add property", menu, true);
      }
      addToPopup("remove", menu, true);
      addToPopup("edit", menu, true);
    }
    rootPanel.tmlTree.add(menu);
    menu.show(e.getComponent(), e.getX(), e.getY());
    }
*/
  }

  void rootPanel_tmlTree_mousePressed(MouseEvent e) {

//    Util.debugLog("in mousePressed: " + e);
/*
    TreePath path = rootPanel.tmlTree.getPathForLocation(e.getX(), e.getY());
    if(path!=null){//
      rootPanel.tmlTree.setSelectionPath(path);
      rootPanel.tmlTree.scrollPathToVisible(path);
    }
*/
    selectedNode = (NavajoTreeNode)rootPanel.getBPFLTree().getLastSelectedPathComponent();

    setButtonsStatus();
  }


  void addToPopup(String name, PopupMenu menu, boolean enabled){
    MenuItem item = new MenuItem(name);
    item.setEnabled(enabled);
    menu.add(item);
    item.addActionListener(this);

  }

  void addMethodButton_actionPerformed(ActionEvent e) {
    NavajoTreeNode root= (NavajoTreeNode)rootPanel.getBPFLTree().getModel().getRoot();
//    NavajoTreeNode root= (NavajoTreeNode)rootPanel.getBPFLTreeModel().getRoot();
//    System.err.println("root: " + root.getTag());


    // find <methods> node
    NavajoTreeNode methodsnode = new NavajoTreeNode();
    for(int i = 0; i<root.getChildCount(); i++){
      NavajoTreeNode tmpNode = (NavajoTreeNode)rootPanel.tslModel.getChild(root, i);
      if(tmpNode.getTag().equals("methods")){
        methodsnode= tmpNode;
      }
    }

    if(!methodsnode.getTag().equals("")){
      // <methods> node found, proceed adding new <method> nodes
      rootPanel.changeContentPane(new BPFLMethodPanel(rootPanel, methodsnode, true), rootPanel.BPFLMETHODS, true);
    }
    else{
      // <methods> node not found tell the new node to add
      //first make a new Methods Node before adding a new method node
      methodsnode = new NavajoTreeNode("methods");
      rootPanel.getBPFLTreeModel().insertNodeInto(methodsnode,root, root.getChildCount());

      rootPanel.changeContentPane(new BPFLMethodPanel(rootPanel, methodsnode, true), rootPanel.BPFLMETHODS, true);
    }

  }

  void editButton_actionPerformed(ActionEvent e) {
//    to do: add disabled and enabled feature
    doEdit();
  }

  void doEdit(){
    getCurrentNode();

    String tag=selectedNode.getTag();
    if(tag.equals("message")){
      BPFLMessagePanel panel = new BPFLMessagePanel(rootPanel, rootPanel.getBPFLTree(), selectedNode, this, false);
      rootPanel.changeContentPane(panel, rootPanel.BPFLMESSAGE, false);
    }
    else if(tag.equals("property")){
      BPFLPropertyPanel panel = new BPFLPropertyPanel(rootPanel, selectedNode, false);
      rootPanel.changeContentPane(panel, rootPanel.BPFLPROPERTY,false);
    }
    else if(tag.equals("method")){
      BPFLMethodPanel panel = new BPFLMethodPanel(rootPanel, selectedNode, false);
      rootPanel.changeContentPane(panel, rootPanel.BPFLMETHODS, false);
    }
    else if(tag.equals("required")){
      BPFLMethodPanel panel = new BPFLMethodPanel(rootPanel, (NavajoTreeNode)selectedNode.getParent(), false);
      rootPanel.changeContentPane(panel, rootPanel.BPFLMETHODS, false);
    }
    else if(tag.equals("tml")||tag.equals("methods")||tag.equals("option")||tag.equals("required")){
    }
  }

  void moveUpButton_actionPerformed(ActionEvent e) {
//    to do: add disabled and enabled feature

    getCurrentNode();

    if(!selectedNode.getTag().equals("")){
      NavajoTreeNode parentNode = (NavajoTreeNode)selectedNode.getParent();
      int currentPos=  rootPanel.getBPFLTreeModel().getIndexOfChild(parentNode, selectedNode);
      if(currentPos>0){
        NavajoTreeNode neighbour = (NavajoTreeNode)parentNode.getChildAt(currentPos-1);
        rootPanel.getBPFLTreeModel().removeNodeFromParent(selectedNode);
        rootPanel.getBPFLTreeModel().insertNodeInto(selectedNode, parentNode, currentPos-1);
        rootPanel.getBPFLTreeModel().removeNodeFromParent(neighbour);
        rootPanel.getBPFLTreeModel().insertNodeInto(neighbour, parentNode, currentPos);
        rootPanel.getBPFLTreeModel().nodeChanged(parentNode);
        rootPanel.isModified();
        rootPanel.saveButton.setEnabled(true);

        TreeNode[] nodes =  rootPanel.getBPFLTreeModel().getPathToRoot(selectedNode);
        currentPos=  rootPanel.getBPFLTreeModel().getIndexOfChild(parentNode, selectedNode);

        TreePath path1 = new TreePath(nodes);
        rootPanel.getBPFLTree().setSelectionPath(path1);
        rootPanel.getBPFLTree().scrollPathToVisible(path1);
//        rootPanel.tmlTree.repaint();
      }
      else{
        rootPanel.showMsg("selectedNode allready at top");
      }
    }
    setButtonsStatus();
  }

  void moveDownButton_actionPerformed(ActionEvent e) {
//    to do: add disabled and enabled feature

    TreePath path = rootPanel.getBPFLTree().getSelectionPath();
    getCurrentNode();

    if(!selectedNode.getTag().equals("")){
      NavajoTreeNode parentNode = (NavajoTreeNode)selectedNode.getParent();
      int currentPos=  rootPanel.getBPFLTreeModel().getIndexOfChild(parentNode, selectedNode);

      if(currentPos<parentNode.getChildCount()-1){
        NavajoTreeNode neighbour = (NavajoTreeNode)parentNode.getChildAt(currentPos+1);
        rootPanel.getBPFLTreeModel().removeNodeFromParent(selectedNode);
        rootPanel.getBPFLTreeModel().insertNodeInto(selectedNode, parentNode, currentPos+1);
        rootPanel.getBPFLTreeModel().removeNodeFromParent(neighbour);
        rootPanel.getBPFLTreeModel().insertNodeInto(neighbour, parentNode, currentPos);
        rootPanel.getBPFLTreeModel().nodeChanged(parentNode);
        rootPanel.isModified();
        //new
        rootPanel.saveButton.setEnabled(true);

        TreeNode[] nodes =  rootPanel.getBPFLTreeModel().getPathToRoot(selectedNode);
        currentPos=  rootPanel.getBPFLTreeModel().getIndexOfChild(parentNode, selectedNode);

        TreePath path1 = new TreePath(nodes);
        rootPanel.getBPFLTree().setSelectionPath(path1);
        rootPanel.getBPFLTree().scrollPathToVisible(path1);
//        rootPanel.tmlTree.repaint();
      }
      else{
        rootPanel.showMsg("selectedNode already at bottom");
//        System.err.println("WARNING in BPFLPanel: selectedNode allready at bottom");
      }
    }
    setButtonsStatus();
  }

  private NavajoTreeNode copyNode(NavajoTreeNode in) {
    NavajoTreeNode out = (NavajoTreeNode) in.clone();
    NavajoTreeNode [] children = in.getAllChilderen();
    if (children != null) {
      for (int i = 0; i < children.length; i++) {
        NavajoTreeNode newChild = copyNode(children[i]);
        out.add(newChild);
      }
    }
    return out;
  }

  //override actions
  void editPaste_actionPerformed(ActionEvent e) {
    getCurrentNode();
    Util.debugLog("PASTE Currently in: " + selectedNode.getTag()+ ": " + selectedNode.getAttribute("name"));
    NavajoTreeNode realCopy = copyNode(copiedNode);
    if (realCopy != null) {
      Util.debugLog("Pasting node: " + realCopy.getTag()+ ": " + realCopy.getAttribute("name"));
      rootPanel.getBPFLTreeModel().insertNodeInto(realCopy, selectedNode, selectedNode.getChildCount());
      TreeNode[] nodes = rootPanel.getBPFLTreeModel().getPathToRoot(selectedNode);
      TreePath path = new TreePath(nodes);
      rootPanel.getBPFLTree().setSelectionPath(path);
      setButtonsStatus();
    }
  }

  void editCopy_actionPerformed(ActionEvent e) {
    getCurrentNode();
    Util.debugLog("COPY: " + selectedNode.getTag() + ": " + selectedNode.getAttribute("name"));
   copiedNode = copyNode(selectedNode);
//    copiedNode = selectedNode;
    rootPanel.setEditPasteEnabled(true);
  }

  void editDelete_actionPerformed(ActionEvent e) {
    getCurrentNode();
    Util.debugLog("DELETE: " + selectedNode.getTag() + ": " + selectedNode.getAttribute("name"));
    this.removeButton_actionPerformed(null);
  }

  private void getCurrentNode(){
    TreePath path = rootPanel.getBPFLTree().getSelectionPath();
    try{
      selectedNode = (NavajoTreeNode)path.getLastPathComponent();
    }
    catch(NullPointerException ne){
      rootPanel.showMsg("please select a node");
    }
    showMsg("selected tag: "+selectedNode.getTag());
  }

  void setButtonsStatus(){
    getCurrentNode();
    NavajoTreeNode tmpNode = selectedNode;

    addMessageButton.setEnabled(true);
    addPropertyButton.setEnabled(true);
    addMethodButton.setEnabled(true);
    removeButton.setEnabled(true);

    editButton.setEnabled(true);
    rootPanel.setEditChangeEnabled(true);

    moveUpButton.setEnabled(true);
    moveDownButton.setEnabled(true);
    //the following sets the buttons to be enabled or disabled for the current node
    if(tmpNode!=null){
      if(tmpNode.isRoot()){
        addPropertyButton.setEnabled(false);
        rootPanel.setEditCopyEnabled(false);

        removeButton.setEnabled(false);
        rootPanel.setEditDeleteEnabled(false);

        editButton.setEnabled(false);
        rootPanel.setEditChangeEnabled(false);

        moveUpButton.setEnabled(false);
        moveDownButton.setEnabled(false);
      }
      else{
        NavajoTreeNode parentNode = (NavajoTreeNode)tmpNode.getParent();
        rootPanel.setEditCopyEnabled(true);

        int currentPos=  rootPanel.getBPFLTreeModel().getIndexOfChild(parentNode, tmpNode);
        if(!(currentPos>0)){
          moveUpButton.setEnabled(false);
        }
        if(!(currentPos<parentNode.getChildCount()-1)){
          moveDownButton.setEnabled(false);
        }

        if(tmpNode.getTag().equals("message")){
          addMessageButton.setEnabled(true);
          addPropertyButton.setEnabled(true);
          addMethodButton.setEnabled(true);

          removeButton.setEnabled(true);
          rootPanel.setEditDeleteEnabled(true);

          editButton.setEnabled(true);
          rootPanel.setEditChangeEnabled(true);
        }
        else if(tmpNode.getTag().equals("property")){
          addMessageButton.setEnabled(false);
          addPropertyButton.setEnabled(false);
        }
        else if(tmpNode.getTag().equals("method")){
          addMessageButton.setEnabled(false);
          addPropertyButton.setEnabled(false);
        }
        else{
          addMessageButton.setEnabled(false);
          addPropertyButton.setEnabled(false);
//          removeButton.setEnabled(false);
          editButton.setEnabled(false);
          rootPanel.setEditChangeEnabled(false);
        }
      }
    }
  }

  //Statusbar events

  void editButton_mouseEntered(MouseEvent e) {
     rootPanel.showStatus("Edit selected node");
     editButton.setToolTipText("Edit node");
  }

  void editButton_mouseExited(MouseEvent e) {
    rootPanel.showStatus(" ");
  }

  void removeButton_mouseEntered(MouseEvent e) {
    rootPanel.showStatus("Remove selected node");
    removeButton.setToolTipText("Remove node");
  }

  void removeButton_mouseExited(MouseEvent e) {
    rootPanel.showStatus(" ");
  }

  void moveUpButton_mouseEntered(MouseEvent e) {
    rootPanel.showStatus("Move selected node up");
    moveUpButton.setToolTipText("Move Up");
  }

  void moveUpButton_mouseExited(MouseEvent e) {
    rootPanel.showStatus(" ");
  }

  void moveDownButton_mouseEntered(MouseEvent e) {
    rootPanel.showStatus("Move selected node down");
    moveDownButton.setToolTipText("Move down");
  }

  void moveDownButton_mouseExited(MouseEvent e) {
    rootPanel.showStatus(" ");
  }


  void addMessageButton_mouseEntered(MouseEvent e) {
      rootPanel.showStatus("Add Message");
      addMessageButton.setToolTipText("Add Message");
  }

  void addMessageButton_mouseExited(MouseEvent e) {
    rootPanel.showStatus(" ");
  }

  void addPropertyButton_mouseEntered(MouseEvent e) {
      rootPanel.showStatus("Add Property");
      addPropertyButton.setToolTipText("Add Property");
  }

  void addPropertyButton_mouseExited(MouseEvent e) {
      rootPanel.showStatus(" ");
  }
  void addMethodButton_mouseEntered(MouseEvent e) {
      rootPanel.showStatus("Add Method");
      addMethodButton.setToolTipText("Add Method");
  }
  void addMethodButton_mouseExited(MouseEvent e) {
      rootPanel.showStatus(" ");
  }


  void refreshTree(){
//    scrollTreeToCurrentLocation()
    rootPanel.getBPFLTree().revalidate();
    setButtonsStatus();
    /*
    rootPanel.getBPFLTree().repaint();
//    ((JTree)jScrollPane2.getViewport().getComponent(0)).revalidate();
//    ((JTree)jScrollPane2.getViewport().getComponent(0)).repaint();
    if(jScrollPane2.getViewport().getComponent(0)!=rootPanel.getBPFLTree()){
      rootPanel.showMsg("tree out of sync");
    }
    rootPanel.showMsg("Debug: BPFLPanel refreshPanel");*/
  }

}
