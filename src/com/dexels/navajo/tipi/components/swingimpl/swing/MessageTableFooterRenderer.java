package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingclient.components.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */


public class MessageTableFooterRenderer extends JLabel
   implements TableCellRenderer {
  private final TipiDataComponent myComponent;

 private GenericPropertyComponent myPropComponent = new GenericPropertyComponent();

 private boolean initialized = false;
 private final Map<Integer,String> aggregateMap = new HashMap<Integer,String>();
 private final Map<Integer,Operand> aggregateValueMap = new HashMap<Integer,Operand>();

 public MessageTableFooterRenderer(TipiDataComponent tc) {
   super();
   myComponent = tc;
   setBorder(null);
   setOpaque(false);
 }


 public Component getTableCellRendererComponent(JTable table, Object value,
                                                boolean isSelected,
                                                boolean hasFocus, int row,
                                                int column) {

//	 if(true) {
//		 return new JLabel("MonkeyMonkey");
//	 }
	 
	 if (!initialized) {
			myPropComponent.setLabelVisible(false);
//			myPropComponent.setBorder(null);
			try {
//				myPropComponent.setHorizontalAlignment(SwingConstants.LEADING);
//				myPropComponent.setHorizontalTextPosition(SwingConstants.LEADING);
				initialized = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 myPropComponent.setForeground(Color.black);
//   setBackground(Color.blue);
	 myPropComponent.hideBorder();
	 myPropComponent.setOpaque(false);
   MessageTable mm = (MessageTable)table;
//   Message cc = myComponent.getStateMessage().getMessage("Columns");
//   try {
//   myComponent.getStateMessage().write(System.err);
//   } catch(Exception e) {
//	   e.printStackTrace();
//   }
//   System.err.println("ColumnIndex:"+column+"value: "+value);
   
//   Message me = cc.getMessage(column);

//  Property name = me.getProperty("Name");
//   JLabel ll = new JLabel((String) name.getTypedValue());
//   if(true) {
//   return ll;
//   }
   Operand val = aggregateValueMap.get(new Integer(column));
   
//   Property aggr = me.getProperty("Aggregate");
//   if(aggr==null) {
//	   setText("Oempaloempa");
//	   return this;
//   }
//     setText(""+val.value);
//     return this;
//   if(mm.isShowingRowHeaders()) {
//	   column--;
//   }
//   myPropComponent.setComponentBorder(BorderFactory.createEmptyBorder(2,2,2,2));
//   myPropComponent.setProperty(aggr);
   
   String expr = getAggregateFunction(column);
//   String expr = (String) aggr.getTypedValue();
//   System.err.println("Column: "+column+" expre: "+expr);

   if (expr==null) {
	   setupProp(mm, new Operand(null,"String",null), column);
//     setText("");
//	  return new JLabel("aap"); 
     return myPropComponent;
   } else {
    try {
      Operand o = myComponent.getContext().evaluate(expr,myComponent,null,mm.getMessage());
      if (o==null) {
        setText("null");
        return this;
      } else {
        setupProp(mm,o,column);
        aggregateValueMap.put(new Integer(column),o);
        myPropComponent.hideBorder();
    	return myPropComponent;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      setForeground(Color.red);
      setText("error");
      return this;
    }
   }

  }

 private final void setupProp(MessageTable mm, Operand val, int column) {
   if (val!=null) {
   Property p = null;
   try {
     p = NavajoFactory.getInstance().createProperty(null,
         mm.getColumnId(column), val.type, "" , 0,
         mm.getColumnName(column), Property.DIR_OUT);
     p.setAnyValue(val.value);
   }
   catch (NavajoException ex1) {
     ex1.printStackTrace();
   }
    myPropComponent.setProperty(p);
  //  myPropComponent
//    System.err.println("TYPE: "+p.getType()+" name: "+p.getName()+" value: "+p.getValue());
   }
 }


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
  return aggregateMap.get(new Integer(column));
 }

 public Set<Integer> getAggregateFunctions() {
   return aggregateMap.keySet();
 }
}
