package com.dexels.navajo.tipi.treetable;
import com.dexels.navajo.nanodocument.*;
import java.util.*;

import javax.swing.tree.*;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import com.dexels.navajo.swingclient.components.*;

public class MessageTreeTable extends JTreeTable {

//  private MessageTreeTableModel myModel = null;
  private ArrayList actionListeners = new ArrayList();


  public MessageTreeTable() {
    super(new MessageTreeTableModel());
//    super(new FilteredMessageTreeTableModel());
    super.setDefaultEditor(Property.class,new PropertyCellEditor());
    super.setDefaultRenderer(Property.class, new PropertyCellRenderer());
//    myModel = (MessageTreeTableModel)getModel();
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent m) {
        if (m.getClickCount()>1) {
          fireActionEvent();
        }
      }
    });
  }

  public void setMessage(Message m, String[] columns) {
//    setModel(new MessageTreeTableModel(m,columns));
//    getMessageModel().reset(m,columns);
//      setModel(getMessageModel());
    getMessageModel().reset(m,columns);
// removed 4 tipi
//    expandAll();
  }

  public void addColumn(String id, String label, boolean editable) {
    MessageTreeTableModel mttm = getMessageModel();
    mttm.addPropertyColumn(id);
  }

  public void setMessage(Message m, String[] columns, String[] exclusions) {
//    setModel(new MessageTreeTableModel(m,columns));
    getMessageModel().reset(m,columns,exclusions);
    for (int i = 0; i < exclusions.length; i++) {
      getMessageModel().addExclusion(exclusions[i]);
    }
    expandAll();

//      setModel(getMessageModel());E
  }

  public Message getRootMessage() {
    return (Message)getMessageModel().getRoot();
  }

  public void setHeaderTextResource(ResourceBundle res) {
    getMessageModel().setHeaderTextResource(res);
  }

  public void setRootVisible(boolean b) {
    getTree().setRootVisible(b);
  }

  public void setColumnWidth(int index, int width) {
    getColumnModel().getColumn(index).setPreferredWidth(width);
  }

  public void reset() {
    getMessageModel().reset();
  }

  public MessageTreeTableModel getMessageModel() {
    return (MessageTreeTableModel)(((TreeTableModelAdapter)getModel()).getTreeTableModel());
  }

  public void delayedFireTableStructureChanged() {
    ((TreeTableModelAdapter)getModel()).delayedFireTableStructureChanged();
  }

  public Message getSelectedMessage() {
    TreePath tp = getTree().getSelectionPath();
    if (tp!=null) {
      Object o = tp.getLastPathComponent();
      if (o==null) {
        return null;
      }
      if (Message.class.isInstance(o)) {
        return (Message)o;
      }
    }
    return null;
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
    if (m==null) {
      return;
    }

    for (int i = 0; i < actionListeners.size(); i++) {
      ActionListener current = (ActionListener)actionListeners.get(i);
      current.actionPerformed(new ActionEvent(this,r,m.getPath()));
    }
  }

  public void setModel(TreeTableModel treeTableModel) {
    ((MessageTreeTableModel)treeTableModel).setTreeTable(this);
    super.setModel(treeTableModel);
  }


}