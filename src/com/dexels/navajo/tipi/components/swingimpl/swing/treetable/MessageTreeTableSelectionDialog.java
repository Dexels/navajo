package com.dexels.navajo.tipi.components.swingimpl.swing.treetable;

import java.awt.*;
import java.awt.event.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;

public class MessageTreeTableSelectionDialog
    extends StandardDialog {
  MessageTreeTablePanel messageTreeTablePanel = new MessageTreeTablePanel();
  public MessageTreeTableSelectionDialog() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    setMode(StandardDialog.MODE_OK_CANCEL);
  }

  public void setMessage(Message m) {
    messageTreeTablePanel.setMessage(m);
  }

  public void setColumnWidth(int id, int size) {
    messageTreeTablePanel.setColumnWidth(id, size);
  }

  public void setMessage(Message m, String[] columns, String[] exclusions) {
    messageTreeTablePanel.setMessage(m, columns, exclusions);
  }

  public Message getSelectedMessage() {
    return messageTreeTablePanel.getSelectedMessage();
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

  private void jbInit() throws Exception {
    messageTreeTablePanel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        messageTreeTablePanel_actionPerformed(e);
      }
    });
    this.getContentPane().add(messageTreeTablePanel, BorderLayout.CENTER);
  }

  void messageTreeTablePanel_actionPerformed(ActionEvent e) {
    commit();
    setVisible(false);
  }
}