package com.dexels.navajo.tipi.swingclient.components.treetable;

import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.*;
import com.dexels.navajo.tipi.swingclient.components.sort.*;

import java.awt.*;

public class MessageTreeTable extends JTreeTable {

  private ArrayList actionListeners = new ArrayList();

  public MessageTreeTable() {
    super(new MessageTreeTableModel());
    setAutoCreateColumnsFromModel(false);
    super.setDefaultEditor(Property.class, new PropertyCellEditor(null));
    super.setDefaultRenderer(Property.class, new PropertyCellRenderer());
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent m) {
        if (m.getClickCount() > 1) {
          fireActionEvent();
        }
      }
    });
  }


  public void setMessage(Message m, String[] columns) {
    expandAll();
  }

  public void addColumn(String id, String label, boolean editable) {
    MessageTreeTableModel mttm = getMessageModel();
    mttm.addPropertyColumn(id);
  }

  public void setMessage(Message m, String[] columns, String[] exclusions) {
    getMessageModel().reset(m, columns, exclusions);
    for (int i = 0; i < exclusions.length; i++) {
      getMessageModel().addExclusion(exclusions[i]);
    }
    createDefaultColumnsFromModel();
    getMessageModel().fireTreeStructureChanged();
    expandAll();

  }

  private final Map columnWidthMap = new HashMap();

  public void setColumnWidth(final int index, final int width) {
    if (index >= getColumnModel().getColumnCount()) {
      return;
    }
    columnWidthMap.put(new Integer(index),new Integer(width));
    getColumnModel().getColumn(index).setPreferredWidth(width);
  }

  public void createDefaultColumnsFromModel() {
      TableModel m = getModel();
      if (m != null) {
          // Remove any current columns
          TableColumnModel cm = getColumnModel();
          while (cm.getColumnCount() > 0) {
              cm.removeColumn(cm.getColumn(0));
          }

          // Create new columns from the data model info
          for (int i = 0; i < m.getColumnCount(); i++) {
              TableColumn newColumn = new TableColumn(i);
              // Need this check. It can be called before this class has been constructed
              if (columnWidthMap!=null) {
                Integer width = (Integer)columnWidthMap.get(new Integer(i));
                if (width!=null) {
                  newColumn.setPreferredWidth(width.intValue());
                }
              }
              addColumn(newColumn);
          }
      }
  }


  public Message getRootMessage() {
    return (Message) getMessageModel().getRoot();
  }

  public void setHeaderTextResource(ResourceBundle res) {
    getMessageModel().setHeaderTextResource(res);
  }

  public void setRootVisible(boolean b) {
    getTree().setRootVisible(b);
  }


  public void reset() {
    getMessageModel().reset();
  }

  public MessageTreeTableModel getMessageModel() {
    return (MessageTreeTableModel) ( ( (TreeTableModelAdapter) getModel()).getTreeTableModel());
  }

  public void delayedFireTableStructureChanged() {
     ( (TreeTableModelAdapter) getModel()).fireTableStructureChanged();
  }

  public Message getSelectedMessage() {
    TreePath tp = getTree().getPathForRow(getSelectedRow());
    if (tp != null) {
      Object o = tp.getLastPathComponent();
      if (o == null) {
        return null;
      }
      if (Message.class.isInstance(o)) {

        return (Message) o;
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
    if (m == null) {
      return;
    }
    for (int i = 0; i < actionListeners.size(); i++) {
      ActionListener current = (ActionListener) actionListeners.get(i);
      current.actionPerformed(new ActionEvent(this, r, m.getFullMessageName()));
    }
  }

  public void setModel(TreeTableModel treeTableModel) {
     ( (MessageTreeTableModel) treeTableModel).setTreeTable(this); super.setModel(treeTableModel);
  }

}
