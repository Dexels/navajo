package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;

import com.dexels.navajo.tipi.swingclient.components.treetable.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public interface TablePrintInterface {
  public void setTitle(String t);
  public void setSubTitle(String t);
  public void printTable(JTable t);
  public void printTreeTable(MessageTreeTableModel model);
}
