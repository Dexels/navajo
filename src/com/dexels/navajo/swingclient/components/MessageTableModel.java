package com.dexels.navajo.swingclient.components;

import javax.swing.table.*;
import com.dexels.navajo.document.*;
import java.util.*;
import com.dexels.navajo.document.lazy.MessageListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class MessageTableModel extends AbstractTableModel implements MessageListener {

  private ArrayList myColumnIds = new ArrayList();
  private ArrayList myColumnTitles = new ArrayList();
  private ArrayList editableList = new ArrayList();
  private Message myMessage;

  public MessageTableModel() {
  }

  public MessageTableModel(Message m) {
    setMessage(m);
  }

  public void setMessage(Message m) {
    myMessage = m;


//  Not implemented for tipi right now, do NOT remove
//    if (LazyMessage.class.isInstance(m)) {
//      LazyMessage lm = (LazyMessage)m;
//      lm.addMessageListener(this);
//      lm.startUpdateThread();
//    }





//    ArrayList msgs = m.getAllMessages();
//    if (msgs.size()==0) {
//      System.err.println("Cannot initialize default columns for MessageTableModel: No child messages");
//    } else {
//      initDefaultColumns((Message)msgs.get(0));
//    }
    messageChanged();
  }


  public void addColumn(String id, String title, boolean editable) {
    myColumnIds.add(id);
    myColumnTitles.add(title);
    editableList.add(new Boolean(editable));
    messageChanged();
  }

    public void removeColumn(String id) {
      int index = myColumnIds.indexOf(id);
      myColumnIds.remove(index);
      myColumnTitles.remove(index);
    }

//  public void initDefaultColumns(Message m) {
//    myColumnIds = new ArrayList();
//    ArrayList props = m.getAllProperties();
//    for (int i = 0; i < props.size(); i++) {
//      Property current = (Property)props.get(i);
//      myColumnIds.add(current.getName());
//    }
//  }

  public void messageChanged() {
    super.fireTableStructureChanged();
  }

  public int getColumnCount() {
//    if (myMessage==null) {
//      return 0;
//    }
    return myColumnIds.size();
  }

  public Object getValueAt(int row, int column) {
    if (myMessage==null) {
      return null;
    }

    Message m = myMessage.getMessage(row);
    if (m!=null) {
      String columnName = (String)myColumnIds.get(column);
      if (columnName==null) {
        System.err.println("Unknown column # "+column);
        return null;
      }
      return  m.getProperty(columnName);
    } else {
//      System.err.println("Unknown row # "+row);
      return null;
    }

  }
  public int getRowCount() {
    if (myMessage==null) {
      return 0;
    }
    try {
      return myMessage.getArraySize();
    }
    catch (NavajoException ex) {
      return 0;
    }
  }
  public String getColumnName(int column) {
//    /**@todo: Override this javax.swing.table.AbstractTableModel method*/
//    return super.getColumnName( column);
    String s = (String)myColumnTitles.get(column);
    if (s==null) {
      return super.getColumnName(column);
    } else {
      return s;
    }

  }

  public void setValueAt(Object aValue, int row, int column) {
//    System.err.println("Is this a good idea? Don't really know.");
//    Property p = (Property)getValueAt(row,column);
//    if (p==aValue) {
//      System.err.println("Ok, it is the same");
//    }
//    Message m = p.getParent();
//    Property p2 = p.copy(new Navajo());
//    m.addProperty(p2);
//    fireTableDataChanged();

    //    if (p!=null) {
//      p.setValue(aValue);
//    }
  }

  public Message getMessageRow(int row) {
    if (myMessage==null) {
      return null;
    }
    return myMessage.getMessage(row);
  }

  public Class getColumnClass(int columnIndex) {
    return Property.class;
  }
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    Boolean b = (Boolean)editableList.get(columnIndex);
    if (b==null) {
      return true;
    }
    return b.booleanValue();
  }
  public void messageLoaded(int startIndex, int endIndex) {
      fireTableRowsUpdated(startIndex,endIndex);
  }

  public void fireDataChanged(){
    fireTableStructureChanged();
  }

}
