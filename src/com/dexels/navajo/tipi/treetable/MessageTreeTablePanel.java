package com.dexels.navajo.tipi.treetable;

import com.dexels.navajo.swingclient.components.BasePanel;
import com.dexels.navajo.document.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import com.dexels.navajo.swingclient.components.Ghostable;

public class MessageTreeTablePanel extends BasePanel implements Ghostable {
  private MessageTreeTable messageTable = new MessageTreeTable();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  private ArrayList columnIds = new ArrayList();
  private ArrayList columnLabels = new ArrayList();
  private ArrayList columnEnabled = new ArrayList();
  private boolean ghosted = false;
  private boolean enabled = true;

  public MessageTreeTablePanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setMessage(Message m) {
    setMessage(m,new String[]{});
    for (int i = 0; i < columnIds.size(); i++) {
      messageTable.addColumn((String)columnIds.get(i),(String)columnLabels.get(i),((Boolean)columnEnabled.get(i)).booleanValue());
    }

  }

  public void setModel(TreeTableModel tta) {
    messageTable.setModel(tta);
  }

  public Message getMessage() {
    return (Message)messageTable.getMessageModel().getRoot();
  }

//  public void setSelectedMessage(Message m) {
//    messageTable.setSelectedMessage(m);
//  }

  public void addColumn(String id, String label, boolean editable) {
    messageTable.addColumn(id,label,editable);
    columnIds.add(id);
    columnLabels.add(label);
    columnEnabled.add(new Boolean(editable));
  }

  public void setHeaderTextResource(ResourceBundle r) {
    messageTable.setHeaderTextResource(r);
  }
  public void setColumnWidth(int id, int size) {
    messageTable.setColumnWidth(id,size);
  }

  public void setMessage(Message m, String[] columns, String[] exclustion) {
    messageTable.setMessage(m,columns,exclustion);
   MessageTreeTableModel mttm = messageTable.getMessageModel();
    if (LazyMessage.class.isInstance(m)) {
      LazyMessage lm = (LazyMessage)m;
      lm.addMessageListener(mttm);
      lm.startUpdateThread();
    }
  }

  public void setMessage(Message m, String[] columns) {
    // todo: Remove listeners from previous message?
    messageTable.setMessage(m,columns);
    MessageTreeTableModel mttm = messageTable.getMessageModel();
    if (LazyMessage.class.isInstance(m)) {
      LazyMessage lm = (LazyMessage)m;
      lm.addMessageListener(mttm);
      lm.startUpdateThread();
    }
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(jScrollPane1,  BorderLayout.CENTER);
    jScrollPane1.getViewport().add(messageTable, null);
  }

  public void addChangeListener(ListSelectionListener l) {
    messageTable.addListSelectionListener(l);
  }

  public void reset() {
    messageTable.reset();
  }

  public Message getSelectedMessage() {
    return messageTable.getSelectedMessage();
  }

  public void addActionListener(ActionListener e) {
    messageTable.addActionListener(e);
  }

  public void removeActionListener(ActionListener e) {
    messageTable.removeActionListener(e);
  }

  public boolean isGhosted() {
    return ghosted;
  }

  public void setGhosted(boolean g) {
    ghosted = g;
    messageTable.setEnabled(enabled && (!ghosted));
    super.setEnabled(enabled && (!ghosted));
  }

  public void setEnabled(boolean e) {
    enabled = e;
    messageTable.setEnabled(enabled && (!ghosted));
    super.setEnabled(enabled && (!ghosted));
  }
  }