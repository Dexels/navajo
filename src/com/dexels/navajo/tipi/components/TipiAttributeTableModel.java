package com.dexels.navajo.tipi.components;

import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import com.dexels.navajo.tipi.tipixml.*;
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
  private ArrayList myListeners = new ArrayList();
  private XMLElement myClassDef;

  public TipiAttributeTableModel() {
  }
  public int getRowCount() {
    return myAttributes.size();
  }
  public int getColumnCount() {
    return 3;
  }

  public void setAttributes(Map m, XMLElement classDef){
    myAttributes.clear();
    myDataMap.clear();
    myTipiValueMap.clear();
    myClassDef = classDef;
    myTipiValueMap = m;
    Iterator it = m.keySet().iterator();
    while(it.hasNext()){
      myAttributes.add((String)it.next());
    }
    super.fireTableStructureChanged();
  }

  public String getColumnName(int col) {
    if(col == 0){
      return "Override";
    }
    if(col == 1){
      return "Attribute";
    }
    if(col == 2){
      return "Value";
    }
    return "Mmm..?";
  }

  public Class getColumnClass(int col) {
    return Object.class;
  }
  public boolean isCellEditable(int row, int col) {
    if(col == 2 || col == 0){
      return true;
    }else{
      return false;
    }
  }
  public Object getValueAt(int row, int col) {
    if(col == 0){
      return new Boolean(false);
    }
    if(col == 1){
      return myAttributes.get(row);
    }
    if(col == 2){
      return myTipiValueMap.get(myAttributes.get(row));
    }
    System.err.println("!!!!!!!!! column out of range: " + col);
    return null;
  }

  public void setValueAt(Object value, int row, int col) {
    if(col ==2){
      System.err.println("Setting..");
       //System.err.println("Setting value: " + row + ", " +col + " value: " + value.toString());
      //myDataMap.put((String) myAttributes.get(row), value);
    }
  }

  public Map getValues(){
    return myDataMap;
  }

  public void addTableModelListener(TableModelListener parm1) {
    myListeners.add(parm1);
  }
  public void removeTableModelListener(TableModelListener parm1) {
    myListeners.remove(parm1);
  }

}