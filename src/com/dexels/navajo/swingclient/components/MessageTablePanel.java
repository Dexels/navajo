package com.dexels.navajo.swingclient.components;
import com.dexels.navajo.document.*;
import java.util.*;
import javax.swing.*;

import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.document.lazy.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class MessageTablePanel extends BasePanel implements MessageListener, Ghostable {

  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  MessageTable messageTable = null;
  private boolean ghosted = false;
  private boolean enabled = true;

  public MessageTablePanel() {
    super();
    messageTable = new MessageTable();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    messageTable.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        messageTable_valueChanged(e);
      }
    });
    this.add(jScrollPane1,  BorderLayout.CENTER);
    jScrollPane1.getViewport().add(messageTable, null);
  }

  public void setSelectionMode(int i) {
    messageTable.setSelectionMode(i);
  }

  public void clearTable() {
    /** @todo Implement? Or just leave it? */
    setMessage( NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(),"no_message"));
   }

  public void rebuildSort() {
    messageTable.rebuildSort();
  }


  public void setMessage(Message m) {
    System.err.println("Set message called");
    messageTable.setMessage(m);

  }

  public Message getMessage(){
    return messageTable.getMessage();
  }

  /**
   * @deprecated
   */

  public void addColumn(String id, String title, Class c, boolean editable) {
    addColumn(id,title,editable);
  }

  public void addSubMessage(Message msg){
    getMessage().addMessage(msg);
//    System.err.println("New Message: " + getMessage().toXml(null).toString());
    MessageTableModel mtm = messageTable.getMessageModel();
    mtm.fireTableRowsInserted(messageTable.getRowCount()-1,messageTable.getRowCount()-1);
    rebuildSort();
  }

  public void addColumn(String id, String title, boolean editable) {
    messageTable.addColumn(id,title,editable);
  }

  /**
   * @deprecated
   */

  public void loadSelectedRowInMap(Map m) {
    Message msg = getSelectedMessage();
    if (msg==null) {
      return;
    }
    ArrayList al = msg.getAllProperties();
    for (int i = 0; i < al.size(); i++) {
      Property p = (Property)al.get(i);
      m.put(p.getName(),p.getValue());
    }
  }


  public void addListSelectionListener(ListSelectionListener l){
    messageTable.addListSelectionListener(l);
  }

  public void addCellEditorListener(CellEditorListener ce) {
    messageTable.addCellEditorListener(ce);
  }

  public void removeCellEditorListener(CellEditorListener ce) {
    messageTable.removeCellEditorListener(ce);
  }

  public Message getMessageRow(int row) {
    return messageTable.getMessageRow(row);
  }

  public Message getSelectedMessage() {
    return messageTable.getSelectedMessage();
  }

  public ArrayList getSelectedMessages(){
    return messageTable.getSelectedMessages();
  }
  public void messageLoaded(int startIndex, int endIndex) {
    messageTable.messageLoaded(startIndex,endIndex);
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
    super.setEnabled(enabled && (!ghosted));
  }

  public void setEnabled(boolean e) {
    enabled = e;
    super.setEnabled(enabled && (!ghosted));
  }

  public void rowSelected(ListSelectionEvent e) {
  }

  void messageTable_valueChanged(ListSelectionEvent e) {
    rowSelected(e);
  }

  public void fireDataChanged(){
    messageTable.getMessageModel().fireDataChanged();
  }

  public void clearSelection() {
    messageTable.clearSelection();
  }

  public void setSelectedMessage(Message m) {
    for (int i = 0; i < messageTable.getRowCount(); i++) {
      Message msg = messageTable.getMessageRow(i);
      if (msg == m) {
        setSelectedRow(i);
      }
    }

  }

  public void setRowSelectionInterval(int start, int end) {
    messageTable.setRowSelectionInterval(start,end);
  }

  public void setSelectedRow(int row) {
    setRowSelectionInterval(row,row);
  }
}
