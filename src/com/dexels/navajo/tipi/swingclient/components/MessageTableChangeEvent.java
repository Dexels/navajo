package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class MessageTableChangeEvent extends ChangeEvent {

  private int myRow = -1;
  private int myColumn = -1;

  public MessageTableChangeEvent(Object o) {
    super(o);
  }

  public MessageTableChangeEvent(Object o, int row, int column) {
    super(o);
    myRow = row;
    myColumn = column;
  }

  public final int getColumn(){
    return myColumn;
  }

  public final int getRow(){
    return myRow;
  }
}