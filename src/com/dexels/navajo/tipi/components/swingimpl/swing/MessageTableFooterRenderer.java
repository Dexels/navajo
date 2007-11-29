package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import com.dexels.navajo.tipi.*;
import java.util.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.*;
import javax.swing.event.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */


public class MessageTableFooterRenderer
   extends JLabel implements TableCellRenderer {
 private int columnIndex;
 private final TipiDataComponent myComponent;

 private GenericPropertyComponent myPropComponent = new GenericPropertyComponent();

 public MessageTableFooterRenderer(TipiDataComponent tc) {
   super();
   myComponent = tc;
   myPropComponent.setLabelVisible(false);
   myPropComponent.setBorder(null);
   try {
     jbInit();
   }
   catch (Exception e) {
     e.printStackTrace();
   }
 }


 public Component getTableCellRendererComponent(JTable table, Object value,
                                                boolean isSelected,
                                                boolean hasFocus, int row,
                                                int column) {
   setForeground(Color.black);
//   System.err.println("Column: "+column);
   MessageTable mm = (MessageTable)table;



   Operand val = (Operand)aggregateValueMap.get(new Integer(column));
   
//     setText(""+val.value);
//     return this;
//   if(mm.isShowingRowHeaders()) {
//	   column--;
//   }
   myPropComponent.setComponentBorder(null);
   if (val!=null) {
      setupProp(mm,val,column);
     return myPropComponent;
   }

   String expr = (String)aggregateMap.get(new Integer(column-1));
   if (expr==null) {
     setText("");
     return this;
   } else {
    try {
      Operand o = myComponent.getContext().evaluate(expr,myComponent,null,mm.getMessage());
      if (o==null) {
        setText("null");
      } else {
        setupProp(mm,o,column);
        aggregateValueMap.put(new Integer(column),o);
        return myPropComponent;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      setForeground(Color.red);
      setText("error");
    }
   }


   return this;
 }

 private final void setupProp(MessageTable mm, Operand val, int column) {
   if (val!=null) {
   Property p = null;
   try {
     p = NavajoFactory.getInstance().createProperty(null,
         mm.getColumnId(column), val.type, "" + val.value, 0,
         mm.getColumnName(column), Property.DIR_OUT);
   }
   catch (NavajoException ex1) {
     ex1.printStackTrace();
   }
    myPropComponent.setProperty(p);
  //  myPropComponent
//    System.err.println("TYPE: "+p.getType()+" name: "+p.getName()+" value: "+p.getValue());
   }
 }

 private final void jbInit() throws Exception {
   this.setHorizontalAlignment(SwingConstants.LEADING);
   this.setHorizontalTextPosition(SwingConstants.LEADING);
 }

 private final Map aggregateMap = new HashMap();
 private final Map aggregateValueMap = new HashMap();

 public void addAggregate(int columnIndex, String expression) {
   aggregateMap.put(new Integer(columnIndex),expression);
 }

 public void flushAggregateValues() {
   aggregateValueMap.clear();
 }


 public void removeAggregate(int columnIndex) {
   aggregateMap.remove(new Integer(columnIndex));
 }

 public void removeAllAggregate() {
   aggregateMap.clear();
 }

 public String getAggregateFunction(int column) {
   return (String)aggregateMap.get(new Integer(column));
 }

 public Set getAggregateFunctions() {
   return aggregateMap.keySet();
 }
// public void setColumnIndex(int columnIndex) {
//   this.columnIndex = columnIndex;
// }
//
// public int getColumnIndex() {
//   return columnIndex;
//
// }


}
