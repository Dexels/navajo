package com.dexels.navajo.tipi.swingclient.components.treetable;

import java.awt.*;
import java.awt.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.*;

import java.util.ResourceBundle;

public class MessageTreeTableSelectionDialog extends StandardDialog {
  MessageTreeTablePanel messageTreeTablePanel = new MessageTreeTablePanel();

  public MessageTreeTableSelectionDialog() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    setMode(StandardDialog.MODE_OK_CANCEL);
    addMainPanel(messageTreeTablePanel);
  }

  public void setMessage(Message m) {
    System.err.println("\n\n SETTING MESSAGE!!!!!!!!\n\n");
    messageTreeTablePanel.setMessage(m);
    if(m.getArraySize() > 0){
      messageTreeTablePanel.setSelectedRow(0);
    }
  }

  public void setColumnWidth(int id, int size) {
    messageTreeTablePanel.setColumnWidth(id, size);
  }

  public void setMessage(Message m, String[] columns, String[] exclusions) {
    messageTreeTablePanel.setMessage(m, columns, exclusions);
    System.err.println("\n\n SETTING MESSAGE!!!!!!!!\n\n");

    if(m.getArraySize() > 0){
      messageTreeTablePanel.setSelectedRow(0);
    }
  }

  public Message getSelectedMessage() {
    return messageTreeTablePanel.getSelectedMessage();
  }

  public void setHeaderTextResource(ResourceBundle res){
    messageTreeTablePanel.setHeaderTextResource(res);
  }

  public void setModel(TreeTableModel ttm) {
    messageTreeTablePanel.setModel(ttm);
  }

//  public void setMessageMap(MessageMappable m) {
//    messageTreeTablePanel.setMessageMap(m);
//  }

  public void addColumn(String id, String name, boolean editable) {
    messageTreeTablePanel.addColumn(id, name, editable);
  }

  private final void jbInit() throws Exception {
    messageTreeTablePanel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        messageTreeTablePanel_actionPerformed(e);
      }
    });
//    this.getContentPane().add(messageTreeTablePanel, BorderLayout.CENTER);
  }

  void messageTreeTablePanel_actionPerformed(ActionEvent e) {
    messageTreeTablePanel.commit();
    commit();
    setVisible(false);
  }

  public void discard(){ 
  }
}
