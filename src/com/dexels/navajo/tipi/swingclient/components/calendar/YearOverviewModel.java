package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.util.*;

import javax.swing.event.*;
import javax.swing.table.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class YearOverviewModel
    implements TableModel {

  private ArrayList myListeners = new ArrayList();

  public YearOverviewModel() {
  }

  public int getRowCount() {
    return 3;
  }

  public int getColumnCount() {
    return 4;
  }

  public String getColumnName(int parm1) {
    return "";
  }

  public Class getColumnClass(int parm1) {
    return Object.class;
  }

  public boolean isCellEditable(int parm1, int parm2) {
    return true;
  }

  public Object getValueAt(int parm1, int parm2) {
    return "TEST";
  }

  public void setValueAt(Object parm1, int parm2, int parm3) {
    /**@todo Implement this javax.swing.table.TableModel method*/
    // NOP
  }

  public void addTableModelListener(TableModelListener parm1) {
    myListeners.add(parm1);
  }

  public void removeTableModelListener(TableModelListener parm1) {
    myListeners.remove(parm1);
  }

}