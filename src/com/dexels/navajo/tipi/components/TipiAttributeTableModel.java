package com.dexels.navajo.tipi.components;

import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiAttributeTableModel extends AbstractTableModel {
  private Map myTipiValueMap = new HashMap();
  private Map myDataMap = new HashMap();
  private ArrayList myAttributes = new ArrayList();
//  private ArrayList myListeners = new ArrayList();
  private XMLElement myClassDef;

  public TipiAttributeTableModel() {
  }
  public int getRowCount() {
    int t = myAttributes.size();
    return t;
  }
  public int getColumnCount() {
    return 2;
  }

  public void setAttributes(TipiComponent tc){
    myAttributes.clear();
    myDataMap.clear();
    myTipiValueMap.clear();
//    myClassDef = tc. classDef;
     Map e = tc.getClassDefValues();
    Iterator it = e.keySet().iterator();
    while (it.hasNext()) {
      String name = (String)it.next();
      System.err.println("Setting: "+name);
      TipiValue tv = (TipiValue)e.get(name);
      myAttributes.add(name);
      myTipiValueMap.put(name,tv);
    }

//    super.fireTableStructureChanged();
  }

  public String getColumnName(int col) {
    if(col == 0){
      return "Attribute";
    }
    if(col == 1){
      return "Value";
    }
    return "Mmm..?";
  }

  public Class getColumnClass(int col) {
    return Object.class;
  }
  public boolean isCellEditable(int row, int col) {
    if(col == 1){
      return true;
    }else{
      return false;
    }
  }

  public Object getValueAt(int row, int col) {
    if(col == 0){
      return myAttributes.get(row);
    }
    if(col == 1){
      return myTipiValueMap.get(myAttributes.get(row));
    }
    System.err.println("!!!!!!!!! column out of range: " + col);
    return null;
  }

  public void setValueAt(Object value, int row, int col) {
    if(col == 1){
      System.err.println("SetValueAt");
      if(value != null){
        System.err.println("Setting value: " + row + ", " +col + " value: " + value.toString());
        System.err.println("For attribute: " + myAttributes.get(row));
      }
    }
  }

  public Map getValues(){
    return myTipiValueMap;
  }

//  public void addTableModelListener(TableModelListener parm1) {
//    myListeners.add(parm1);
//  }
//  public void removeTableModelListener(TableModelListener parm1) {
//    myListeners.remove(parm1);
//  }

}