package com.dexels.navajo.echoclient.components;

import echopoint.SortableTable;
import com.dexels.navajo.document.*;
import nextapp.echo.*;
import nextapp.echo.table.*;
import java.util.*;
import nextapp.echo.event.*;
import echopoint.*;

public class MessageTable extends SelectableTable {
  private MessageTableModel myModel = null;
  private TableCellRenderer myRenderer = new EchoPropertyComponent();

  private ArrayList ids = new ArrayList();
  private ArrayList names = new ArrayList();
  private ArrayList editables = new ArrayList();

  public void setMessage(Message m) {
    setAutoCreateColumnsFromModel(false);
    myModel = new MessageTableModel(m);
//    System.err.println("message size: "+m.getArraySize());
//    System.err.println("modelrows: "+myModel.getRowCount());
//    System.err.println("columnrows: "+myModel.getColumnCount());

    for (int i = 0; i < ids.size(); i++) {
      myModel.addColumn((String)ids.get(i),(String)names.get(i),((Boolean)editables.get(i)).booleanValue());
    }
    super.setModel(myModel);
    createDefaultColumnsFromModel();
    setDefaultRenderer(Property.class,myRenderer);
//    debugTableModel();
  }

  public MessageTableModel getMessageTableModel() {
    return (MessageTableModel)getModel();
  }

  public void debugTableModel() {
    System.err.println("RowCount: "+myModel.getRowCount());
    System.err.println("ColumnCount: "+myModel.getColumnCount());
    for (int i = 0; i < myModel.getRowCount(); i++) {
      for (int j = 0; j < myModel.getColumnCount(); j++) {
        System.err.println("ROW: "+i+" COLUMN: "+j);
        Object value = myModel.getValueAt(i,j);
        if (value!=null) {
          System.err.print("Value class: "+value.getClass());
          System.err.println("Value: "+value.toString());
        }
      }
    }

  }

  public void addActionListener(ActionListener al) {
    System.err.println("Not yet implemented");
  }

  public void addColumn(String id, String title, boolean editable) {
//    System.err.println("addColumn: start");
//    System.err.println("ID: "+id);
//    System.err.println("Title: "+title+" editable: "+editable);
//
    ids.add(id);
    names.add(title);
    editables.add(new Boolean(editable));
//    System.err.println("addCOlumn: finished");
  }

  public void removeColumn(String id) {
    getMessageTableModel().removeColumn(id);
    /** @todo Implement. Or restructure class. */
  }


}
