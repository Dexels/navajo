package com.dexels.navajo.tipi.components.swingimpl.questioneditor;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;
import java.util.*;
import com.dexels.navajo.document.*;
import javax.swing.event.*;
import com.dexels.navajo.document.nanoimpl.*;

//import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class PropertyTableModel
    implements TableModel {
  private final ArrayList myListeners = new ArrayList();
//  private XMLElement myNode = null;
  private Property myProperty = null;
  public PropertyTableModel() {
  }

  public void load(Property p) {
    myProperty = p;
    for (int i = 0; i < myListeners.size(); i++) {
      TableModelListener current = (TableModelListener) myListeners.get(i);
      current.tableChanged(new TableModelEvent(this));
    }
  }

  public int getColumnCount() {
    return 3;
  }

  public int getRowCount() {
    try {
      return myProperty == null ? 0 : myProperty.getAllSelections().size();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public void clear() {
    myProperty = null;
    for (int i = 0; i < myListeners.size(); i++) {
      TableModelListener current = (TableModelListener) myListeners.get(i);
      current.tableChanged(new TableModelEvent(this));
    }
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  public Class getColumnClass(int columnIndex) {
    if (columnIndex == 0) {
      return Boolean.class;
    }
    else {
      return String.class;
    }
  }

  public Selection getSelection(int rowIndex) {
    if (myProperty == null) {
      return null;
    }
    ArrayList al;
    try {
      al = myProperty.getAllSelections();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
      return null;
    }
    if (rowIndex > al.size()) {
      return null;
    }
    Selection s = (Selection) al.get(rowIndex);
    return s;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    Selection s = getSelection(rowIndex);
//    XMLElement myOption =  (XMLElement)myNode.getChildren().get(rowIndex);
    if (s == null) {
      if (columnIndex == 0) {
        return new Boolean(false);
      }
      return "error";
    }
    switch (columnIndex) {
      case 0:
        return new Boolean(s.isSelected());
      case 1:
        return s.getName();
      case 2:
        return s.getValue();
    }
    return "";
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if (myProperty == null) {
      return;
    }
    ArrayList al;
    try {
      al = myProperty.getAllSelections();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
      return;
    }
    if (rowIndex > al.size()) {
      return;
    }
    Selection s = (Selection) al.get(rowIndex);
    switch (columnIndex) {
      case 0:
        s.setSelected( ( (Boolean) aValue).booleanValue());
        break;
      case 1:
        s.setName("" + aValue);
        break;
      case 2:
        s.setValue("" + aValue);
        break;
    }
    for (int i = 0; i < myListeners.size(); i++) {
      TableModelListener current = (TableModelListener) myListeners.get(i);
      current.tableChanged(new TableModelEvent(this));
    }
  }

  public String getColumnName(int columnIndex) {
    switch (columnIndex) {
      case 0:
        return "Selected:";
      case 1:
        return "Name:";
      case 2:
        return "Value:";
    }
    return "";
  }

  public void addTableModelListener(TableModelListener l) {
    myListeners.add(l);
  }

  public void removeTableModelListener(TableModelListener l) {
    myListeners.remove(l);
  }
}
