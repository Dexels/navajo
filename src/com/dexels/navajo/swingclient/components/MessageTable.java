package com.dexels.navajo.swingclient.components;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import com.dexels.navajo.document.*;
import java.util.*;
import javax.swing.event.*;
import com.dexels.navajo.swingclient.components.lazy.MessageListener;

import com.dexels.navajo.swingclient.components.sort.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class MessageTable extends JTable implements MessageListener, TableModelListener, CellEditorListener, Ghostable {

  private PropertyCellEditor myEditor = new PropertyCellEditor();
  private PropertyCellRenderer myRenderer = new PropertyCellRenderer();
  private Message myMessage = null;
  private MessageTableModel myModel = null;
  private MouseAdapter headerMouseAdapter = null;
  private ArrayList actionListeners = new ArrayList();

//  private boolean[] changed = null;

  private boolean enabled;
  private boolean ghosted;

  public MessageTable() {
    myModel = new MessageTableModel();
    TableSorter ts = new TableSorter(myModel);
//    ts.addTableModelListener(this);
    setModel(ts);
    headerMouseAdapter = ts.addMouseListenerToHeaderInTable(this);
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent m) {
        if (m.getClickCount()>1) {
          fireActionEvent();
        }
      }
    });
  }

//  public TableModel getModel() {
//    if (myModel!=null) {
//      return myModel;
//    } else {
//      return super.getModel();
//    }
//  }

  public MessageTableModel getMessageModel() {
    return myModel;
  }

  public void setMessage(Message m) {
    MessageTableModel mtm = getMessageModel();
    mtm.setMessage(m);
    myMessage = m;
    //changed = new boolean[mtm.getRowCount()];
    setDefaultEditor(Property.class,myEditor);
    setDefaultRenderer(Property.class,myRenderer);
//    setAutoCreateColumnsFromModel(false);
    TableColumnModel tcm  =  getTableHeader().getColumnModel();
//    int count = tcm.getColumnCount();
//    for (int i = count-1; i > 0; i--) {
//      tcm.removeColumn(tcm.getColumn(i));
//    }
//    setAutoCreateColumnsFromModel(true);
    for (int i = 0; i < tcm.getColumnCount(); i++) {
      TableColumn tc = tcm.getColumn(i);
      tc.setHeaderRenderer(new CustomTableHeaderRenderer());
    }
//    tableChanged(new TableModelEvent(getModel()));
  }

  public void addColumn(String id, String title, boolean editable) {
    MessageTableModel mtm = getMessageModel();
    mtm.addColumn(id,title,editable);
  }

  public void addListSelectionListener(ListSelectionListener l){
    getSelectionModel().addListSelectionListener(l);
  }

  public void addCellEditorListener(CellEditorListener ce) {
    myEditor.addCellEditorListener(ce);
  }
  public void removeCellEditorListener(CellEditorListener ce) {
    myEditor.removeCellEditorListener(ce);
  }

  public Message getMessageRow(int row) {
    MessageTableModel mtm = getMessageModel();

    return mtm.getMessageRow(mapRowNumber(row));
  }

  public Message getSelectedMessage() {
    int s = getSelectedRow();
    if (s>=0) {
      return getMessageRow(s);
    }
    return null;
  }

  public int mapRowNumber(int row) {
    if (TableSorter.class.isInstance(getModel())) {
      TableSorter ts = (TableSorter)getModel();
      return ts.getRowIndex(row);
    }
    return row;
  }
  public void rebuildSort() {
    if (TableSorter.class.isInstance(getModel())) {
      TableSorter ts = (TableSorter)getModel();
      ts.checkModel();
     }
  }

  public ArrayList getSelectedMessages(){
    int rows = getRowCount();
    ArrayList selectedMsgs = new ArrayList();
    for(int i=0;i<rows;i++){
      if(isRowSelected(i)){
        Message selected = getMessageRow(i);
        selectedMsgs.add(selected);
      }
    }
    if(selectedMsgs.size() < 1){
      return null;
    }
    return selectedMsgs;
  }

  public void messageLoaded(int startIndex, int endIndex) {
    MessageTableModel mtm = getMessageModel();
    mtm.messageLoaded(startIndex,endIndex);
  }

  public Message getMessage(){
    return myMessage;
  }
  public void tableChanged(TableModelEvent parm1) {
    /**@todo: Override this javax.swing.JTable method*/
//    System.err.println("TABLE CHANGED!");
    super.tableChanged( parm1);
  }

  public void addActionListener(ActionListener e) {
    actionListeners.add(e);
  }

  public void removeActionListener(ActionListener e) {
    actionListeners.remove(e);
  }

  protected void fireActionEvent() {
    int r = getSelectedRow();
    Message m = getSelectedMessage();

    for (int i = 0; i < actionListeners.size(); i++) {
      ActionListener current = (ActionListener)actionListeners.get(i);
      current.actionPerformed(new ActionEvent(this,r,m.getFullMessageName()));
    }
  }

  public void editingCanceled(ChangeEvent parm1) {
    //super.editingCanceled( parm1);
  }

  public void editingStopped(ChangeEvent parm1) {
    //super.editingStopped( parm1);
    System.err.println("ROW UPDATED: "+getSelectedRow());
//    changed[getSelectedRow()] = true;
  }

//  public ArrayList getAllUpdated() {
//    ArrayList al = new ArrayList();
//    for (int i = 0; i < getRowCount(); i++) {
//      if (changed[i]) {
//        Message m = getMessageRow(i);
//        al.add(m);
//      }
//    }
//    return al;
//  }


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

}
