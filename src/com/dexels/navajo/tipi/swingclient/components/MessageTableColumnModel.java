package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.table.*;
import javax.swing.event.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.sort.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public final class MessageTableColumnModel
    extends DefaultTableColumnModel {
  private Message myMessage;
  private int maxadvance;

  public MessageTableColumnModel() {
    CustomTableHeaderRenderer myRenderer = new CustomTableHeaderRenderer();
    maxadvance = myRenderer.getFontMetrics(myRenderer.getFont()).charWidth('M');
//    addColumnModelListener(new TableColumnModelListener() {
//      public void columnAdded(TableColumnModelEvent e){}
//      public void columnMarginChanged(ChangeEvent e){
//        System.err.println("Source: " + e.getSource());
//
//        System.err.println("COlumn size changed!!!!!!!!!!!!!!!!!!!!___________________________________________________>>");
//      }
//      public void columnMoved(TableColumnModelEvent e){}
//      public void columnRemoved(TableColumnModelEvent e){}
//      public void columnSelectionChanged(ListSelectionEvent e){}
//    });
  }

  public final void dumpColumnWidths() {
    for (int i = 0; i < getColumnCount(); i++) {
      TableColumn tc = getColumn(i);
//      System.err.println("Identifier: " + tc.getIdentifier() + " size: " + tc.getWidth());
    }

  }

  @Override
protected final void fireColumnMoved(TableColumnModelEvent e) {
    /**@todo Override this javax.swing.table.DefaultTableColumnModel method*/
    super.fireColumnMoved(e);
//    dumpColumnWidths();
  }


  @Override
public final void setColumnMargin(int newMargin) {
    super.setColumnMargin(newMargin);
    /**@todo Implement this javax.swing.table.TableColumnModel abstract method*/
  }

  public final void removeColumns() {
//    super.remo
    for (int i = getColumnCount() - 1; i >= 0; i--) {
//      System.err.println("Removing column: "+i);
      removeColumn(getColumn(i));
    }
  }

  public final void setMessage(Message m, MessageTableModel mtm) {
 //    removeColumns();
    if (m==null) {
      createFromModel(mtm);
      return;
    }
    if (m.getArraySize()==0) {
      createFromModel(mtm);
      return;
    }

    Message def = m.getDefinitionMessage();
    if (def!=null) {
      for (int i = 0; i < mtm.getColumnCount(); i++) {
        createColumn(i,mtm,def);
      }
    } else {
    Message first = m.getMessage(0);
    for (int i = 0; i < mtm.getColumnCount(); i++) {
      createColumn(i,mtm,first);
    }
    }
  }

  private final void createFromModel(MessageTableModel mtm) {
    for (int i = 0; i < mtm.getColumnCount(); i++) {
      createColumn(i,mtm,null);
    }
  }

  @Override
public TableColumn getColumn(int columnIndex) {
    if (getColumnCount()==0) {
      return null;
    }
    if (columnIndex < super.getColumnCount()) {
      return super.getColumn(columnIndex);
    } else {
      return null;
    }
   }

   public void loadSizes(Map m) {
     for (Iterator iter = m.entrySet().iterator(); iter.hasNext(); ) {

       Entry e = (Entry) iter.next();
       Integer size = (Integer) e.getValue();
       Integer index = (Integer) e.getKey();
       
       
 
       if (size!=null) {
         TableColumn tc = getColumn(index.intValue());
         if (tc!=null) {
         tc.setPreferredWidth(size.intValue());
           tc.setWidth(size.intValue());
             }
       }
       
     }
   }

  public final void createColumn(int index, MessageTableModel mtm, Message m) {
    String cId = mtm.getColumnId(index);
    int width = 0;
    TableColumn tc;
    if (m == null) {
      tc = new TableColumn(index);
    }
    else {
      Property p = m.getProperty(cId);
      if (p == null) {
        tc = new TableColumn(index);
      }
      else {
        width = p.getLength() * maxadvance;
        //System.err.println("Creating column [" + index + "]: " + width);
        tc = new TableColumn(index, width);
        tc.setHeaderValue(p.getDescription());
        tc.setPreferredWidth(width);
        tc.setMinWidth(10);
        tc.setWidth(width);
      }
    }
    addColumn(tc);
  }

  @Override
public void valueChanged(ListSelectionEvent e) {
    //System.err.println("Column selection changed!");
       super.valueChanged(e);
   }

}
