package com.dexels.navajo.tipi.components.swingimpl.swing;

import com.dexels.navajo.document.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.swingclient.components.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import com.dexels.navajo.tipi.components.swingimpl.questioneditor.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingGroupPanel extends JPanel {
  private String myId = null;
  private String myName = null;
  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane mainSplit = new JSplitPane();
  TipiSwingQuestionEditForm questionEditPanel = new TipiSwingQuestionEditForm(this);
  JPanel jPanel1 = new JPanel();
  MessageTreePanel messageTree = new MessageTreePanel();
  JScrollPane treeScroll = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  NavigationPanel navigationPanel1 = new NavigationPanel();

  private Message myQuestionListMessage = null;
  private MessageTreeNode selectedNode =null;

  public TipiSwingGroupPanel(Message groupMsg) {
    myQuestionListMessage = groupMsg;
//    Property idProp = groupMsg.getProperty("Id");
//    Property nameProp = groupMsg.getProperty("Name");
//    myId = idProp.getValue();
//    myName = nameProp.getValue();
//    Message group = groupMsg.getMessage("Question");
//    System.err.println("Group: "+group);
//    group.write(System.err);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    reload();
  }


  public void reload() {
    messageTree.setMessage(myQuestionListMessage,"Id");
    messageTree.expandAll();
  }

  public TipiSwingGroupPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public String getGroupName() {
    return myName;
  }

  private final void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    mainSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    mainSplit.setContinuousLayout(true);
    jPanel1.setLayout(borderLayout2);
    navigationPanel1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        navigationPanel1_actionPerformed(e);
      }
    });
    messageTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        messageTree_valueChanged(e);
      }
    });
    this.add(mainSplit, BorderLayout.CENTER);
    mainSplit.add(questionEditPanel, JSplitPane.RIGHT);
    mainSplit.add(jPanel1, JSplitPane.TOP);
    jPanel1.add(treeScroll, BorderLayout.CENTER);
    jPanel1.add(navigationPanel1,  BorderLayout.SOUTH);
    treeScroll.getViewport().add(messageTree, null);
    mainSplit.setDividerLocation(120);
  }

  void messageTree_valueChanged(TreeSelectionEvent e) {
    TreePath tp = messageTree.getSelectionPath();
    if (tp==null) {
      questionEditPanel.setMessage(null);
      return;
    }
    selectedNode = (MessageTreeNode)tp.getLastPathComponent();
    if (selectedNode!=null) {
      questionEditPanel.setMessage(selectedNode.getMessage());
      if (selectedNode.getMessage()!=null) {
        navigationPanel1.setEnabled(selectedNode.getMessage().getName().equals("Question"));
      } else {
        navigationPanel1.setEnabled(false);
      }
    } else {
      navigationPanel1.setEnabled(false);
    }
  }

  void navigationPanel1_actionPerformed(ActionEvent e) {
    if (selectedNode==null) {
      return;
    }
    MessageTreeNode parentNode = (MessageTreeNode)selectedNode.getParent();
    int index = parentNode.getIndex(selectedNode);
    Message current = selectedNode.getMessage();
    Message parent = current.getParentMessage();

    if (e.getActionCommand().equals("up")) {
      if (index<=0) {
        return;
      }
      try {
        parent.removeMessage(current);
        parent.addMessage(current, index - 1);
        reload();
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
    }

    if (e.getActionCommand().equals("down")) {
      if (index>=parent.getArraySize()-1) {
        return;
      }
      try {
        parent.removeMessage(current);
        parent.addMessage(current, index +1);
        reload();
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
    }

  }

}

//class TipiSwingGroupPanel_messageTree_treeSelectionAdapter implements javax.swing.event.TreeSelectionListener {
//  TipiSwingGroupPanel adaptee;
//
//  TipiSwingGroupPanel_messageTree_treeSelectionAdapter(TipiSwingGroupPanel adaptee) {
//    this.adaptee = adaptee;
//  }
//  public void valueChanged(TreeSelectionEvent e) {
//    adaptee.messageTree_valueChanged(e);
//  }
//}
